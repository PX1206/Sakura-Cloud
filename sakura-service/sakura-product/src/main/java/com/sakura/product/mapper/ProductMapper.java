package com.sakura.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.product.entity.Product;
import com.sakura.product.param.ProductPageParam;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 商品表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-10-23
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


}
