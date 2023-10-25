package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.*;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.MerchantUser;
import com.sakura.user.mapper.MerchantUserMapper;
import com.sakura.user.param.*;
import com.sakura.user.service.MerchantUserService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.utils.AliyunSmsUtil;
import com.sakura.user.vo.MerchantUserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * 商户用户表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantUserServiceImpl extends BaseServiceImpl<MerchantUserMapper, MerchantUser> implements MerchantUserService {

    @Value("${user.password-error-num}")
    Integer PASSWORD_ERROR_NUM;

    @Autowired
    private MerchantUserMapper merchantUserMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private AliyunSmsUtil aliyunSmsUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addMerchantUser(AddMerchantUserParam addMerchantUserParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(addMerchantUserParam.getMobile(), addMerchantUserParam.getSaltKey(), false);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }
        addMerchantUserParam.setMobile(mobile);

        // 这里有一个问题，因为曾经碰到过一个人在多个商户任职（有登录权限）
        // 所以这里将账号也配置成多商户的模式，如果一个手机号存在于多个商户，那么登录的时候就需要选择要登录的商户

        // 校验当前手机号是否已存在
        MerchantUser merchantUser = merchantUserMapper.selectOne(
                Wrappers.<MerchantUser>lambdaQuery()
                        .eq(MerchantUser::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantUser::getMobile, addMerchantUserParam.getMobile())
                        .ne(MerchantUser::getStatus, 0));
        if (merchantUser != null) {
            throw new BusinessException(500, "用户已存在");
        }

        // 添加用户信息
        merchantUser = new MerchantUser();
        BeanUtils.copyProperties(addMerchantUserParam, merchantUser);
        merchantUser.setUserId(StringUtil.random(32)); // 生成用户ID
        merchantUser.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // 随机生成密码通过短信发送到用户手机号
        String password = StringUtil.random(8);
        boolean sendStatus = aliyunSmsUtil.sendPassword(merchantUser.getMobile(), password);
        if (!sendStatus) {
            throw new BusinessException(500, "短信发送失败，请联系管理员");
        }
        // HA256S加密
        merchantUser.setPassword(SHA256Util.getSHA256Str(password + merchantUser.getSalt()));
        merchantUser.setMerchantNo(LoginUtil.getMerchantNo());
        merchantUser.setStatus(1);

        merchantUserMapper.insert(merchantUser);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantUser(UpdateMerchantUserParam updateMerchantUserParam) throws Exception {
        // 获取当前登录用户信息
        MerchantUser merchantUser = merchantUserMapper.selectOne(
                Wrappers.<MerchantUser>lambdaQuery()
                        .eq(MerchantUser::getUserId, LoginUtil.getUserId())
                        .eq(MerchantUser::getStatus, 1));
        if (merchantUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }
        BeanUtils.copyProperties(updateMerchantUserParam, merchantUser);

        return super.updateById(merchantUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantUser(String userId) throws Exception {
        // 非物理删除，保留用户数据
        // 获取用户信息， 商户只能删除自己商户内的商户且超级管理员无法删除
        MerchantUser merchantUser = merchantUserMapper.selectOne(
                Wrappers.<MerchantUser>lambdaQuery()
                        .eq(MerchantUser::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantUser::getUserId, userId)
                        .ne(MerchantUser::getStatus, 0));
        if (merchantUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        merchantUser.setStatus(0);
        merchantUser.setUpdateDt(new Date());
        merchantUserMapper.updateById(merchantUser);

        return true;
    }

    @Override
    public MerchantUserInfoVo getMerchantUser() throws Exception {
        return merchantUserMapper.findMerchantUserInfoVoById(LoginUtil.getUserId(), 1);
    }

    @Override
    public Paging<MerchantUserInfoVo> getMerchantUserPageList(MerchantUserPageParam merchantUserPageParam) throws Exception {
        Page<MerchantUserInfoVo> page = new PageInfo<>(merchantUserPageParam, OrderItem.desc(getLambdaColumn(MerchantUser::getCreateDt)));
        IPage<MerchantUserInfoVo> iPage = merchantUserMapper.getMerchantUserList(page, LoginUtil.getMerchantNo(), merchantUserPageParam);
        return new Paging<MerchantUserInfoVo>(iPage);
    }

    @Override
    public LoginUserInfoVo login(LoginParam loginParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(loginParam.getMobile(), loginParam.getSaltKey(), false);
        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(loginParam.getPassword(), loginParam.getSaltKey(), null);

        // 获取当前登录用户信息
        MerchantUser merchantUser = merchantUserMapper.selectOne(Wrappers.<MerchantUser>lambdaQuery()
                .eq(MerchantUser::getMobile, mobile)
                .ne(MerchantUser::getStatus, 0));
        if (merchantUser == null || !merchantUser.getPassword().equals(SHA256Util.getSHA256Str(password + merchantUser.getSalt()))) {
            // 添加逻辑，当天输入密码错误冻结用户
            if (merchantUser != null && merchantUser.getStatus() == 1) {
                // 用户每天输错密码不得超过最大限制数
                long errorNum = redisUtil.incr(CommonConstant.PASSWORD_ERROR_NUM + merchantUser.getUserId(), 1);
                if (errorNum > PASSWORD_ERROR_NUM) {

                    // 密码多次错误冻结用户账号，保护账号安全，可次日自动解冻也可由管理员手动解冻
                    merchantUser.setStatus(3); // 临时冻结
                    merchantUser.setUpdateDt(new Date());
                    merchantUserMapper.updateById(merchantUser);

                    throw new BusinessException(500, "密码输入错误次数过多账号已冻结，次日将自动解除");
                }
                // 设置当前计数器有效期,当前日期到晚上23：59:59
                redisUtil.expire(CommonConstant.PASSWORD_ERROR_NUM +  merchantUser.getUserId(), DateUtil.timeToMidnight());
            }
            throw new BusinessException(500, "用户名或密码错误"); // 此处写法固定，防止有人用脚本尝试账号
        }
        if (merchantUser.getStatus() != 1) {
            throw new BusinessException(500, "账号状态异常，请联系管理员");
        }

        // 获取用户详细信息
        LoginUserInfoVo loginUserInfoVo = merchantUserMapper.findLoginMerchantUserInfoVoById(merchantUser.getUserId());

        // 登录成功保存token信息
        String token = UUID.randomUUID().toString();
        loginUserInfoVo.setToken(token);

        loginUserInfoVo.setType(2);

        // 将信息放入Redis，有效时间2小时
        redisUtil.set(token, loginUserInfoVo, 2 * 60 * 60);

        // 商户用户需要记录当前用户merchantNo
        redisUtil.set(CommonConstant.MERCHANT_NO_TOKEN + token, merchantUser.getMerchantNo());

        // 记录用户登录token
        LoginUtil.saveUserLoginToken(merchantUser.getUserId(), token);

        return loginUserInfoVo;
    }

}
