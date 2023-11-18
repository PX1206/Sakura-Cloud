package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.MerchantTypePermission;
import com.sakura.user.param.MerchantTypePermissionPageParam;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.Set;

/**
 * 商户类型权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Mapper
public interface MerchantTypePermissionMapper extends BaseMapper<MerchantTypePermission> {

    Set<Integer> findPermissionIdByTypeId(@Param("typeId") Integer typeId);

    int saveMerchantTypePermission(@Param("typeId") Integer typeId, @Param("permissionIds") Set<Integer> permissionIds);

    int deleteByPermissionsId(@Param("typeId") Integer typeId, @Param("permissionIds") Set<Integer> permissionIds);

    int deleteByTypeId(@Param("typeId") Integer typeId);

}
