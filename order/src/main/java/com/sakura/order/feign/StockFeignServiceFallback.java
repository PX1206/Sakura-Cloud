package com.sakura.order.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Sakura
 * @date 2023/7/27 17:30
 */
@Component
public class StockFeignServiceFallback implements StockFeignService {

    @Override
    public String reduct() {
        // 我们可以在这里处理降级逻辑，比如记录日志或者发短信提示管理用户等
        return "stock服务异常降级";
    }

    @Override
    public Integer getProductNum(@PathVariable("productNo") String productNo) {
        return 0;
    }
}
