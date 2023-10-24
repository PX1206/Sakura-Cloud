package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.*;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.AdminUser;
import com.sakura.user.mapper.AdminUserMapper;
import com.sakura.user.param.AddAdminUserParam;
import com.sakura.user.param.LoginParam;
import com.sakura.user.param.UpdateAdminUserParam;
import com.sakura.user.service.AdminUserService;
import com.sakura.user.param.AdminUserPageParam;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.utils.AliyunSmsUtil;
import com.sakura.user.vo.AdminUserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * admin用户表 服务实现类
 * 后台管理用户
 * 由管理人员手动添加
 * 密码重置手机号更换可由相关管理员操作也可以由用户自己操作
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Value("${user.password-error-num}")
    Integer PASSWORD_ERROR_NUM;

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private AliyunSmsUtil aliyunSmsUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveAdminUser(AddAdminUserParam addAdminUserParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(addAdminUserParam.getMobile(), addAdminUserParam.getSaltKey(), false);
        if (!StringUtil.isValidPhoneNumber(mobile)) {
            throw new BusinessException(500, "手机号格式异常");
        }
        addAdminUserParam.setMobile(mobile);

        // 校验当前手机号是否已存在
        AdminUser adminUser = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery()
                        .eq(AdminUser::getMobile, addAdminUserParam.getMobile())
                        .ne(AdminUser::getStatus, 0));
        if (adminUser != null) {
            throw new BusinessException(500, "用户已存在");
        }

        // 添加用户信息
        adminUser = new AdminUser();
        BeanUtils.copyProperties(addAdminUserParam, adminUser);
        adminUser.setUserId(StringUtil.random(32)); // 生成用户ID
        adminUser.setSalt(SHA256Util.getSHA256Str(UUID.randomUUID().toString())); // 随机生成盐
        // 随机生成密码通过短信发送到用户手机号
        String password = StringUtil.random(8);
        boolean sendStatus = aliyunSmsUtil.sendPassword(adminUser.getMobile(), password);
        if (!sendStatus) {
            throw new BusinessException(500, "短信发送失败，请联系管理员");
        }
        // HA256S加密
        adminUser.setPassword(SHA256Util.getSHA256Str(password + adminUser.getSalt()));
        adminUser.setStatus(1);

        adminUserMapper.insert(adminUser);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAdminUser(UpdateAdminUserParam updateAdminUserParam) throws Exception {
        // 获取当前登录用户信息
        AdminUser adminUser = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery()
                        .eq(AdminUser::getUserId, LoginUtil.getUserId())
                        .eq(AdminUser::getStatus, 1));
        if (adminUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }
        BeanUtils.copyProperties(updateAdminUserParam, adminUser);

        return super.updateById(adminUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAdminUser(String userId) throws Exception {
        // 非物理删除，保留用户数据
        // 获取用户信息
        AdminUser adminUser = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery()
                        .eq(AdminUser::getUserId, userId)
                        .ne(AdminUser::getStatus, 0));
        if (adminUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        adminUser.setStatus(0);
        adminUser.setUpdateDt(new Date());
        adminUserMapper.updateById(adminUser);

        return true;
    }

    @Override
    public AdminUserInfoVo getAdminUser() throws Exception {
        return adminUserMapper.findAdminUserInfoVoById(LoginUtil.getUserId(), 1);
    }

    @Override
    public Paging<AdminUserInfoVo> getAdminUserPageList(AdminUserPageParam adminUserPageParam) throws Exception {
        Page<AdminUserInfoVo> page = new PageInfo<>(adminUserPageParam, OrderItem.desc(getLambdaColumn(AdminUser::getCreateDt)));
        IPage<AdminUserInfoVo> iPage = adminUserMapper.getAdminUserList(page, adminUserPageParam);
        return new Paging<AdminUserInfoVo>(iPage);
    }

    @Override
    public LoginUserInfoVo login(LoginParam loginParam) throws Exception {
        // 获取真实手机号
        String mobile = commonUtil.getDecryptStr(loginParam.getMobile(), loginParam.getSaltKey(), false);
        // 获取用户真实密码
        String password = commonUtil.getDecryptStr(loginParam.getPassword(), loginParam.getSaltKey(), null);

        // 获取当前登录用户信息
        AdminUser adminUser = adminUserMapper.selectOne(Wrappers.<AdminUser>lambdaQuery()
                .eq(AdminUser::getMobile, mobile)
                .ne(AdminUser::getStatus, 0));
        if (adminUser == null || !adminUser.getPassword().equals(SHA256Util.getSHA256Str(password + adminUser.getSalt()))) {
            // 添加逻辑，当天输入密码错误冻结用户
            if (adminUser != null && adminUser.getStatus() == 1) {
                // 用户每天输错密码不得超过最大限制数
                long errorNum = redisUtil.incr(CommonConstant.PASSWORD_ERROR_NUM + adminUser.getUserId(), 1);
                if (errorNum > PASSWORD_ERROR_NUM) {

                    // 密码多次错误冻结用户账号，保护账号安全，可次日自动解冻也可由管理员手动解冻
                    adminUser.setStatus(3); // 临时冻结
                    adminUser.setUpdateDt(new Date());
                    adminUserMapper.updateById(adminUser);

                    throw new BusinessException(500, "密码输入错误次数过多账号已冻结，次日将自动解除");
                }
                // 设置当前计数器有效期,当前日期到晚上23：59:59
                redisUtil.expire(CommonConstant.PASSWORD_ERROR_NUM +  adminUser.getUserId(), DateUtil.timeToMidnight());
            }
            throw new BusinessException(500, "用户名或密码错误"); // 此处写法固定，防止有人用脚本尝试账号
        }
        if (adminUser.getStatus() != 1) {
            throw new BusinessException(500, "账号状态异常，请联系管理员");
        }

        // 获取用户详细信息
        LoginUserInfoVo loginUserInfoVo = adminUserMapper.findLoginAdminUserInfoVoById(adminUser.getUserId());

        // 登录成功保存token信息
        String token = UUID.randomUUID().toString();
        loginUserInfoVo.setToken(token);

        loginUserInfoVo.setType(3);

        // 将信息放入Redis，有效时间2小时
        redisUtil.set(token, loginUserInfoVo, 2 * 60 * 60);

        // 记录用户登录token
        LoginUtil.saveUserLoginToken(adminUser.getUserId(), token);

        return loginUserInfoVo;
    }

}
