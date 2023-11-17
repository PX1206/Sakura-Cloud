package com.sakura.user.service;

import com.sakura.user.entity.MerchantRolePermission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.MerchantRolePermissionParam;

import java.util.Set;

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
     * @param merchantRolePermissionParam
     * @return
     * @throws Exception
     */
    boolean addMerchantRolePermission(MerchantRolePermissionParam merchantRolePermissionParam) throws Exception;

    /**
     * 获取角色权限ID
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    Set<Integer> getMerchantRolePermissionId(Integer roleId);

}
