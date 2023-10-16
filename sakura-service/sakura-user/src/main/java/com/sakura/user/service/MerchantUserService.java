package com.sakura.user.service;

import com.sakura.user.entity.MerchantUser;
import com.sakura.user.param.MerchantUserPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 商户用户表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantUserService extends BaseService<MerchantUser> {

    /**
     * 保存
     *
     * @param merchantUser
     * @return
     * @throws Exception
     */
    boolean saveMerchantUser(MerchantUser merchantUser) throws Exception;

    /**
     * 修改
     *
     * @param merchantUser
     * @return
     * @throws Exception
     */
    boolean updateMerchantUser(MerchantUser merchantUser) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantUser(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantUserPageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantUser> getMerchantUserPageList(MerchantUserPageParam merchantUserPageParam) throws Exception;

}
