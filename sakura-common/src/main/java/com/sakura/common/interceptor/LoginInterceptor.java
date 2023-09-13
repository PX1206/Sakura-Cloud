package com.sakura.common.interceptor;

import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.StringUtil;
import com.sakura.common.tool.TokenUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		// 获取当前访问接口
		String url = httpServletRequest.getRequestURI();
		log.info(url);

		// 处理OPTIONS请求
		if (httpServletRequest.getMethod().equals("OPTIONS")) {
			return true;
		}
		// 校验登录信息是否正常
		String token = TokenUtil.getToken();
		log.info(token);

		if (StringUtil.isBlank(token) || !redisUtil.hasKey(token)) {
			throw new BusinessException(401, "请重新登录！");
		}

		// 一秒内同一个token请求同一个接口超过一次直接拦截，防止接口请求过快
		long reqNum = redisUtil.incr("req-num-" + token + url, 1);
		if (reqNum > 1) {
			throw new BusinessException(500, "请求过于频繁！");
		}
		redisUtil.expire("req-num-" + token + url, 1);

		// 刷新token时效
		redisUtil.expire(token, 2 * 60 * 60);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

}
