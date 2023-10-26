package com.sakura.user.controller;

import com.sakura.user.entity.MerchantTypePermission;
import com.sakura.user.service.MerchantTypePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantTypePermissionPageParam;
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
 * 商户类型权限表 控制器
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantTypePermission")
@Module("user")
@Api(value = "商户类型权限表API", tags = {"商户类型权限表"})
public class MerchantTypePermissionController extends BaseController {

    @Autowired
    private MerchantTypePermissionService merchantTypePermissionService;

    /**
     * 添加商户类型权限表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户类型权限表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户类型权限表", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantTypePermission(@Validated(Add.class) @RequestBody MerchantTypePermission merchantTypePermission) throws Exception {
        boolean flag = merchantTypePermissionService.saveMerchantTypePermission(merchantTypePermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户类型权限表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户类型权限表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户类型权限表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantTypePermission(@Validated(Update.class) @RequestBody MerchantTypePermission merchantTypePermission) throws Exception {
        boolean flag = merchantTypePermissionService.updateMerchantTypePermission(merchantTypePermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户类型权限表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户类型权限表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户类型权限表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantTypePermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantTypePermissionService.deleteMerchantTypePermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户类型权限表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户类型权限表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户类型权限表详情", response = MerchantTypePermission.class)
    public ApiResult<MerchantTypePermission> getMerchantTypePermission(@PathVariable("id") Long id) throws Exception {
        MerchantTypePermission merchantTypePermission = merchantTypePermissionService.getById(id);
        return ApiResult.ok(merchantTypePermission);
    }

    /**
     * 商户类型权限表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户类型权限表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户类型权限表分页列表", response = MerchantTypePermission.class)
    public ApiResult<Paging<MerchantTypePermission>> getMerchantTypePermissionPageList(@Validated @RequestBody MerchantTypePermissionPageParam merchantTypePermissionPageParam) throws Exception {
        Paging<MerchantTypePermission> paging = merchantTypePermissionService.getMerchantTypePermissionPageList(merchantTypePermissionPageParam);
        return ApiResult.ok(paging);
    }

}

