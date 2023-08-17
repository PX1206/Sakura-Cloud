package com.sakura.user.service;

import com.sakura.user.entity.Role;
import com.sakura.user.param.RolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 角色表 服务类
 *
 * @author Sakura
 * @since 2023-08-17
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 保存
     *
     * @param role
     * @return
     * @throws Exception
     */
    boolean saveRole(Role role) throws Exception;

    /**
     * 修改
     *
     * @param role
     * @return
     * @throws Exception
     */
    boolean updateRole(Role role) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param rolePageParam
     * @return
     * @throws Exception
     */
    Paging<Role> getRolePageList(RolePageParam rolePageParam) throws Exception;

}
