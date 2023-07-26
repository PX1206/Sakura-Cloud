package com.sakura.order.controller;

import com.sakura.order.feign.ProductFeignService;
import com.sakura.order.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sakura
 * @date 2023/7/19 11:25
 */
@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Autowired
    StockFeignService stockFeignService;
    @Autowired
    ProductFeignService productFeignService;

    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private String userAge;

    @Value("${user.sex}")
    private String userSex;

    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功");
        String msg = stockFeignService.reduct();
        String productMsg = productFeignService.get(1);
        return userName + userAge + userSex +  "下单成功" + msg + "-" + productMsg;
    }

}
