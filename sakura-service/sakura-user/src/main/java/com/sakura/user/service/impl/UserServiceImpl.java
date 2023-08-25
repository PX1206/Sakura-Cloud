package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.DateUtil;
import com.sakura.common.tool.LoginUtil;
import com.sakura.common.tool.SHA256Util;
import com.sakura.common.tool.StringUtil;
import com.sakura.user.entity.User;
import com.sakura.user.mapper.UserMapper;
import com.sakura.user.param.*;
import com.sakura.user.service.UserService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.tool.CommonUtil;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * 用户表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Value("${user.password-error-num}")
    Integer PASSWORD_ERROR_NUM;

    @Autowired
    CommonUtil commonUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(UserRegisterParam userRegisterParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(userRegisterParam.getMobile(), userRegisterParam.getSaltKey(), false);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }
        userRegisterParam.setMobile(mobile);

        // 先校验短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + userRegisterParam.getMobile(), userRegisterParam.getSmsCode())) {
            throw new BusinessException(500, "短信验证码错误");
        }

        // 校验当前手机号是否已存在
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().
                eq(User::getMobile, userRegisterParam.getMobile()));
        if (user != null) {
            throw new BusinessException(500, "用户已存在");
        }

        // 添加用户信息
        user = new User();
        BeanUtils.copyProperties(userRegisterParam, user);
        user.setUserId(StringUtil.random(32)); // 生成用户ID
        user.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(userRegisterParam.getPassword(), userRegisterParam.getSaltKey(), null);
        // HA256S加密
        user.setPassword(SHA256Util.getSHA256Str(password + user.getSalt()));
        user.setStatus(1);

        userMapper.insert(user);

        return true;
    }

    @Override
    public LoginUserInfoVo login(LoginParam loginParam) throws Exception {
//        // 获取真实手机号
//        String mobile = commonUtil.getDecryptStr(loginParam.getMobile(), loginParam.getSaltKey(), false);
//        // 获取用户真实密码
//        String password = commonUtil.getDecryptStr(loginParam.getPassword(), loginParam.getSaltKey(), null);
//
//        // 获取当前登录用户信息
//        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
//                .eq(User::getMobile, mobile));
//        if (user == null || !user.getPassword().equals(SHA256Util.getSHA256Str(password + user.getSalt()))) {
//            // 添加逻辑，当天输入密码错误冻结用户
//            if (user != null && user.getStatus() == 1) {
//                // 用户每天输错密码不得超过最大限制数
//                long errorNum = redisUtil.incr(CommonConstant.PASSWORD_ERROR_NUM + user.getUserId(), 1);
//                if (errorNum > PASSWORD_ERROR_NUM) {
//
//                    // 密码多次错误冻结用户账号，保护账号安全，可次日自动解冻也可由管理员手动解冻
//                    user.setStatus(3); // 临时冻结
//                    user.setUpdateDt(new Date());
//                    userMapper.updateById(user);
//
//                    throw new BusinessException(500, "密码输入错误次数过多账号已冻结，次日将自动解除");
//                }
//                // 设置当前计数器有效期,当前日期到晚上23：59:59
//                redisUtil.expire(CommonConstant.PASSWORD_ERROR_NUM +  user.getUserId(), DateUtil.timeToMidnight());
//            }
//            throw new BusinessException(500, "用户名或密码错误"); // 此处写法固定，防止有人用脚本尝试账号
//        }
//        if (user.getStatus() != 1) {
//            throw new BusinessException(500, "账号状态异常，请联系管理员");
//        }

        // 获取用户详细信息
        LoginUserInfoVo loginUserInfoVo = userMapper.findLoginUserInfoVoById("LdUjrFaLiw1aFSgJ87bFXE0IyCgkYhxC");

        // 登录成功保存token信息
        String token = UUID.randomUUID().toString();
        loginUserInfoVo.setToken(token);

        loginUserInfoVo.setType(1);

        // 将信息放入Redis，有效时间2小时
        redisUtil.set(token, loginUserInfoVo, 60 * 60 * 2);


        return loginUserInfoVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UpdateUserParam updateUserParam) throws Exception {
        // 获取当前登录用户信息
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserId, LoginUtil.getUserId())
                        .eq(User::getStatus, 1));
        if (user == null) {
            throw new BusinessException(500, "用户信息异常");
        }
        BeanUtils.copyProperties(updateUserParam, user);
        user.setUpdateDt(new Date());

        return super.updateById(user);
    }

    @Override
    public boolean updateMobile(UpdateMobileParam updateMobileParam) throws Exception {
        // 获取当前登录用户信息
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserId, LoginUtil.getUserId())
                        .eq(User::getStatus, 1));
        if (user == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        // 先校验原手机短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + user.getMobile(), updateMobileParam.getOldSmsCode())) {
            throw new BusinessException(500, "原手机号短信验证码错误");
        }

        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(updateMobileParam.getNewMobile(), updateMobileParam.getSaltKey(), null);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }
        // 校验新手机短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + mobile, updateMobileParam.getNewSmsCode())) {
            throw new BusinessException(500, "新手机号短信验证码错误");
        }

        // 保存用户信息
        user.setMobile(mobile);
        user.setUpdateDt(new Date());
        userMapper.updateById(user);

        return true;
    }

    @Override
    public UserInfoVo getUserInfo() throws Exception {
        UserInfoVo userInfoVo = userMapper.findUserInfoVoById(LoginUtil.getUserId(), 1);
        return userInfoVo;
    }

    @Override
    public Paging<UserInfoVo> getUserList(UserPageParam userPageParam) throws Exception {
        Page<UserInfoVo> page = new PageInfo<>(userPageParam, OrderItem.desc(getLambdaColumn(User::getCreateDt)));
        IPage<UserInfoVo> iPage = userMapper.getUserList(page, userPageParam);
        return new Paging(iPage);
    }

    @Override
    public UserInfoVo getUser(String userId) throws Exception {
        UserInfoVo userInfoVo = userMapper.findUserInfoVoById(userId, null);
        return userInfoVo;
    }

    @Override
    public boolean unfreezeAccount(FreezeAccountParam freezeAccountParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(freezeAccountParam.getKey(), freezeAccountParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        // 获取当前登录用户信息
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserId, freezeAccountParam.getUserId()));
        if (user == null || (user.getStatus() != 2 && user.getStatus() != 3)) {
            throw new BusinessException(500, "用户信息异常");
        }

        user.setStatus(1);
        user.setUpdateDt(new Date());
        userMapper.updateById(user);

        return true;
    }

    @Override
    public boolean freezeAccount(FreezeAccountParam freezeAccountParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(freezeAccountParam.getKey(), freezeAccountParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        // 获取当前登录用户信息
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserId, freezeAccountParam.getUserId())
                        .eq(User::getStatus, 1));
        if (user == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        user.setStatus(2);
        user.setUpdateDt(new Date());
        userMapper.updateById(user);

        return true;
    }

}
