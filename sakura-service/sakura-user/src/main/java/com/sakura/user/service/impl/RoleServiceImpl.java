package com.sakura.user.service.impl;

import com.sakura.user.entity.Role;
import com.sakura.user.mapper.RoleMapper;
import com.sakura.user.service.RoleService;
import com.sakura.user.param.RolePageParam;
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
 * 角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRole(Role role) throws Exception {
        return super.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRole(Role role) throws Exception {
        return super.updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRole(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Role> getRolePageList(RolePageParam rolePageParam) throws Exception {
        Page<Role> page = new PageInfo<>(rolePageParam, OrderItem.desc(getLambdaColumn(Role::getCreateDt)));
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        IPage<Role> iPage = roleMapper.selectPage(page, wrapper);
        return new Paging<Role>(iPage);
    }

}
