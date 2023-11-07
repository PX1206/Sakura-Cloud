package com.sakura.user.controller;

import com.sakura.user.entity.Merchant;
import com.sakura.user.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantPageParam;
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
 * 商户表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchant")
@Module("user")
@Api(value = "商户API", tags = {"商户管理"})
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    /**
     * 添加商户表
     */
    @PostMapping("/applySettled")
    @OperationLog(name = "入驻申请", type = OperationLogType.ADD)
    @ApiOperation(value = "入驻申请", response = ApiResult.class)
    public ApiResult<Boolean>applySettled(@Validated(Add.class) @RequestBody Merchant merchant) throws Exception {
        boolean flag = merchantService.saveMerchant(merchant);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchant(@Validated(Update.class) @RequestBody Merchant merchant) throws Exception {
        boolean flag = merchantService.updateMerchant(merchant);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchant(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantService.deleteMerchant(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户表详情", response = Merchant.class)
    public ApiResult<Merchant> getMerchant(@PathVariable("id") Long id) throws Exception {
        Merchant merchant = merchantService.getById(id);
        return ApiResult.ok(merchant);
    }

    /**
     * 商户表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户表分页列表", response = Merchant.class)
    public ApiResult<Paging<Merchant>> getMerchantPageList(@Validated @RequestBody MerchantPageParam merchantPageParam) throws Exception {
        Paging<Merchant> paging = merchantService.getMerchantPageList(merchantPageParam);
        return ApiResult.ok(paging);
    }

}

