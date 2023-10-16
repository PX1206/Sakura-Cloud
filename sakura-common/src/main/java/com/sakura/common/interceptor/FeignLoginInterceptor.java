package com.sakura.common.interceptor;

import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.StringUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log
public class FeignLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

		log.info("进入FeignLoginInterceptor：");
		// 校验feign接口认证信息
		String feign_token = httpServletRequest.getHeader(CommonConstant.FEIGN_TOKEN);
		if (StringUtil.isBlank(feign_token) || !redisUtil.hasKey(feign_token)) {
			throw new BusinessException(401, "接口请求错误！");
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

}
