package com.sakura.user.service;

import com.sakura.user.entity.MerchantRolePermission;
import com.sakura.user.param.MerchantRolePermissionPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 商户角色权限表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantRolePermissionService extends BaseService<MerchantRolePermission> {

    /**
     * 保存
     *
     * @param merchantRolePermission
     * @return
     * @throws Exception
     */
    boolean saveMerchantRolePermission(MerchantRolePermission merchantRolePermission) throws Exception;

    /**
     * 修改
     *
     * @param merchantRolePermission
     * @return
     * @throws Exception
     */
    boolean updateMerchantRolePermission(MerchantRolePermission merchantRolePermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantRolePermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantRolePermissionPageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantRolePermission> getMerchantRolePermissionPageList(MerchantRolePermissionPageParam merchantRolePermissionPageParam) throws Exception;

}
