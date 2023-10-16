package com.sakura.user.controller;

import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.AdminUser;
import com.sakura.user.param.AddAdminUserParam;
import com.sakura.user.param.LoginParam;
import com.sakura.user.param.UpdateAdminUserParam;
import com.sakura.user.service.AdminUserService;
import com.sakura.user.vo.AdminUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.AdminUserPageParam;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.pagination.Paging;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.common.enums.OperationLogType;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * admin用户表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/adminUser")
@Module("user")
@Api(value = "admin用户管理API", tags = {"admin用户管理"})
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 添加admin用户表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户", response = ApiResult.class)
    public ApiResult<Boolean> addAdminUser(@Validated @RequestBody AddAdminUserParam addAdminUserParam) throws Exception {
        boolean flag = adminUserService.saveAdminUser(addAdminUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改admin用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户信息", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户信息", response = ApiResult.class)
    public ApiResult<Boolean> updateAdminUser(@Validated @RequestBody UpdateAdminUserParam updateAdminUserParam) throws Exception {
        boolean flag = adminUserService.updateAdminUser(updateAdminUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除admin用户表
     */
    @PostMapping("/delete/{userId}")
    @OperationLog(name = "删除用户", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteAdminUser(@PathVariable("userId") String userId) throws Exception {
        boolean flag = adminUserService.deleteAdminUser(userId);
        return ApiResult.result(flag);
    }

    /**
     * 获取admin用户表详情
     */
    @GetMapping("/info")
    @OperationLog(name = "用户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户详情（当前登录用户）", response = AdminUserInfoVo.class)
    public ApiResult<AdminUserInfoVo> getAdminUser() throws Exception {
        AdminUserInfoVo adminUserInfoVo = adminUserService.getAdminUser();
        return ApiResult.ok(adminUserInfoVo);
    }

    /**
     * admin用户表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "用户表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "用户表分页列表", response = AdminUser.class)
    public ApiResult<Paging<AdminUserInfoVo>> getAdminUserPageList(@Validated @RequestBody AdminUserPageParam adminUserPageParam) throws Exception {
        Paging<AdminUserInfoVo> paging = adminUserService.getAdminUserPageList(adminUserPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @OperationLog(name = "用户登录", type = OperationLogType.ADD)
    @ApiOperation(value = "用户登录 adminUser", response = LoginUserInfoVo.class)
    public ApiResult<LoginUserInfoVo> login(/*@Validated*/ @RequestBody LoginParam loginParam) throws Exception {
        LoginUserInfoVo loginUserInfoVo = adminUserService.login(loginParam);
        return ApiResult.ok(loginUserInfoVo);
    }

}

