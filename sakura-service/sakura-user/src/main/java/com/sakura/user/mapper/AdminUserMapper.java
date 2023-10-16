package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.common.vo.RoleVo;
import com.sakura.user.entity.AdminUser;

import com.sakura.user.param.AdminUserPageParam;
import com.sakura.user.vo.AdminUserInfoVo;
import com.sakura.user.vo.AdminUserRoleVo;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

/**
 * admin用户表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    AdminUserInfoVo findAdminUserInfoVoById(@Param("userId") String userId, @Param("status") Integer status);

    // xml内部调用方法
    List<AdminUserRoleVo> findAdminUserRoles(@Param("userId") String userId);

    IPage<AdminUserInfoVo> getAdminUserList(@Param("page") Page page, @Param("param") AdminUserPageParam param);

    LoginUserInfoVo findLoginAdminUserInfoVoById(@Param("userId") String userId);

    // xml内部调用方法
    List<RoleVo> findAdminUserRole(@Param("userId") String userId);

    // xml内部调用方法
    Set<String> findAdminUserPermissionCodes(@Param("userId") String userId);


}
