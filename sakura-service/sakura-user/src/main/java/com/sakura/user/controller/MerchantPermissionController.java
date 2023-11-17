package com.sakura.user.controller;

import com.sakura.user.param.MerchantPermissionParam;
import com.sakura.user.service.MerchantPermissionService;
import com.sakura.user.vo.MerchantPermissionTreeVo;
import com.sakura.user.vo.MerchantPermissionVo;
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

import java.util.List;

/**
 * 商户权限 控制器
 * 商户基本权限根据商户类型划分
 * 此处分配商户特殊权限，比如一些定制开发功能，收费板块功能
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantPermission")
@Module("user")
@Api(value = "商户权限API", tags = {"商户权限管理"})
public class MerchantPermissionController extends BaseController {

    @Autowired
    private MerchantPermissionService merchantPermissionService;

    /**
     * 添加商户权限
     */
    @PostMapping("/save")
    @OperationLog(name = "保存商户权限", type = OperationLogType.ADD)
    @ApiOperation(value = "保存商户权限 admin", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantPermission(@Validated @RequestBody MerchantPermissionParam merchantPermissionParam) throws Exception {
        boolean flag = merchantPermissionService.addMerchantPermission(merchantPermissionParam);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户权限
     */
    @GetMapping("/getMerchantPermission/{merchantNo}")
    @OperationLog(name = "获取商户权限", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取商户权限 admin", response = MerchantPermissionVo.class)
    public ApiResult<List<MerchantPermissionVo>> getMerchantPermission(@PathVariable("merchantNo") String merchantNo) throws Exception {
        List<MerchantPermissionVo> merchantPermissionVos = merchantPermissionService.getMerchantPermission(merchantNo);
        return ApiResult.ok(merchantPermissionVos);
    }

    /**
     * 获取商户权限树
     */
    @GetMapping("/getMerchantPermission/tree")
    @OperationLog(name = "获取商户权限树", type = OperationLogType.QUERY)
    @ApiOperation(value = "获取商户权限（当前登录用户） merchant", response = MerchantPermissionTreeVo.class)
    public ApiResult<List<MerchantPermissionTreeVo>> getMerchantPermissionTree() throws Exception {
        List<MerchantPermissionTreeVo> merchantPermissionTreeVos = merchantPermissionService.getMerchantPermissionTree();
        return ApiResult.ok(merchantPermissionTreeVos);
    }

}

