package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.user.entity.CustomerUserRole;
import com.sakura.user.mapper.CustomerUserRoleMapper;
import com.sakura.user.param.CustomerUserRoleParam;
import com.sakura.user.service.CustomerUserRoleService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 客户用户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class CustomerUserRoleServiceImpl extends BaseServiceImpl<CustomerUserRoleMapper, CustomerUserRole> implements CustomerUserRoleService {

    @Autowired
    private CustomerUserRoleMapper customerUserRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCustomerUserRole(CustomerUserRoleParam customerUserRoleParam) throws Exception {
        // 校验是否已存在该角色信息
        CustomerUserRole customerUserRole = customerUserRoleMapper.selectOne(
                Wrappers.<CustomerUserRole>lambdaQuery()
                        .eq(CustomerUserRole::getUserId, customerUserRoleParam.getUserId())
                        .eq(CustomerUserRole::getRoleId, customerUserRoleParam.getRoleId())
                        .eq(CustomerUserRole::getStatus, 1));
        if (customerUserRole != null) {
            throw new BusinessException(500, "当前角色已存在");
        }

        customerUserRole = new CustomerUserRole();
        customerUserRole.setUserId(customerUserRoleParam.getUserId());
        customerUserRole.setRoleId(customerUserRoleParam.getRoleId());
        customerUserRole.setStatus(1);

        return super.save(customerUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCustomerUserRole(CustomerUserRoleParam customerUserRoleParam) throws Exception {
        // 校验是否已存在该角色信息
        CustomerUserRole customerUserRole = customerUserRoleMapper.selectOne(
                Wrappers.<CustomerUserRole>lambdaQuery()
                        .eq(CustomerUserRole::getUserId, customerUserRoleParam.getUserId())
                        .eq(CustomerUserRole::getRoleId, customerUserRoleParam.getRoleId())
                        .eq(CustomerUserRole::getStatus, 1));
        if (customerUserRole == null) {
            throw new BusinessException(500, "用户角色信息异常");
        }

        // 将数据置为不可用
        customerUserRole.setStatus(0);
        customerUserRole.setUpdateDt(new Date());
        customerUserRoleMapper.updateById(customerUserRole);

        return true;
    }

}
