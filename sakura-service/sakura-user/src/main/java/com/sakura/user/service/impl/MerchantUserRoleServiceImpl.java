package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.LoginUtil;
import com.sakura.user.entity.MerchantRole;
import com.sakura.user.entity.MerchantUser;
import com.sakura.user.entity.MerchantUserRole;
import com.sakura.user.mapper.MerchantRoleMapper;
import com.sakura.user.mapper.MerchantUserMapper;
import com.sakura.user.mapper.MerchantUserRoleMapper;
import com.sakura.user.param.MerchantUserRoleParam;
import com.sakura.user.service.MerchantUserRoleService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 商户用户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantUserRoleServiceImpl extends BaseServiceImpl<MerchantUserRoleMapper, MerchantUserRole> implements MerchantUserRoleService {

    @Autowired
    private MerchantUserRoleMapper merchantUserRoleMapper;
    @Autowired
    private MerchantUserMapper merchantUserMapper;
    @Autowired
    private MerchantRoleMapper merchantRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantUserRole(MerchantUserRoleParam merchantUserRoleParam) throws Exception {
        // 商户用户需要校验用户和角色信息是否为该商户所有
        MerchantUser merchantUser = merchantUserMapper.selectOne(
                Wrappers.<MerchantUser>lambdaQuery()
                        .eq(MerchantUser::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantUser::getUserId, merchantUserRoleParam.getUserId())
                        .ne(MerchantUser::getStatus, 0)
        );
        if (merchantUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        MerchantRole merchantRole = merchantRoleMapper.selectOne(
                Wrappers.<MerchantRole>lambdaQuery()
                        .eq(MerchantRole::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantRole::getId, merchantUserRoleParam.getRoleId())
                        .eq(MerchantRole::getStatus, 1)
        );
        if (merchantRole == null) {
            throw new BusinessException(500, "角色信息异常");
        }

        // 校验是否已存在该角色信息
        MerchantUserRole merchantUserRole = merchantUserRoleMapper.selectOne(
                Wrappers.<MerchantUserRole>lambdaQuery()
                        .eq(MerchantUserRole::getUserId, merchantUserRoleParam.getUserId())
                        .eq(MerchantUserRole::getRoleId, merchantUserRoleParam.getRoleId())
                        .eq(MerchantUserRole::getStatus, 1));
        if (merchantUserRole != null) {
            throw new BusinessException(500, "当前角色已存在");
        }

        merchantUserRole = new MerchantUserRole();
        merchantUserRole.setUserId(merchantUserRoleParam.getUserId());
        merchantUserRole.setRoleId(merchantUserRoleParam.getRoleId());
        merchantUserRole.setStatus(1);

        return super.save(merchantUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantUserRole(MerchantUserRoleParam merchantUserRoleParam) throws Exception {
        // 商户用户需要校验用户和角色信息是否为该商户所有
        MerchantUser merchantUser = merchantUserMapper.selectOne(
                Wrappers.<MerchantUser>lambdaQuery()
                        .eq(MerchantUser::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantUser::getUserId, merchantUserRoleParam.getUserId())
                        .ne(MerchantUser::getStatus, 0)
        );
        if (merchantUser == null) {
            throw new BusinessException(500, "用户信息异常");
        }

        MerchantRole merchantRole = merchantRoleMapper.selectOne(
                Wrappers.<MerchantRole>lambdaQuery()
                        .eq(MerchantRole::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantRole::getId, merchantUserRoleParam.getRoleId())
                        .eq(MerchantRole::getStatus, 1)
        );
        if (merchantRole == null) {
            throw new BusinessException(500, "角色信息异常");
        }

        // 校验是否已存在该角色信息
        MerchantUserRole merchantUserRole = merchantUserRoleMapper.selectOne(
                Wrappers.<MerchantUserRole>lambdaQuery()
                        .eq(MerchantUserRole::getUserId, merchantUserRoleParam.getUserId())
                        .eq(MerchantUserRole::getRoleId, merchantUserRoleParam.getRoleId())
                        .eq(MerchantUserRole::getStatus, 1));
        if (merchantUserRole == null) {
            throw new BusinessException(500, "用户角色信息异常");
        }

        // 将数据置为不可用
        merchantUserRole.setStatus(0);
        merchantUserRole.setUpdateDt(new Date());
        merchantUserRoleMapper.updateById(merchantUserRole);

        return true;
    }

}
