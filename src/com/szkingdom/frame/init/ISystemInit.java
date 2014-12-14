/*
 * 文件名：com.szkingdom.frame.init.ISystemInit.java
 * 简述：系统初始化接口类
 * 详述：
 * 修改内容：[新增]
 * 修改时间：2012-12-11 下午02:42:46
 * 修改人：yisin
 * 
 */
package com.szkingdom.frame.init;

/**
 * 
 * <pre>
 * 封装初始化init方法，用于初始化资源
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 下午02:42:46
 * @see com.szkingdom.frame.init.ISystemInit
 * 
 */
public interface ISystemInit {

	public abstract void init();

	/**
	 * 此方法为配置信息处理过程，在一些比较复杂的配置中，需要使用该方法进行处理信息</br> 该方法可以不用实现
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午02:43:17
	 * @see com.szkingdom.frame.init.ISystemInit#process
	 */
	public abstract void process();

	/**
	 * 该方法用于销毁对象信息
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午02:43:26
	 * @see com.szkingdom.frame.init.ISystemInit#destroy
	 */
	public abstract void destroy();
}