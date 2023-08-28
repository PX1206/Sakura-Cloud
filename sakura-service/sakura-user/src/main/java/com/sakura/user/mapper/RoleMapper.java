package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sakura.user.entity.Role;
import com.sakura.user.param.RolePageParam;

import com.sakura.user.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 角色表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    // xml内部调用方法
    IPage<RoleVo> findRoles(@Param("page") Page page, @Param("param") RolePageParam param);


}
