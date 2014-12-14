/*
 * 文件名：com.szkingdom.frame.init.PortalServletInit.java
 * 简述：初始化Portal应用程序
 * 详述：容器自动初始化资源信息
 * 最后修改人：yisin
 * 最后修改时间：2012-11-30 上午11:58:57
 */
package com.szkingdom.frame.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;

public class SmmsServletInit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10000L;
	private static ILogger logger = LogFactory
			.getRunningLogger(SmmsServletInit.class);

	/**
	 * <pre>
	 * 初始化信息
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * 
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletConfig servletConfig = config;
		logger.debug("Begin to init the program resources.");
		// 初始化配置信息（如：数据库配置文件信息）
		ConfigManager configMgr = ConfigManager.getInstance();
		configMgr.init(servletConfig);
		logger.debug("End to init the program resources.");
		
		// 在系统停止时必须显式关闭所有缓存
		//Runtime.getRuntime().addShutdownHook(new EHcacheShutdownHookThread());

	}

}