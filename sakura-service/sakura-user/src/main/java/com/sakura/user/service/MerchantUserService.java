package com.sakura.user.service;

import com.sakura.user.entity.MerchantUser;
import com.sakura.user.param.*;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.vo.ChooseMerchantUserVo;
import com.sakura.user.vo.LoginMerchantUserInfoVo;
import com.sakura.user.vo.MerchantUserInfoVo;

import java.util.List;

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
     * @param addMerchantUserParam
     * @return
     * @throws Exception
     */
    boolean addMerchantUser(AddMerchantUserParam addMerchantUserParam) throws Exception;

    /**
     * 修改
     *
     * @param updateMerchantUserParam
     * @return
     * @throws Exception
     */
    boolean updateMerchantUser(UpdateMerchantUserParam updateMerchantUserParam) throws Exception;

    /**
     * 删除
     *
     * @param userId
     * @return
     * @throws Exception
     */
    boolean deleteMerchantUser(String userId) throws Exception;

    /**
     * 详情
     *
     * @return
     * @throws Exception
     */
    MerchantUserInfoVo getMerchantUser() throws Exception;

    /**
     * 获取分页对象
     *
     * @param merchantUserPageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantUserInfoVo> getMerchantUserPageList(MerchantUserPageParam merchantUserPageParam) throws Exception;

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     * @throws Exception
     */
    List<ChooseMerchantUserVo> login(LoginParam loginParam) throws Exception;

    /**
     * 用户登录，选择要登录的商户
     *
     * @param chooseMerchantParam
     * @return
     * @throws Exception
     */
    LoginMerchantUserInfoVo chooseMerchant(ChooseMerchantParam chooseMerchantParam) throws Exception;

}
