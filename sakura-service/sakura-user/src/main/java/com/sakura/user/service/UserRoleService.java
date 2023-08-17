package com.sakura.user.service;

import com.sakura.user.entity.UserRole;
import com.sakura.user.param.UserRolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

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
     * @param userRole
     * @return
     * @throws Exception
     */
    boolean saveUserRole(UserRole userRole) throws Exception;

    /**
     * 修改
     *
     * @param userRole
     * @return
     * @throws Exception
     */
    boolean updateUserRole(UserRole userRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteUserRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param userRolePageParam
     * @return
     * @throws Exception
     */
    Paging<UserRole> getUserRolePageList(UserRolePageParam userRolePageParam) throws Exception;

}
