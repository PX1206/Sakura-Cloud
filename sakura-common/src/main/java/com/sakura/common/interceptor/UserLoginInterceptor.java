package com.sakura.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.feign.PermissionsFeignService;
import com.sakura.common.redis.RedisUtil;
import com.sakura.common.tool.StringUtil;
import com.sakura.common.vo.UserInfoVo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@Component
@Log
public class UserLoginInterceptor implements HandlerInterceptor {


	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private PermissionsFeignService permissionsFeignService;


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
		String token = httpServletRequest.getHeader("Access-Token");

		if (StringUtil.isBlank(token) || !redisUtil.hasKey(token)) {
			throw new BusinessException(401, "超时请重新登录！");
		}
		// 刷新token时效
		redisUtil.expire(token, 2 * 60 * 60);

		// 如果是获取权限code请求则直接返回
		if ("/permissions/getCode".equals(url)) {
			return true;
		}

		// 获取当前接口所需权限
		JSONObject json = new JSONObject();
		json.put("url", url);
		Set<String> codes = permissionsFeignService.getCodeByUrl(json.toJSONString());
		if (codes == null || codes.size() < 1) {
			return true;
		}
		// 获取登录用户权限信息
		UserInfoVo userInfoVo = (UserInfoVo)redisUtil.get(token);
		if (userInfoVo == null || userInfoVo.getPermissions() == null || userInfoVo.getPermissions().size() < 1) {
			throw new BusinessException(500, "未配置权限！");
		}

		// 获取交集，如果没有交集则说明该用户没有访问这个url的权限
		codes.retainAll(userInfoVo.getPermissions());

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


	public void returnJson(HttpServletResponse response, String json) throws Exception {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(json);

		} catch (IOException e) {
			log.info("response error" + e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private boolean checkUrl(List<String> urls, String url) {
		if (urls == null || urls.size() < 1) {
			return false;
		}
		for (String str:urls) {
			if (str.endsWith("/**")) {
				str = str.replace("/**", "");
				if (url.startsWith(str)) {
					return true;
				}
			}
		}
		return false;
	}

}
