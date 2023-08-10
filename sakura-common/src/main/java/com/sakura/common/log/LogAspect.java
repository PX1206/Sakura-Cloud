package com.sakura.common.log;

import com.sakura.common.constant.CommonConstant;
import com.sakura.common.entity.RequestInfo;
import com.sakura.common.tool.DateUtil;
import com.sakura.common.tool.IpUtil;
import com.sakura.common.tool.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Sakura
 * @date 2023/8/9 11:11
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    @Autowired
    private LogUtil logUtil;

    @Pointcut("@annotation(com.sakura.common.log.OperationLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求相关信息
        // HTTP请求信息对象
        RequestInfo requestInfo = new RequestInfo();
        OperationLogInfo operationLogInfo = new OperationLogInfo();
        try {
            // 获取当前的HttpServletRequest对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 请求路径
            String path = request.getRequestURI();
            requestInfo.setPath(path);

            // 获取请求类名和方法名称
            Signature signature = joinPoint.getSignature();

            // 获取真实的方法对象
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            // 处理操作日志信息
            operationLogInfo = logUtil.handleOperationLogInfo(method);

            // IP地址
            String ip = IpUtil.getRequestIp();
            requestInfo.setIp(ip);

            // 获取请求方式
            String requestMethod = request.getMethod();
            requestInfo.setRequestMethod(requestMethod);

            // 获取请求内容类型
            String contentType = request.getContentType();
            requestInfo.setContentType(contentType);

            // 判断控制器方法参数中是否有RequestBody注解
            Annotation[][] annotations = method.getParameterAnnotations();
            boolean isRequestBody = logUtil.isRequestBody(annotations);
            requestInfo.setRequestBody(isRequestBody);

            // 设置请求参数
            Object requestParamObject = logUtil.getRequestParamObject(joinPoint, request, requestMethod, contentType, isRequestBody);
            requestInfo.setParam(requestParamObject);
            requestInfo.setTime(DateUtil.getDateTimeString(new Date()));

            // 获取请求头token
            String token = request.getHeader("token");
            requestInfo.setToken(token);

            // 用户浏览器代理字符串
            requestInfo.setUserAgent(request.getHeader(CommonConstant.USER_AGENT));

            // 记录请求ID
            logUtil.setRequestId(requestInfo, request.getHeader(CommonConstant.REQUEST_ID));

            // 调用子类重写方法，控制请求信息日志处理
            logUtil.getRequestInfo(requestInfo);
        } catch (Exception e) {
            log.error("请求日志AOP处理异常", e);
        }

        String tgoken = TokenUtil.getToken();

        // 执行目标方法,获得返回值
        // 方法异常时，会调用子类的@AfterThrowing注解的方法，不会调用下面的代码，异常单独处理
        Object result = joinPoint.proceed();
        try {
            // 调用子类重写方法，控制响应结果日志处理
            logUtil.getResponseResult(result);
        } catch (Exception e) {
            log.error("处理响应结果异常", e);
        } finally {
            logUtil.finish(result, requestInfo, operationLogInfo);
        }
        return result;
    }

}
