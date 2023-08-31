package com.sakura.merchant.interceptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sakura
 * @date 2023/7/20 17:24
 */
public class CustomFeignInterceptor implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("进入feign自定义拦截器");
//        // 我们可以在这里记录日志或者添加参数以及修改参数等
//        requestTemplate.header("123", "123");
//        requestTemplate.query("456", "456");
//        // 我们把参数从1改成5
//        requestTemplate.uri("/get/5");
    }
}
