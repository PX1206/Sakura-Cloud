package com.sakura.user.controller;

import com.sakura.user.param.*;
import com.sakura.user.service.CustomerRoleService;
import com.sakura.user.vo.CustomerRoleVo;
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
 * 客户角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/customerRole")
@Module("user")
@Api(value = "客户角色API", tags = {"客户角色管理"})
public class CustomerRoleController extends BaseController {

    @Autowired
    private CustomerRoleService customerRoleService;

    /**
     * 添加角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色", response = ApiResult.class)
    public ApiResult<Boolean> addCustomerRole(@Validated(Add.class) @RequestBody CustomerRoleParam customerRoleParam) throws Exception {
        boolean flag = customerRoleService.addCustomerRole(customerRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改角色", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改角色", response = ApiResult.class)
    public ApiResult<Boolean> updateCustomerRole(@Validated(Update.class) @RequestBody CustomerRoleParam customerRoleParam) throws Exception {
        boolean flag = customerRoleService.updateCustomerRole(customerRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteCustomerRole(@Validated @RequestBody DeleteCustomerRoleParam deleteCustomerRoleParam) throws Exception {
        boolean flag = customerRoleService.deleteCustomerRole(deleteCustomerRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "角色详情", type = OperationLogType.INFO)
    @ApiOperation(value = "角色详情", response = CustomerRoleVo.class)
    public ApiResult<CustomerRoleVo> getCustomerRole(@PathVariable("id") Long id) throws Exception {
        CustomerRoleVo customerRoleVo = customerRoleService.getCustomerRole(id);
        return ApiResult.ok(customerRoleVo);
    }

    /**
     * 角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "角色分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "角色分页列表", response = CustomerRoleVo.class)
    public ApiResult<Paging<CustomerRoleVo>> getCustomerRolePageList(@Validated @RequestBody CustomerRolePageParam customerRolePageParam) throws Exception {
        Paging<CustomerRoleVo> paging = customerRoleService.getCustomerRolePageList(customerRolePageParam);
        return ApiResult.ok(paging);
    }

}

