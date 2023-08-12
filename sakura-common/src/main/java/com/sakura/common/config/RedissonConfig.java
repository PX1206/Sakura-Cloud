package com.sakura.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sakura
 * @date 2023/8/12 10:56
 */
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient getRedisson() {
        //1.创建配置
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.43.118:6379")
                //.setAddress("redis://192.168.43.128:6379") // 如果是集群模式这里可以填多个Redis地址
                .setPassword("px123456"); // 如果有密码就需要加上这个
        //2.根据config创建处RedissonClient实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}

