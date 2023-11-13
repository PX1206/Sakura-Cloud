package com.sakura.user.controller;

import com.sakura.user.entity.Merchant;
import com.sakura.user.param.ApplySettled;
import com.sakura.user.param.DeleteMerchantParam;
import com.sakura.user.param.MerchantParam;
import com.sakura.user.service.MerchantService;
import com.sakura.user.vo.MerchantVo;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantPageParam;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.pagination.Paging;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.common.enums.OperationLogType;
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
    public ApiResult<Boolean>applySettled(@Validated @RequestBody ApplySettled applySettled) throws Exception {
        boolean flag = merchantService.applySettled(applySettled);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户（只有超管可操作）", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchant(@Validated(Update.class) @RequestBody MerchantParam merchantParam) throws Exception {
        boolean flag = merchantService.updateMerchant(merchantParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除商户", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户 admin", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchant(DeleteMerchantParam deleteMerchantParam) throws Exception {
        boolean flag = merchantService.deleteMerchant(deleteMerchantParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取当前登录用户商户详情
     */
    @GetMapping("/info")
    @OperationLog(name = "商户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户详情 merchant", response = MerchantVo.class)
    public ApiResult<MerchantVo> getMerchant() throws Exception {
        MerchantVo merchantVo = merchantService.getMerchant();
        return ApiResult.ok(merchantVo);
    }

    /**
     * 根据商户号获取商户详情
     */
    @GetMapping("/info/{merchantNo}")
    @OperationLog(name = "商户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户详情 admin", response = MerchantVo.class)
    public ApiResult<MerchantVo> getMerchant(@PathVariable("merchantNo") String merchantNo) throws Exception {
        MerchantVo merchantVo = merchantService.getMerchant(merchantNo);
        return ApiResult.ok(merchantVo);
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

