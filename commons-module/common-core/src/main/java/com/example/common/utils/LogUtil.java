package com.example.common.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * @author web
 */
public class LogUtil {
	/**
	 * 获取Sl4j日志组件入口
	 * @param clazz 目标类Class
	 * @return Logger
	 */
	public static Logger getSl4jLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * 获取CommonLog日志组件入口
	 * @param clazz 目标类Class
	 * @return Logger
	 */
	public static Log getCommonLogger(Class<?> clazz) {
		return LogFactory.getLog(clazz);	}
}
