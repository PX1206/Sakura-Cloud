package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.*;
import com.sakura.user.entity.Merchant;
import com.sakura.user.entity.MerchantUser;
import com.sakura.user.mapper.MerchantMapper;
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
import com.sakura.user.vo.ChooseMerchantUserVo;
import com.sakura.user.vo.LoginMerchantUserInfoVo;
import com.sakura.user.vo.MerchantUserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private MerchantMapper merchantMapper;

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

        // 退出当前用户所有登录信息
        LoginUtil.logoutAll(merchantUser.getUserId());

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
    public List<ChooseMerchantUserVo> login(LoginParam loginParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(loginParam.getMobile(), loginParam.getSaltKey(), false);
        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(loginParam.getPassword(), loginParam.getSaltKey(), null);

        // 考虑到当前允许同一手机号在多商户任职，所以登录的时候需要校验账号在不同商户的密码
        // 当前逻辑是如果手机号存在多个商户那么只返回密码正确的商户供用户选择
        // 前端进行判断，如果只有一个默认直接登录，如果返回多个让用户手动选择
        // 获取当前登录用户信息
        List<MerchantUser> merchantUsers = merchantUserMapper.selectList(Wrappers.<MerchantUser>lambdaQuery()
                .eq(MerchantUser::getMobile, mobile)
                .ne(MerchantUser::getStatus, 0));

        // 记录能成功登录的商户信息
        List<ChooseMerchantUserVo> chooseMerchantUserVos = new ArrayList<>();
        for (MerchantUser merchantUser:merchantUsers) {
            if (merchantUser.getPassword().equals(SHA256Util.getSHA256Str(password + merchantUser.getSalt()))) {

                // 获取商户信息
                Merchant merchant = merchantMapper.selectOne(
                        Wrappers.<Merchant>lambdaQuery()
                                .eq(Merchant::getMerchantNo, merchantUser.getMerchantNo())
                                .ne(Merchant::getStatus, 0));
                if (merchant != null) {
                    ChooseMerchantUserVo chooseMerchantUserVo = new ChooseMerchantUserVo();
                    chooseMerchantUserVo.setMerchantName(merchant.getName());
                    // 生成一个key并将这个key放入Redis,5分钟有效
                    chooseMerchantUserVo.setKey(UUID.randomUUID().toString());
                    redisUtil.set(chooseMerchantUserVo.getKey(), merchantUser.getUserId(), 5*60);

                    chooseMerchantUserVos.add(chooseMerchantUserVo);
                }
            }
        }

        if (chooseMerchantUserVos.isEmpty()) {
            // 用户每天输错密码不得超过最大限制数
            long errorNum = redisUtil.incr(CommonConstant.PASSWORD_ERROR_NUM + mobile, 1);
            if (errorNum > PASSWORD_ERROR_NUM) {
                // 因存在同一个手机多商户，所以这里无法冻结账号，只能锁定当前手机号
                // 当手机号锁定后用户可改为使用验证码的方式登录
                // 密码多次错误冻结用户账号，保护账号安全，可次日自动解冻也可由管理员手动解冻
                throw new BusinessException(500, "密码输入错误次数过多账号已冻结，请重置密码或次日自行解冻");
            }
            // 设置当前计数器有效期,当前日期到晚上23：59:59
            redisUtil.expire(CommonConstant.PASSWORD_ERROR_NUM +  mobile, DateUtil.timeToMidnight());

            throw new BusinessException(500, "用户名或密码错误"); // 此处写法固定，防止有人用脚本尝试账号
        }

        return chooseMerchantUserVos;
    }

    @Override
    public LoginMerchantUserInfoVo chooseMerchant(ChooseMerchantParam chooseMerchantParam) throws Exception {
        // 校验key是否存在
        if (!redisUtil.hasKey(chooseMerchantParam.getKey())) {
            throw new BusinessException(500, "key已失效，请重新登录");
        }

        // 获取登录用户ID
        String userId = redisUtil.get(chooseMerchantParam.getKey()).toString();

        // 用完删除
        redisUtil.del(chooseMerchantParam.getKey());

        // 获取用户详细信息
        LoginMerchantUserInfoVo loginMerchantUserInfoVo = merchantUserMapper.findLoginMerchantUserInfoVoById(userId);

        if (loginMerchantUserInfoVo.getStatus() != 1) {
            throw new BusinessException(500, "账号状态异常，请联系管理员");
        }

        // 商户用户还需要校验商户状态
        Merchant merchant = merchantMapper.selectOne(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getMerchantNo, loginMerchantUserInfoVo.getMerchantNo())
                        .ne(Merchant::getStatus, 0));
        if (merchant == null) {
            throw new BusinessException(500, "数据异常，请联系管理员");
        }

        if (merchant.getStatus() == 2) {
            throw new BusinessException(500, "商户已被冻结");
        }

        // 登录成功保存token信息
        String token = UUID.randomUUID().toString();
        loginMerchantUserInfoVo.setToken(token);

        loginMerchantUserInfoVo.setType(2);

        // 将信息放入Redis，有效时间2小时
        redisUtil.set(token, loginMerchantUserInfoVo, 2 * 60 * 60);

        // 商户用户需要记录当前用户merchantNo
        redisUtil.set(CommonConstant.MERCHANT_NO_TOKEN + token, loginMerchantUserInfoVo.getMerchantNo());

        // 记录用户登录token
        LoginUtil.saveUserLoginToken(loginMerchantUserInfoVo.getUserId(), token);

        return loginMerchantUserInfoVo;
    }

}
