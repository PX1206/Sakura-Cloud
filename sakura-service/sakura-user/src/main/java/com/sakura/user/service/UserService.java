package com.sakura.user.service;

import com.sakura.user.entity.User;
import com.sakura.user.param.UserPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 用户表 服务类
 *
 * @author Sakura
 * @since 2023-08-14
 */
public interface UserService extends BaseService<User> {

    /**
     * 保存
     *
     * @param user
     * @return
     * @throws Exception
     */
    boolean saveUser(User user) throws Exception;

    /**
     * 修改
     *
     * @param user
     * @return
     * @throws Exception
     */
    boolean updateUser(User user) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteUser(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param userPageParam
     * @return
     * @throws Exception
     */
    Paging<User> getUserPageList(UserPageParam userPageParam) throws Exception;

}
