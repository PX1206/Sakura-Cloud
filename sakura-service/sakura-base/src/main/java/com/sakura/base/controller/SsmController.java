package com.sakura.base.controller;

import com.sakura.base.service.SmsService;
import com.sakura.common.api.ApiResult;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.base.param.SmsCodeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sakura
 * @date 2023/8/14 14:15
 */
@Slf4j
@RestController
@RequestMapping("/sms")
@Module("code")
@Api(value = "短信验证码管理", tags = {"短信验证码管理"})
public class SsmController {

    @Autowired
    private SmsService smsService;

    /**
     * 短信验证码
     */
    @PostMapping(value = "/getCode")
    @OperationLog(name = "获取短信验证码", type = OperationLogType.INFO)
    @ApiOperation(value = "获取短信验证码")
    public ApiResult<String> getSMSCode(@Validated @RequestBody SmsCodeParam smsCodeParam) throws Exception {
        String msg = smsService.getSMSCode(smsCodeParam);
        return ApiResult.ok(msg);
    }
}
