package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.Permission;

import com.sakura.user.vo.PermissionVo;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<String> findPermissionCodeByUrl(@Param("url") String url);

    List<PermissionVo> findAllPermission();


}
