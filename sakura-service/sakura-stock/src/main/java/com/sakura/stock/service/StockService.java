package com.sakura.stock.service;

import com.sakura.stock.entity.Stock;
import com.sakura.stock.param.StockPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 库存表 服务类
 *
 * @author Sakura
 * @since 2023-10-23
 */
public interface StockService extends BaseService<Stock> {

    /**
     * 保存
     *
     * @param stock
     * @return
     * @throws Exception
     */
    boolean saveStock(Stock stock) throws Exception;

    /**
     * 修改
     *
     * @param stock
     * @return
     * @throws Exception
     */
    boolean updateStock(Stock stock) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteStock(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param stockPageParam
     * @return
     * @throws Exception
     */
    Paging<Stock> getStockPageList(StockPageParam stockPageParam) throws Exception;

    /**
     * 获取商品库存数量
     *
     * @param productNo
     * @return
     * @throws Exception
     */
    Integer getProductNum(String productNo);

}
