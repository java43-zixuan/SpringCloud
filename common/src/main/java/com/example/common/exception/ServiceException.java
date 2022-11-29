package com.example.common.exception;

/**
 * 业务异常类<br/>
 * Spring的事务默认回滚RuntimeException类型异常，故继承该类。
 * 
 * @author web
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

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

}
