package com.sakura.user.controller;

import com.sakura.user.param.AdminRoleParam;
import com.sakura.user.param.DeleteAdminRoleParam;
import com.sakura.user.service.AdminRoleService;
import com.sakura.user.vo.AdminRoleVo;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.AdminRolePageParam;
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
 * admin角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/adminRole")
@Module("user")
@Api(value = "admin角色API", tags = {"admin角色管理"})
public class AdminRoleController extends BaseController {

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 添加admin角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色", response = ApiResult.class)
    public ApiResult<Boolean> addAdminRole(@Validated(Add.class) @RequestBody AdminRoleParam adminRoleParam) throws Exception {
        boolean flag = adminRoleService.saveAdminRole(adminRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改admin角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改角色", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改角色", response = ApiResult.class)
    public ApiResult<Boolean> updateAdminRole(@Validated(Update.class) @RequestBody AdminRoleParam adminRoleParam) throws Exception {
        boolean flag = adminRoleService.updateAdminRole(adminRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除admin角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteAdminRole(@Validated @RequestBody DeleteAdminRoleParam deleteAdminRoleParam) throws Exception {
        boolean flag = adminRoleService.deleteAdminRole(deleteAdminRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取admin角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "角色详情", type = OperationLogType.INFO)
    @ApiOperation(value = "角色详情", response = AdminRoleVo.class)
    public ApiResult<AdminRoleVo> getAdminRole(@PathVariable("id") Long id) throws Exception {
        AdminRoleVo adminRoleVo = adminRoleService.getAdminRole(id);
        return ApiResult.ok(adminRoleVo);
    }

    /**
     * admin角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "角色分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "角色分页列表", response = AdminRoleVo.class)
    public ApiResult<Paging<AdminRoleVo>> getAdminRolePageList(@Validated @RequestBody AdminRolePageParam adminRolePageParam) throws Exception {
        Paging<AdminRoleVo> paging = adminRoleService.getAdminRolePageList(adminRolePageParam);
        return ApiResult.ok(paging);
    }

}

