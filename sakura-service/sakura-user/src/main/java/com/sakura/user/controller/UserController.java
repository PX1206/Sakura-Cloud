package com.sakura.user.controller;

import com.sakura.user.param.*;
import com.sakura.user.service.UserService;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
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
 * 用户表 控制器
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Module("user")
@Api(value = "用户管理", tags = {"用户管理"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @OperationLog(name = "用户注册", type = OperationLogType.ADD)
    @ApiOperation(value = "用户注册 User", response = ApiResult.class)
    public ApiResult<Boolean> register(@Validated(Add.class) @RequestBody UserRegisterParam userRegisterParam) throws Exception {
        boolean flag = userService.register(userRegisterParam);
        return ApiResult.result(flag);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @OperationLog(name = "用户登录", type = OperationLogType.ADD)
    @ApiOperation(value = "用户登录 User", response = LoginUserInfoVo.class)
    public ApiResult<LoginUserInfoVo> login(@Validated @RequestBody LoginParam loginParam) throws Exception {
        LoginUserInfoVo loginUserInfoVo = userService.login(loginParam);
        return ApiResult.ok(loginUserInfoVo);
    }

    /**
     * 修改用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户信息", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户信息 User", response = ApiResult.class)
    public ApiResult<Boolean> updateUser(@Validated(Update.class) @RequestBody UpdateUserParam updateUserParam) throws Exception {
        boolean flag = userService.updateUser(updateUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户手机号
     */
    @PostMapping("/updateMobile")
    @OperationLog(name = "修改用户手机号", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户手机号 User", response = ApiResult.class)
    public ApiResult<Boolean> updateMobile(@Validated(Update.class) @RequestBody UpdateMobileParam updateMobileParam) throws Exception {
        boolean flag = userService.updateMobile(updateMobileParam);
        return ApiResult.result(flag);
    }


    /**
     * 获取用户表详情
     */
    @GetMapping("/info")
    @OperationLog(name = "用户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户详情 User", response = UserInfoVo.class)
    public ApiResult<UserInfoVo> getUserInfo() throws Exception {
        UserInfoVo userInfoVo = userService.getUserInfo();
        return ApiResult.ok(userInfoVo);
    }

    /**
     * 用户分页列表
     */
    @PostMapping("/getUserList")
    @OperationLog(name = "用户分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "用户分页列表 Admin", response = UserInfoVo.class)
    public ApiResult<Paging<UserInfoVo>> getUserList(@Validated @RequestBody UserPageParam userPageParam) throws Exception {
        Paging<UserInfoVo> paging = userService.getUserList(userPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 用户详情
     */
    @PostMapping("/getUser/{userId}")
    @OperationLog(name = "获取用户详细信息", type = OperationLogType.PAGE)
    @ApiOperation(value = "获取用户详细信息 Admin", response = UserInfoVo.class)
    public ApiResult<UserInfoVo> getUser(@PathVariable("userId") String userId) throws Exception {
        UserInfoVo userInfoVo = userService.getUser(userId);
        return ApiResult.ok(userInfoVo);
    }

    /**
     * 账号解冻
     */
    @PostMapping("/unfreezeAccount")
    @OperationLog(name = "账号解冻", type = OperationLogType.UPDATE)
    @ApiOperation(value = "账号解冻 Admin", response = ApiResult.class)
    public ApiResult<Boolean> unfreezeAccount(@RequestBody FreezeAccountParam freezeAccountParam) throws Exception {
        boolean flag = userService.unfreezeAccount(freezeAccountParam);
        return ApiResult.result(flag);
    }

    /**
     * 账号冻结
     */
    @PostMapping("/freezeAccount")
    @OperationLog(name = "账号冻结", type = OperationLogType.UPDATE)
    @ApiOperation(value = "账号冻结 Admin", response = ApiResult.class)
    public ApiResult<Boolean> freezeAccount(@RequestBody FreezeAccountParam freezeAccountParam) throws Exception {
        boolean flag = userService.freezeAccount(freezeAccountParam);
        return ApiResult.result(flag);
    }

}

