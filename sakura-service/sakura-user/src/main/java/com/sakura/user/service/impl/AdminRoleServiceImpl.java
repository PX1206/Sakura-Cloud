package com.sakura.user.service.impl;

import com.sakura.user.entity.AdminRole;
import com.sakura.user.mapper.AdminRoleMapper;
import com.sakura.user.service.AdminRoleService;
import com.sakura.user.param.AdminRolePageParam;
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
 * admin角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveAdminRole(AdminRole adminRole) throws Exception {
        return super.save(adminRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAdminRole(AdminRole adminRole) throws Exception {
        return super.updateById(adminRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAdminRole(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<AdminRole> getAdminRolePageList(AdminRolePageParam adminRolePageParam) throws Exception {
        Page<AdminRole> page = new PageInfo<>(adminRolePageParam, OrderItem.desc(getLambdaColumn(AdminRole::getCreateDt)));
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        IPage<AdminRole> iPage = adminRoleMapper.selectPage(page, wrapper);
        return new Paging<AdminRole>(iPage);
    }

}
