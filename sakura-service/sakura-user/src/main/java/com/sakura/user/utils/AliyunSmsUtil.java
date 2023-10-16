package com.sakura.user.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AliyunSmsUtil {

    @Value("${aliyun.sms.access-key-id}")
    String ALIYUN_SMS_ACCESS_KEY_ID;

    @Value("${aliyun.sms.secret}")
    String ALIYUN_SMS_SECRET;

    @Value("${aliyun.sms.region-id}")
    String ALIYUN_SMS_REGION_ID;

    @Value("${aliyun.sms.sys-domain}")
    String ALIYUN_SMS_SYS_DOMAIN;

    @Value("${aliyun.sms.sys-action}")
    String ALIYUN_SMS_SYS_ACTION;

    @Value("${aliyun.sms.sys-version}")
    String ALIYUN_SMS_SYS_VERSION;

    @Value("${aliyun.sms.register.sign-name}")
    String ALIYUN_SMS_REGISTER_SIGN_NAME;

    @Value("${aliyun.sms.register.template-code}")
    String ALIYUN_SMS_REGISTER_TEMPLATE_CODE;

    @Value("${aliyun.sms.register.template-param}")
    String ALIYUN_SMS_REGISTER_TEMPLATE_PARAM;

    public boolean sendPassword(String phone, String password) {
        DefaultProfile profile = DefaultProfile.getProfile(ALIYUN_SMS_REGION_ID, ALIYUN_SMS_ACCESS_KEY_ID, ALIYUN_SMS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        // 封装参数
        JSONObject jsonParam = new JSONObject();
        jsonParam.put(ALIYUN_SMS_REGISTER_TEMPLATE_PARAM, password);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(ALIYUN_SMS_SYS_DOMAIN);
        request.setSysVersion(ALIYUN_SMS_SYS_VERSION);
        request.setSysAction(ALIYUN_SMS_SYS_ACTION);
        request.putQueryParameter("RegionId", ALIYUN_SMS_REGION_ID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", ALIYUN_SMS_REGISTER_SIGN_NAME);
        request.putQueryParameter("TemplateCode", ALIYUN_SMS_REGISTER_TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam", jsonParam.toJSONString());

        try {
            CommonResponse response = client.getCommonResponse(request);
			JSONObject json = JSONObject.parseObject(response.getData());
            if ("OK".equals(json.get("Message"))) {
                return true;
            } else {
				log.error("阿里云短信发送失败：" + phone);
            }
        } catch (Exception e) {
            log.error("阿里云短信发送异常：" + phone);
            e.printStackTrace();
        }
        return false;
    }
}
