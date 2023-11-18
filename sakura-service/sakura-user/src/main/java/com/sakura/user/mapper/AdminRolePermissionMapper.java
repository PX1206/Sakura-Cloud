package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.AdminRolePermission;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.Set;

/**
 * admin角色权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface AdminRolePermissionMapper extends BaseMapper<AdminRolePermission> {

    Set<Integer> findPermissionIdByRoleId(@Param("roleId") Integer roleId);

    int saveAdminRolePermission(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);

    int deleteByPermissionsId(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);

    int deleteByRoleId(@Param("roleId") Integer roleId);


}
