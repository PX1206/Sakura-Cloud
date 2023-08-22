package com.sakura.common.tool;

import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.vo.LoginUserInfoVo;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Sakura
 * @date 2023/8/21 15:31
 */
@Component
public class LoginUtil {

    private static RedisUtil redisUtil;

    public LoginUtil(RedisUtil redisUtil) {
        LoginUtil.redisUtil = redisUtil;
    }

    public static LoginUserInfoVo getUserInfoVo() {
        // 先获取登录token
        String token = TokenUtil.getToken();

        // 获取登录用户权限信息
        LoginUserInfoVo userInfoVo = (LoginUserInfoVo)redisUtil.get(token);
        if (userInfoVo == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        return userInfoVo;
    }

    public static String getUserId() {
        // 获取登录用户权限信息
        LoginUserInfoVo userInfoVo = getUserInfoVo();
        if (userInfoVo == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        return userInfoVo.getUserId();
    }

    public static String getUserName() {
        // 获取登录用户权限信息
        LoginUserInfoVo userInfoVo = getUserInfoVo();
        if (userInfoVo == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        return userInfoVo.getName();
    }

    public static Integer getUserType() {
        // 获取登录用户权限信息
        LoginUserInfoVo userInfoVo = getUserInfoVo();
        if (userInfoVo == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        return userInfoVo.getType();
    }

    public static Set<String> getPermissions() {
        // 获取登录用户权限信息
        LoginUserInfoVo userInfoVo = getUserInfoVo();
        if (userInfoVo == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        return userInfoVo.getPermissions();
    }


}
