package com.sakura.user.service;

import com.sakura.user.entity.RolePermission;
import com.sakura.user.param.RolePermissionPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

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
     * @param rolePermission
     * @return
     * @throws Exception
     */
    boolean saveRolePermission(RolePermission rolePermission) throws Exception;

    /**
     * 修改
     *
     * @param rolePermission
     * @return
     * @throws Exception
     */
    boolean updateRolePermission(RolePermission rolePermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRolePermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param rolePermissionPageParam
     * @return
     * @throws Exception
     */
    Paging<RolePermission> getRolePermissionPageList(RolePermissionPageParam rolePermissionPageParam) throws Exception;

}
