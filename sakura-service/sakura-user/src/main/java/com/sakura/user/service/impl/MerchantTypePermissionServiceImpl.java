package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantTypePermission;
import com.sakura.user.mapper.MerchantTypePermissionMapper;
import com.sakura.user.service.MerchantTypePermissionService;
import com.sakura.user.param.MerchantTypePermissionPageParam;
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
 * 商户类型权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Slf4j
@Service
public class MerchantTypePermissionServiceImpl extends BaseServiceImpl<MerchantTypePermissionMapper, MerchantTypePermission> implements MerchantTypePermissionService {

    @Autowired
    private MerchantTypePermissionMapper merchantTypePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantTypePermission(MerchantTypePermission merchantTypePermission) throws Exception {
        return super.save(merchantTypePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantTypePermission(MerchantTypePermission merchantTypePermission) throws Exception {
        return super.updateById(merchantTypePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantTypePermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<MerchantTypePermission> getMerchantTypePermissionPageList(MerchantTypePermissionPageParam merchantTypePermissionPageParam) throws Exception {
        Page<MerchantTypePermission> page = new PageInfo<>(merchantTypePermissionPageParam, OrderItem.desc(getLambdaColumn(MerchantTypePermission::getCreateDt)));
        LambdaQueryWrapper<MerchantTypePermission> wrapper = new LambdaQueryWrapper<>();
        IPage<MerchantTypePermission> iPage = merchantTypePermissionMapper.selectPage(page, wrapper);
        return new Paging<MerchantTypePermission>(iPage);
    }

}
