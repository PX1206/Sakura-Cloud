package com.sakura.user.service;

import com.sakura.user.entity.MerchantTypePermission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.MerchantTypePermissionParam;

import java.util.Set;

/**
 * 商户类型权限表 服务类
 *
 * @author Sakura
 * @since 2023-10-26
 */
public interface MerchantTypePermissionService extends BaseService<MerchantTypePermission> {

    /**
     * 保存
     *
     * @param merchantTypePermissionParam
     * @return
     * @throws Exception
     */
    boolean addMerchantTypePermission(MerchantTypePermissionParam merchantTypePermissionParam) throws Exception;

    /**
     * 获取角色权限ID
     *
     * @param typeId
     * @return
     * @throws Exception
     */
    Set<Integer> getMerchantTypePermissionId(Integer typeId);

}
