package com.sakura.order.feign;

import com.sakura.common.api.ApiResult;
import com.sakura.order.feign.fallback.StockFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: stock服务接口
 * @author: Sakura
 * @date: 2023/7/20 14:38
 */
@FeignClient(name = "stock-service", path = "/feign/stock", fallback = StockFeignServiceFallback.class)
public interface StockFeignService {

    @GetMapping("/getProductNum/{productNo}")
    ApiResult<Integer> getProductNum(@PathVariable("productNo") String productNo);
}
