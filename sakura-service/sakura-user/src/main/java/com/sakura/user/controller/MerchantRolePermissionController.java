package com.sakura.user.controller;

import com.sakura.user.param.MerchantRolePermissionParam;
import com.sakura.user.service.MerchantRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.common.enums.OperationLogType;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
     * 添加角色权限
     */
    @PostMapping("/save")
    @OperationLog(name = "保存角色权限", type = OperationLogType.ADD)
    @ApiOperation(value = "保存角色权限", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantRolePermission(@Validated @RequestBody MerchantRolePermissionParam merchantRolePermissionParam) throws Exception {
        boolean flag = merchantRolePermissionService.addMerchantRolePermission(merchantRolePermissionParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色权限ID
     */
    @GetMapping("/getMerchantRolePermissionId/{roleId}")
    @OperationLog(name = "获取角色权限ID", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取角色权限ID", response = ApiResult.class)
    public ApiResult<Set<Integer>> getMerchantRolePermissionId(@PathVariable("roleId") Integer roleId) throws Exception {
        Set<Integer> permissionIds = merchantRolePermissionService.getMerchantRolePermissionId(roleId);
        return ApiResult.ok(permissionIds);
    }

}

