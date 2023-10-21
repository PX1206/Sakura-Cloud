package com.sakura.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.product.entity.Order;

import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-07
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


}
