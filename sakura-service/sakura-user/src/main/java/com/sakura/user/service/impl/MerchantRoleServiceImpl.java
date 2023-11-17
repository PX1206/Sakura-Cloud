package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.CommonUtil;
import com.sakura.common.tool.LoginUtil;
import com.sakura.user.entity.MerchantRole;
import com.sakura.user.mapper.MerchantRoleMapper;
import com.sakura.user.param.*;
import com.sakura.user.service.MerchantRoleService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.vo.MerchantRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

    @Autowired
    private CommonUtil commonUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMerchantRole(MerchantRoleParam merchantRoleParam) throws Exception {
        // 先校验角色名称是否重复
        Integer num = merchantRoleMapper.selectCount(
                Wrappers.<MerchantRole>lambdaQuery()
                        .eq(MerchantRole::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantRole::getName, merchantRoleParam.getName())
                        .eq(MerchantRole::getStatus, 1));
        if (num > 0) {
            throw new BusinessException(500, "角色名称重复");
        }

        MerchantRole merchantRole = new MerchantRole();
        BeanUtils.copyProperties(merchantRoleParam, merchantRole);
        merchantRole.setMerchantNo(LoginUtil.getMerchantNo());
        merchantRole.setStatus(1);
        merchantRoleMapper.insert(merchantRole);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMerchantRole(MerchantRoleParam merchantRoleParam) throws Exception {
        // 先校验角色名称是否重复
        Integer num = merchantRoleMapper.selectCount(
                Wrappers.<MerchantRole>lambdaQuery()
                        .eq(MerchantRole::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantRole::getName, merchantRoleParam.getName())
                        .eq(MerchantRole::getStatus, 1)
                        .notIn(MerchantRole::getId, merchantRoleParam.getId()));
        if (num > 0) {
            throw new BusinessException(500, "角色名称重复");
        }

        MerchantRole merchantRole = new MerchantRole();
        BeanUtils.copyProperties(merchantRoleParam, merchantRole);
        merchantRole.setUpdateDt(new Date());
        merchantRoleMapper.updateById(merchantRole);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMerchantRole(DeleteMerchantRoleParam deleteMerchantRoleParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(deleteMerchantRoleParam.getKey(), deleteMerchantRoleParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        MerchantRole merchantRole = merchantRoleMapper.selectById(deleteMerchantRoleParam.getId());
        if (merchantRole == null || merchantRole.getStatus() != 1
                || !merchantRole.getMerchantNo().equals(LoginUtil.getMerchantNo())) {
            throw new BusinessException(500, "角色信息异常");
        }
        merchantRole.setStatus(0);
        merchantRole.setUpdateDt(new Date());
        merchantRoleMapper.updateById(merchantRole);

        return true;
    }

    @Override
    public MerchantRoleVo getMerchantRole(Long id) throws Exception {
        MerchantRole merchantRole = merchantRoleMapper.selectById(id);
        if (merchantRole == null || merchantRole.getStatus() != 1
                || !merchantRole.getMerchantNo().equals(LoginUtil.getMerchantNo())) {
            throw new BusinessException(500, "角色信息异常");
        }

        MerchantRoleVo merchantRoleVo = new MerchantRoleVo();
        BeanUtils.copyProperties(merchantRole, merchantRoleVo);

        return merchantRoleVo;
    }

    @Override
    public Paging<MerchantRoleVo> getMerchantRolePageList(MerchantRolePageParam merchantRolePageParam) throws Exception {
        Page<MerchantRoleVo> page = new PageInfo<>(merchantRolePageParam, OrderItem.desc(getLambdaColumn(MerchantRole::getCreateDt)));
        IPage<MerchantRoleVo> iPage = merchantRoleMapper.findMerchantRoles(page, LoginUtil.getMerchantNo(), merchantRolePageParam);
        return new Paging<>(iPage);
    }

}
