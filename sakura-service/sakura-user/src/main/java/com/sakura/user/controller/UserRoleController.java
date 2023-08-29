package com.sakura.user.controller;

import com.sakura.user.param.UserRoleParam;
import com.sakura.user.service.UserRoleService;
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
 * 用户角色表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/userRole")
@Module("user")
@Api(value = "用户角色管理", tags = {"用户角色管理"})
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addUserRole(@Validated(Add.class) @RequestBody UserRoleParam userRoleParam) throws Exception {
        boolean flag = userRoleService.saveUserRole(userRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除用户角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteUserRole(@RequestBody UserRoleParam userRoleParam) throws Exception {
        boolean flag = userRoleService.deleteUserRole(userRoleParam);
        return ApiResult.result(flag);
    }

}

