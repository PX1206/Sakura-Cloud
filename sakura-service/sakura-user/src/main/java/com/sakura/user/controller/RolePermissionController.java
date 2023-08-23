package com.sakura.user.controller;

import com.sakura.user.entity.RolePermission;
import com.sakura.user.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.RolePermissionPageParam;
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
 * 角色权限表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/rolePermission")
@Module("user")
@Api(value = "角色权限表API", tags = {"角色权限表"})
public class RolePermissionController extends BaseController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 添加角色权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> addRolePermission(@Validated(Add.class) @RequestBody RolePermission rolePermission) throws Exception {
        boolean flag = rolePermissionService.saveRolePermission(rolePermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改角色权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改角色权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> updateRolePermission(@Validated(Update.class) @RequestBody RolePermission rolePermission) throws Exception {
        boolean flag = rolePermissionService.updateRolePermission(rolePermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除角色权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除角色权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> deleteRolePermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = rolePermissionService.deleteRolePermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "角色权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "角色权限表详情", response = RolePermission.class)
    public ApiResult<RolePermission> getRolePermission(@PathVariable("id") Long id) throws Exception {
        RolePermission rolePermission = rolePermissionService.getById(id);
        return ApiResult.ok(rolePermission);
    }

    /**
     * 角色权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "角色权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "角色权限表分页列表", response = RolePermission.class)
    public ApiResult<Paging<RolePermission>> getRolePermissionPageList(@Validated @RequestBody RolePermissionPageParam rolePermissionPageParam) throws Exception {
        Paging<RolePermission> paging = rolePermissionService.getRolePermissionPageList(rolePermissionPageParam);
        return ApiResult.ok(paging);
    }

}

