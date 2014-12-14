/*
 * 文件名：com.szkingdom.frame.init.ConfigManager.java
 * 简述：用于管理配置信息的初始化
 * 详述：管理配置初始化并得到配置信息，提供获取配置的方法
 * 版权: wintao
 * 修改内容：[新增]
 * 修改时间：2012-12-11
 * 修改人：yisin
 * 版本：1.0
 * 
 */
package com.szkingdom.frame.init;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;

import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.RootContext;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;

/**
 * <pre>
 * 简述:用于管理配置信息的初始化
 * 详述:管理配置初始化并得到配置信息，提供获取配置的方法
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11下午 13:44:55
 * @see com.szkingdom.frame.init.ConfigManager
 * 
 */
public class ConfigManager {
	/**
	 * 使用类似xpath的方式来转换值，最终获取配置值
	 */
	private static final String CONFIG_KEY_PATTERN = "/configs/config[@name='{0}']/text()";
	private static ILogger logger = LogFactory
			.getDefaultLogger(ConfigManager.class);
	private static boolean started = false;

	// 构造PortalConfigManager对象
	private static ConfigManager cfgMgr = new ConfigManager();
	private static final Map<String, Map<String, String>> configMaps = new HashMap<String, Map<String, String>>();

	/**
	 * 私有构造器
	 */
	private ConfigManager() {
	}

	/**
	 * <pre>
	 * 获取PortalConfigManager对象
	 * </pre>
	 * 
	 * @author yanxuefeng
	 * @since 1.0
	 * 
	 */
	public static ConfigManager getInstance() {
		return cfgMgr;
	}

	/**
	 * <pre>
	 *  构造AbstractSystemInit对象,并将ServletConfig赋值本地ServletConfig
	 * </pre>
	 * 
	 * @param servletConfig
	 *            ServletConfig对象
	 * @author yanxuefeng
	 * @since 1.0
	 * 
	 */
	public synchronized void init(ServletConfig servletConfig) {
		if (!started) {
			// 初始化上下文信息
			initServletContext(servletConfig);
			// 初始化
			initSystemConfig();
		}
	}

	public void registerConfigure(String hashKey,
			Map<String, String> configvalues) {
		if (StringUtil.isEmpty(hashKey)) {
			logger.error("the parameter hashKey is null.");
			return;
		}
		if (configvalues == null) {
			logger.error("the parameter configvalues is null.");
			return;
		}
		if (configMaps.containsKey(hashKey)) {
			logger.error("the configMap has contains the key [" + hashKey + "]");
			return;
		}
		configMaps.put(hashKey, configvalues);
	}

	public static String getDataBaseConfigValue(String key) {
		if (configMaps.containsKey(GlobalConstants.DB_CONFIG)) {
			Map<String, String> dbconfigMap = configMaps
					.get(GlobalConstants.DB_CONFIG);
			String xpath = CONFIG_KEY_PATTERN.replace("{0}", key);
			return dbconfigMap != null ? dbconfigMap.get(xpath) : null;
		} else {
			return null;
		}
	}

	private void initServletContext(ServletConfig servletConfig) {
		// 得到根目录信息
		String contextPath = servletConfig.getServletContext().getRealPath("/");
		System.out.println("context Path = " + contextPath);
		if (contextPath.endsWith(File.separator)) {
			contextPath = contextPath.substring(GlobalConstants.NUM_0,
					contextPath.length() - GlobalConstants.NUM_1);
			contextPath = contextPath.replace(File.separatorChar, '/');
		}
		// 设置根目录信息
		String rootName = servletConfig.getServletContext().getContextPath();
		String projectName = rootName;
		if (StringUtil.isEmpty(projectName)) {
			projectName = contextPath;
			if (projectName.substring(projectName.length() - 1).equals("/")) {
				projectName = projectName
						.substring(0, projectName.length() - 1);
			}
			projectName = projectName.substring(projectName.lastIndexOf("/"));
		}
		RootContext.setRootPath(contextPath);
		// 设置虚拟目录名称
		RootContext.setRootName(rootName);
		RootContext.setProjectName(projectName);
		System.out.println("ProjectName = " + projectName);
		logger.info("Setting the Context information success.."
				+ RootContext.getRootName());
	}

	private void initSystemConfig() {
		ISystemInit abstractSystemInit = new DefaultSystemInit();
		try {
			abstractSystemInit.init();
			abstractSystemInit.process();
			abstractSystemInit.destroy();
			logger.info("init the Config information Success.");
		} catch (Exception e) {
			logger.error("init the Config Config failed", e);
		}
	}
}
