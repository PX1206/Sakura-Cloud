package com.sakura.user.service;

import com.sakura.user.entity.MerchantRole;
import com.sakura.user.param.MerchantRolePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 商户角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantRoleService extends BaseService<MerchantRole> {

    /**
     * 保存
     *
     * @param merchantRole
     * @return
     * @throws Exception
     */
    boolean saveMerchantRole(MerchantRole merchantRole) throws Exception;

    /**
     * 修改
     *
     * @param merchantRole
     * @return
     * @throws Exception
     */
    boolean updateMerchantRole(MerchantRole merchantRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantRolePageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantRole> getMerchantRolePageList(MerchantRolePageParam merchantRolePageParam) throws Exception;

}
