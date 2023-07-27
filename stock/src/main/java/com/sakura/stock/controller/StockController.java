package com.sakura.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sakura
 * @date 2023/7/19 11:35
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct(){
        // 抛异常测试openfeign整合sentinel熔断降级
        int a = 1/0;
        System.out.println("扣减库存");
        return "扣减库存" + port;
    }

}
