package com.sakura.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: stock服务接口
 * @author: Sakura
 * @date: 2023/7/20 14:38
 */
@FeignClient(name = "product-service", path = "/product")
public interface ProductFeignService {

    @RequestMapping("/get/{id}")
    String get(@PathVariable("id") Integer id);

    @GetMapping("/getUnitPrice/{productNo}")
    Integer getUnitPrice(@PathVariable("productNo") String productNo);
}
