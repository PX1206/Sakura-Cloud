package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantRole;
import com.sakura.user.mapper.MerchantRoleMapper;
import com.sakura.user.service.MerchantRoleService;
import com.sakura.user.param.MerchantRolePageParam;
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
 * 商户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantRoleServiceImpl extends BaseServiceImpl<MerchantRoleMapper, MerchantRole> implements MerchantRoleService {

    @Autowired
    private MerchantRoleMapper merchantRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantRole(MerchantRole merchantRole) throws Exception {
        return super.save(merchantRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantRole(MerchantRole merchantRole) throws Exception {
        return super.updateById(merchantRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantRole(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<MerchantRole> getMerchantRolePageList(MerchantRolePageParam merchantRolePageParam) throws Exception {
        Page<MerchantRole> page = new PageInfo<>(merchantRolePageParam, OrderItem.desc(getLambdaColumn(MerchantRole::getCreateDt)));
        LambdaQueryWrapper<MerchantRole> wrapper = new LambdaQueryWrapper<>();
        IPage<MerchantRole> iPage = merchantRoleMapper.selectPage(page, wrapper);
        return new Paging<MerchantRole>(iPage);
    }

}
