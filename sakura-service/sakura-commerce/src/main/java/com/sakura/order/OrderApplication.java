package com.sakura.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Sakura
 * @date 2023/7/19 11:43
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.sakura.common", "com.sakura.order"})
@ComponentScan(basePackages = {"com.sakura.common", "com.sakura.order"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
