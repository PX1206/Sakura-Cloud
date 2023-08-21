package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.Permissions;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import java.util.Set;

/**
 * 权限表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Mapper
public interface PermissionsMapper extends BaseMapper<Permissions> {

    Set<String> findPermissionCodeByUrl(@Param("url") String url);


}
