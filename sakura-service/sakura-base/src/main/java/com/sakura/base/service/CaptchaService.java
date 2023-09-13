package com.sakura.base.service;

import com.sakura.base.vo.PictureCodeVo;

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

}
