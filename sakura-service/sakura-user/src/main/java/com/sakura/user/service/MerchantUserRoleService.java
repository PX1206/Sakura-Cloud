package com.sakura.user.service;

import com.sakura.user.entity.MerchantUserRole;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.MerchantUserRoleParam;

/**
 * 商户用户角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantUserRoleService extends BaseService<MerchantUserRole> {

    /**
     * 保存
     *
     * @param merchantUserRoleParam
     * @return
     * @throws Exception
     */
    boolean saveMerchantUserRole(MerchantUserRoleParam merchantUserRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param merchantUserRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteMerchantUserRole(MerchantUserRoleParam merchantUserRoleParam) throws Exception;

}
