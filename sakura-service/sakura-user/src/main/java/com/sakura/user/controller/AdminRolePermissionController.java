package com.sakura.user.controller;

import com.sakura.user.entity.AdminRolePermission;
import com.sakura.user.service.AdminRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.AdminRolePermissionPageParam;
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
 * admin角色权限表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/adminRolePermission")
@Module("user")
@Api(value = "admin角色权限表API", tags = {"admin角色权限表"})
public class AdminRolePermissionController extends BaseController {

    @Autowired
    private AdminRolePermissionService adminRolePermissionService;

    /**
     * 添加admin角色权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加admin角色权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加admin角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> addAdminRolePermission(@Validated(Add.class) @RequestBody AdminRolePermission adminRolePermission) throws Exception {
        boolean flag = adminRolePermissionService.saveAdminRolePermission(adminRolePermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改admin角色权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改admin角色权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改admin角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> updateAdminRolePermission(@Validated(Update.class) @RequestBody AdminRolePermission adminRolePermission) throws Exception {
        boolean flag = adminRolePermissionService.updateAdminRolePermission(adminRolePermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除admin角色权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除admin角色权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除admin角色权限表", response = ApiResult.class)
    public ApiResult<Boolean> deleteAdminRolePermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = adminRolePermissionService.deleteAdminRolePermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取admin角色权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "admin角色权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "admin角色权限表详情", response = AdminRolePermission.class)
    public ApiResult<AdminRolePermission> getAdminRolePermission(@PathVariable("id") Long id) throws Exception {
        AdminRolePermission adminRolePermission = adminRolePermissionService.getById(id);
        return ApiResult.ok(adminRolePermission);
    }

    /**
     * admin角色权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "admin角色权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "admin角色权限表分页列表", response = AdminRolePermission.class)
    public ApiResult<Paging<AdminRolePermission>> getAdminRolePermissionPageList(@Validated @RequestBody AdminRolePermissionPageParam adminRolePermissionPageParam) throws Exception {
        Paging<AdminRolePermission> paging = adminRolePermissionService.getAdminRolePermissionPageList(adminRolePermissionPageParam);
        return ApiResult.ok(paging);
    }

}

