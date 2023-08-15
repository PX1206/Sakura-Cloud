package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.SHA256Util;
import com.sakura.common.tool.StringUtil;
import com.sakura.user.entity.User;
import com.sakura.user.mapper.UserMapper;
import com.sakura.user.param.UserRegisterParam;
import com.sakura.user.service.UserService;
import com.sakura.user.param.UserPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(UserRegisterParam userRegisterParam) throws Exception {
        // 先校验短信验证码是否正确
        if (!redisUtil.hasKey(CommonConstant.SMS_CODE + userRegisterParam.getMobile())) {
            throw new BusinessException(500, "短信验证码已过期");
        }
        String smsCode = redisUtil.get("sms_code_" + userRegisterParam.getMobile()).toString();
        if (StringUtil.isEmpty(smsCode) || !smsCode.equals(userRegisterParam.getSmsCode())) {
            throw new BusinessException(500, "短信验证码错误");
        }
        // 验证码使用后就删除
        redisUtil.del("sms_code_" + userRegisterParam.getMobile());

        // 校验当前手机号是否已存在
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().
                eq(User::getMobile, userRegisterParam.getMobile()).
                eq(User::getStatus, 1));
        if (user != null) {
            throw new BusinessException(500, "用户已存在");
        }

        // 添加用户信息
        user = new User();
        BeanUtils.copyProperties(userRegisterParam, user);
        user.setUserId(StringUtil.random(32)); // 生成用户ID
        user.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // 如果用户未设置密码那么初始密码为123456
        if (StringUtil.isEmpty(userRegisterParam.getPassword())) {
            // 原始密码为123456
            // 前端传输时先加密SHA256(123456)
            userRegisterParam.setPassword(SHA256Util.getSHA256Str("123456"));
        }
        // 二次加密
        user.setPassword(SHA256Util.getSHA256Str(userRegisterParam.getPassword() + user.getSalt()));
        user.setStatus(1);

        userMapper.insert(user);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(User user) throws Exception {
        return super.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(User user) throws Exception {
        return super.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUser(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<User> getUserPageList(UserPageParam userPageParam) throws Exception {
        Page<User> page = new PageInfo<>(userPageParam, OrderItem.desc(getLambdaColumn(User::getCreateDt)));
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        return new Paging<User>(iPage);
    }

}
