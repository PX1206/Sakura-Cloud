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

    public static LoginUserInfoVo getLoginUserInfoVo() {
        // 先获取登录token
        String token = TokenUtil.getToken();

        // 获取登录用户权限信息
        LoginUserInfoVo loginUserInfoVo = (LoginUserInfoVo)redisUtil.get(token);
        if (loginUserInfoVo == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        return loginUserInfoVo;
    }

    public static String getUserId() {
        // 获取登录用户权限信息
        LoginUserInfoVo loginUserInfoVo = getLoginUserInfoVo();

        return loginUserInfoVo.getUserId();
    }

    public static String getUserName() {
        // 获取登录用户权限信息
        LoginUserInfoVo loginUserInfoVo = getLoginUserInfoVo();

        return loginUserInfoVo.getName();
    }

    public static Integer getUserType() {
        // 获取登录用户权限信息
        LoginUserInfoVo loginUserInfoVo = getLoginUserInfoVo();

        return loginUserInfoVo.getType();
    }

    public static Set<String> getPermissions() {
        // 获取登录用户权限信息
        LoginUserInfoVo loginUserInfoVo = getLoginUserInfoVo();

        return loginUserInfoVo.getPermissions();
    }

}
