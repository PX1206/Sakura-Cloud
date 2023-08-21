package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.common.vo.RoleVo;
import com.sakura.user.entity.User;

import com.sakura.common.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 用户表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    UserInfoVo findUserInfoVoById(@Param("userId") String userId);

    // xml内部调用方法
    List<RoleVo> findUserRoles(@Param("userId") String userId);

    // xml内部调用方法
    Set<String> findUserPermissions(@Param("userId") String userId);


}
