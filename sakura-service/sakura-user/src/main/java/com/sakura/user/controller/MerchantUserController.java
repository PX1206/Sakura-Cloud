package com.sakura.user.controller;

import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.MerchantUser;
import com.sakura.user.param.*;
import com.sakura.user.service.MerchantUserService;
import com.sakura.user.vo.ChooseMerchantUserVo;
import com.sakura.user.vo.LoginMerchantUserInfoVo;
import com.sakura.user.vo.MerchantUserInfoVo;
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

import java.util.List;

/**
 * 商户用户表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantUser")
@Module("user")
@Api(value = "商户用户API", tags = {"商户用户管理"})
public class MerchantUserController extends BaseController {

    @Autowired
    private MerchantUserService merchantUserService;

    /**
     * 添加merchant用户表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantUser(@Validated @RequestBody AddMerchantUserParam addMerchantUserParam) throws Exception {
        boolean flag = merchantUserService.addMerchantUser(addMerchantUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改merchant用户表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改用户信息", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改用户信息", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantUser(@Validated @RequestBody UpdateMerchantUserParam updateMerchantUserParam) throws Exception {
        boolean flag = merchantUserService.updateMerchantUser(updateMerchantUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除merchant用户表
     */
    @PostMapping("/delete/{userId}")
    @OperationLog(name = "删除用户", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantUser(@PathVariable("userId") String userId) throws Exception {
        boolean flag = merchantUserService.deleteMerchantUser(userId);
        return ApiResult.result(flag);
    }

    /**
     * 获取merchant用户表详情
     */
    @GetMapping("/info")
    @OperationLog(name = "用户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "用户详情（当前登录用户）", response = MerchantUserInfoVo.class)
    public ApiResult<MerchantUserInfoVo> getMerchantUser() throws Exception {
        MerchantUserInfoVo merchantUserInfoVo = merchantUserService.getMerchantUser();
        return ApiResult.ok(merchantUserInfoVo);
    }

    /**
     * merchant用户表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "用户表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "用户表分页列表", response = MerchantUser.class)
    public ApiResult<Paging<MerchantUserInfoVo>> getMerchantUserPageList(@Validated @RequestBody MerchantUserPageParam merchantUserPageParam) throws Exception {
        Paging<MerchantUserInfoVo> paging = merchantUserService.getMerchantUserPageList(merchantUserPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @OperationLog(name = "用户登录", type = OperationLogType.OTHER)
    @ApiOperation(value = "用户登录 merchantUser", response = ChooseMerchantUserVo.class)
    public ApiResult<List<ChooseMerchantUserVo>> login(@Validated @RequestBody LoginParam loginParam) throws Exception {
        List<ChooseMerchantUserVo> chooseMerchantUserVos = merchantUserService.login(loginParam);
        return ApiResult.ok(chooseMerchantUserVos);
    }

    /**
     * 用户登录，选择要登录的商户
     */
    @PostMapping("/choose")
    @OperationLog(name = "选择登录商户", type = OperationLogType.OTHER)
    @ApiOperation(value = "选择登录商户 merchantUser", response = LoginMerchantUserInfoVo.class)
    public ApiResult<LoginMerchantUserInfoVo> chooseMerchant(@Validated @RequestBody ChooseMerchantParam chooseMerchantParam) throws Exception {
        LoginMerchantUserInfoVo loginMerchantUserInfoVo = merchantUserService.chooseMerchant(chooseMerchantParam);
        return ApiResult.ok(loginMerchantUserInfoVo);
    }

}

