package com.sakura.user.service;

import com.sakura.user.entity.CustomerRole;
import com.sakura.user.param.*;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.vo.CustomerRoleVo;

/**
 * 客户角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface CustomerRoleService extends BaseService<CustomerRole> {

    /**
     * 保存
     *
     * @param customerRoleParam
     * @return
     * @throws Exception
     */
    boolean addCustomerRole(CustomerRoleParam customerRoleParam) throws Exception;

    /**
     * 修改
     *
     * @param customerRoleParam
     * @return
     * @throws Exception
     */
    boolean updateCustomerRole(CustomerRoleParam customerRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param deleteCustomerRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteCustomerRole(DeleteCustomerRoleParam deleteCustomerRoleParam) throws Exception;

    /**
     * 获取角色详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    CustomerRoleVo getCustomerRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param customerRolePageParam
     * @return
     * @throws Exception
     */
    Paging<CustomerRoleVo> getCustomerRolePageList(CustomerRolePageParam customerRolePageParam) throws Exception;

}
