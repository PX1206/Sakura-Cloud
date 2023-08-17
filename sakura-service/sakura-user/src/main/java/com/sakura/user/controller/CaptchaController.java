package com.sakura.user.controller;

import com.sakura.common.api.ApiResult;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.user.param.SMSCodeParam;
import com.sakura.user.service.CaptchaService;
import com.sakura.user.vo.PictureCodeVo;
import com.sakura.user.vo.SaltVo;
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
@RequestMapping("/captcha")
@Module("user")
@Api(value = "验证码管理", tags = {"验证码管理"})
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    /**
     * 图片验证码
     */
    @GetMapping("getPictureCode")
    @OperationLog(name = "获取图片验证码", type = OperationLogType.INFO)
    @ApiOperation(value = "获取图片验证码")
    public ApiResult<PictureCodeVo> getPictureCode() throws Exception {
        PictureCodeVo pictureCodeVo = captchaService.getPictureCode();
        return ApiResult.ok(pictureCodeVo);
    }

    /**
     * 获取加密盐
     * 前端传输特殊信息时用该盐进行AES加密，后端拿到值后再进行解密
     */
    @GetMapping("getSalt")
    @OperationLog(name = "获取加密盐", type = OperationLogType.INFO)
    @ApiOperation(value = "获取加密盐，用于用户密码等信息加密传输")
    public ApiResult<SaltVo> getSalt() throws Exception {
        SaltVo saltVo = captchaService.getSalt();
        return ApiResult.ok(saltVo);
    }

    /**
     * 短信验证码
     */
    @PostMapping(value = "/getSMSCode")
    @OperationLog(name = "获取短信验证码", type = OperationLogType.INFO)
    @ApiOperation(value = "获取短信验证码")
    public ApiResult<String> getSMSCode(@Validated @RequestBody SMSCodeParam smsCodeParam) throws Exception {
        String msg = captchaService.getSMSCode(smsCodeParam);
        return ApiResult.ok(msg);
    }
}
