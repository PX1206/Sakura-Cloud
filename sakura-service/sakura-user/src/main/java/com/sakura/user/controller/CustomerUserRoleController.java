package com.sakura.user.controller;

import com.sakura.user.param.CustomerUserRoleParam;
import com.sakura.user.service.CustomerUserRoleService;
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
 * 客户用户角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/customerUserRole")
@Module("user")
@Api(value = "客户用户角色API", tags = {"客户用户角色管理"})
public class CustomerUserRoleController extends BaseController {

    @Autowired
    private CustomerUserRoleService customerUserRoleService;

    /**
     * 添加用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加用户角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addCustomerUserRole(@Validated(Add.class) @RequestBody CustomerUserRoleParam customerUserRoleParam) throws Exception {
        boolean flag = customerUserRoleService.saveCustomerUserRole(customerUserRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除用户角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除用户角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteCustomerUserRole(@RequestBody CustomerUserRoleParam customerUserRoleParam) throws Exception {
        boolean flag = customerUserRoleService.deleteCustomerUserRole(customerUserRoleParam);
        return ApiResult.result(flag);
    }

}

