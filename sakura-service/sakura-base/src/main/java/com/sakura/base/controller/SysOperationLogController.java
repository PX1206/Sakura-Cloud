package com.sakura.base.controller;

import com.sakura.base.entity.SysOperationLog;
import com.sakura.base.service.SysOperationLogService;
import lombok.extern.slf4j.Slf4j;
import com.sakura.base.param.SysOperationLogPageParam;
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
 *  控制器
 *
 * @author Sakura
 * @since 2023-10-24
 */
@Slf4j
@RestController
@RequestMapping("/sysOperationLog")
@Module("base")
@Api(value = "API", tags = {""})
public class SysOperationLogController extends BaseController {

    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSysOperationLog(@Validated(Add.class) @RequestBody SysOperationLog sysOperationLog) throws Exception {
        boolean flag = sysOperationLogService.saveSysOperationLog(sysOperationLog);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSysOperationLog(@Validated(Update.class) @RequestBody SysOperationLog sysOperationLog) throws Exception {
        boolean flag = sysOperationLogService.updateSysOperationLog(sysOperationLog);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysOperationLog(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysOperationLogService.deleteSysOperationLog(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SysOperationLog.class)
    public ApiResult<SysOperationLog> getSysOperationLog(@PathVariable("id") Long id) throws Exception {
        SysOperationLog sysOperationLog = sysOperationLogService.getById(id);
        return ApiResult.ok(sysOperationLog);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SysOperationLog.class)
    public ApiResult<Paging<SysOperationLog>> getSysOperationLogPageList(@Validated @RequestBody SysOperationLogPageParam sysOperationLogPageParam) throws Exception {
        Paging<SysOperationLog> paging = sysOperationLogService.getSysOperationLogPageList(sysOperationLogPageParam);
        return ApiResult.ok(paging);
    }

}

