package com.sakura.product.controller;

import com.sakura.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private ProductService productService;

    @RequestMapping("/get/{id}")
    public String get(@PathVariable("id") Integer id) throws Exception {
        System.out.println("查询商品信息" + id);
        // Thread.sleep(5000);
        return "查询商品信息" + id + "-" + port;
    }

    /**
     * @description: 获取商品单价
     * @param productNo
     * @return [java.lang.String]
     * @author: Sakura
     * @date: 2023/7/28 16:51
     */
    @GetMapping("/getUnitPrice/{productNo}")
    public Integer getUnitPrice(@PathVariable("productNo") String productNo) throws Exception {
        return productService.getUnitPrice(productNo);
    }

}
