package com.sakura.user.controller;

import com.sakura.user.entity.Permission;
import com.sakura.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.PermissionPageParam;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;

/**
 * 权限表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@Module("user")
@Api(value = "权限表API", tags = {"权限表"})
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 添加权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加权限表", response = ApiResult.class)
    public ApiResult<Boolean> addPermission(@Validated(Add.class) @RequestBody Permission permission) throws Exception {
        boolean flag = permissionService.savePermission(permission);
        return ApiResult.result(flag);
    }

    /**
     * 修改权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改权限表", response = ApiResult.class)
    public ApiResult<Boolean> updatePermission(@Validated(Update.class) @RequestBody Permission permission) throws Exception {
        boolean flag = permissionService.updatePermission(permission);
        return ApiResult.result(flag);
    }

    /**
     * 删除权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除权限表", response = ApiResult.class)
    public ApiResult<Boolean> deletePermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = permissionService.deletePermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "权限表详情", response = Permission.class)
    public ApiResult<Permission> getPermission(@PathVariable("id") Long id) throws Exception {
        Permission permission = permissionService.getById(id);
        return ApiResult.ok(permission);
    }

    /**
     * 权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "权限表分页列表", response = Permission.class)
    public ApiResult<Paging<Permission>> getPermissionPageList(@Validated @RequestBody PermissionPageParam permissionPageParam) throws Exception {
        Paging<Permission> paging = permissionService.getPermissionPageList(permissionPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 获取权限表详情
     */
    @PostMapping("/getCode")
    @ApiIgnore // 该方法只做权限认证使用
    public Set<String> getCodeByUrl(@RequestBody String strJson) throws Exception {
        Set<String> codes = permissionService.getCodeByUrl(strJson);
        return codes;
    }

}

