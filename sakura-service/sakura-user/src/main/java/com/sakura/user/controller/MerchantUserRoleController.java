package com.sakura.user.controller;

import com.sakura.user.entity.MerchantUserRole;
import com.sakura.user.service.MerchantUserRoleService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantUserRolePageParam;
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
 * 商户用户角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantUserRole")
@Module("user")
@Api(value = "商户用户角色表API", tags = {"商户用户角色表"})
public class MerchantUserRoleController extends BaseController {

    @Autowired
    private MerchantUserRoleService merchantUserRoleService;

    /**
     * 添加商户用户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户用户角色表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantUserRole(@Validated(Add.class) @RequestBody MerchantUserRole merchantUserRole) throws Exception {
        boolean flag = merchantUserRoleService.saveMerchantUserRole(merchantUserRole);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户用户角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户用户角色表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantUserRole(@Validated(Update.class) @RequestBody MerchantUserRole merchantUserRole) throws Exception {
        boolean flag = merchantUserRoleService.updateMerchantUserRole(merchantUserRole);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户用户角色表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户用户角色表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户用户角色表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantUserRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantUserRoleService.deleteMerchantUserRole(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户用户角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户用户角色表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户用户角色表详情", response = MerchantUserRole.class)
    public ApiResult<MerchantUserRole> getMerchantUserRole(@PathVariable("id") Long id) throws Exception {
        MerchantUserRole merchantUserRole = merchantUserRoleService.getById(id);
        return ApiResult.ok(merchantUserRole);
    }

    /**
     * 商户用户角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户用户角色表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户用户角色表分页列表", response = MerchantUserRole.class)
    public ApiResult<Paging<MerchantUserRole>> getMerchantUserRolePageList(@Validated @RequestBody MerchantUserRolePageParam merchantUserRolePageParam) throws Exception {
        Paging<MerchantUserRole> paging = merchantUserRoleService.getMerchantUserRolePageList(merchantUserRolePageParam);
        return ApiResult.ok(paging);
    }

}

