package com.sakura.base.controller;

import com.sakura.common.api.ApiResult;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.base.service.CaptchaService;
import com.sakura.base.vo.PictureCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sakura
 * @date 2023/8/14 14:15
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
@Module("code")
@Api(value = "图片验证码管理", tags = {"图片验证码管理"})
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
}
