/*
 * 文件名：com.szkingdom.frame.init.DataBaseConfigInit.java
 * 简述：数据库配置项管理类
 * 详述：使用该类可以初始化数据库配置文件中的配置项
 * 修改内容：[新增]
 * 修改时间：2011-11-02
 * 修改人：yisin
 * 版本：1.0
 * 
 */
package com.szkingdom.frame.init;

import java.io.FileInputStream;
import java.util.Map;

import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.RootContext;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.frame.xml.XPathConfigParser;

/**
 * <pre>
 * 简述:数据库配置项管理类
 * 详述:使用该类可以初始化数据库配置文件中的配置项
 * </pre>
 * 
 * @author yisin
 * @since 1.0
 * @see *
 */
public class DataBaseConfigInit implements ISystemInit {
	private static final String DEFAULT_DATABASECONFIG_PATH = "/WEB-INF/config/oracle_config.xml";

	/**
	 * XPath方式解析xml文件
	 */
	private XPathConfigParser parser;

	private SystemInitConfigBean bean;

	public DataBaseConfigInit(SystemInitConfigBean bean) {
		this.bean = bean;
		parser = new XPathConfigParser();
	}

	/**
	 * <pre>
	 * 初始化方法，用于构造对象
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @throws Exception
	 *             抛出异常信息
	 * @see com.szkingdom.frame.init.ISystemInit#init()
	 * 
	 */
	public void init() {

		// 获取database配置文件路径
		String databaseconfig = bean.getFilePath();
		if (StringUtil.isEmpty(databaseconfig)) {
			databaseconfig = DEFAULT_DATABASECONFIG_PATH;
		}
		String systemFileName = RootContext.getRootPath() + databaseconfig;
		try {
			parser.parse(new FileInputStream(systemFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void process() {
		Map<String, String> databaseConfig = parser.getMapCollection();
		if ("true".equals(bean.getIsCache())) {
			ConfigManager.getInstance().registerConfigure(
					GlobalConstants.DB_CONFIG, databaseConfig);
		}
	}

	public void destroy() {
	}

}
