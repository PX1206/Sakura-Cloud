package com.sakura.user.controller;

import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.param.*;
import com.sakura.user.service.CustomerUserService;
import com.sakura.user.vo.CustomerUserInfoVo;
import lombok.extern.slf4j.Slf4j;
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
 * 客户用户表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/customerUser")
@Module("user")
@Api(value = "客户用户API", tags = {"客户用户管理"})
public class CustomerUserController extends BaseController {

    @Autowired
    private CustomerUserService customerUserService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @OperationLog(name = "用户注册", type = OperationLogType.ADD)
    @ApiOperation(value = "用户注册 User", response = ApiResult.class)
    public ApiResult<Boolean> register(@Validated @RequestBody CustomerUserRegisterParam customerUserRegisterParam) throws Exception {
        boolean flag = customerUserService.register(customerUserRegisterParam);
        return ApiResult.result(flag);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @OperationLog(name = "用户登录", type = OperationLogType.ADD)
    @ApiOperation(value = "用户登录 User", response = LoginUserInfoVo.class)
    public ApiResult<LoginUserInfoVo> login(@Validated @RequestBody LoginParam loginParam) throws Exception {
        LoginUserInfoVo loginUserInfoVo = customerUserService.login(loginParam);
        return ApiResult.ok(loginUserInfoVo);
    }

    /**
     * 修改用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户信息", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户信息 User", response = ApiResult.class)
    public ApiResult<Boolean> updateCustomerUser(@Validated @RequestBody UpdateCustomerUserParam updateCustomerUserParam) throws Exception {
        boolean flag = customerUserService.updateCustomerUser(updateCustomerUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户手机号
     */
    @PostMapping("/updateMobile")
    @OperationLog(name = "修改用户手机号", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户手机号 User", response = ApiResult.class)
    public ApiResult<Boolean> updateMobile(@Validated @RequestBody UpdateMobileParam updateMobileParam) throws Exception {
        boolean flag = customerUserService.updateMobile(updateMobileParam);
        return ApiResult.result(flag);
    }


    /**
     * 获取用户表详情
     */
    @GetMapping("/info")
    @OperationLog(name = "用户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户详情 User", response = CustomerUserInfoVo.class)
    public ApiResult<CustomerUserInfoVo> getCustomerUserInfo() throws Exception {
        CustomerUserInfoVo customerUserInfoVo = customerUserService.getCustomerUserInfo();
        return ApiResult.ok(customerUserInfoVo);
    }

    /**
     * 用户分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "用户分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "用户分页列表 Admin", response = CustomerUserInfoVo.class)
    public ApiResult<Paging<CustomerUserInfoVo>> getCustomerUserList(@Validated @RequestBody CustomerUserPageParam customerUserPageParam) throws Exception {
        Paging<CustomerUserInfoVo> paging = customerUserService.getCustomerUserList(customerUserPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 用户详情
     */
    @PostMapping("/getCustomerUser/{userId}")
    @OperationLog(name = "获取用户详细信息", type = OperationLogType.PAGE)
    @ApiOperation(value = "获取用户详细信息 Admin", response = CustomerUserInfoVo.class)
    public ApiResult<CustomerUserInfoVo> getCustomerUser(@PathVariable("userId") String userId) throws Exception {
        CustomerUserInfoVo customerUserInfoVo = customerUserService.getCustomerUser(userId);
        return ApiResult.ok(customerUserInfoVo);
    }

    /**
     * 账号解冻
     */
    @PostMapping("/unfreezeAccount")
    @OperationLog(name = "账号解冻", type = OperationLogType.UPDATE)
    @ApiOperation(value = "账号解冻 Admin", response = ApiResult.class)
    public ApiResult<Boolean> unfreezeAccount(@RequestBody FreezeAccountParam freezeAccountParam) throws Exception {
        boolean flag = customerUserService.unfreezeAccount(freezeAccountParam);
        return ApiResult.result(flag);
    }

    /**
     * 账号冻结
     */
    @PostMapping("/freezeAccount")
    @OperationLog(name = "账号冻结", type = OperationLogType.UPDATE)
    @ApiOperation(value = "账号冻结 Admin", response = ApiResult.class)
    public ApiResult<Boolean> freezeAccount(@RequestBody FreezeAccountParam freezeAccountParam) throws Exception {
        boolean flag = customerUserService.freezeAccount(freezeAccountParam);
        return ApiResult.result(flag);
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPassword")
    @OperationLog(name = "重置密码", type = OperationLogType.UPDATE)
    @ApiOperation(value = "重置密码 User", response = ApiResult.class)
    public ApiResult<Boolean> resetPassword(@Validated @RequestBody ResetPasswordParam resetPasswordParam) throws Exception {
        boolean flag = customerUserService.resetPassword(resetPasswordParam);
        return ApiResult.result(flag);
    }

    /**
     * 账号注销，用户自行注销账号
     */
    @PostMapping("/cancellation")
    @OperationLog(name = "账号注销", type = OperationLogType.UPDATE)
    @ApiOperation(value = "账号注销 User", response = ApiResult.class)
    public ApiResult<Boolean> AccountCancellation(@Validated @RequestBody AccountCancellationParam accountCancellationParam) throws Exception {
        boolean flag = customerUserService.AccountCancellation(accountCancellationParam);
        return ApiResult.result(flag);
    }

}

