package com.sakura.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Sakura
 * @date 2023/7/19 11:43
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.sakura.common", "com.sakura.user"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
