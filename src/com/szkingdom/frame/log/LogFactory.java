/**
 * 文件名：com.szkingdom.frame.log.LogFactory.java
 * 简述：日志管理器
 * 详述：使用管理器管理打印模块信息等
 * 最后修改人：yisin
 * 最后修改时间：2012-11-29 下午04:21:01
 */
package com.szkingdom.frame.log;

import com.szkingdom.frame.log.impl.LogConstants;
import com.szkingdom.frame.log.impl.LoggerImpl;
import com.szkingdom.frame.log.impl.MyLogger;

/**
 * <pre>
 * 简述:日志管理器
 * 详述:使用管理器管理打印模块信息等
 * </pre>
 * 
 * @author yisin
 * @date 2012-11-29 下午04:21:31
 * @see com.szkingdom.frame.log.LogFactory
 */
public final class LogFactory {

	private static IMyLogger logger = new MyLogger();

	/**
	 * <pre>
	 * 获取运行日志对象
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param clazz
	 *            Class实例
	 * @see com.szkingdom.frame.log.LogFactory#getRunningLogger
	 */
	public static ILogger getRunningLogger(Class<?> clazz) {
		return new LoggerImpl(clazz, LogConstants.RUN_MODULE1);
	}

	/**
	 * <pre>
	 * 获取操作日志对象
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param clazz
	 *            Class实例
	 * @see com.szkingdom.frame.log.LogFactory#getRunningLogger
	 */
	public static ILogger getOperateLogger(Class<?> clazz) {
		return new LoggerImpl(clazz, LogConstants.RUN_MODULE2);
	}

	/**
	 * <pre>
	 *  获取默认日志对象，默认值为Running对象
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param clazz
	 *            Class实例
	 * 
	 */
	public static ILogger getDefaultLogger(Class<?> clazz) {
		return getRunningLogger(clazz);
	}

	/**
	 * <pre>
	 * 获取操作日志对象
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 */
	public static IMyLogger getOperateLogger() {
		return logger;
	}

}
