package com.sakura.user.controller;

import com.sakura.user.param.MerchantUserRoleParam;
import com.sakura.user.service.MerchantUserRoleService;
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
 * 商户用户角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantUserRole")
@Module("user")
@Api(value = "商户用户角色API", tags = {"商户用户角色管理"})
public class MerchantUserRoleController extends BaseController {

    @Autowired
    private MerchantUserRoleService merchantUserRoleService;

    /**
     * 添加用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantUserRole(@Validated(Add.class) @RequestBody MerchantUserRoleParam merchantUserRoleParam) throws Exception {
        boolean flag = merchantUserRoleService.saveMerchantUserRole(merchantUserRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除用户角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantUserRole(@Validated @RequestBody MerchantUserRoleParam merchantUserRoleParam) throws Exception {
        boolean flag = merchantUserRoleService.deleteMerchantUserRole(merchantUserRoleParam);
        return ApiResult.result(flag);
    }

}

