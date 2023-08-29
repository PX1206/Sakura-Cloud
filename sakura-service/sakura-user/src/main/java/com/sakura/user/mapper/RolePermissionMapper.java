package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.RolePermission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 角色权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    Set<Integer> findPermissionIdByRoleId(@Param("roleId") Integer roleId);

    int saveRolePermission(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);

    int deleteByPermissionsId(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);


}
