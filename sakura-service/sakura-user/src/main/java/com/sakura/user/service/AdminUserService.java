package com.sakura.user.service;

import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.user.entity.AdminUser;
import com.sakura.user.param.AdminUserPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.param.AddAdminUserParam;
import com.sakura.user.param.LoginParam;
import com.sakura.user.param.UpdateAdminUserParam;
import com.sakura.user.vo.AdminUserInfoVo;

/**
 * admin用户表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface AdminUserService extends BaseService<AdminUser> {

    /**
     * 保存
     *
     * @param addAdminUserParam
     * @return
     * @throws Exception
     */
    boolean saveAdminUser(AddAdminUserParam addAdminUserParam) throws Exception;

    /**
     * 修改
     *
     * @param updateAdminUserParam
     * @return
     * @throws Exception
     */
    boolean updateAdminUser(UpdateAdminUserParam updateAdminUserParam) throws Exception;

    /**
     * 删除
     *
     * @param userId
     * @return
     * @throws Exception
     */
    boolean deleteAdminUser(String userId) throws Exception;

    /**
     * 详情
     *
     * @return
     * @throws Exception
     */
    AdminUserInfoVo getAdminUser() throws Exception;

    /**
     * 获取分页对象
     *
     * @param adminUserPageParam
     * @return
     * @throws Exception
     */
    Paging<AdminUserInfoVo> getAdminUserPageList(AdminUserPageParam adminUserPageParam) throws Exception;

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     * @throws Exception
     */
    LoginUserInfoVo login(LoginParam loginParam) throws Exception;

}
