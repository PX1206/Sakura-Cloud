package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantUserRole;
import com.sakura.user.mapper.MerchantUserRoleMapper;
import com.sakura.user.service.MerchantUserRoleService;
import com.sakura.user.param.MerchantUserRolePageParam;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantUserRole(MerchantUserRole merchantUserRole) throws Exception {
        return super.save(merchantUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantUserRole(MerchantUserRole merchantUserRole) throws Exception {
        return super.updateById(merchantUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantUserRole(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<MerchantUserRole> getMerchantUserRolePageList(MerchantUserRolePageParam merchantUserRolePageParam) throws Exception {
        Page<MerchantUserRole> page = new PageInfo<>(merchantUserRolePageParam, OrderItem.desc(getLambdaColumn(MerchantUserRole::getCreateDt)));
        LambdaQueryWrapper<MerchantUserRole> wrapper = new LambdaQueryWrapper<>();
        IPage<MerchantUserRole> iPage = merchantUserRoleMapper.selectPage(page, wrapper);
        return new Paging<MerchantUserRole>(iPage);
    }

}
