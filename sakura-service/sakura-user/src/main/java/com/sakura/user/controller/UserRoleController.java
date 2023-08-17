package com.sakura.user.controller;

import com.sakura.user.entity.UserRole;
import com.sakura.user.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.UserRolePageParam;
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
 * 用户角色表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/userRole")
@Module("user")
@Api(value = "用户角色表API", tags = {"用户角色表"})
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户角色表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> addUserRole(@Validated(Add.class) @RequestBody UserRole userRole) throws Exception {
        boolean flag = userRoleService.saveUserRole(userRole);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户角色表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> updateUserRole(@Validated(Update.class) @RequestBody UserRole userRole) throws Exception {
        boolean flag = userRoleService.updateUserRole(userRole);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户角色表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除用户角色表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> deleteUserRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = userRoleService.deleteUserRole(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取用户角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "用户角色表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户角色表详情", response = UserRole.class)
    public ApiResult<UserRole> getUserRole(@PathVariable("id") Long id) throws Exception {
        UserRole userRole = userRoleService.getById(id);
        return ApiResult.ok(userRole);
    }

    /**
     * 用户角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "用户角色表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "用户角色表分页列表", response = UserRole.class)
    public ApiResult<Paging<UserRole>> getUserRolePageList(@Validated @RequestBody UserRolePageParam userRolePageParam) throws Exception {
        Paging<UserRole> paging = userRoleService.getUserRolePageList(userRolePageParam);
        return ApiResult.ok(paging);
    }

}

