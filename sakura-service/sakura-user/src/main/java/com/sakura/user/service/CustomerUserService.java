package com.sakura.user.service;

import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.CustomerUser;
import com.sakura.user.param.*;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.vo.CustomerUserInfoVo;

/**
 * 客户用户表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface CustomerUserService extends BaseService<CustomerUser> {

    /**
     * 用户注册
     *
     * @param customerUserRegisterParam
     * @return
     * @throws Exception
     */
    boolean register(CustomerUserRegisterParam customerUserRegisterParam) throws Exception;

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     * @throws Exception
     */
    LoginUserInfoVo login(LoginParam loginParam) throws Exception;

    /**
     * 修改
     *
     * @param updateCustomerUserParam
     * @return
     * @throws Exception
     */
    boolean updateCustomerUser(UpdateCustomerUserParam updateCustomerUserParam) throws Exception;

    /**
     * 修改
     *
     * @param updateMobileParam
     * @return
     * @throws Exception
     */
    boolean updateMobile(UpdateMobileParam updateMobileParam) throws Exception;


    /**
     * 用户详情
     *
     * @return
     * @throws Exception
     */
    CustomerUserInfoVo getCustomerUserInfo() throws Exception;


    /**
     * 获取分页对象
     *
     * @param customerUserPageParam
     * @return
     * @throws Exception
     */
    Paging<CustomerUserInfoVo> getCustomerUserList(CustomerUserPageParam customerUserPageParam) throws Exception;

    /**
     * 用户详情
     *
     * @return
     * @throws Exception
     */
    CustomerUserInfoVo getCustomerUser(String userId) throws Exception;

    /**
     * 账号解冻
     *
     * @param freezeAccountParam
     * @return
     * @throws Exception
     */
    boolean unfreezeAccount(FreezeAccountParam freezeAccountParam) throws Exception;

    /**
     * 账号冻结
     *
     * @param freezeAccountParam
     * @return
     * @throws Exception
     */
    boolean freezeAccount(FreezeAccountParam freezeAccountParam) throws Exception;

    /**
     * 用户注册
     *
     * @param resetPasswordParam
     * @return
     * @throws Exception
     */
    boolean resetPassword(ResetPasswordParam resetPasswordParam) throws Exception;

    /**
     * 账号注销，用户自行注销账号
     *
     * @param accountCancellationParam
     * @return
     * @throws Exception
     */
    boolean AccountCancellation(AccountCancellationParam accountCancellationParam) throws Exception;

    /**
     * 删除用户，admin专用接口
     *
     * @param deleteCustomerUserParam
     * @return
     * @throws Exception
     */
    boolean deleteCustomerUser(DeleteCustomerUserParam deleteCustomerUserParam) throws Exception;

}
