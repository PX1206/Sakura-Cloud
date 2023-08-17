package com.sakura.user.controller;

import com.sakura.user.entity.Permissions;
import com.sakura.user.service.PermissionsService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.PermissionsPageParam;
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
 * 权限表 控制器
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@RestController
@RequestMapping("/permissions")
@Module("user")
@Api(value = "权限表API", tags = {"权限表"})
public class PermissionsController extends BaseController {

    @Autowired
    private PermissionsService permissionsService;

    /**
     * 添加权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加权限表", response = ApiResult.class)
    public ApiResult<Boolean> addPermissions(@Validated(Add.class) @RequestBody Permissions permissions) throws Exception {
        boolean flag = permissionsService.savePermissions(permissions);
        return ApiResult.result(flag);
    }

    /**
     * 修改权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改权限表", response = ApiResult.class)
    public ApiResult<Boolean> updatePermissions(@Validated(Update.class) @RequestBody Permissions permissions) throws Exception {
        boolean flag = permissionsService.updatePermissions(permissions);
        return ApiResult.result(flag);
    }

    /**
     * 删除权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除权限表", response = ApiResult.class)
    public ApiResult<Boolean> deletePermissions(@PathVariable("id") Long id) throws Exception {
        boolean flag = permissionsService.deletePermissions(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "权限表详情", response = Permissions.class)
    public ApiResult<Permissions> getPermissions(@PathVariable("id") Long id) throws Exception {
        Permissions permissions = permissionsService.getById(id);
        return ApiResult.ok(permissions);
    }

    /**
     * 权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "权限表分页列表", response = Permissions.class)
    public ApiResult<Paging<Permissions>> getPermissionsPageList(@Validated @RequestBody PermissionsPageParam permissionsPageParam) throws Exception {
        Paging<Permissions> paging = permissionsService.getPermissionsPageList(permissionsPageParam);
        return ApiResult.ok(paging);
    }

}

