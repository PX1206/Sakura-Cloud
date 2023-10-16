package com.sakura.user.controller;

import com.sakura.user.param.CustomerRolePermissionParam;
import com.sakura.user.service.CustomerRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.common.enums.OperationLogType;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 客户角色权限表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/customerRolePermission")
@Module("user")
@Api(value = "客户角色权限API", tags = {"客户角色权限管理"})
public class CustomerRolePermissionController extends BaseController {

    @Autowired
    private CustomerRolePermissionService customerRolePermissionService;

    /**
     * 添加角色权限
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色权限", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色权限", response = ApiResult.class)
    public ApiResult<Boolean> addCustomerRolePermission(@Validated @RequestBody CustomerRolePermissionParam customerRolePermissionParam) throws Exception {
        boolean flag = customerRolePermissionService.saveCustomerRolePermission(customerRolePermissionParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色权限ID
     */
    @GetMapping("/getCustomerRolePermissionId/{roleId}")
    @OperationLog(name = "获取角色权限ID", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取角色权限ID", response = ApiResult.class)
    public ApiResult<Set<Integer>> getCustomerRolePermissionId(@PathVariable("roleId") Integer roleId) throws Exception {
        Set<Integer> permissionIds = customerRolePermissionService.getCustomerRolePermissionId(roleId);
        return ApiResult.ok(permissionIds);
    }

}

