package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.MerchantRole;
import com.sakura.user.param.MerchantRolePageParam;

import com.sakura.user.vo.MerchantRoleVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 商户角色表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Mapper
public interface MerchantRoleMapper extends BaseMapper<MerchantRole> {

    IPage<MerchantRoleVo> findMerchantRoles(@Param("page") Page page, @Param("merchantNo") String merchantNo,
                                            @Param("param") MerchantRolePageParam param);


}
