package com.sakura.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.product.entity.Product;
import com.sakura.product.mapper.ProductMapper;
import com.sakura.product.service.ProductService;
import com.sakura.product.param.ProductPageParam;
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
 * 商品表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-08
 */
@Slf4j
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveProduct(Product product) throws Exception {
        return super.save(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProduct(Product product) throws Exception {
        return super.updateById(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteProduct(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Product> getProductPageList(ProductPageParam productPageParam) throws Exception {
        Page<Product> page = new PageInfo<>(productPageParam, OrderItem.desc(getLambdaColumn(Product::getCreateDt)));
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        IPage<Product> iPage = productMapper.selectPage(page, wrapper);
        return new Paging<Product>(iPage);
    }

    @Override
    public Integer getUnitPrice(String productNo) {
        Product product = productMapper.selectOne(
                Wrappers.<Product>lambdaQuery()
                        .eq(Product::getProductNo, productNo)
                        .eq(Product::getStatus, 1));
        if (product == null || product.getUnitPrice() == null) {
            return -1;
        }

        //int a = 1/0;

        return product.getUnitPrice();
    }

}
