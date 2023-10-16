package com.sakura.order.controller;

import com.sakura.order.entity.Order;
import com.sakura.order.param.AddOrderParam;
import com.sakura.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.order.param.OrderPageParam;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.pagination.Paging;
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
 * 订单表 控制器
 *
 * @author Sakura
 * @since 2023-08-07
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Module("order")
@Api(value = "订单表API", tags = {"订单表"})
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 添加订单表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加订单表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加订单表", response = ApiResult.class)
    public ApiResult<Boolean> addOrder(@Validated(Add.class) @RequestBody AddOrderParam addOrderParam) throws Exception {
        boolean flag = orderService.saveOrder(addOrderParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改订单表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改订单表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改订单表", response = ApiResult.class)
    public ApiResult<Boolean> updateOrder(@Validated(Update.class) @RequestBody Order order) throws Exception {
        boolean flag = orderService.updateOrder(order);
        return ApiResult.result(flag);
    }

    /**
     * 删除订单表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除订单表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除订单表", response = ApiResult.class)
    public ApiResult<Boolean> deleteOrder(@PathVariable("id") Long id) throws Exception {
        boolean flag = orderService.deleteOrder(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取订单表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "订单表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "订单表详情", response = Order.class)
    public ApiResult<Order> getOrder(@PathVariable("id") Long id) throws Exception {
        Order order = orderService.getById(id);
        return ApiResult.ok(order);
    }

    /**
     * 订单表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "订单表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "订单表分页列表", response = Order.class)
    public ApiResult<Paging<Order>> getOrderPageList(@Validated @RequestBody OrderPageParam orderPageParam) throws Exception {
        Paging<Order> paging = orderService.getOrderPageList(orderPageParam);
        return ApiResult.ok(paging);
    }

}

