package com.sakura.common.tool;

import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.vo.LoginUserInfoVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
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

    // 注意该字段只有商户用户才有
    public static String getMerchantNo() {
        // 获取登录用户权限信息
        String merchantNo = (String)redisUtil.get(CommonConstant.MERCHANT_NO_TOKEN + TokenUtil.getToken());
        if (StringUtil.isEmpty(merchantNo)) {
            throw new BusinessException(500, "用户信息异常");
        }

        return merchantNo;
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

    public static void saveUserLoginToken(String userId, String token) {
        // 先更新之前登录的token，将失效token删除
        // 目前没有找到可以自动维护token集合的方法，此办法为折中方法，临时使用
        if (redisUtil.hasKey(CommonConstant.USER_TOKEN_SET + userId)) {
            Set<Object> tokens = redisUtil.sGet(CommonConstant.USER_TOKEN_SET + userId);
            tokens.forEach(obj-> {
                // 如果当前token已失效则删除
                if (!redisUtil.hasKey(obj.toString())) {
                    redisUtil.setRemove(CommonConstant.USER_TOKEN_SET + userId, obj.toString());
                }
            });
        }
        // 记录用户登录token，当用户被冻结删除或重置密码等操作时需要清空所有设备上的登录token
        redisUtil.sSetAndTime(CommonConstant.USER_TOKEN_SET + userId, 2 * 60 * 60 , token);
    }

    // 退出当前登录
    public static void logout() {
        // 先获取登录token
        String token = TokenUtil.getToken();
        if (redisUtil.hasKey(token)) {
            redisUtil.del(token);
        }
    }

    // 退出用户所有设备登录
    public static void logoutAll(String userId) {
        // 获取当前登录用户所有登录token
        if (redisUtil.hasKey(CommonConstant.USER_TOKEN_SET + userId)) {
            Set<Object> tokens = redisUtil.sGet(CommonConstant.USER_TOKEN_SET + userId);
            tokens.forEach(obj-> {
                // 删除这些token
                if (redisUtil.hasKey(obj.toString())) {
                    redisUtil.del(obj.toString());
                }
            });

            redisUtil.del(CommonConstant.USER_TOKEN_SET + userId);
        }

    }

}
