package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.common.vo.RoleVo;
import com.sakura.user.entity.MerchantUser;
import com.sakura.user.vo.LoginMerchantUserInfoVo;
import com.sakura.user.param.MerchantUserPageParam;

import com.sakura.user.vo.MerchantUserInfoVo;
import com.sakura.user.vo.MerchantUserRoleVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

/**
 * 商户用户表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface MerchantUserMapper extends BaseMapper<MerchantUser> {

    MerchantUserInfoVo findMerchantUserInfoVoById(@Param("userId") String userId, @Param("status") Integer status);

    // xml内部调用方法
    List<MerchantUserRoleVo> findMerchantUserRoles(@Param("userId") String userId);

    IPage<MerchantUserInfoVo> getMerchantUserList(@Param("page") Page page, @Param("merchantNo") String merchantNo,
                                                  @Param("param") MerchantUserPageParam merchantUserPageParam);

    LoginMerchantUserInfoVo findLoginMerchantUserInfoVoById(@Param("userId") String userId);

    // xml内部调用方法
    List<RoleVo> findMerchantUserRole(@Param("userId") String userId);

    // xml内部调用方法
    Set<String> findMerchantUserPermissionCodes(@Param("userId") String userId);

    int deleteMerchantUserByMerchantNo(@Param("merchantNo") String merchantNo);

    List<String> findMerchantUserId(@Param("merchantNo") String merchantNo);


}
