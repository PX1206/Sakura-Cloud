package com.sakura.order.feign;

import com.sakura.common.api.ApiResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sakura
 * @date 2023/7/27 17:30
 */
@Component
public class StockFeignServiceFallback implements StockFeignService {

    @Override
    public ApiResult<Integer> getProductNum(@PathVariable("productNo") String productNo) {
        return ApiResult.ok(0);
    }
}
