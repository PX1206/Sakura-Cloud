package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.MerchantPermission;
import com.sakura.user.param.MerchantPermissionPageParam;

import com.sakura.user.vo.MerchantPermissionTreeVo;
import com.sakura.user.vo.MerchantPermissionVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 商户权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface MerchantPermissionMapper extends BaseMapper<MerchantPermission> {

    // 获取商户特殊权限ID
    Set<Integer> findPermissionIdByMerchantNo(@Param("merchantNo") String merchantNo);

    // 保存商户特殊权限
    int saveMerchantPermission(@Param("merchantNo") String merchantNo, @Param("permissionIds") Set<Integer> permissionIds);

    // 根据权限ID删除商户特殊权限
    int deleteByPermissionsId(@Param("merchantNo") String merchantNo, @Param("permissionIds") Set<Integer> permissionIds);

    // 根据商户号删除所有特殊权限
    int deleteMerchantPermission(@Param("merchantNo") String merchantNo);

    List<MerchantPermissionVo> findMerchantPermission(@Param("merchantNo") String merchantNo);

    List<MerchantPermissionTreeVo> findMerchantPermissionTree(@Param("merchantNo") String merchantNo);

    // 获取商户所有权限包含特殊权限
    Set<Integer> findAllMerchantPermissionId(@Param("merchantNo") String merchantNo);


}
