package com.sakura.product.controller;

import com.sakura.product.entity.Product;
import com.sakura.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.product.param.ProductPageParam;
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
 * 商品表 控制器
 *
 * @author Sakura
 * @since 2023-10-23
 */
@Slf4j
@RestController
@RequestMapping("/product")
@Module("product")
@Api(value = "商品表API", tags = {"商品表"})
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 添加商品表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商品表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商品表", response = ApiResult.class)
    public ApiResult<Boolean> addProduct(@Validated(Add.class) @RequestBody Product product) throws Exception {
        boolean flag = productService.saveProduct(product);
        return ApiResult.result(flag);
    }

    /**
     * 修改商品表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商品表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商品表", response = ApiResult.class)
    public ApiResult<Boolean> updateProduct(@Validated(Update.class) @RequestBody Product product) throws Exception {
        boolean flag = productService.updateProduct(product);
        return ApiResult.result(flag);
    }

    /**
     * 删除商品表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商品表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商品表", response = ApiResult.class)
    public ApiResult<Boolean> deleteProduct(@PathVariable("id") Long id) throws Exception {
        boolean flag = productService.deleteProduct(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商品表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商品表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商品表详情", response = Product.class)
    public ApiResult<Product> getProduct(@PathVariable("id") Long id) throws Exception {
        Product product = productService.getById(id);
        return ApiResult.ok(product);
    }

    /**
     * 商品表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商品表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商品表分页列表", response = Product.class)
    public ApiResult<Paging<Product>> getProductPageList(@Validated @RequestBody ProductPageParam productPageParam) throws Exception {
        Paging<Product> paging = productService.getProductPageList(productPageParam);
        return ApiResult.ok(paging);
    }

}

