package com.sakura.user.service;

import com.sakura.user.entity.AdminRolePermission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.AdminRolePermissionParam;

import java.util.Set;

/**
 * admin角色权限表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface AdminRolePermissionService extends BaseService<AdminRolePermission> {

    /**
     * 保存
     *
     * @param adminRolePermissionParam
     * @return
     * @throws Exception
     */
    boolean addAdminRolePermission(AdminRolePermissionParam adminRolePermissionParam) throws Exception;

    /**
     * 获取角色权限ID
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    Set<Integer> getAdminrRolePermissionId(Integer roleId);

}
