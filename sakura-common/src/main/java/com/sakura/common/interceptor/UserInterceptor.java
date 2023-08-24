package com.sakura.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.feign.PermissionFeignService;
import com.sakura.common.tool.LoginUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
@Log
public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	private PermissionFeignService permissionFeignService;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		// 获取当前访问接口
		String url = httpServletRequest.getRequestURI();
		log.info(url);

		// 处理OPTIONS请求
		if (httpServletRequest.getMethod().equals("OPTIONS")) {
			return true;
		}

		// 如果当前用户不是普通用户则无需走当前权限校验
		if (LoginUtil.getUserType() != 1) {
			return true;
		}

		// 获取当前接口所需权限
		JSONObject json = new JSONObject();
		json.put("url", url);
		Set<String> codes = permissionFeignService.getCodeByUrl(json.toJSONString());
		if (codes == null || codes.size() < 1) {
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
