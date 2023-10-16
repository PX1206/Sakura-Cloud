package com.sakura.user.controller;

import com.sakura.user.entity.MerchantRolePermission;
import com.sakura.user.service.MerchantRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantRolePermissionPageParam;
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
 * 商户角色权限表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantRolePermission")
@Module("user")
@Api(value = "商户角色权限表API", tags = {"商户角色权限表"})
public class MerchantRolePermissionController extends BaseController {

    @Autowired
    private MerchantRolePermissionService merchantRolePermissionService;

    /**
     * 添加商户角色权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户角色权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantRolePermission(@Validated(Add.class) @RequestBody MerchantRolePermission merchantRolePermission) throws Exception {
        boolean flag = merchantRolePermissionService.saveMerchantRolePermission(merchantRolePermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户角色权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户角色权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantRolePermission(@Validated(Update.class) @RequestBody MerchantRolePermission merchantRolePermission) throws Exception {
        boolean flag = merchantRolePermissionService.updateMerchantRolePermission(merchantRolePermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户角色权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户角色权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantRolePermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantRolePermissionService.deleteMerchantRolePermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户角色权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户角色权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户角色权限表详情", response = MerchantRolePermission.class)
    public ApiResult<MerchantRolePermission> getMerchantRolePermission(@PathVariable("id") Long id) throws Exception {
        MerchantRolePermission merchantRolePermission = merchantRolePermissionService.getById(id);
        return ApiResult.ok(merchantRolePermission);
    }

    /**
     * 商户角色权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户角色权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户角色权限表分页列表", response = MerchantRolePermission.class)
    public ApiResult<Paging<MerchantRolePermission>> getMerchantRolePermissionPageList(@Validated @RequestBody MerchantRolePermissionPageParam merchantRolePermissionPageParam) throws Exception {
        Paging<MerchantRolePermission> paging = merchantRolePermissionService.getMerchantRolePermissionPageList(merchantRolePermissionPageParam);
        return ApiResult.ok(paging);
    }

}

