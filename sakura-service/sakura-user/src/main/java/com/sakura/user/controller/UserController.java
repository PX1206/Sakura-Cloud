package com.sakura.user.controller;

import com.sakura.user.entity.User;
import com.sakura.user.param.UserRegisterParam;
import com.sakura.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.UserPageParam;
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
     * 添加用户表
     */
    @PostMapping("/register")
    @OperationLog(name = "用户注册", type = OperationLogType.ADD)
    @ApiOperation(value = "用户注册", response = ApiResult.class)
    public ApiResult<Boolean> register(@Validated(Add.class) @RequestBody UserRegisterParam userRegisterParam) throws Exception {
        boolean flag = userService.register(userRegisterParam);
        return ApiResult.result(flag);
    }

    /**
     * 添加用户表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户表", response = ApiResult.class)
    public ApiResult<Boolean> addUser(@Validated(Add.class) @RequestBody User user) throws Exception {
        boolean flag = userService.saveUser(user);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户表", response = ApiResult.class)
    public ApiResult<Boolean> updateUser(@Validated(Update.class) @RequestBody User user) throws Exception {
        boolean flag = userService.updateUser(user);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除用户表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户表", response = ApiResult.class)
    public ApiResult<Boolean> deleteUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = userService.deleteUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取用户表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "用户表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户表详情", response = User.class)
    public ApiResult<User> getUser(@PathVariable("id") Long id) throws Exception {
        User user = userService.getById(id);
        return ApiResult.ok(user);
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

