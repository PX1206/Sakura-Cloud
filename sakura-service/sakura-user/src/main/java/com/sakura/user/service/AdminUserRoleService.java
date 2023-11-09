package com.sakura.user.service;

import com.sakura.user.entity.AdminUserRole;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.AdminUserRoleParam;

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
     * @param adminUserRoleParam
     * @return
     * @throws Exception
     */
    boolean saveAdminUserRole(AdminUserRoleParam adminUserRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param adminUserRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteAdminUserRole(AdminUserRoleParam adminUserRoleParam) throws Exception;

}
