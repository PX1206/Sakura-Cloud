package com.sakura.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.stock.entity.Stock;
import com.sakura.stock.param.StockPageParam;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 库存表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-10-23
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {


}
