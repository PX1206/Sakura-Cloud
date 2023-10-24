package com.sakura.user.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Sakura
 * @date 2023/10/20 11:00
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class SwaggerConfiguration {

    @Bean(value = "userApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sakura.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("用户管理接口文档")
                .description("<div style='font-size:14px;color:red;'>用户管理APIs</div>")
                .termsOfServiceUrl("http://localhost:8000/api-user/")
                .contact(new Contact("Sakura","https://github.com/PX1206/Sakura-Cloud","97442433@qq.com"))
                .version("1.0")
                .build();
    }

}

