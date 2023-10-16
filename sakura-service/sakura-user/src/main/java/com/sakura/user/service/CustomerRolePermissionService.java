package com.sakura.user.service;

import com.sakura.user.entity.CustomerRolePermission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.CustomerRolePermissionParam;

import java.util.Set;

/**
 * 客户角色权限表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface CustomerRolePermissionService extends BaseService<CustomerRolePermission> {

    /**
     * 保存
     *
     * @param customerRolePermissionParam
     * @return
     * @throws Exception
     */
    boolean saveCustomerRolePermission(CustomerRolePermissionParam customerRolePermissionParam) throws Exception;

    /**
     * 获取角色权限ID
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    Set<Integer> getCustomerRolePermissionId(Integer roleId);

}
