package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.user.entity.Merchant;
import com.sakura.user.entity.MerchantType;
import com.sakura.user.mapper.MerchantMapper;
import com.sakura.user.mapper.MerchantTypeMapper;
import com.sakura.user.param.MerchantTypeParam;
import com.sakura.user.service.MerchantTypeService;
import com.sakura.user.param.MerchantTypePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.vo.MerchantTypeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 商户类型表 服务实现类
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Slf4j
@Service
public class MerchantTypeServiceImpl extends BaseServiceImpl<MerchantTypeMapper, MerchantType> implements MerchantTypeService {

    @Autowired
    private MerchantTypeMapper merchantTypeMapper;
    @Autowired
    private MerchantMapper merchantMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantType(MerchantTypeParam merchantTypeParam) throws Exception {
        // 校验是否存在同名类型
        MerchantType merchantType = merchantTypeMapper.selectOne(
                Wrappers.<MerchantType>lambdaQuery()
                        .eq(MerchantType::getName, merchantTypeParam.getName())
                        .eq(MerchantType::getStatus, 1));
        if (merchantType != null) {
            throw new BusinessException(500, "已存在相同名称类型");
        }
        merchantType = new MerchantType();
        BeanUtils.copyProperties(merchantTypeParam, merchantType);
        merchantType.setStatus(1);

        return super.save(merchantType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantType(MerchantTypeParam merchantTypeParam) throws Exception {
        // 校验是否存在同名类型
        MerchantType merchantType = merchantTypeMapper.selectOne(
                Wrappers.<MerchantType>lambdaQuery()
                        .eq(MerchantType::getName, merchantTypeParam.getName())
                        .ne(MerchantType::getId, merchantTypeParam.getId())
                        .eq(MerchantType::getStatus, 1));
        if (merchantType != null) {
            throw new BusinessException(500, "已存在相同名称类型");
        }
        merchantType = new MerchantType();
        BeanUtils.copyProperties(merchantTypeParam, merchantType);

        return super.updateById(merchantType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantType(Long id) throws Exception {
        // 删除之前校验一下是否有该类型商户，存在则不让删除
        Integer merchantNum = merchantMapper.selectCount(
                Wrappers.<Merchant>lambdaQuery()
                        .eq(Merchant::getTypeId, id)
                        .eq(Merchant::getStatus, 1));
        if (merchantNum > 0) {
            throw new BusinessException(500, "当前类型存在商户数据无法删除");
        }

        MerchantType merchantType = merchantTypeMapper.selectById(id);
        if (merchantType == null) {
            throw new BusinessException(500, "类型信息异常");
        }

        merchantType.setStatus(0);
        merchantType.setUpdateDt(new Date());
        merchantTypeMapper.updateById(merchantType);

        return true;
    }

    @Override
    public MerchantTypeVo getMerchantType(Long id) throws Exception {
        MerchantType merchantType = merchantTypeMapper.selectById(id);
        if (merchantType == null) {
            throw new BusinessException(500, "类型信息异常");
        }
        MerchantTypeVo merchantTypeVo = new MerchantTypeVo();
        BeanUtils.copyProperties(merchantType, merchantTypeVo);

        return merchantTypeVo;
    }

    @Override
    public Paging<MerchantTypeVo> getMerchantTypePageList(MerchantTypePageParam merchantTypePageParam) throws Exception {
        Page<MerchantTypeVo> page = new PageInfo<>(merchantTypePageParam, OrderItem.desc(getLambdaColumn(MerchantType::getCreateDt)));
        IPage<MerchantTypeVo> iPage = merchantTypeMapper.findMerchantTypes(page, merchantTypePageParam);
        return new Paging<MerchantTypeVo>(iPage);
    }

}
