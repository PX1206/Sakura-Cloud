package com.sakura.user.controller;

import com.sakura.user.entity.AdminRole;
import com.sakura.user.service.AdminRoleService;
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
@Api(value = "admin角色表API", tags = {"admin角色表"})
public class AdminRoleController extends BaseController {

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 添加admin角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加admin角色表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加admin角色表", response = ApiResult.class)
    public ApiResult<Boolean> addAdminRole(@Validated(Add.class) @RequestBody AdminRole adminRole) throws Exception {
        boolean flag = adminRoleService.saveAdminRole(adminRole);
        return ApiResult.result(flag);
    }

    /**
     * 修改admin角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改admin角色表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改admin角色表", response = ApiResult.class)
    public ApiResult<Boolean> updateAdminRole(@Validated(Update.class) @RequestBody AdminRole adminRole) throws Exception {
        boolean flag = adminRoleService.updateAdminRole(adminRole);
        return ApiResult.result(flag);
    }

    /**
     * 删除admin角色表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除admin角色表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除admin角色表", response = ApiResult.class)
    public ApiResult<Boolean> deleteAdminRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = adminRoleService.deleteAdminRole(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取admin角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "admin角色表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "admin角色表详情", response = AdminRole.class)
    public ApiResult<AdminRole> getAdminRole(@PathVariable("id") Long id) throws Exception {
        AdminRole adminRole = adminRoleService.getById(id);
        return ApiResult.ok(adminRole);
    }

    /**
     * admin角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "admin角色表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "admin角色表分页列表", response = AdminRole.class)
    public ApiResult<Paging<AdminRole>> getAdminRolePageList(@Validated @RequestBody AdminRolePageParam adminRolePageParam) throws Exception {
        Paging<AdminRole> paging = adminRoleService.getAdminRolePageList(adminRolePageParam);
        return ApiResult.ok(paging);
    }

}

