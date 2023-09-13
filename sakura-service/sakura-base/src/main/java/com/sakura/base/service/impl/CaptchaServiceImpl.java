package com.sakura.base.service.impl;

import com.sakura.common.redis.RedisUtil;
import com.sakura.base.captcha.GifCaptcha;
import com.sakura.base.service.CaptchaService;
import com.sakura.base.vo.PictureCodeVo;
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
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired

    @Override
    public PictureCodeVo getPictureCode() throws Exception {
        // 通过GifCaptcha生成图片验证码
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
        String verCode = gifCaptcha.text().toLowerCase();

        PictureCodeVo pictureCodeVo = new PictureCodeVo();
        String key = UUID.randomUUID().toString();
        pictureCodeVo.setKey(key);
        pictureCodeVo.setImage(gifCaptcha.toBase64());

        log.info(key + "+++++++++++++" + verCode); // 测试用的，建议删除

        // 将验证码放入Redis,有效期5分钟
        redisUtil.set(key, verCode, 60 * 5);

        return pictureCodeVo;
    }
}
