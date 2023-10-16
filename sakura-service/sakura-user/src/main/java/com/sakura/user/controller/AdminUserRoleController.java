package com.sakura.user.controller;

import com.sakura.user.entity.AdminUserRole;
import com.sakura.user.service.AdminUserRoleService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.AdminUserRolePageParam;
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
 * admin用户角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/adminUserRole")
@Module("user")
@Api(value = "admin用户角色表API", tags = {"admin用户角色表"})
public class AdminUserRoleController extends BaseController {

    @Autowired
    private AdminUserRoleService adminUserRoleService;

    /**
     * 添加admin用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加admin用户角色表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加admin用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> addAdminUserRole(@Validated(Add.class) @RequestBody AdminUserRole adminUserRole) throws Exception {
        boolean flag = adminUserRoleService.saveAdminUserRole(adminUserRole);
        return ApiResult.result(flag);
    }

    /**
     * 修改admin用户角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改admin用户角色表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改admin用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> updateAdminUserRole(@Validated(Update.class) @RequestBody AdminUserRole adminUserRole) throws Exception {
        boolean flag = adminUserRoleService.updateAdminUserRole(adminUserRole);
        return ApiResult.result(flag);
    }

    /**
     * 删除admin用户角色表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除admin用户角色表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除admin用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> deleteAdminUserRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = adminUserRoleService.deleteAdminUserRole(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取admin用户角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "admin用户角色表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "admin用户角色表详情", response = AdminUserRole.class)
    public ApiResult<AdminUserRole> getAdminUserRole(@PathVariable("id") Long id) throws Exception {
        AdminUserRole adminUserRole = adminUserRoleService.getById(id);
        return ApiResult.ok(adminUserRole);
    }

    /**
     * admin用户角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "admin用户角色表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "admin用户角色表分页列表", response = AdminUserRole.class)
    public ApiResult<Paging<AdminUserRole>> getAdminUserRolePageList(@Validated @RequestBody AdminUserRolePageParam adminUserRolePageParam) throws Exception {
        Paging<AdminUserRole> paging = adminUserRoleService.getAdminUserRolePageList(adminUserRolePageParam);
        return ApiResult.ok(paging);
    }

}

