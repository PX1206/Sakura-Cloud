package com.sakura.user.service.impl;

import com.sakura.user.entity.Merchant;
import com.sakura.user.mapper.MerchantMapper;
import com.sakura.user.service.MerchantService;
import com.sakura.user.param.MerchantPageParam;
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
 * 商户表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantServiceImpl extends BaseServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchant(Merchant merchant) throws Exception {
        return super.save(merchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchant(Merchant merchant) throws Exception {
        return super.updateById(merchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchant(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Merchant> getMerchantPageList(MerchantPageParam merchantPageParam) throws Exception {
        Page<Merchant> page = new PageInfo<>(merchantPageParam, OrderItem.desc(getLambdaColumn(Merchant::getCreateDt)));
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        IPage<Merchant> iPage = merchantMapper.selectPage(page, wrapper);
        return new Paging<Merchant>(iPage);
    }

}
