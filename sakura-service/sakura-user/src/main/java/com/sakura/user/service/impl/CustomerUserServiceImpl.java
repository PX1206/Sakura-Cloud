package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.*;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.CustomerUser;
import com.sakura.user.mapper.CustomerUserMapper;
import com.sakura.user.param.*;
import com.sakura.user.service.CustomerUserService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.vo.CustomerUserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * 客户用户表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class CustomerUserServiceImpl extends BaseServiceImpl<CustomerUserMapper, CustomerUser> implements CustomerUserService {

    @Value("${user.password-error-num}")
    Integer PASSWORD_ERROR_NUM;

    @Autowired
    private CustomerUserMapper customerUserMapper;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean register(CustomerUserRegisterParam customerUserRegisterParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(customerUserRegisterParam.getMobile(),
                customerUserRegisterParam.getSaltKey(), false);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }
        customerUserRegisterParam.setMobile(mobile);

        // 先校验短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + customerUserRegisterParam.getMobile(),
                customerUserRegisterParam.getSmsCode())) {
            throw new BusinessException(500, "短信验证码错误");
        }

        // 校验当前手机号是否已存在
        CustomerUser customerUser = customerUserMapper.selectOne(Wrappers.<CustomerUser>lambdaQuery()
                .eq(CustomerUser::getMobile, customerUserRegisterParam.getMobile())
                .ne(CustomerUser::getStatus, 0));

        if (customerUser != null) {
            throw new BusinessException(500, "用户已存在");
        }

        // 添加用户信息
        customerUser = new CustomerUser();
        BeanUtils.copyProperties(customerUserRegisterParam, customerUser);
        customerUser.setUserId(StringUtil.random(32)); // 生成用户ID
        customerUser.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(customerUserRegisterParam.getPassword(), customerUserRegisterParam.getSaltKey(), null);
        // HA256S加密
        customerUser.setPassword(SHA256Util.getSHA256Str(password + customerUser.getSalt()));
        customerUser.setStatus(1);

        customerUserMapper.insert(customerUser);

        return true;
    }

    @Override
    public LoginUserInfoVo login(LoginParam loginParam) throws Exception {
//        // 获取真实手机号
//        String mobile = commonUtil.getDecryptStr(loginParam.getMobile(), loginParam.getSaltKey(), false);
//        // 获取用户真实密码
//        String password = commonUtil.getDecryptStr(loginParam.getPassword(), loginParam.getSaltKey(), null);

        // 获取当前登录用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(Wrappers.<CustomerUser>lambdaQuery()
                .eq(CustomerUser::getUserId, "LdUjrFaLiw1aFSgJ87bFXE0IyCgkYhxC")
                .ne(CustomerUser::getStatus, 0));

