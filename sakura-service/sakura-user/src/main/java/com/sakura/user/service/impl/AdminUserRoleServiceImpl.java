package com.sakura.user.service.impl;

import com.sakura.user.entity.AdminUserRole;
import com.sakura.user.mapper.AdminUserRoleMapper;
import com.sakura.user.service.AdminUserRoleService;
import com.sakura.user.param.AdminUserRolePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
    public boolean saveAdminUserRole(AdminUserRole adminUserRole) throws Exception {
        return super.save(adminUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAdminUserRole(AdminUserRole adminUserRole) throws Exception {
        return super.updateById(adminUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAdminUserRole(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<AdminUserRole> getAdminUserRolePageList(AdminUserRolePageParam adminUserRolePageParam) throws Exception {
        Page<AdminUserRole> page = new PageInfo<>(adminUserRolePageParam, OrderItem.desc(getLambdaColumn(AdminUserRole::getCreateDt)));
        LambdaQueryWrapper<AdminUserRole> wrapper = new LambdaQueryWrapper<>();
        IPage<AdminUserRole> iPage = adminUserRoleMapper.selectPage(page, wrapper);
        return new Paging<AdminUserRole>(iPage);
    }

}
