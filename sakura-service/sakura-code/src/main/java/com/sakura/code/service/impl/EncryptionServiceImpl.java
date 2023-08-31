package com.sakura.code.service.impl;

import com.sakura.code.service.EncryptionService;
import com.sakura.common.redis.RedisUtil;
import com.sakura.code.vo.SaltVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Sakura
 * @date 2023/8/14 14:25
 */
@Service
@Slf4j
public class EncryptionServiceImpl implements EncryptionService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SaltVo getSalt() throws Exception {
        String key = UUID.randomUUID().toString();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");

        // 将值放入Redis，有效期30分钟
        redisUtil.set(key, salt, 60 * 30);

        SaltVo saltVo = new SaltVo();
        saltVo.setSaltKey(key);
        saltVo.setSalt(salt);

        return saltVo;
    }
}
