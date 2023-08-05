package com.sakura.generator.config;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@Accessors(chain = true)
public class MybatisPlusGeneratorConfig {
    /**
     * 全局配置
     */
    @NestedConfigurationProperty
    private GlobalConfig globalConfig = new GlobalConfig();

    /**
     * 包配置
     */
    @NestedConfigurationProperty
    private PackageConfig packageConfig = new PackageConfig();

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig = new StrategyConfig();

}
