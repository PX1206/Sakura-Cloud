package com.sakura.user.service;

import com.sakura.user.entity.CustomerUserRole;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.CustomerUserRoleParam;

/**
 * 客户用户角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface CustomerUserRoleService extends BaseService<CustomerUserRole> {

    /**
     * 保存
     *
     * @param customerUserRoleParam
     * @return
     * @throws Exception
     */
    boolean saveCustomerUserRole(CustomerUserRoleParam customerUserRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param customerUserRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteCustomerUserRole(CustomerUserRoleParam customerUserRoleParam) throws Exception;

}
