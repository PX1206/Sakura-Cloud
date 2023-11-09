package com.sakura.user.service;

import com.sakura.user.entity.AdminRole;
import com.sakura.user.param.AdminRolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.param.AdminRoleParam;
import com.sakura.user.param.DeleteAdminRoleParam;
import com.sakura.user.vo.AdminRoleVo;

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
     * @param adminRoleParam
     * @return
     * @throws Exception
     */
    boolean saveAdminRole(AdminRoleParam adminRoleParam) throws Exception;

    /**
     * 修改
     *
     * @param adminRoleParam
     * @return
     * @throws Exception
     */
    boolean updateAdminRole(AdminRoleParam adminRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param deleteAdminRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteAdminRole(DeleteAdminRoleParam deleteAdminRoleParam) throws Exception;

    /**
     * 获取角色详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    AdminRoleVo getAdminRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param adminRolePageParam
     * @return
     * @throws Exception
     */
    Paging<AdminRoleVo> getAdminRolePageList(AdminRolePageParam adminRolePageParam) throws Exception;

}
