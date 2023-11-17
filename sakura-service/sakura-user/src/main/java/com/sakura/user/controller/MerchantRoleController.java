package com.sakura.user.controller;

import com.sakura.common.pagination.Paging;
import com.sakura.user.param.*;
import com.sakura.user.service.MerchantRoleService;
import com.sakura.user.vo.MerchantRoleVo;
import lombok.extern.slf4j.Slf4j;
import com.sakura.common.base.BaseController;
import com.sakura.common.api.ApiResult;
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
@Api(value = "商户角色API", tags = {"商户角色管理"})
public class MerchantRoleController extends BaseController {

    @Autowired
    private MerchantRoleService merchantRoleService;

    /**
     * 添加Merchant角色表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加角色", type = OperationLogType.ADD)
    @ApiOperation(value = "添加角色", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantRole(@Validated(Add.class) @RequestBody MerchantRoleParam merchantRoleParam) throws Exception {
        boolean flag = merchantRoleService.saveMerchantRole(merchantRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改Merchant角色表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改角色", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改角色", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantRole(@Validated(Update.class) @RequestBody MerchantRoleParam merchantRoleParam) throws Exception {
        boolean flag = merchantRoleService.updateMerchantRole(merchantRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除Merchant角色表
     */
    @PostMapping("/delete")
    @OperationLog(name = "删除角色", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除角色", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantRole(@Validated @RequestBody DeleteMerchantRoleParam deleteMerchantRoleParam) throws Exception {
        boolean flag = merchantRoleService.deleteMerchantRole(deleteMerchantRoleParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取Merchant角色表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "角色详情", type = OperationLogType.INFO)
    @ApiOperation(value = "角色详情", response = MerchantRoleVo.class)
    public ApiResult<MerchantRoleVo> getMerchantRole(@PathVariable("id") Long id) throws Exception {
        MerchantRoleVo merchantRoleVo = merchantRoleService.getMerchantRole(id);
        return ApiResult.ok(merchantRoleVo);
    }

    /**
     * Merchant角色表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "角色分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "角色分页列表", response = MerchantRoleVo.class)
    public ApiResult<Paging<MerchantRoleVo>> getMerchantRolePageList(@Validated @RequestBody MerchantRolePageParam merchantRolePageParam) throws Exception {
        Paging<MerchantRoleVo> paging = merchantRoleService.getMerchantRolePageList(merchantRolePageParam);
        return ApiResult.ok(paging);
    }

}

