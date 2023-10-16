package com.sakura.user.service.impl;

import com.sakura.user.entity.AdminRolePermission;
import com.sakura.user.mapper.AdminRolePermissionMapper;
import com.sakura.user.service.AdminRolePermissionService;
import com.sakura.user.param.AdminRolePermissionPageParam;
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
 * admin角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class AdminRolePermissionServiceImpl extends BaseServiceImpl<AdminRolePermissionMapper, AdminRolePermission> implements AdminRolePermissionService {

    @Autowired
    private AdminRolePermissionMapper adminRolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveAdminRolePermission(AdminRolePermission adminRolePermission) throws Exception {
        return super.save(adminRolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAdminRolePermission(AdminRolePermission adminRolePermission) throws Exception {
        return super.updateById(adminRolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAdminRolePermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<AdminRolePermission> getAdminRolePermissionPageList(AdminRolePermissionPageParam adminRolePermissionPageParam) throws Exception {
        Page<AdminRolePermission> page = new PageInfo<>(adminRolePermissionPageParam, OrderItem.desc(getLambdaColumn(AdminRolePermission::getCreateDt)));
        LambdaQueryWrapper<AdminRolePermission> wrapper = new LambdaQueryWrapper<>();
        IPage<AdminRolePermission> iPage = adminRolePermissionMapper.selectPage(page, wrapper);
        return new Paging<AdminRolePermission>(iPage);
    }

}
