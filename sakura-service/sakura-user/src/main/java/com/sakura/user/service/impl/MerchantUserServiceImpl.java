package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantUser;
import com.sakura.user.mapper.MerchantUserMapper;
import com.sakura.user.service.MerchantUserService;
import com.sakura.user.param.MerchantUserPageParam;
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
 * 商户用户表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantUserServiceImpl extends BaseServiceImpl<MerchantUserMapper, MerchantUser> implements MerchantUserService {

    @Autowired
    private MerchantUserMapper merchantUserMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantUser(MerchantUser merchantUser) throws Exception {
        return super.save(merchantUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantUser(MerchantUser merchantUser) throws Exception {
        return super.updateById(merchantUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantUser(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<MerchantUser> getMerchantUserPageList(MerchantUserPageParam merchantUserPageParam) throws Exception {
        Page<MerchantUser> page = new PageInfo<>(merchantUserPageParam, OrderItem.desc(getLambdaColumn(MerchantUser::getCreateDt)));
        LambdaQueryWrapper<MerchantUser> wrapper = new LambdaQueryWrapper<>();
        IPage<MerchantUser> iPage = merchantUserMapper.selectPage(page, wrapper);
        return new Paging<MerchantUser>(iPage);
    }

}
