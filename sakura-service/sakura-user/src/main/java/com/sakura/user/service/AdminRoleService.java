package com.sakura.user.service;

import com.sakura.user.entity.AdminRole;
import com.sakura.user.param.AdminRolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * admin角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface AdminRoleService extends BaseService<AdminRole> {

    /**
     * 保存
     *
     * @param adminRole
     * @return
     * @throws Exception
     */
    boolean saveAdminRole(AdminRole adminRole) throws Exception;

    /**
     * 修改
     *
     * @param adminRole
     * @return
     * @throws Exception
     */
    boolean updateAdminRole(AdminRole adminRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteAdminRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param adminRolePageParam
     * @return
     * @throws Exception
     */
    Paging<AdminRole> getAdminRolePageList(AdminRolePageParam adminRolePageParam) throws Exception;

}
