package com.szkingdom.frame.init;

import java.util.List;

import com.szkingdom.frame.common.RootContext;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.frame.xml.CommonXMLParser;

/**
 * @简述：<一句话介绍>
 * @详述：<详细介绍>
 * @author Administrator
 * @since 1.0
 * @see
 */
class DefaultSystemInit implements ISystemInit {
	private static final String DEFAULT_SYSTEMINIT_PATH = "/WEB-INF/config/sysinitconfig.xml";
	private static ILogger logger = LogFactory
			.getRunningLogger(DefaultSystemInit.class);
	private CommonXMLParser cxmlp;

	public DefaultSystemInit() {
	}

	public void init() {
		// 获取database配置文件路径
		String sysConfigLocation = System
				.getProperty("systemintconfiglocation");
		if (StringUtil.isEmpty(sysConfigLocation)) {
			sysConfigLocation = DEFAULT_SYSTEMINIT_PATH;
		}
		String systemFileName = RootContext.getRootPath() + sysConfigLocation;
		try {
			cxmlp = new CommonXMLParser(systemFileName);
		} catch (Exception e) {
			logger.info("init the config failed.", e);
			return;
		}
	}

	public void process() {
		List<SystemInitConfigBean> listBeans = cxmlp.getListCollection();
		for (SystemInitConfigBean bean : listBeans) {
			try {
				Class<?> clazz = Class.forName(bean.getClassName());
				ISystemInit isysteminit = (ISystemInit) clazz.getConstructor(
						SystemInitConfigBean.class).newInstance(
						new Object[] { bean });
				isysteminit.init();
				isysteminit.process();
				isysteminit.destroy();
			} catch (Exception e) {
				logger.error("init the [" + bean.getClassName() + "] failed.",
						e);
			}

		}
	}

	public void destroy() {
	}

}
