package com.sakura.order.controller;

import com.sakura.order.feign.ProductFeignService;
import com.sakura.order.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sakura
 * @date 2023/7/19 11:25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    StockFeignService stockFeignService;
    @Autowired
    ProductFeignService productFeignService;

    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功");
        String msg = stockFeignService.reduct();
        String productMsg = productFeignService.get(1);
        return "下单成功" + msg + "-" + productMsg;
    }

}
