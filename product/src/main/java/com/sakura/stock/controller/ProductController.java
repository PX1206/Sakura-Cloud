package com.sakura.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sakura
 * @date 2023/7/19 11:35
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/get/{id}")
    public String get(@PathVariable("id") Integer id) throws Exception {
        System.out.println("查询商品信息" + id);
        Thread.sleep(5000);
        return "查询商品信息" + id + "-" + port;
    }

}
