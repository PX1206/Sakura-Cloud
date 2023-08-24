package com.sakura.product.controller.feign;

import com.sakura.common.api.ApiResult;
import com.sakura.common.base.BaseController;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.OperationLog;
import com.sakura.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品表 控制器
 *
 * @author Sakura
 * @since 2023-08-24
 */
@Slf4j
@RestController
@RequestMapping("/feign/product")
public class ProductFeignController extends BaseController {

    @Autowired
    private ProductService productService;


    /**
     * 商品表分页列表
     */
    @GetMapping("/getUnitPrice/{productNo}")
    @OperationLog(name = "获取商品单价", type = OperationLogType.query)
    public ApiResult<Integer> getUnitPrice(@PathVariable("productNo") String productNo) throws Exception {
        Integer unitPrice = productService.getUnitPrice(productNo);
        return ApiResult.ok(unitPrice);
    }

}

