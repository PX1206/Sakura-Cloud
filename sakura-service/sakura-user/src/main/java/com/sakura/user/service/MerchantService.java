package com.sakura.user.service;

import com.sakura.user.entity.Merchant;
import com.sakura.user.param.ApplySettled;
import com.sakura.user.param.DeleteMerchantParam;
import com.sakura.user.param.MerchantPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.param.MerchantParam;
import com.sakura.user.vo.MerchantVo;

/**
 * 商户表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantService extends BaseService<Merchant> {

    /**
     * 入驻申请
     *
     * @param applySettled
     * @return
     * @throws Exception
     */
    boolean applySettled(ApplySettled applySettled) throws Exception;

    /**
     * 修改
     *
     * @param merchantParam
     * @return
     * @throws Exception
     */
    boolean updateMerchant(MerchantParam merchantParam) throws Exception;

    /**
     * 获取当前登录用户商户详情
     *
     * @return
     * @throws Exception
     */
    MerchantVo getMerchant() throws Exception;

    /**
     * 根据商户号获取商户详情
     *
     * @param merchantNo
     * @return
     * @throws Exception
     */
    MerchantVo getMerchant(String merchantNo) throws Exception;

    /**
     * 删除
     *
     * @param deleteMerchantParam
     * @return
     * @throws Exception
     */
    boolean deleteMerchant(DeleteMerchantParam deleteMerchantParam) throws Exception;

    /**
     * 获取分页对象
     *
     * @param merchantPageParam
     * @return
     * @throws Exception
     */
    Paging<Merchant> getMerchantPageList(MerchantPageParam merchantPageParam) throws Exception;

}
