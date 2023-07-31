package com.sakura.order.service.impl;

import com.sakura.order.entify.Order;
import com.sakura.order.feign.ProductFeignService;
import com.sakura.order.feign.StockFeignService;
import com.sakura.order.mapper.OrderMapper;
import com.sakura.order.param.AddOrderParam;
import com.sakura.order.service.OrderService;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.java.Log;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Sakura
 * @date 2023/7/28 16:18
 */
@Service
@Log
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    StockFeignService stockFeignService;

    @Override
    @GlobalLock
    @GlobalTransactional
    public String addOrder(AddOrderParam addOrderParam) {

        Order order1 = new Order();
        order1.setOrderNo("6341341");
        orderMapper.insert(order1);

        // 先去查询商品库存信息
        Integer num = stockFeignService.getProductNum(addOrderParam.getProductNo());
        log.info("商品库存数量：" + num);
        if (num == null || num < 1 || num < addOrderParam.getNum()) {
            return "商品库存不足";
        }
        Order order = new Order();
        // 根据当前日期加随机数生成一个订单号
        order.setOrderNo(DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS")
                + (int) ((Math.random() * 9 + 1) * 10000000));
        order.setProductNo(addOrderParam.getProductNo());
        order.setNum(addOrderParam.getNum());
        // 去商品服务获取商品单价
        Integer unitPrice = productFeignService.getUnitPrice(addOrderParam.getProductNo());
        log.info("商品单价：" + unitPrice);
        if (unitPrice == null || unitPrice < 0) {
            return "商品价格异常";
        }
        order.setTotalPrice(addOrderParam.getNum() * unitPrice);
        order.setStatus(1);
        orderMapper.insert(order);

        return "商品下单成功：" + order.getOrderNo() + " 库存：" + num + " 单价：" + unitPrice;
    }
}
