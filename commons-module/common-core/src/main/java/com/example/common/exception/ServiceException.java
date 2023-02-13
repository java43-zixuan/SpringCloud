package com.example.common.exception;

import com.example.common.response.ResponseEnum;

/**
 * 业务异常类<br/>
 * Spring的事务默认回滚RuntimeException类型异常，故继承该类。
 * 
 * @author web
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Object object;

	private ResponseEnum responseEnum;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(ResponseEnum responseEnum) {
		super(responseEnum.getMsg());
		this.responseEnum = responseEnum;
	}

	public ServiceException(ResponseEnum responseEnum, Object object) {
		super(responseEnum.getMsg());
		this.responseEnum = responseEnum;
		this.object = object;
	}

}
