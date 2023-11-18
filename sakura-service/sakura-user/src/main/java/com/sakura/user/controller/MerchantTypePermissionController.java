package com.sakura.user.controller;

import com.sakura.user.param.MerchantTypePermissionParam;
import com.sakura.user.service.MerchantTypePermissionService;
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
 * 商户类型权限表 控制器
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantTypePermission")
@Module("user")
@Api(value = "商户类型权限API", tags = {"商户类型权限管理"})
public class MerchantTypePermissionController extends BaseController {

    @Autowired
    private MerchantTypePermissionService merchantTypePermissionService;

    /**
     * 添加类型权限
     */
    @PostMapping("/save")
    @OperationLog(name = "保存类型权限", type = OperationLogType.ADD)
    @ApiOperation(value = "保存类型权限", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantTypePermission(@Validated @RequestBody MerchantTypePermissionParam merchantTypePermissionParam) throws Exception {
        boolean flag = merchantTypePermissionService.addMerchantTypePermission(merchantTypePermissionParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取类型权限ID
     */
    @GetMapping("/getAdminRolePermissionId/{typeId}")
    @OperationLog(name = "获取类型权限ID", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取类型权限ID", response = ApiResult.class)
    public ApiResult<Set<Integer>> getMerchantTypePermissionId(@PathVariable("typeId") Integer typeId) throws Exception {
        Set<Integer> permissionIds = merchantTypePermissionService.getMerchantTypePermissionId(typeId);
        return ApiResult.ok(permissionIds);
    }

}

