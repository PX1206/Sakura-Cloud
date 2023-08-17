package com.sakura.user.tool;

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
public class AliyunSmsUtils {

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

    public boolean sendSms(String phone, String paramJson, String signName, String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile(ALIYUN_SMS_REGION_ID, ALIYUN_SMS_ACCESS_KEY_ID, ALIYUN_SMS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(ALIYUN_SMS_SYS_DOMAIN);
        request.setSysVersion(ALIYUN_SMS_SYS_VERSION);
        request.setSysAction(ALIYUN_SMS_SYS_ACTION);
        request.putQueryParameter("RegionId", ALIYUN_SMS_REGION_ID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", paramJson);

        try {
            CommonResponse response = client.getCommonResponse(request);
			JSONObject json = JSONObject.parseObject(response.getData());
            if ("OK".equals(json.get("Message"))) {
                return true;
            } else {
				log.error("阿里云短信发送失败：" + phone + "+" + paramJson);
            }
        } catch (Exception e) {
            log.error("阿里云短信发送异常：" + phone + "+" + paramJson);
            e.printStackTrace();
        }
        return false;
    }
}
