package com.sakura.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.product.entify.Product;
import com.sakura.product.mapper.ProductMapper;
import com.sakura.product.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Sakura
 * @date 2023/7/28 16:18
 */
@Service
@Log
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public Integer getUnitPrice(String productNo) {
        Product product = productMapper.selectOne(
                Wrappers.<Product>lambdaQuery()
                        .eq(Product::getProductNo, productNo)
                        .eq(Product::getStatus, 1));
        if (product == null || product.getUnitPrice() == null) {
            return -1;
        }

        return product.getUnitPrice();
    }
}
