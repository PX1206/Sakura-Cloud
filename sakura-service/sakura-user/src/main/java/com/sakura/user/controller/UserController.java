package com.sakura.user.controller;

import com.sakura.user.entity.User;
import com.sakura.user.param.LoginParam;
import com.sakura.user.param.UpdateUserParam;
import com.sakura.user.param.UserRegisterParam;
import com.sakura.user.service.UserService;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.UserPageParam;
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
    @ApiOperation(value = "用户注册", response = ApiResult.class)
    public ApiResult<Boolean> register(@Validated(Add.class) @RequestBody UserRegisterParam userRegisterParam) throws Exception {
        boolean flag = userService.register(userRegisterParam);
        return ApiResult.result(flag);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @OperationLog(name = "用户登录", type = OperationLogType.ADD)
    @ApiOperation(value = "用户登录", response = LoginUserInfoVo.class)
    public ApiResult<LoginUserInfoVo> login(@Validated @RequestBody LoginParam loginParam) throws Exception {
        LoginUserInfoVo loginUserInfoVo = userService.login(loginParam);
        return ApiResult.ok(loginUserInfoVo);
    }

    /**
     * 修改用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户信息", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户信息", response = ApiResult.class)
    public ApiResult<Boolean> updateUser(@Validated(Update.class) @RequestBody UpdateUserParam updateUserParam) throws Exception {
        boolean flag = userService.updateUser(updateUserParam);
        return ApiResult.result(flag);
    }


    /**
     * 获取用户表详情
     */
    @GetMapping("/info")
    @OperationLog(name = "用户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户详情", response = UserInfoVo.class)
    public ApiResult<UserInfoVo> getUserInfo() throws Exception {
        UserInfoVo userInfoVo = userService.getUserInfo();
        return ApiResult.ok(userInfoVo);
    }

    /**
     * 用户表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "用户表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "用户表分页列表", response = User.class)
    public ApiResult<Paging<User>> getUserPageList(@Validated @RequestBody UserPageParam userPageParam) throws Exception {
        Paging<User> paging = userService.getUserPageList(userPageParam);
        return ApiResult.ok(paging);
    }

}

