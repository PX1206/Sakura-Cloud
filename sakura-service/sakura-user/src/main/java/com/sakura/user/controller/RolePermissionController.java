package com.sakura.user.controller;

import com.sakura.user.entity.RolePermission;
import com.sakura.user.param.RolePermissionParam;
import com.sakura.user.service.RolePermissionService;
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
 * 角色权限表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/rolePermission")
@Module("user")
@Api(value = "角色权限管理", tags = {"角色权限管理"})
public class RolePermissionController extends BaseController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 添加角色权限
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色权限", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色权限", response = ApiResult.class)
    public ApiResult<Boolean> addRolePermission(@Validated @RequestBody RolePermissionParam rolePermissionParam) throws Exception {
        boolean flag = rolePermissionService.saveRolePermission(rolePermissionParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色权限ID
     */
    @GetMapping("/getRolePermissionId/{roleId}")
    @OperationLog(name = "获取角色权限ID", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取角色权限ID", response = ApiResult.class)
    public ApiResult<Set<Integer>> getRolePermissionId(@PathVariable("roleId") Integer roleId) throws Exception {
        Set<Integer> permissionIds = rolePermissionService.getRolePermissionId(roleId);
        return ApiResult.ok(permissionIds);
    }

}

