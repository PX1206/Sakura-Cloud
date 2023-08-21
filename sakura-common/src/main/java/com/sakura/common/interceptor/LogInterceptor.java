package com.sakura.common.interceptor;

import com.sakura.common.constant.CommonConstant;
import com.sakura.common.tool.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Sakura
 * @date 2023/8/21 11:44
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    public LogInterceptor() {
        super();
    }

    public static String getLogId() {
        return MDC.get(CommonConstant.REQUEST_ID);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("请求接口：" + request.getRequestURI());
        try {
            // 全局拦截器，先判断是否有其它服务传递过来的logId
            String logId = request.getHeader(CommonConstant.REQUEST_ID);
            if (StringUtil.isEmpty(logId)) {
                logId = UUID.randomUUID().toString().replaceAll("-", "");
            }

            // 设置logId
            MDC.put(CommonConstant.REQUEST_ID, logId);
            return true;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            // 删除logId
            MDC.remove(CommonConstant.REQUEST_ID);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

}
