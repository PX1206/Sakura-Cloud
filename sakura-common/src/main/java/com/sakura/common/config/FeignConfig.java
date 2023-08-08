package com.sakura.common.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sakura
 * @date 2023/7/20 15:30
 */
@Configuration
public class FeignConfig {

    // 配置feign的日志等级，默认是不打印日志的，这里我们设置成最高级别full
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
