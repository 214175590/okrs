/**
 * 文件名：com.szkingdom.frame.log.impl.LoggerImpl.java
 * 简述：log4j日志处理
 * 详述：主要处理日志debug,info,warn,error,fatal级别的日志信息
 * 最后修改人：yisin
 * 最后修改时间：2012-11-29 下午04:17:32
 */
package com.szkingdom.frame.log.impl;

import org.apache.log4j.Logger;

import com.szkingdom.frame.log.ILogger;

/**
 * <pre>
 * 简述:log4j日志处理模块
 * 详述:主要处理日志debug,info,warn,error,fatal级别的日志信息
 * </pre>
 * 
 * @author yisin
 * @date 2012-11-29 下午04:18:31
 * @see com.szkingdom.frame.log.impl.LoggerImpl
 */
public class LoggerImpl implements ILogger {
	private Logger logger;
	private String module;

	/**
	 * <pre>
	 * 构造器
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param clazz
	 *            Class对象
	 * @param module
	 *            日志模块
	 * 
	 */
	public LoggerImpl(Class<?> clazz, String module) {
		logger = Logger.getLogger(clazz);
		this.module = module;
	}

	/**
	 * <pre>
	 *  打印debug日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * 
	 */
	public void debug(String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuffer().append(module).append(" - ")
					.append(message).toString());
		}
	}

	/**
	 * <pre>
	 *  打印debug日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * @param t
	 *            异常堆栈对象
	 * 
	 */
	public void debug(String message, Throwable t) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuffer().append(module).append(" - ")
					.append(message).toString(), t);
		}
	}

	/**
	 * <pre>
	 *  打印info日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * 
	 */
	public void info(String message) {
		if (logger.isInfoEnabled()) {
			logger.info(new StringBuffer().append(module).append(" - ")
					.append(message).toString());
		}
	}

	/**
	 * <pre>
	 *  打印info日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * @param t
	 *            异常堆栈对象
	 * 
	 */
	public void info(String message, Throwable t) {
		logger.info(
				new StringBuffer().append(module).append(" - ").append(message)
						.toString(), t);
	}

	/**
	 * <pre>
	 *  打印warn日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * 
	 */
	public void warn(String message) {
		logger.warn(new StringBuffer().append(module).append(" - ")
				.append(message).toString());
	}

	/**
	 * <pre>
	 *  打印warn日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * @param t
	 *            异常堆栈对象
	 * 
	 */
	public void warn(String message, Throwable t) {
		logger.warn(
				new StringBuffer().append(module).append(" - ").append(message)
						.toString(), t);
	}

	/**
	 * <pre>
	 *  打印error日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * 
	 */
	public void error(String message) {
		logger.error(new StringBuffer().append(module).append(" - ")
				.append(message).toString());
	}

	/**
	 * <pre>
	 *  打印error日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * @param t
	 *            异常堆栈对象
	 * 
	 */
	public void error(String message, Throwable t) {
		logger.error(
				new StringBuffer().append(module).append(" - ").append(message)
						.toString(), t);
	}

	/**
	 * <pre>
	 *  打印fatal日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * 
	 */
	public void fatal(String message) {
		logger.fatal(new StringBuffer().append(module).append(" - ")
				.append(message).toString());
	}

	/**
	 * <pre>
	 *  打印fatal日志信息，只有默认设置的打印日志级别等于或高于当前日志其别才会打印该日志
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param message
	 *            日志信息
	 * @param t
	 *            异常堆栈对象
	 * 
	 */
	public void fatal(String message, Throwable t) {
		logger.fatal(
				new StringBuffer().append(module).append(" - ").append(message)
						.toString(), t);
	}

}
