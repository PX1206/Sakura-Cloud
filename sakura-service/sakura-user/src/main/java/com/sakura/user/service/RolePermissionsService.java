package com.sakura.user.service;

import com.sakura.user.entity.RolePermissions;
import com.sakura.user.param.RolePermissionsPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 角色权限表 服务类
 *
 * @author Sakura
 * @since 2023-08-17
 */
public interface RolePermissionsService extends BaseService<RolePermissions> {

    /**
     * 保存
     *
     * @param rolePermissions
     * @return
     * @throws Exception
     */
    boolean saveRolePermissions(RolePermissions rolePermissions) throws Exception;

    /**
     * 修改
     *
     * @param rolePermissions
     * @return
     * @throws Exception
     */
    boolean updateRolePermissions(RolePermissions rolePermissions) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRolePermissions(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param rolePermissionsPageParam
     * @return
     * @throws Exception
     */
    Paging<RolePermissions> getRolePermissionsPageList(RolePermissionsPageParam rolePermissionsPageParam) throws Exception;

}
