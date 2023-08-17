package com.sakura.user.service;

import com.sakura.user.param.SMSCodeParam;
import com.sakura.user.vo.PictureCodeVo;
import com.sakura.user.vo.SaltVo;

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
     * @description: 获取加密盐
     * @author: Sakura
     * @date: 2023/8/16 14:28
     */
    SaltVo getSalt() throws Exception;

    /**
     * @description: 获取短信验证码
     * @author: Sakura
     * @date: 2023/8/14 15:13
     */
    String getSMSCode(SMSCodeParam smsCodeParam) throws Exception;


}
