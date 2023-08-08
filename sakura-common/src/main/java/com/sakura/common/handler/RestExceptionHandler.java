package com.sakura.common.handler;

import com.sakura.common.api.ApiResult;
import com.sakura.common.exception.BaseException;
import com.sakura.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 业务异常处理
	 */
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<ApiResult> businessExceptionHandler(HttpServletRequest request, BusinessException e) throws BaseException {
		logger.info("异常接口:" + request.getRequestURI());
		logger.info("业务异常:" + e.getMessage());
		return new ResponseEntity(new ApiResult(e.getErrorCode(), e.getMessage()), HttpStatus.OK);
	}


	/**
	 * @param e
	 * @return ErrorInfo
	 */
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiResult> exceptionHandler(HttpServletRequest request, Exception e) throws BaseException {
		logger.info("异常接口:" + request.getRequestURI());
		logger.error("系统异常:",e);
		return new ResponseEntity(new ApiResult(500, "系统异常，工程师正在抢修ing"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常BindException
	 * 处理@RequestParam上validate失败后抛出的异常是ConstraintViolationException
	 * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException
	 * 拦截自定义参数验证失败异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
	@ResponseBody
	public ResponseEntity<BaseException> handleApiConstraintViolationException(HttpServletRequest request, Exception e) {
		logger.info("异常接口:" + request.getRequestURI());
		logger.info("业务异常:" + e.getMessage());
		String message = null;
		if (e instanceof BindException) {
			message = ((BindException) e).getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.joining(","));
		}
		if (e instanceof MethodArgumentNotValidException) {
			message = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.joining(","));
		}
		if (e instanceof ConstraintViolationException) {
			message = ((ConstraintViolationException) e).getConstraintViolations().stream()
					.map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(","));
		}

		logger.info("异常接口:" + request.getRequestURI());
		logger.info("业务异常:" + e.getMessage());
		return new ResponseEntity(new ApiResult(500, message), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