//        if (customerUser == null || !customerUser.getPassword().equals(SHA256Util.getSHA256Str(password + customerUser.getSalt()))) {
//            // 添加逻辑，当天输入密码错误冻结用户
//            if (customerUser != null && customerUser.getStatus() == 1) {
//                // 用户每天输错密码不得超过最大限制数
//                long errorNum = redisUtil.incr(CommonConstant.PASSWORD_ERROR_NUM + customerUser.getUserId(), 1);
//                if (errorNum > PASSWORD_ERROR_NUM) {
//
//                    // 密码多次错误冻结用户账号，保护账号安全，可次日自动解冻也可由管理员手动解冻
//                    customerUser.setStatus(3); // 临时冻结
//                    customerUser.setUpdateDt(new Date());
//                    customerUserMapper.updateById(customerUser);
//
//                    throw new BusinessException(500, "密码输入错误次数过多账号已冻结，次日将自动解除");
//                }
//                // 设置当前计数器有效期,当前日期到晚上23：59:59
//                redisUtil.expire(CommonConstant.PASSWORD_ERROR_NUM + customerUser.getUserId(), DateUtil.timeToMidnight());
//            }
//            throw new BusinessException(500, "用户名或密码错误"); // 此处写法固定，防止有人用脚本尝试账号
//        }
//
//        if (customerUser.getStatus() != 1) {
//            throw new BusinessException(500, "账号状态异常，请联系管理员");
//        }

        // 获取用户详细信息
        LoginUserInfoVo loginUserInfoVo = customerUserMapper.findLoginCustomerUserInfoVoById(customerUser.getUserId());

        // 登录成功保存token信息
        String token = UUID.randomUUID().toString();
        loginUserInfoVo.setToken(token);

        loginUserInfoVo.setType(1);

        // 将信息放入Redis，有效时间2小时
        redisUtil.set(token, loginUserInfoVo, 2 * 60 * 60);

        // 记录用户登录token，当用户被冻结删除或重置密码等操作时需要清空所有设备上的登录token
        redisUtil.sSetAndTime(CommonConstant.USER_TOKEN_SET + customerUser.getUserId(), 2 * 60 * 60 , token);

        // 记录用户登录token
        LoginUtil.saveUserLoginToken(customerUser.getUserId(), token);

        return loginUserInfoVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCustomerUser(UpdateCustomerUserParam updateCustomerUserParam) throws Exception {
        // 获取当前登录用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getUserId, LoginUtil.getUserId())
                        .eq(CustomerUser::getStatus, 1));
        if (customerUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }
        BeanUtils.copyProperties(updateCustomerUserParam, customerUser);
        customerUser.setUpdateDt(new Date());

        return super.updateById(customerUser);
    }

    @Override
    public boolean updateMobile(UpdateMobileParam updateMobileParam) throws Exception {
        // 获取当前登录用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getUserId, LoginUtil.getUserId())
                        .eq(CustomerUser::getStatus, 1));
        if (customerUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        // 先校验原手机短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + customerUser.getMobile(), updateMobileParam.getOldSmsCode())) {
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
        customerUser.setMobile(mobile);
        customerUser.setUpdateDt(new Date());
        customerUserMapper.updateById(customerUser);

        return true;
    }

    @Override
    public CustomerUserInfoVo getCustomerUserInfo() throws Exception {
        return customerUserMapper.findCustomerUserInfoVoById(LoginUtil.getUserId(), 1);
    }

    @Override
    public Paging<CustomerUserInfoVo> getCustomerUserList(CustomerUserPageParam customerUserPageParam) throws Exception {
        Page<CustomerUserInfoVo> page = new PageInfo<>(customerUserPageParam, OrderItem.desc(getLambdaColumn(CustomerUser::getCreateDt)));
        IPage<CustomerUserInfoVo> iPage = customerUserMapper.getCustomerUserList(page, customerUserPageParam);
        return new Paging(iPage);
    }

    @Override
    public CustomerUserInfoVo getCustomerUser(String userId) throws Exception {
        return customerUserMapper.findCustomerUserInfoVoById(userId, null);
    }

    @Override
    public boolean unfreezeAccount(FreezeAccountParam freezeAccountParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(freezeAccountParam.getKey(), freezeAccountParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        // 获取用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getUserId, freezeAccountParam.getUserId())
                        .ne(CustomerUser::getStatus, 0));
        if (customerUser == null || (customerUser.getStatus() != 2 && customerUser.getStatus() != 3)) {
            throw new BusinessException(500, "用户信息异常");
        }

        customerUser.setStatus(1);
        customerUser.setUpdateDt(new Date());
        customerUserMapper.updateById(customerUser);

        return true;
    }

    @Override
    public boolean freezeAccount(FreezeAccountParam freezeAccountParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(freezeAccountParam.getKey(), freezeAccountParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        // 获取当前登录用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getUserId, freezeAccountParam.getUserId())
                        .eq(CustomerUser::getStatus, 1));
        if (customerUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        customerUser.setStatus(2);
        customerUser.setUpdateDt(new Date());
        customerUserMapper.updateById(customerUser);

        return true;
    }

    @Override
    public boolean resetPassword(ResetPasswordParam resetPasswordParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(resetPasswordParam.getMobile(), resetPasswordParam.getSaltKey(), false);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }

        // 校验短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + mobile, resetPasswordParam.getSmsCode())) {
            throw new BusinessException(500, "短信验证码错误");
        }

        // 获取用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getMobile, mobile)
                        .ne(CustomerUser::getStatus, 0));
        if (customerUser == null) {
            throw new BusinessException(500, "用户不存在，请直接注册登录");
        }
        if (customerUser.getStatus() != 1) {
            throw new BusinessException(500, "用户状态异常，请联系管理员");
        }

        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(resetPasswordParam.getPassword(), resetPasswordParam.getSaltKey(), true);
        // 校验密码合规性
        if (password.length() < 8) {
            throw new BusinessException(500, "密码长度不能小于8位");
        }
        if (!PwdCheckUtil.checkContainDigit(password) || !PwdCheckUtil.checkContainCase(password)) {
            throw new BusinessException(500, "密码必须由数字和字母组成");
        }
        if (PwdCheckUtil.checkSequentialSameChars(password, 4)) {
            throw new BusinessException(500, "存在四个或以上连续相同字符");
        }
        customerUser.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // HA256S加密
        customerUser.setPassword(SHA256Util.getSHA256Str(password + customerUser.getSalt()));
        customerUser.setUpdateDt(new Date());
        customerUserMapper.updateById(customerUser);

        return true;
    }

    @Override
    public boolean AccountCancellation(AccountCancellationParam accountCancellationParam) throws Exception {
        // 获取当前登录用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getUserId, LoginUtil.getUserId())
                        .ne(CustomerUser::getStatus, 0));

        if (customerUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }
        if (customerUser.getStatus() != 1) {
            throw new BusinessException(500, "用户状态异常，请联系管理员");
        }

        // 校验短信验证码是否正确
        if (!commonUtil.checkCode(CommonConstant.SMS_CODE + customerUser.getMobile(), accountCancellationParam.getSmsCode())) {
            throw new BusinessException(500, "短信验证码错误");
        }

        // 用户注销后将无法继续使用
        customerUser.setStatus(0);
        customerUser.setUpdateDt(new Date());
        customerUserMapper.updateById(customerUser);

        // 清除当前登录用户token（此处存在一个问题，如果当前账号在多个地方登录，其它地方未退出，待完善！！！！！！！！！）
        redisUtil.del(TokenUtil.getToken());

        return true;
    }

    @Override
    public boolean deleteCustomerUser(DeleteCustomerUserParam deleteCustomerUserParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(deleteCustomerUserParam.getKey(), deleteCustomerUserParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        // 获取用户信息
        CustomerUser customerUser = customerUserMapper.selectOne(
                Wrappers.<CustomerUser>lambdaQuery()
                        .eq(CustomerUser::getUserId, deleteCustomerUserParam.getUserId())
                        .ne(CustomerUser::getStatus, 0));

        if (customerUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        // 用户注销后将无法继续使用
        customerUser.setStatus(0);
        customerUser.setUpdateDt(new Date());
        customerUserMapper.updateById(customerUser);

        return false;
    }

}
