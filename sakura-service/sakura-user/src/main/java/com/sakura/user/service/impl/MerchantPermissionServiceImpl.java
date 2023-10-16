package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantPermission;
import com.sakura.user.mapper.MerchantPermissionMapper;
import com.sakura.user.service.MerchantPermissionService;
import com.sakura.user.param.MerchantPermissionPageParam;
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
 * 商户权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantPermissionServiceImpl extends BaseServiceImpl<MerchantPermissionMapper, MerchantPermission> implements MerchantPermissionService {

    @Autowired
    private MerchantPermissionMapper merchantPermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantPermission(MerchantPermission merchantPermission) throws Exception {
        return super.save(merchantPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantPermission(MerchantPermission merchantPermission) throws Exception {
        return super.updateById(merchantPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantPermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<MerchantPermission> getMerchantPermissionPageList(MerchantPermissionPageParam merchantPermissionPageParam) throws Exception {
        Page<MerchantPermission> page = new PageInfo<>(merchantPermissionPageParam, OrderItem.desc(getLambdaColumn(MerchantPermission::getCreateDt)));
        LambdaQueryWrapper<MerchantPermission> wrapper = new LambdaQueryWrapper<>();
        IPage<MerchantPermission> iPage = merchantPermissionMapper.selectPage(page, wrapper);
        return new Paging<MerchantPermission>(iPage);
    }

}
