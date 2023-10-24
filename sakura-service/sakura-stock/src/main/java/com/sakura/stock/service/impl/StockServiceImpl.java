package com.sakura.stock.service.impl;

import com.sakura.stock.entity.Stock;
import com.sakura.stock.mapper.StockMapper;
import com.sakura.stock.service.StockService;
import com.sakura.stock.param.StockPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 库存表 服务实现类
 *
 * @author Sakura
 * @since 2023-10-23
 */
@Slf4j
@Service
public class StockServiceImpl extends BaseServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveStock(Stock stock) throws Exception {
        return super.save(stock);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateStock(Stock stock) throws Exception {
        return super.updateById(stock);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteStock(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Stock> getStockPageList(StockPageParam stockPageParam) throws Exception {
        Page<Stock> page = new PageInfo<>(stockPageParam, OrderItem.desc(getLambdaColumn(Stock::getCreateDt)));
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        IPage<Stock> iPage = stockMapper.selectPage(page, wrapper);
        return new Paging<Stock>(iPage);
    }

}
