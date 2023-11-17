package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.MerchantRolePermission;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.Set;

/**
 * 商户角色权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface MerchantRolePermissionMapper extends BaseMapper<MerchantRolePermission> {

    Set<Integer> findPermissionIdByRoleId(@Param("roleId") Integer roleId);

    int saveMerchantRolePermission(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);

    int deleteByPermissionsId(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);


}
