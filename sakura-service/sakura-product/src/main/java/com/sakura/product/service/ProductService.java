package com.sakura.product.service;

import com.sakura.product.entity.Product;
import com.sakura.product.param.ProductPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 商品表 服务类
 *
 * @author Sakura
 * @since 2023-08-08
 */
public interface ProductService extends BaseService<Product> {

    /**
     * 保存
     *
     * @param product
     * @return
     * @throws Exception
     */
    boolean saveProduct(Product product) throws Exception;

    /**
     * 修改
     *
     * @param product
     * @return
     * @throws Exception
     */
    boolean updateProduct(Product product) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteProduct(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param productPageParam
     * @return
     * @throws Exception
     */
    Paging<Product> getProductPageList(ProductPageParam productPageParam) throws Exception;

    /**
     * 获取商品单价
     *
     * @param productNo
     * @return
     * @throws Exception
     */
    Integer getUnitPrice(String productNo);

}
