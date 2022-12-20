package com.example.common.exception;


import com.example.common.constant.UnitConstants;
import com.example.common.utils.LogUtil;
import com.example.common.vo.ResultVO;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 全局异常处理切面：利用 @ControllerAdvice + @ExceptionHandler组合处理控制器层RuntimeException异常
 * 必须被spring扫描到才会生效（注意启动类扫描范围）
 * @author web
 */
@Component
@RestControllerAdvice
public class ExceptionAspect {
	private final Log logger = LogUtil.getCommonLogger(ExceptionAspect.class);

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResultVO<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("控制器绑定方法参数异常...", e);
		return ResultVO.fail(UnitConstants.FAIL_BIND_CODE,"控制器绑定方法参数异常...");
	}

	/**
	 * 500 - 控制器返回数据绑定异常
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HttpMessageNotWritableException.class)
	public ResultVO<?> handleHttpMessageNotWritableException(HttpMessageNotWritableException e) {
		logger.error("控制器绑定返回数据异常...", e);
		return ResultVO.fail(UnitConstants.FAIL_BIND_CODE, "控制器绑定返回数据异常...");
	}

	/**
	 * 400 - 参数校验未通过，统一处理
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ValidationException.class, BindException.class, MethodArgumentNotValidException.class})
	public ResultVO<?> handleValidationException(Exception e) {
		logger.error("控制器校验参数异常...", e);
		//@Validated 校验方法【普通类型参数】，直接在参数前写@NotNull等校验注解
		if(e instanceof ValidationException) {
			if(e instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> cvs = ((ConstraintViolationException)e).getConstraintViolations();
				StringBuilder errMsg = new StringBuilder();
				cvs.forEach(cv -> errMsg.append(cv.getPropertyPath()).append(cv.getMessage()).append("，输入值为[").append(cv.getInvalidValue()).append("]。"));
				return ResultVO.fail(UnitConstants.FAIL_VALIDATE_CODE,errMsg.toString());
			}
		}else {
			//@Valid 校验方法【对象类型参数】，对象中写@NotNull等校验注解
			BindingResult br = null;
			if(e instanceof BindException) {
				br = ((BindException)e).getBindingResult();
			}else if(e instanceof MethodArgumentNotValidException) {
				br = ((MethodArgumentNotValidException)e).getBindingResult();
			}
			if (null != br) {
				StringBuilder errMsg = new StringBuilder();
				br.getFieldErrors().forEach(error -> errMsg.append(error.getDefaultMessage()).append("，输入值为[").append(error.getRejectedValue()).append("]。"));
				return ResultVO.fail(UnitConstants.FAIL_VALIDATE_CODE,errMsg.toString());
			}
		}
		return ResultVO.fail(UnitConstants.FAIL_VALIDATE_CODE,"控制器校验参数异常...");
	}

	/**
	 * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResultVO<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error("控制器不支持Http请求的方式...", e);
		return ResultVO.fail("控制器不支持Http请求的方式...");
	}

	/**
	 * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public ResultVO<?> handleHttpMediaTypeNotSupportedException(Exception e) {
		logger.error("控制器不支持Http请求的MediaType...", e);
		return ResultVO.fail("控制器不支持Http请求的MediaType...");
	}

	/**
	 * 200 - 业务异常正常返回
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ServiceException.class})
	public ResultVO<?> handleServiceException(Exception e) {
		//ServiceException是正常的业务情况，用来控制业务流畅，返回处理失败即可；
		return ResultVO.fail(e.getMessage());
	}

	/**
	 * 401 - 用户认证授权失败
	 */
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	@ExceptionHandler({AuthException.class })
//	public ResultVO<?> handleJwtException(Exception e) {
//		logger.error("Token验证失败："+e.getMessage(), e);
//		return ResultVO.fail(UnitConstants.FAIL_VALIDATE_CODE, "用户验证失败："+e.getMessage());
//	}

	/**
	 * 500 - 服务端异常
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Throwable.class})
	public ResultVO<?> handleException(Exception e) {
		//其他类型的异常是系统真正的异常，需打印异常日志，返回异常信息
		logger.error("系统异常："+e.getMessage(), e);
		return ResultVO.fail("系统异常："+e.getMessage());
	}

}