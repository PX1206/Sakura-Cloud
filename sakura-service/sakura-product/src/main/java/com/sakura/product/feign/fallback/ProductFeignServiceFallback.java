package com.sakura.product.feign.fallback;

import com.sakura.common.api.ApiResult;
import com.sakura.product.feign.ProductFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sakura
 * @date 2023/7/27 17:30
 */
@Component
@Slf4j
public class ProductFeignServiceFallback implements ProductFeignService {

    @GetMapping("/getUnitPrice/{productNo}")
    public ApiResult<Integer> getUnitPrice(@PathVariable("productNo") String productNo) {
        log.info("Product服务getUnitPrice服务降级了+++++++++");
        return ApiResult.ok(0);
    }
}
