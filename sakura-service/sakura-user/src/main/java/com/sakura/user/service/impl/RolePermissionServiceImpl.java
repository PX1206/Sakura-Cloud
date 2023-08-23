package com.sakura.user.service.impl;

import com.sakura.user.entity.RolePermission;
import com.sakura.user.mapper.RolePermissionMapper;
import com.sakura.user.service.RolePermissionService;
import com.sakura.user.param.RolePermissionPageParam;
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
 * 角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRolePermission(RolePermission rolePermission) throws Exception {
        return super.save(rolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRolePermission(RolePermission rolePermission) throws Exception {
        return super.updateById(rolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRolePermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<RolePermission> getRolePermissionPageList(RolePermissionPageParam rolePermissionPageParam) throws Exception {
        Page<RolePermission> page = new PageInfo<>(rolePermissionPageParam, OrderItem.desc(getLambdaColumn(RolePermission::getCreateDt)));
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        IPage<RolePermission> iPage = rolePermissionMapper.selectPage(page, wrapper);
        return new Paging<RolePermission>(iPage);
    }

}
