package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.CustomerRole;
import com.sakura.user.param.CustomerRolePageParam;

import com.sakura.user.vo.CustomerRoleVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 客户角色表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface CustomerRoleMapper extends BaseMapper<CustomerRole> {

    IPage<CustomerRoleVo> findCustomerRoles(@Param("page") Page page, @Param("param") CustomerRolePageParam param);


}
