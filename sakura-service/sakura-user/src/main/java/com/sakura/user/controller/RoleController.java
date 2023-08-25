package com.sakura.user.controller;

import com.sakura.user.entity.Role;
import com.sakura.user.param.DeleteRoleParam;
import com.sakura.user.param.RoleParam;
import com.sakura.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.RolePageParam;
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
 * 角色表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Module("user")
@Api(value = "角色管理", tags = {"角色管理"})
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色", response = ApiResult.class)
    public ApiResult<Boolean> addRole(@Validated(Add.class) @RequestBody RoleParam roleParam) throws Exception {
        boolean flag = roleService.saveRole(roleParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改角色", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改角色", response = ApiResult.class)
    public ApiResult<Boolean> updateRole(@Validated(Update.class) @RequestBody RoleParam roleParam) throws Exception {
        boolean flag = roleService.updateRole(roleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteRole(@RequestBody DeleteRoleParam deleteRoleParam) throws Exception {
        boolean flag = roleService.deleteRole(deleteRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "角色表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "角色表详情", response = Role.class)
    public ApiResult<Role> getRole(@PathVariable("id") Long id) throws Exception {
        Role role = roleService.getById(id);
        return ApiResult.ok(role);
    }

    /**
     * 角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "角色表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "角色表分页列表", response = Role.class)
    public ApiResult<Paging<Role>> getRolePageList(@Validated @RequestBody RolePageParam rolePageParam) throws Exception {
        Paging<Role> paging = roleService.getRolePageList(rolePageParam);
        return ApiResult.ok(paging);
    }

}

