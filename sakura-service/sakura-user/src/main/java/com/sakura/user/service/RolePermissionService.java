package com.sakura.user.service;

import com.sakura.user.entity.RolePermission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.RolePermissionParam;

import java.util.Set;

/**
 * 角色权限表 服务类
 *
 * @author Sakura
 * @since 2023-08-17
 */
public interface RolePermissionService extends BaseService<RolePermission> {

    /**
     * 保存
     *
     * @param rolePermissionParam
     * @return
     * @throws Exception
     */
    boolean saveRolePermission(RolePermissionParam rolePermissionParam) throws Exception;

    /**
     * 获取角色权限ID
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    Set<Integer> getRolePermissionId(Integer roleId);

}
