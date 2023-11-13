package com.sakura.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.common.api.ApiCode;
import com.sakura.common.api.ApiResult;
import com.sakura.common.constant.RocketMqConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.order.entity.Order;
import com.sakura.order.feign.ProductFeignService;
import com.sakura.order.feign.StockFeignService;
import com.sakura.order.mapper.OrderMapper;
import com.sakura.order.param.AddOrderParam;
import com.sakura.order.service.OrderService;
import com.sakura.order.param.OrderPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.order.utils.OrderUtil;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.http.client.utils.DateUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 订单表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-07
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    StockFeignService stockFeignService;
    @Autowired
    private OrderUtil orderUtil;

    @Override
    @GlobalLock
    @GlobalTransactional
    @Trace
    @Tags({
            @Tag(key = "addOrder", value = "returnedObj"),
            @Tag(key = "addOrder", value = "arg[0]")
    })
    public boolean saveOrder(AddOrderParam addOrderParam) throws Exception {
        // 通过redis防止重复提交
        if (redisUtil.sHasKey("order-add", "10001")) {
            throw new BusinessException(500, "重复提交");
        }
        redisUtil.sSetAndTime("order-add", 1, "10001");

        orderUtil.Ayn();


        // 先去查询商品库存信息
        // 优先去Redis里面去查，通过increment保证线程安全，如果Redis里面的库存显示不足则去刷新数据库库存
        long stockNum = redisUtil.decr("stock-num" + addOrderParam.getProductNo(), addOrderParam.getNum());
        // 如果Redis库存为空则去数据库获取新的库存信息
        if (stockNum < 1) {
            // 通过Redisson配置分布式全局锁保证数据安全性
            RLock stockLock = redissonClient.getLock("GetProductNum" + addOrderParam.getProductNo());
            try {
                //尝试加锁, 最多等待3秒, 10秒后自动解锁
                boolean getStockLock = stockLock.tryLock(3, 10, TimeUnit.SECONDS);
                if (getStockLock) {
                    ApiResult<Integer> apiResultNum = stockFeignService.getProductNum(addOrderParam.getProductNo());
                    log.info("商品库存数量：" + apiResultNum.toString());
                    if (apiResultNum.getCode() != 200 || apiResultNum.getData() < 1 || apiResultNum.getData() < addOrderParam.getNum()) {
                        throw new BusinessException(500, "商品库存不足");
                    }
                    redisUtil.set("stock-num" + addOrderParam.getProductNo(), apiResultNum.getData() - addOrderParam.getNum());
                }
            } catch (Exception e) {
                log.error("新增订单查询商品库存异常：" + e.getMessage(), e);
                throw new BusinessException(500, "查询商品库存信息异常");
            } finally {
                //解锁
                if (stockLock.isLocked() && stockLock.isHeldByCurrentThread()) {
                    stockLock.unlock();
                }
            }
        }

        // 去商品服务获取商品单价
        ApiResult<Integer> apiResultUnitPrice = productFeignService.getUnitPrice(addOrderParam.getProductNo());
        log.info("商品单价：" + apiResultUnitPrice.toString());
        if (apiResultUnitPrice.getCode() != 200 || apiResultUnitPrice.getData() < 0) {
            throw new BusinessException(500, "商品价格异常");
        }

        // 通过MQ异步去处理扣减库存操作，加快下单响应速度
        rocketMQTemplate.asyncSend(RocketMqConstant.REDUCE_STOCK_TOPIC, JSON.toJSONString(addOrderParam), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("推送扣减库存消息成功：" + JSON.toJSONString(addOrderParam));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("推送扣减库存消息失败：" + JSON.toJSONString(addOrderParam) + "------>" + throwable.getMessage());
            }
        });

        Order order = new Order();
        // 根据当前日期加随机数生成一个订单号
        order.setOrderNo(DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS")
                + (int) ((Math.random() * 9 + 1) * 10000000));
        order.setProductNo(addOrderParam.getProductNo());
        order.setNum(addOrderParam.getNum());
        order.setTotalPrice(addOrderParam.getNum() * apiResultUnitPrice.getData());
        order.setStatus(1);
        orderMapper.insert(order);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateOrder(Order order) throws Exception {
        return super.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteOrder(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Order> getOrderPageList(OrderPageParam orderPageParam) throws Exception {
        Page<Order> page = new PageInfo<>(orderPageParam, OrderItem.desc(getLambdaColumn(Order::getCreateDt)));
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        IPage<Order> iPage = orderMapper.selectPage(page, wrapper);
        return new Paging<Order>(iPage);
    }

}
