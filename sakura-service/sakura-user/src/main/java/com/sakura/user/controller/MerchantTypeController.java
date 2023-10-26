package com.sakura.user.controller;

import com.sakura.user.param.MerchantTypeParam;
import com.sakura.user.service.MerchantTypeService;
import com.sakura.user.vo.MerchantTypeVo;
import lombok.extern.slf4j.Slf4j;
import com.sakura.user.param.MerchantTypePageParam;
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
 * 商户类型表 控制器
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Slf4j
@RestController
@RequestMapping("/merchantType")
@Module("user")
@Api(value = "商户类型API", tags = {"商户类型管理"})
public class MerchantTypeController extends BaseController {

    @Autowired
    private MerchantTypeService merchantTypeService;

    /**
     * 添加商户类型表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加商户类型", type = OperationLogType.ADD)
    @ApiOperation(value = "添加商户类型", response = ApiResult.class)
    public ApiResult<Boolean> addMerchantType(@Validated(Add.class) @RequestBody MerchantTypeParam merchantTypeParam) throws Exception {
        boolean flag = merchantTypeService.saveMerchantType(merchantTypeParam);
        return ApiResult.result(flag);
    }

    /**
     * 修改商户类型表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改商户类型", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改商户类型", response = ApiResult.class)
    public ApiResult<Boolean> updateMerchantType(@Validated(Update.class) @RequestBody MerchantTypeParam merchantTypeParam) throws Exception {
        boolean flag = merchantTypeService.updateMerchantType(merchantTypeParam);
        return ApiResult.result(flag);
    }

    /**
     * 删除商户类型表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除商户类型", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除商户类型", response = ApiResult.class)
    public ApiResult<Boolean> deleteMerchantType(@PathVariable("id") Long id) throws Exception {
        boolean flag = merchantTypeService.deleteMerchantType(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取商户类型表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "商户类型表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "商户类型表详情", response = MerchantTypeVo.class)
    public ApiResult<MerchantTypeVo> getMerchantType(@PathVariable("id") Long id) throws Exception {
        MerchantTypeVo merchantType = merchantTypeService.getMerchantType(id);
        return ApiResult.ok(merchantType);
    }

    /**
     * 商户类型表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "商户类型表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "商户类型表分页列表", response = MerchantTypeVo.class)
    public ApiResult<Paging<MerchantTypeVo>> getMerchantTypePageList(@Validated @RequestBody MerchantTypePageParam merchantTypePageParam) throws Exception {
        Paging<MerchantTypeVo> paging = merchantTypeService.getMerchantTypePageList(merchantTypePageParam);
        return ApiResult.ok(paging);
    }

}

