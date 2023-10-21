package com.sakura.product.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sakura
 * @date 2023/7/27 16:07
 */
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.info("MyBlockExceptionHandler ---------------" + e.getRule());

        // 处理返回参数
        JSONObject json = new JSONObject();
        json.put("code", 500);

        if (e instanceof FlowException) {
            log.info("接口限流+++++++++++++++++++");
            json.put("msg", "接口限流");
        } else if (e instanceof DegradeException) {
            log.info("服务降级+++++++++++++++++++");
            json.put("msg", "服务降级");
        } else if (e instanceof ParamFlowException) {
            log.info("热点参数限流+++++++++++++++++++");
            json.put("msg", "热点参数限流");
        } else if (e instanceof SystemBlockException) {
            log.info("触发系统保护规则+++++++++++++++++++");
            json.put("msg", "触发系统保护规则");
        } else if (e instanceof AuthorityException) {
            log.info("授权规则不通过+++++++++++++++++++");
            json.put("msg", "授权规则不通过");
        } else {
            log.info("未知异常+++++++++++++++++++");
            json.put("msg", "未知异常");
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), json);
    }
}
