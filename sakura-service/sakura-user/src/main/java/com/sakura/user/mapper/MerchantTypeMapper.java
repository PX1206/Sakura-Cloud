package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.MerchantType;
import com.sakura.user.param.MerchantTypePageParam;

import com.sakura.user.vo.MerchantTypeVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 商户类型表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Mapper
public interface MerchantTypeMapper extends BaseMapper<MerchantType> {

    IPage<MerchantTypeVo> findMerchantTypes(@Param("page") Page page, @Param("param") MerchantTypePageParam param);


}
