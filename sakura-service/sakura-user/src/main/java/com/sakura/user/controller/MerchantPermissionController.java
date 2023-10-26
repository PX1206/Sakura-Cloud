package com.sakura.user.controller;

import com.sakura.user.entity.MerchantPermission;
import com.sakura.user.service.MerchantPermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantPermissionPageParam;
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
 * 商户权限 控制器
 * 100001为默认商户，此权限为所有商户共有权限
 * 商户也可以分配独有权限，比如一些定制开发功能，收费板块功能
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantPermission")
@Module("user")
@Api(value = "商户权限表API", tags = {"商户权限表"})
public class MerchantPermissionController extends BaseController {

    @Autowired
    private MerchantPermissionService merchantPermissionService;

    /**
     * 添加商户权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户权限表", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantPermission(@Validated(Add.class) @RequestBody MerchantPermission merchantPermission) throws Exception {
        boolean flag = merchantPermissionService.saveMerchantPermission(merchantPermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户权限表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantPermission(@Validated(Update.class) @RequestBody MerchantPermission merchantPermission) throws Exception {
        boolean flag = merchantPermissionService.updateMerchantPermission(merchantPermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户权限表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantPermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantPermissionService.deleteMerchantPermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户权限表详情", response = MerchantPermission.class)
    public ApiResult<MerchantPermission> getMerchantPermission(@PathVariable("id") Long id) throws Exception {
        MerchantPermission merchantPermission = merchantPermissionService.getById(id);
        return ApiResult.ok(merchantPermission);
    }

    /**
     * 商户权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户权限表分页列表", response = MerchantPermission.class)
    public ApiResult<Paging<MerchantPermission>> getMerchantPermissionPageList(@Validated @RequestBody MerchantPermissionPageParam merchantPermissionPageParam) throws Exception {
        Paging<MerchantPermission> paging = merchantPermissionService.getMerchantPermissionPageList(merchantPermissionPageParam);
        return ApiResult.ok(paging);
    }

}

