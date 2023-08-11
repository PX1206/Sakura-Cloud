package com.sakura.order.service.impl;

import com.sakura.common.api.ApiCode;
import com.sakura.common.api.ApiResult;
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
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.http.client.utils.DateUtils;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
    ProductFeignService productFeignService;
    @Autowired
    StockFeignService stockFeignService;

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
        redisUtil.sSetAndTime("order-add", 3, "10001");
        // 先去查询商品库存信息
        ApiResult<Integer> apiResultNum = stockFeignService.getProductNum(addOrderParam.getProductNo());
        log.info("商品库存数量：" + apiResultNum.toString());
        if (apiResultNum.getCode() != 200 || apiResultNum.getData() < 1 || apiResultNum.getData() < addOrderParam.getNum()) {
            throw new BusinessException(500, "商品库存不足");
        }
        Order order = new Order();
        // 根据当前日期加随机数生成一个订单号
        order.setOrderNo(DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS")
                + (int) ((Math.random() * 9 + 1) * 10000000));
        order.setProductNo(addOrderParam.getProductNo());
        order.setNum(addOrderParam.getNum());
        // 去商品服务获取商品单价
        ApiResult<Integer> apiResultUnitPrice = productFeignService.getUnitPrice(addOrderParam.getProductNo());
        log.info("商品单价：" + apiResultUnitPrice.toString());
        if (apiResultUnitPrice.getCode() != 200 || apiResultUnitPrice.getData() < 0) {
            throw new BusinessException(500, "商品价格异常");
        }
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
