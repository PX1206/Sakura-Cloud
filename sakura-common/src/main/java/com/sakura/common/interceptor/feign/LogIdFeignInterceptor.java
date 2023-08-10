package com.sakura.common.interceptor.feign;

import com.sakura.common.constant.CommonConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author Sakura
 * @date 2023/8/10 11:34
 */
public class LogIdFeignInterceptor implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("进入LogIdFeignInterceptor自定义拦截器: ");
        // 在header里面放入logid
        if (MDC.get(CommonConstant.REQUEST_ID) != null) {
            logger.info("进入LogIdFeignInterceptor自定义拦截器: " + MDC.get(CommonConstant.REQUEST_ID));
            requestTemplate.header(CommonConstant.REQUEST_ID, MDC.get(CommonConstant.REQUEST_ID));
        }

    }
}
