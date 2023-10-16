package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantRolePermission;
import com.sakura.user.mapper.MerchantRolePermissionMapper;
import com.sakura.user.service.MerchantRolePermissionService;
import com.sakura.user.param.MerchantRolePermissionPageParam;
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
 * 商户角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantRolePermissionServiceImpl extends BaseServiceImpl<MerchantRolePermissionMapper, MerchantRolePermission> implements MerchantRolePermissionService {

    @Autowired
    private MerchantRolePermissionMapper merchantRolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantRolePermission(MerchantRolePermission merchantRolePermission) throws Exception {
        return super.save(merchantRolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantRolePermission(MerchantRolePermission merchantRolePermission) throws Exception {
        return super.updateById(merchantRolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantRolePermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<MerchantRolePermission> getMerchantRolePermissionPageList(MerchantRolePermissionPageParam merchantRolePermissionPageParam) throws Exception {
        Page<MerchantRolePermission> page = new PageInfo<>(merchantRolePermissionPageParam, OrderItem.desc(getLambdaColumn(MerchantRolePermission::getCreateDt)));
        LambdaQueryWrapper<MerchantRolePermission> wrapper = new LambdaQueryWrapper<>();
        IPage<MerchantRolePermission> iPage = merchantRolePermissionMapper.selectPage(page, wrapper);
        return new Paging<MerchantRolePermission>(iPage);
    }

}
