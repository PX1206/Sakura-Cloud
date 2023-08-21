package com.sakura.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Sakura
 * @date 2023/7/19 11:43
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.sakura.common", "com.sakura.product"})
@ComponentScan(basePackages = {"com.sakura.common", "com.sakura.product"})
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
