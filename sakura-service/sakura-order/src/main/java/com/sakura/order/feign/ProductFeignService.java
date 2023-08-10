package com.sakura.order.feign;

import com.sakura.common.api.ApiResult;
import com.sakura.order.feign.fallback.ProductFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: stock服务接口
 * @author: Sakura
 * @date: 2023/7/20 14:38
 */
@FeignClient(name = "product-service", path = "/product", fallback = ProductFeignServiceFallback.class)
public interface ProductFeignService {

    @GetMapping("/getUnitPrice/{productNo}")
    ApiResult<Integer> getUnitPrice(@PathVariable("productNo") String productNo);
}
