package com.sakura.user.service;

import com.sakura.user.entity.MerchantPermission;
import com.sakura.user.param.MerchantPermissionPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 商户权限表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantPermissionService extends BaseService<MerchantPermission> {

    /**
     * 保存
     *
     * @param merchantPermission
     * @return
     * @throws Exception
     */
    boolean saveMerchantPermission(MerchantPermission merchantPermission) throws Exception;

    /**
     * 修改
     *
     * @param merchantPermission
     * @return
     * @throws Exception
     */
    boolean updateMerchantPermission(MerchantPermission merchantPermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantPermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantPermissionPageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantPermission> getMerchantPermissionPageList(MerchantPermissionPageParam merchantPermissionPageParam) throws Exception;

}
