package com.sakura.user.service;

import com.sakura.user.param.SMSCodeParam;
import com.sakura.user.vo.PictureCodeVo;

/**
 * @author Sakura
 * @date 2023/8/14 14:25
 */
public interface CaptchaService {

    /**
     * @description: 获取图片验证码
     * @author: Sakura
     * @date: 2023/8/14 14:28
     */
    PictureCodeVo getPictureCode() throws Exception;

    /**
     * @description: 获取短信验证码
     * @author: Sakura
     * @date: 2023/8/14 15:13
     */
    String getSMSCode(SMSCodeParam smsCodeParam) throws Exception;


}
