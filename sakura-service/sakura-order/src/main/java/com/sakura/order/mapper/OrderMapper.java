package com.sakura.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.order.entity.Order;
import com.sakura.order.param.OrderPageParam;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 订单表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-07
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


}
