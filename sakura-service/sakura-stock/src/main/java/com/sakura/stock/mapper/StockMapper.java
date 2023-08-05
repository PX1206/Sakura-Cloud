package com.sakura.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.stock.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Sakura
 * @date 2023/7/28 15:13
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {


}
