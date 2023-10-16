package com.sakura.user.service;

import com.sakura.user.entity.MerchantUserRole;
import com.sakura.user.param.MerchantUserRolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

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
     * @param merchantUserRole
     * @return
     * @throws Exception
     */
    boolean saveMerchantUserRole(MerchantUserRole merchantUserRole) throws Exception;

    /**
     * 修改
     *
     * @param merchantUserRole
     * @return
     * @throws Exception
     */
    boolean updateMerchantUserRole(MerchantUserRole merchantUserRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantUserRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantUserRolePageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantUserRole> getMerchantUserRolePageList(MerchantUserRolePageParam merchantUserRolePageParam) throws Exception;

}
