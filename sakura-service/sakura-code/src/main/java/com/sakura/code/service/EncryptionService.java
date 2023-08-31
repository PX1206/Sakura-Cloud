package com.sakura.code.service;

import com.sakura.code.vo.SaltVo;

/**
 * @author Sakura
 * @date 2023/8/14 14:25
 */
public interface EncryptionService {

    /**
     * @description: 获取加密盐
     * @author: Sakura
     * @date: 2023/8/16 14:28
     */
    SaltVo getSalt() throws Exception;

}
