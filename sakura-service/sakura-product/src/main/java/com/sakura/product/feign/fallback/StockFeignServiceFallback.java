package com.sakura.product.feign.fallback;

import com.sakura.common.api.ApiResult;
import com.sakura.product.feign.StockFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sakura
 * @date 2023/7/27 17:30
 */
@Component
@Slf4j
public class StockFeignServiceFallback implements StockFeignService {

    @Override
    public ApiResult<Integer> getProductNum(@PathVariable("productNo") String productNo) {
        log.info("Stock服务getProductNum服务降级了+++++++++");
        return ApiResult.ok(0);
    }
}
