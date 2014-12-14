package com.szkingdom.frame.init;

/**
 * 
 * <pre>
 * 系统配置信息实体
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 下午01:55:43
 * @see com.szkingdom.frame.init.SystemInitConfigBean
 * 
 */
public class SystemInitConfigBean {
	private String systemInitAttr;
	private String className;
	private String filePath;
	private String isCache;

	public SystemInitConfigBean() {
		isCache = "false";
	}

	public String getSystemInitAttr() {
		return systemInitAttr;
	}

	public void setSystemInitAttr(String systemInitAttr) {
		this.systemInitAttr = systemInitAttr;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getIsCache() {
		return isCache;
	}

	public void setIsCache(String isCache) {
		this.isCache = isCache;
	}

}
