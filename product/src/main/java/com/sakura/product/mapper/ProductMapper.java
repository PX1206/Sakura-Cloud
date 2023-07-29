package com.sakura.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.product.entify.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Sakura
 * @date 2023/7/28 15:13
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


}
