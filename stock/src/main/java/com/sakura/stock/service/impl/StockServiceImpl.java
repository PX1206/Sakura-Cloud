package com.sakura.stock.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.stock.entify.Stock;
import com.sakura.stock.mapper.StockMapper;
import com.sakura.stock.service.StockService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sakura
 * @date 2023/7/28 16:18
 */
@Service
@Log
public class StockServiceImpl implements StockService {

    @Autowired
    StockMapper stockMapper;

    @Override
    public Integer getProductNum(String productNo) {
        Stock stock = stockMapper.selectOne(
                Wrappers.<Stock>lambdaQuery()
                        .eq(Stock::getProductNo, productNo)
                        .eq(Stock::getStatus, 1));
        if (stock == null || stock.getProductNum() == null) {
            return 0;
        }
        // 修改库存
        stock.setProductNum(stock.getProductNum() -1);
        stockMapper.updateById(stock);

        return stock.getProductNum();
    }
}
