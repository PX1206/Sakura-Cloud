package com.sakura.user.service.impl;

import com.sakura.user.entity.UserRole;
import com.sakura.user.mapper.UserRoleMapper;
import com.sakura.user.service.UserRoleService;
import com.sakura.user.param.UserRolePageParam;
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
 * 用户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUserRole(UserRole userRole) throws Exception {
        return super.save(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserRole(UserRole userRole) throws Exception {
        return super.updateById(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUserRole(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<UserRole> getUserRolePageList(UserRolePageParam userRolePageParam) throws Exception {
        Page<UserRole> page = new PageInfo<>(userRolePageParam, OrderItem.desc(getLambdaColumn(UserRole::getCreateDt)));
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        IPage<UserRole> iPage = userRoleMapper.selectPage(page, wrapper);
        return new Paging<UserRole>(iPage);
    }

}
