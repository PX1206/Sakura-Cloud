package com.sakura.user.service;

import com.sakura.user.entity.User;
import com.sakura.user.param.*;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.vo.UserInfoVo;

/**
 * 用户表 服务类
 *
 * @author Sakura
 * @since 2023-08-14
 */
public interface UserService extends BaseService<User> {

    /**
     * 用户注册
     *
     * @param userRegisterParam
     * @return
     * @throws Exception
     */
    boolean register(UserRegisterParam userRegisterParam) throws Exception;

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
     * @param updateUserParam
     * @return
     * @throws Exception
     */
    boolean updateUser(UpdateUserParam updateUserParam) throws Exception;

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
    UserInfoVo getUserInfo() throws Exception;


    /**
     * 获取分页对象
     *
     * @param userPageParam
     * @return
     * @throws Exception
     */
    Paging<UserInfoVo> getUserList(UserPageParam userPageParam) throws Exception;

}
