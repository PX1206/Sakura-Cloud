package com.sakura.user.service;

import com.sakura.user.entity.Merchant;
import com.sakura.user.param.MerchantPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 商户表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantService extends BaseService<Merchant> {

    /**
     * 保存
     *
     * @param merchant
     * @return
     * @throws Exception
     */
    boolean saveMerchant(Merchant merchant) throws Exception;

    /**
     * 修改
     *
     * @param merchant
     * @return
     * @throws Exception
     */
    boolean updateMerchant(Merchant merchant) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchant(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantPageParam
     * @return
     * @throws Exception
     */
    Paging<Merchant> getMerchantPageList(MerchantPageParam merchantPageParam) throws Exception;

}
