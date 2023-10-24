package com.sakura.stock.controller;

import com.sakura.stock.entity.Stock;
import com.sakura.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.stock.param.StockPageParam;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.pagination.Paging;
import com.sakura.common.api.IdParam;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.api.Add;
import com.sakura.common.api.Update;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 库存表 控制器
 *
 * @author Sakura
 * @since 2023-10-23
 */
@Slf4j
@RestController
@RequestMapping("/stock")
@Module("stock")
@Api(value = "库存表API", tags = {"库存表"})
public class StockController extends BaseController {

    @Autowired
    private StockService stockService;

    /**
     * 添加库存表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加库存表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加库存表", response = ApiResult.class)
    public ApiResult<Boolean> addStock(@Validated(Add.class) @RequestBody Stock stock) throws Exception {
        boolean flag = stockService.saveStock(stock);
        return ApiResult.result(flag);
    }

    /**
     * 修改库存表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改库存表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改库存表", response = ApiResult.class)
    public ApiResult<Boolean> updateStock(@Validated(Update.class) @RequestBody Stock stock) throws Exception {
        boolean flag = stockService.updateStock(stock);
        return ApiResult.result(flag);
    }

    /**
     * 删除库存表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除库存表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除库存表", response = ApiResult.class)
    public ApiResult<Boolean> deleteStock(@PathVariable("id") Long id) throws Exception {
        boolean flag = stockService.deleteStock(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取库存表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "库存表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "库存表详情", response = Stock.class)
    public ApiResult<Stock> getStock(@PathVariable("id") Long id) throws Exception {
        Stock stock = stockService.getById(id);
        return ApiResult.ok(stock);
    }

    /**
     * 库存表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "库存表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "库存表分页列表", response = Stock.class)
    public ApiResult<Paging<Stock>> getStockPageList(@Validated @RequestBody StockPageParam stockPageParam) throws Exception {
        Paging<Stock> paging = stockService.getStockPageList(stockPageParam);
        return ApiResult.ok(paging);
    }

}

