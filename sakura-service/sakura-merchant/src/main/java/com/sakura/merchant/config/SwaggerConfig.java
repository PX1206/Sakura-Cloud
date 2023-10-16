package com.sakura.merchant.config;

import com.sakura.common.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sakura
 * @date 2023/8/7 15:27
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sakura.merchant.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContexts()))
                .securitySchemes(Arrays.asList(securitySchemes()))
                .apiInfo(new ApiInfoBuilder()
                        .description("商户管理管理")
                        .title("商户管理管理接口文档")
                        .contact(new Contact("Sakura","https://github.com/PX1206/Sakura-Cloud","97442433@qq.com"))
                        .version("v1.0")
                        .license("Apache2.0")
                        .build());
    }
    private SecurityScheme securitySchemes() {
        return new ApiKey(CommonConstant.ACCESS_TOKEN, CommonConstant.ACCESS_TOKEN, "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(CommonConstant.ACCESS_TOKEN, authorizationScopes));
    }

}
