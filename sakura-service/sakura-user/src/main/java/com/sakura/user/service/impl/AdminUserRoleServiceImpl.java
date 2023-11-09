package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.user.entity.AdminUserRole;
import com.sakura.user.mapper.AdminUserRoleMapper;
import com.sakura.user.param.AdminUserRoleParam;
import com.sakura.user.service.AdminUserRoleService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * admin用户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class AdminUserRoleServiceImpl extends BaseServiceImpl<AdminUserRoleMapper, AdminUserRole> implements AdminUserRoleService {

    @Autowired
    private AdminUserRoleMapper adminUserRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveAdminUserRole(AdminUserRoleParam adminUserRoleParam) throws Exception {
        // 校验是否已存在该角色信息
        AdminUserRole adminUserRole = adminUserRoleMapper.selectOne(
                Wrappers.<AdminUserRole>lambdaQuery()
                        .eq(AdminUserRole::getUserId, adminUserRoleParam.getUserId())
                        .eq(AdminUserRole::getRoleId, adminUserRoleParam.getRoleId())
                        .eq(AdminUserRole::getStatus, 1));
        if (adminUserRole != null) {
            throw new BusinessException(500, "当前角色已存在");
        }

        adminUserRole = new AdminUserRole();
        adminUserRole.setUserId(adminUserRoleParam.getUserId());
        adminUserRole.setRoleId(adminUserRoleParam.getRoleId());
        adminUserRole.setStatus(1);

        return super.save(adminUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAdminUserRole(AdminUserRoleParam adminUserRoleParam) throws Exception {
        // 校验是否已存在该角色信息
        AdminUserRole adminUserRole = adminUserRoleMapper.selectOne(
                Wrappers.<AdminUserRole>lambdaQuery()
                        .eq(AdminUserRole::getUserId, adminUserRoleParam.getUserId())
                        .eq(AdminUserRole::getRoleId, adminUserRoleParam.getRoleId())
                        .eq(AdminUserRole::getStatus, 1));
        if (adminUserRole == null) {
            throw new BusinessException(500, "用户角色信息异常");
        }

        // 将数据置为不可用
        adminUserRole.setStatus(0);
        adminUserRole.setUpdateDt(new Date());
        adminUserRoleMapper.updateById(adminUserRole);

        return true;
    }

}
