package com.sakura.user.service;

import com.sakura.user.entity.MerchantTypePermission;
import com.sakura.user.param.MerchantTypePermissionPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

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
     * @param merchantTypePermission
     * @return
     * @throws Exception
     */
    boolean saveMerchantTypePermission(MerchantTypePermission merchantTypePermission) throws Exception;

    /**
     * 修改
     *
     * @param merchantTypePermission
     * @return
     * @throws Exception
     */
    boolean updateMerchantTypePermission(MerchantTypePermission merchantTypePermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantTypePermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantTypePermissionPageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantTypePermission> getMerchantTypePermissionPageList(MerchantTypePermissionPageParam merchantTypePermissionPageParam) throws Exception;

}
