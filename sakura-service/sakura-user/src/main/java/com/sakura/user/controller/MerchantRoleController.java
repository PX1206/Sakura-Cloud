package com.sakura.user.controller;

import com.sakura.user.entity.MerchantRole;
import com.sakura.user.service.MerchantRoleService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantRolePageParam;
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
 * 商户角色表 控制器
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantRole")
@Module("user")
@Api(value = "商户角色表API", tags = {"商户角色表"})
public class MerchantRoleController extends BaseController {

    @Autowired
    private MerchantRoleService merchantRoleService;

    /**
     * 添加商户角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户角色表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户角色表", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantRole(@Validated(Add.class) @RequestBody MerchantRole merchantRole) throws Exception {
        boolean flag = merchantRoleService.saveMerchantRole(merchantRole);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户角色表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户角色表", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantRole(@Validated(Update.class) @RequestBody MerchantRole merchantRole) throws Exception {
        boolean flag = merchantRoleService.updateMerchantRole(merchantRole);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户角色表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户角色表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户角色表", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantRoleService.deleteMerchantRole(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户角色表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户角色表详情", response = MerchantRole.class)
    public ApiResult<MerchantRole> getMerchantRole(@PathVariable("id") Long id) throws Exception {
        MerchantRole merchantRole = merchantRoleService.getById(id);
        return ApiResult.ok(merchantRole);
    }

    /**
     * 商户角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户角色表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户角色表分页列表", response = MerchantRole.class)
    public ApiResult<Paging<MerchantRole>> getMerchantRolePageList(@Validated @RequestBody MerchantRolePageParam merchantRolePageParam) throws Exception {
        Paging<MerchantRole> paging = merchantRoleService.getMerchantRolePageList(merchantRolePageParam);
        return ApiResult.ok(paging);
    }

}

