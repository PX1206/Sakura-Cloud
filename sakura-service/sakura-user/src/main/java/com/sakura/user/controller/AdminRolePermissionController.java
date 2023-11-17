package com.sakura.user.controller;

import com.sakura.user.param.AdminRolePermissionParam;
import com.sakura.user.service.AdminRolePermissionService;
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
 * admin角色权限表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/adminRolePermission")
@Module("user")
@Api(value = "admin角色权限API", tags = {"admin角色权限管理"})
public class AdminRolePermissionController extends BaseController {

    @Autowired
    private AdminRolePermissionService adminRolePermissionService;

    /**
     * 添加角色权限
     */
    @PostMapping("/save")
    @OperationLog(name = "保存角色权限", type = OperationLogType.ADD)
    @ApiOperation(value = "保存角色权限", response = ApiResult.class)
    public ApiResult<Boolean> addAdminRolePermission(@Validated @RequestBody AdminRolePermissionParam adminRolePermissionParam) throws Exception {
        boolean flag = adminRolePermissionService.addAdminRolePermission(adminRolePermissionParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色权限ID
     */
    @GetMapping("/getAdminRolePermissionId/{roleId}")
    @OperationLog(name = "获取角色权限ID", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取角色权限ID", response = ApiResult.class)
    public ApiResult<Set<Integer>> getAdminRolePermissionId(@PathVariable("roleId") Integer roleId) throws Exception {
        Set<Integer> permissionIds = adminRolePermissionService.getAdminRolePermissionId(roleId);
        return ApiResult.ok(permissionIds);
    }

}

