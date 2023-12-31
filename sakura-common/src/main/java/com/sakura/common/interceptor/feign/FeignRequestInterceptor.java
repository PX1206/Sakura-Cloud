package com.sakura.common.interceptor.feign;

import com.sakura.common.constant.CommonConstant;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Sakura
 * @date 2023/8/10 11:34
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("进入LogIdFeignInterceptor自定义拦截器: ");
        // 在header里面放入logid
        if (MDC.get(CommonConstant.REQUEST_ID) != null) {
            requestTemplate.header(CommonConstant.REQUEST_ID, MDC.get(CommonConstant.REQUEST_ID));
        }

        // 获取当前请求的token传递到下一个服务中
        // 获取当前的HttpServletRequest对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(CommonConstant.ACCESS_TOKEN);
        if (!StringUtil.isEmpty(token)) {
            requestTemplate.header(CommonConstant.ACCESS_TOKEN, token);
        }

        // 生成feign临时认证token, 有效时间30秒
        String feign_token = UUID.randomUUID().toString();
        requestTemplate.header(CommonConstant.FEIGN_TOKEN, feign_token);
        redisUtil.set(feign_token, 1, 30);
    }
}
