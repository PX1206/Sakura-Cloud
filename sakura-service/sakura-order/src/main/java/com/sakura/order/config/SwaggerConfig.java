package com.sakura.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Sakura
 * @date 2023/8/7 15:27
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket creatApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select() //选择哪些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.sakura.order.controller"))//controller路径
                .paths(PathSelectors.any())  //对所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("订单服务")//文档主标题
                .description("订单管理")//文档描述
                .version("1.0.0")//API的版本
                .build();
    }
}
