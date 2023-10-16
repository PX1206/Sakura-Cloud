package com.sakura.user.service;

import com.sakura.user.entity.AdminRolePermission;
import com.sakura.user.param.AdminRolePermissionPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

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
     * @param adminRolePermission
     * @return
     * @throws Exception
     */
    boolean saveAdminRolePermission(AdminRolePermission adminRolePermission) throws Exception;

    /**
     * 修改
     *
     * @param adminRolePermission
     * @return
     * @throws Exception
     */
    boolean updateAdminRolePermission(AdminRolePermission adminRolePermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteAdminRolePermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param adminRolePermissionPageParam
     * @return
     * @throws Exception
     */
    Paging<AdminRolePermission> getAdminRolePermissionPageList(AdminRolePermissionPageParam adminRolePermissionPageParam) throws Exception;

}
