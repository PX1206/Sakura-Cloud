package com.sakura.user.controller;

import com.sakura.user.param.AdminUserRoleParam;
import com.sakura.user.service.AdminUserRoleService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.api.Add;
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
@Api(value = "admin用户角色API", tags = {"admin用户角色管理"})
public class AdminUserRoleController extends BaseController {

    @Autowired
    private AdminUserRoleService adminUserRoleService;

    /**
     * 添加用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addAdminUserRole(@Validated(Add.class) @RequestBody AdminUserRoleParam adminUserRoleParam) throws Exception {
        boolean flag = adminUserRoleService.saveAdminUserRole(adminUserRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除用户角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteAdminUserRole(@RequestBody AdminUserRoleParam adminUserRoleParam) throws Exception {
        boolean flag = adminUserRoleService.deleteAdminUserRole(adminUserRoleParam);
        return ApiResult.result(flag);
    }

}

