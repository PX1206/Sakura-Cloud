package com.sakura.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sakura
 * @date 2023/7/31 11:43
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        // 如果sentinel控制台不显示API管理菜单
        // 添加参数，让sentinel知道这个是网关然后在控制台展示相关功能
        //System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(GatewayApplication.class, args);
    }

}
