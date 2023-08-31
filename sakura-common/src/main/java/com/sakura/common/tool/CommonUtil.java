package com.sakura.common.tool;

import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.AESUtil;
import com.sakura.common.tool.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sakura
 * @date 2023/8/16 15:40
 */
@Component
public class CommonUtil {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @description: 根据saltKey解密字符串
     * @param str 待解密字符串
     * @param saltKey getSalt()方法获取的saltKey
     * @param delKey 是否删除key，默认为删除
     * @author: Sakura
     * @date: 2023/8/16 15:47
     */
    public String getDecryptStr(String str, String saltKey, Boolean delKey) {
        if (!redisUtil.hasKey(saltKey)) {
            throw new BusinessException(500, "saltKey已失效");
        }
        String salt = redisUtil.get(saltKey).toString();
        String decryptStr = AESUtil.decrypt(str, salt);
        if (StringUtil.isEmpty(decryptStr)) {
            throw new BusinessException(500, "加密数据异常");
        }
        // 删除加密盐
        if (delKey == null || delKey) {
            redisUtil.del(saltKey);
        }

        return decryptStr;
    }

    /**
     * @description: 校验验证码是否正确
     * @param key 验证码key
     * @param code 验证码
     * @author: Sakura
     * @date: 2023/8/16 15:47
     */
    public boolean checkCode(String key, String code) {
        if (!redisUtil.hasKey(key)) {
            return false;
        }
        String checkCode = redisUtil.get(key).toString();
        if (StringUtil.isEmpty(checkCode) || !checkCode.equals(code)) {
            return false;
        }
        // 验证码使用后就删除
        redisUtil.del(key);

        return true;
    }
}
