package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.common.vo.LoginUserInfoVo;
import com.sakura.common.vo.RoleVo;
import com.sakura.user.entity.CustomerUser;
import com.sakura.user.param.CustomerUserPageParam;

import com.sakura.user.vo.CustomerRoleVo;
import com.sakura.user.vo.CustomerUserInfoVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 客户用户表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface CustomerUserMapper extends BaseMapper<CustomerUser> {

    LoginUserInfoVo findLoginCustomerUserInfoVoById(@Param("userId") String userId);

    // xml内部调用方法
    Set<RoleVo> findLoginCustomerUserRoles(@Param("userId") String userId);

    // xml内部调用方法
    Set<String> findCustomerUserPermissionCodes(@Param("userId") String userId);

    CustomerUserInfoVo findCustomerUserInfoVoById(@Param("userId") String userId, @Param("status") Integer status);

    // xml内部调用方法
    List<CustomerRoleVo> findCustomerUserRoles(@Param("userId") String userId);

    IPage<CustomerUserInfoVo> getCustomerUserList(@Param("page") Page page, @Param("param") CustomerUserPageParam param);

}
