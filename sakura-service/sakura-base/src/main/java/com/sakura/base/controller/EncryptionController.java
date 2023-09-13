package com.sakura.base.controller;

import com.sakura.base.service.EncryptionService;
import com.sakura.common.api.ApiResult;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.Module;
import com.sakura.common.log.OperationLog;
import com.sakura.base.vo.SaltVo;
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
@RequestMapping("/encryption")
@Module("code")
@Api(value = "加密管理", tags = {"加密管理"})
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    /**
     * 获取加密盐
     * 前端传输特殊信息时用该盐进行AES加密，后端拿到值后再进行解密
     */
    @GetMapping("getSalt")
    @OperationLog(name = "获取加密盐", type = OperationLogType.INFO)
    @ApiOperation(value = "获取加密盐，用于用户密码等信息加密传输")
    public ApiResult<SaltVo> getSalt() throws Exception {
        SaltVo saltVo = encryptionService.getSalt();
        return ApiResult.ok(saltVo);
    }

}
