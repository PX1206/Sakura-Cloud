package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.AdminRole;
import com.sakura.user.param.AdminRolePageParam;

import com.sakura.user.vo.AdminRoleVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * admin角色表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    IPage<AdminRoleVo> findAdminRoles(@Param("page") Page page, @Param("param") AdminRolePageParam param);


}
