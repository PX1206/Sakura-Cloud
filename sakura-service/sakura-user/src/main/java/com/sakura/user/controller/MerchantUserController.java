package com.sakura.user.controller;

import com.sakura.user.entity.MerchantUser;
import com.sakura.user.service.MerchantUserService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantUserPageParam;
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
 * 商户用户表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantUser")
@Module("user")
@Api(value = "商户用户表API", tags = {"商户用户表"})
public class MerchantUserController extends BaseController {

    @Autowired
    private MerchantUserService merchantUserService;

    /**
     * 添加商户用户表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户用户表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户用户表", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantUser(@Validated(Add.class) @RequestBody MerchantUser merchantUser) throws Exception {
        boolean flag = merchantUserService.saveMerchantUser(merchantUser);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户用户表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户用户表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantUser(@Validated(Update.class) @RequestBody MerchantUser merchantUser) throws Exception {
        boolean flag = merchantUserService.updateMerchantUser(merchantUser);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户用户表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户用户表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户用户表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantUserService.deleteMerchantUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户用户表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户用户表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户用户表详情", response = MerchantUser.class)
    public ApiResult<MerchantUser> getMerchantUser(@PathVariable("id") Long id) throws Exception {
        MerchantUser merchantUser = merchantUserService.getById(id);
        return ApiResult.ok(merchantUser);
    }

    /**
     * 商户用户表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户用户表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户用户表分页列表", response = MerchantUser.class)
    public ApiResult<Paging<MerchantUser>> getMerchantUserPageList(@Validated @RequestBody MerchantUserPageParam merchantUserPageParam) throws Exception {
        Paging<MerchantUser> paging = merchantUserService.getMerchantUserPageList(merchantUserPageParam);
        return ApiResult.ok(paging);
    }

}

