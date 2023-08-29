package com.sakura.user.service;

import com.sakura.user.entity.UserRole;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.UserRoleParam;

/**
 * 用户角色表 服务类
 *
 * @author Sakura
 * @since 2023-08-17
 */
public interface UserRoleService extends BaseService<UserRole> {

    /**
     * 保存
     *
     * @param userRoleParam
     * @return
     * @throws Exception
     */
    boolean saveUserRole(UserRoleParam userRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param userRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteUserRole(UserRoleParam userRoleParam) throws Exception;

}
