package com.sakura.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.DateUtil;
import com.sakura.common.tool.StringUtil;
import com.sakura.user.captcha.GifCaptcha;
import com.sakura.user.param.SMSCodeParam;
import com.sakura.user.service.CaptchaService;
import com.sakura.user.tool.AliyunSmsUtils;
import com.sakura.user.vo.PictureCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Sakura
 * @date 2023/8/14 14:25
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${aliyun.sms.send-max-num}")
    Integer ALIYUN_SMS_SEND_MAX_NUM;

    @Value("${aliyun.sms.register.sign-name}")
    String ALIYUN_SMS_REGISTER_SIGN_NAME;

    @Value("${aliyun.sms.register.template-code}")
    String ALIYUN_SMS_REGISTER_TEMPLATE_CODE;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AliyunSmsUtils aliyunSmsUtils;

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

    @Override
    public String getSMSCode(SMSCodeParam smsCodeParam) throws Exception {
        // 校验图片验证码是否正确，防止脚本刷短信
        String verCode = redisUtil.get(smsCodeParam.getKey()).toString();
        if (StringUtil.isEmpty(verCode) || !verCode.equals(smsCodeParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码已过期");
        }

        // 用户每天发送短信不得超过最大限制数
        long smsNum = redisUtil.incr("sms-send-num" + smsCodeParam.getMobile(), 1);
        if (smsNum > ALIYUN_SMS_SEND_MAX_NUM) {
            throw new BusinessException(500, "当天短信发送数量已达最大");
        }
        // 设置当前短信记录发送有效期,当前日期到晚上23：59:59
        redisUtil.expire("sms-send-num" + smsCodeParam.getMobile(), DateUtil.timeToMidnight());

        // 发送短信验证码
        String smsCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000)); // 生成一个6位数验证码
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("code", smsCode);
        boolean sendStatus = aliyunSmsUtils.sendSms(smsCodeParam.getMobile(), jsonParam.toJSONString(),
                ALIYUN_SMS_REGISTER_SIGN_NAME, ALIYUN_SMS_REGISTER_TEMPLATE_CODE);
        if (!sendStatus) {
            redisUtil.decr("sms-send-num" + smsCodeParam.getMobile(), 1); // 短信发送失败不计算次数
            throw new BusinessException(500, "短信发送失败，请联系管理员");
        }
        // 将短信验证码放入Redis,有效期5分钟
        redisUtil.set("sms_code_" + smsCodeParam.getMobile(), smsCode, 60 * 5);

        return "验证码已发送至手机号：" + smsCodeParam.getMobile() + ",5分钟内有效！";
    }
}
