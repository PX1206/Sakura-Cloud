package com.sakura.common.log;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.api.ApiResult;
import com.sakura.common.entity.RequestInfo;
import com.sakura.common.entity.SysOperationLog;
import com.sakura.common.tool.LoginUtil;
import com.sakura.common.tool.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sakura
 * @date 2023/8/9 16:29
 */
@Slf4j
@Component
public class LogUtil {

    /**
     * 项目上下文路径
     */
    //@Value("${server.servlet.context-path}")
    protected String contextPath;

    protected void getResponseResult(Object result) {
        if (result != null && result instanceof ApiResult) {
            ApiResult<?> apiResult = (ApiResult<?>) result;
            // 获取格式化后的响应结果
            String responseResultString = JSONObject.toJSONString(apiResult);
            log.info("返回参数：" + responseResultString);
        }
    }

//    /**
//     * 设置请求ID
//     *
//     * @param requestInfo
//     */
//    protected void setRequestId(RequestInfo requestInfo, String requestId) {
//        if (StringUtil.isEmpty(requestId)) {
//            requestId = UUID.randomUUID().toString().replaceAll("-", "");
//        }
//        log.info("requestId：" + requestId);
//        // 设置请求ID
//        MDC.put(CommonConstant.REQUEST_ID, requestId);
//        requestInfo.setRequestId(requestId);
//    }

    protected void getRequestInfo(RequestInfo requestInfo) {
        // 获取请求信息
        String requestInfoString = JSONObject.toJSONString(requestInfo);
        log.info("请求参数：" + requestInfoString);
    }

    protected OperationLogInfo handleOperationLogInfo(Method method) {
        // 设置控制器类名称和方法名称
        OperationLogInfo operationLogInfo = new OperationLogInfo()
                .setControllerClassName(method.getDeclaringClass().getName())
                .setControllerMethodName(method.getName());

        // 获取Module类注解
        Class<?> controllerClass = method.getDeclaringClass();
        Module module = controllerClass.getAnnotation(Module.class);
        if (module != null) {
            String moduleName = module.name();
            String moduleValue = module.value();
            if (StringUtil.isNotBlank(moduleValue)) {
                operationLogInfo.setModule(moduleValue);
            }
            if (StringUtil.isNotBlank(moduleName)) {
                operationLogInfo.setModule(moduleName);
            }
        }
        // 获取OperationLogIgnore注解
        OperationLogIgnore classOperationLogIgnore = controllerClass.getAnnotation(OperationLogIgnore.class);
        if (classOperationLogIgnore != null) {
            // 不记录日志
            operationLogInfo.setIgnore(true);
        }
        // 判断方法是否要过滤
        OperationLogIgnore operationLogIgnore = method.getAnnotation(OperationLogIgnore.class);
        if (operationLogIgnore != null) {
            operationLogInfo.setIgnore(true);
        }
        // 从方法上获取OperationLog注解
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            String operationLogName = operationLog.name();
            String operationLogValue = operationLog.value();
            if (StringUtil.isNotBlank(operationLogValue)) {
                operationLogInfo.setName(operationLogValue);
            }
            if (StringUtil.isNotBlank(operationLogName)) {
                operationLogInfo.setName(operationLogName);
            }
            operationLogInfo.setType(operationLog.type().getCode()).setRemark(operationLog.remark());
        }
        return operationLogInfo;
    }

    /**
     * 判断控制器方法参数中是否有RequestBody注解
     *
     * @param annotations
     * @return
     */
    protected boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }

    /**
     * 获取请求参数JSON字符串
     *
     * @param joinPoint
     * @param request
     * @param requestMethod
     * @param contentType
     * @param isRequestBody
     */
    protected Object getRequestParamObject(ProceedingJoinPoint joinPoint, HttpServletRequest request, String requestMethod, String contentType, boolean isRequestBody) {
        Object paramObject = null;
        if (isRequestBody) {
            // POST,application/json,RequestBody的类型,简单判断,然后序列化成JSON字符串
            Object[] args = joinPoint.getArgs();
            paramObject = getArgsObject(args);
        } else {
            // 获取getParameterMap中所有的值,处理后序列化成JSON字符串
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getParamJSONObject(paramsMap);
        }
        return paramObject;
    }

    /**
     * 请求参数拼装
     *
     * @param args
     * @return
     */
    protected Object getArgsObject(Object[] args) {
        if (args == null) {
            return null;
        }
        // 去掉HttpServletRequest和HttpServletResponse
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                continue;
            }
            if (arg instanceof ModelAndView) {
                continue;
            }
            realArgs.add(arg);
        }
        if (realArgs.size() == 1) {
            return realArgs.get(0);
        } else {
            return realArgs;
        }
    }

    /**
     * 获取参数Map的JSON字符串
     *
     * @param paramsMap
     * @return
     */
    protected JSONObject getParamJSONObject(Map<String, String[]> paramsMap) {
        if (MapUtils.isEmpty(paramsMap)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            // 没有值
            if (values == null) {
                jsonObject.put(key, null);
            } else if (values.length == 1) {
                // 一个值
                jsonObject.put(key, values[0]);
            } else {
                // 多个值
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }

    @Async
    public void finish(Object result, RequestInfo requestInfo, OperationLogInfo operationLogInfo) {
        // 保存日志操作
        saveSysOperationLog(requestInfo, operationLogInfo, result, null);
        // 释放资源
        //MDC.clear();
    }

    /**
     * 异步保存系统操作日志
     *
     * @param requestInfo
     * @param operationLogInfo
     * @param result
     * @param exception
     */
    protected void saveSysOperationLog(RequestInfo requestInfo, OperationLogInfo operationLogInfo, Object result, Exception exception) {
        try {
            // 操作日志
            SysOperationLog sysOperationLog = new SysOperationLog();
            // 设置操作日志信息
            if (operationLogInfo != null) {
                // 如果类或方法上标注有OperationLogIgnore，则跳过
                if (operationLogInfo.isIgnore()) {
                    return;
                }
                sysOperationLog.setModule(operationLogInfo.getModule())
                        .setName(operationLogInfo.getName())
                        .setType(operationLogInfo.getType())
                        .setRemark(operationLogInfo.getRemark())
                        .setClassName(operationLogInfo.getControllerClassName())
                        .setMethodName(operationLogInfo.getControllerMethodName());
            }
            // 设置请求参数信息
            if (requestInfo != null) {
                sysOperationLog.setIp(requestInfo.getIp())
                        .setPath(requestInfo.getPath())
                        .setRequestId(requestInfo.getRequestId())
                        .setRequestMethod(requestInfo.getRequestMethod())
                        .setContentType(requestInfo.getContentType())
                        .setRequestBody(requestInfo.getRequestBody())
                        .setToken(requestInfo.getTokenMd5());

                // 设置参数字符串
                sysOperationLog.setParam(JSONObject.toJSONString(requestInfo.getParam()));
            }

            // 设置响应结果
            if (result != null && result instanceof ApiResult) {
                ApiResult<?> apiResult = (ApiResult<?>) result;
                apiResult.getCode();
                sysOperationLog.setSuccess(apiResult.isSuccess())
                        .setCode(apiResult.getCode())
                        .setMessage(apiResult.getMessage());
            }

            // 保存请求用户信息
            sysOperationLog.setUserName(LoginUtil.getUserName());
            sysOperationLog.setUserId(LoginUtil.getUserId());

            // 保存日志到数据库
            log.info("日志信息：" + JSONObject.toJSONString(sysOperationLog));


        } catch (Exception e) {
            log.error("保存系统操作日志失败", e);
        }
    }
}
