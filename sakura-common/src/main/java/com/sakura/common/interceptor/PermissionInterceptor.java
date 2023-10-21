package com.sakura.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.feign.PermissionFeignService;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.LoginUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log
public class PermissionInterceptor implements HandlerInterceptor {

	@Autowired
	private PermissionFeignService permissionFeignService;
	@Autowired
	RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		// 获取当前访问接口
		String url = httpServletRequest.getRequestURI();
		log.info(url);

		// 处理OPTIONS请求
		if (httpServletRequest.getMethod().equals("OPTIONS")) {
			return true;
		}

		// 获取当前接口所需权限
		JSONObject json = new JSONObject();
		json.put("url", url);
		// 优先从Redis获取，Redis没有再去查询
		Set<String> codes;
		if (!redisUtil.hasKey(CommonConstant.PERMISSION_URL + url) || redisUtil.sGet(CommonConstant.PERMISSION_URL + url).isEmpty()) {
			codes = permissionFeignService.getCodeByUrl(json.toJSONString());
			// 将数据缓存到数据库并设置有效期
			redisUtil.sSetAndTime(CommonConstant.PERMISSION_URL + url, 72*60*60 ,codes.toArray());
		} else {
			codes = redisUtil.sGet(CommonConstant.PERMISSION_URL + url).stream()
					.map(Object::toString)
					.collect(Collectors.toSet());
		}

		if ( codes.size() < 1) {
			return true;
		}
		// 获取登录用户权限信息
		Set<String> permissions = LoginUtil.getPermissions();
		if (permissions == null || permissions.size() < 1) {
			throw new BusinessException(500, "未配置权限！");
		}

		// 获取交集，如果没有交集则说明该用户没有访问这个url的权限
		codes.retainAll(permissions);

		if (codes.isEmpty()) {
			throw new BusinessException(500, "非法访问！");
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
