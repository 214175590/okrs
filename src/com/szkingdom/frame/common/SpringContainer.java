/*
 * 简述：获取spring容器对象
 * 详述：spring容器管理对象
 * 最后修改人：yisin
 * 最后修改时间：2012-11-29 下午04:47:05
 */
package com.szkingdom.frame.common;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * spring容器管理， 并直接通过该方式获取容器管理对象，可继续扩展
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.common.SpringContainer
 * 
 */
public class SpringContainer {
	private static WebApplicationContext context = ContextLoader
			.getCurrentWebApplicationContext();

	/**
	 * 通过注解的方式获取实体<br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:51:19
	 * @param beanName
	 * @return Object
	 * @see com.szkingdom.frame.common.SpringContainer#getBeanByName
	 */
	public static Object getBeanByName(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 获取ServletContext <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:53:56
	 * @return ServletContext
	 * @see com.szkingdom.frame.common.SpringContainer#getServletContext
	 */
	public static ServletContext getServletContext() {
		return context.getServletContext();
	}

	/**
	 * 获取web应用服务器上下文对象 <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:55:16
	 * @return WebApplicationContext
	 * @see com.szkingdom.frame.common.SpringContainer#getWebApplicationContext
	 */
	public static WebApplicationContext getWebApplicationContext() {
		return context;
	}
}
