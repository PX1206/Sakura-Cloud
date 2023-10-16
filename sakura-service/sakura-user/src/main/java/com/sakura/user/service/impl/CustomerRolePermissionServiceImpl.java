package com.sakura.user.service.impl;

import com.sakura.user.entity.CustomerRolePermission;
import com.sakura.user.mapper.CustomerRolePermissionMapper;
import com.sakura.user.param.CustomerRolePermissionParam;
import com.sakura.user.service.CustomerRolePermissionService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 客户角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class CustomerRolePermissionServiceImpl extends BaseServiceImpl<CustomerRolePermissionMapper, CustomerRolePermission> implements CustomerRolePermissionService {

    @Autowired
    private CustomerRolePermissionMapper customerRolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCustomerRolePermission(CustomerRolePermissionParam customerRolePermissionParam) throws Exception {
        // 先获取角色原有权限信息
        Set<Integer> permissionIds = customerRolePermissionMapper.findPermissionIdByRoleId(customerRolePermissionParam.getRoleId());
        // 如果角色原先就有权限则先处理原有权限
        if (permissionIds != null && permissionIds.size() > 0) {
            // 找出新增的权限
            Set<Integer> newAddPermissions = customerRolePermissionParam.getPermissionIds().stream()
                    .filter(element -> !permissionIds.contains(element))
                    .collect(Collectors.toSet());
            if (newAddPermissions != null && newAddPermissions.size() > 0) {
                customerRolePermissionMapper.saveCustomerRolePermission(customerRolePermissionParam.getRoleId(), newAddPermissions);
            }
            // 找出删除的权限
            Set<Integer> deletePermissions = permissionIds.stream()
                    .filter(element -> !customerRolePermissionParam.getPermissionIds().contains(element))
                    .collect(Collectors.toSet());
            if (deletePermissions != null && deletePermissions.size() > 0) {
                customerRolePermissionMapper.deleteByPermissionsId(customerRolePermissionParam.getRoleId(), deletePermissions);
            }
        } else {
            customerRolePermissionMapper.saveCustomerRolePermission(customerRolePermissionParam.getRoleId(), customerRolePermissionParam.getPermissionIds());
        }

        return true;
    }

    @Override
    public Set<Integer> getCustomerRolePermissionId(Integer roleId) {
        return customerRolePermissionMapper.findPermissionIdByRoleId(roleId);
    }

}
