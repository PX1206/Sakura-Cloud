package com.sakura.user.service;

import com.sakura.user.entity.AdminUserRole;
import com.sakura.user.param.AdminUserRolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * admin用户角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface AdminUserRoleService extends BaseService<AdminUserRole> {

    /**
     * 保存
     *
     * @param adminUserRole
     * @return
     * @throws Exception
     */
    boolean saveAdminUserRole(AdminUserRole adminUserRole) throws Exception;

    /**
     * 修改
     *
     * @param adminUserRole
     * @return
     * @throws Exception
     */
    boolean updateAdminUserRole(AdminUserRole adminUserRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteAdminUserRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param adminUserRolePageParam
     * @return
     * @throws Exception
     */
    Paging<AdminUserRole> getAdminUserRolePageList(AdminUserRolePageParam adminUserRolePageParam) throws Exception;

}
