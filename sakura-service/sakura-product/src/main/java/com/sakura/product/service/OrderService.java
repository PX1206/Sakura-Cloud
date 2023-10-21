package com.sakura.product.service;

import com.sakura.product.entity.Order;
import com.sakura.product.param.AddOrderParam;
import com.sakura.product.param.OrderPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 订单表 服务类
 *
 * @author Sakura
 * @since 2023-08-07
 */
public interface OrderService extends BaseService<Order> {

    /**
     * 保存
     *
     * @param order
     * @return
     * @throws Exception
     */
    boolean saveOrder(AddOrderParam addOrderParam) throws Exception;

    /**
     * 修改
     *
     * @param order
     * @return
     * @throws Exception
     */
    boolean updateOrder(Order order) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteOrder(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param orderQueryParam
     * @return
     * @throws Exception
     */
    Paging<Order> getOrderPageList(OrderPageParam orderPageParam) throws Exception;

}
