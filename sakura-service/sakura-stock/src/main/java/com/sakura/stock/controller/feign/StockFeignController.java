package com.sakura.stock.controller.feign;

import com.sakura.common.api.ApiResult;
import com.sakura.common.base.BaseController;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.OperationLog;
import com.sakura.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 库存表 控制器
 *
 * @author Sakura
 * @since 2023-08-24
 */
@Slf4j
@RestController
@RequestMapping("/feign/stock")
public class StockFeignController extends BaseController {

    @Autowired
    private StockService stockService;

    /**
     * 获取商品库存数量
     */
    @GetMapping("/getProductNum/{productNo}")
    @OperationLog(name = "获取商品库存数量", type = OperationLogType.query)
    public ApiResult<Integer> getProductNum(@PathVariable("productNo") String productNo) throws Exception {
        Integer num = stockService.getProductNum(productNo);
        return ApiResult.ok(num);
    }
}

