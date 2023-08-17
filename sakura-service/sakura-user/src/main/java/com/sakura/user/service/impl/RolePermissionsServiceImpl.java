package com.sakura.user.service.impl;

import com.sakura.user.entity.RolePermissions;
import com.sakura.user.mapper.RolePermissionsMapper;
import com.sakura.user.service.RolePermissionsService;
import com.sakura.user.param.RolePermissionsPageParam;
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
public class RolePermissionsServiceImpl extends BaseServiceImpl<RolePermissionsMapper, RolePermissions> implements RolePermissionsService {

    @Autowired
    private RolePermissionsMapper rolePermissionsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRolePermissions(RolePermissions rolePermissions) throws Exception {
        return super.save(rolePermissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRolePermissions(RolePermissions rolePermissions) throws Exception {
        return super.updateById(rolePermissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRolePermissions(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<RolePermissions> getRolePermissionsPageList(RolePermissionsPageParam rolePermissionsPageParam) throws Exception {
        Page<RolePermissions> page = new PageInfo<>(rolePermissionsPageParam, OrderItem.desc(getLambdaColumn(RolePermissions::getCreateDt)));
        LambdaQueryWrapper<RolePermissions> wrapper = new LambdaQueryWrapper<>();
        IPage<RolePermissions> iPage = rolePermissionsMapper.selectPage(page, wrapper);
        return new Paging<RolePermissions>(iPage);
    }

}
