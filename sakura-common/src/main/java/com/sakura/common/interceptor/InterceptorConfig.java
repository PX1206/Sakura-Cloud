package com.sakura.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport implements WebMvcConfigurer {

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		return converter;
	}

	@Bean
	public LogInterceptor getLogInterceptor() {
		return new LogInterceptor();
	}

	@Bean
	public UserLoginInterceptor getUserLoginInterceptor() {
		return new UserLoginInterceptor();
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {

	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

	}

	@Override
	public void addFormatters(FormatterRegistry registry) {

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getLogInterceptor())
				.addPathPatterns("/**");

		registry.addInterceptor(getUserLoginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user/register")
				.excludePathPatterns("/user/login")
				.excludePathPatterns("/captcha/**")
				.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**", "/favicon.ico", "/error");

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		WebMvcConfigurer.super.configureMessageConverters(converters);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

	}

	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		return null;
	}
}