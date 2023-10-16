package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.CommonUtil;
import com.sakura.user.entity.CustomerRole;
import com.sakura.user.mapper.CustomerRoleMapper;
import com.sakura.user.param.*;
import com.sakura.user.service.CustomerRoleService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.vo.CustomerRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 客户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class CustomerRoleServiceImpl extends BaseServiceImpl<CustomerRoleMapper, CustomerRole> implements CustomerRoleService {

    @Autowired
    private CustomerRoleMapper customerRoleMapper;

    @Autowired
    CommonUtil commonUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addCustomerRole(CustomerRoleParam customerRoleParam) throws Exception {
        // 先校验角色名称是否重复
        Integer num = customerRoleMapper.selectCount(
                Wrappers.<CustomerRole>lambdaQuery()
                        .eq(CustomerRole::getName, customerRoleParam.getName())
                        .eq(CustomerRole::getStatus, 1));
        if (num > 0) {
            throw new BusinessException(500, "角色名称重复");
        }

        CustomerRole customerRole = new CustomerRole();
        BeanUtils.copyProperties(customerRoleParam, customerRole);
        customerRole.setStatus(1);
        customerRoleMapper.insert(customerRole);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCustomerRole(CustomerRoleParam customerRoleParam) throws Exception {
        // 先校验角色名称是否重复
        Integer num = customerRoleMapper.selectCount(
                Wrappers.<CustomerRole>lambdaQuery()
                        .eq(CustomerRole::getName, customerRoleParam.getName())
                        .eq(CustomerRole::getStatus, 1)
                        .notIn(CustomerRole::getId, customerRoleParam.getId()));
        if (num > 0) {
            throw new BusinessException(500, "角色名称重复");
        }

        CustomerRole customerRole = new CustomerRole();
        BeanUtils.copyProperties(customerRoleParam, customerRole);
        customerRoleMapper.updateById(customerRole);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCustomerRole(DeleteCustomerRoleParam deleteCustomerRoleParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(deleteCustomerRoleParam.getKey(), deleteCustomerRoleParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        CustomerRole customerRole = customerRoleMapper.selectById(deleteCustomerRoleParam.getId());
        customerRole.setStatus(0);
        customerRole.setUpdateDt(new Date());
        customerRoleMapper.updateById(customerRole);

        return true;
    }

    @Override
    public CustomerRoleVo getCustomerRole(Long id) throws Exception {
        CustomerRole customerRole = customerRoleMapper.selectById(id);
        if (customerRole == null) {
            throw new BusinessException(500, "角色信息异常");
        }

        CustomerRoleVo customerRoleVo = new CustomerRoleVo();
        BeanUtils.copyProperties(customerRole, customerRoleVo);

        return customerRoleVo;
    }

    @Override
    public Paging<CustomerRoleVo> getCustomerRolePageList(CustomerRolePageParam customerRolePageParam) throws Exception {
        Page<CustomerRoleVo> page = new PageInfo<>(customerRolePageParam, OrderItem.desc(getLambdaColumn(CustomerRole::getCreateDt)));
        IPage<CustomerRoleVo> iPage = customerRoleMapper.findCustomerRoles(page, customerRolePageParam);
        return new Paging<>(iPage);
    }

}
