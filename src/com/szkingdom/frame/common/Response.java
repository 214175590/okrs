/*
 * @describe 自定义DAO通用返回对象，可继续扩展
 * @fileName com.szkingdom.frame.common.Response
 * @author yisin
 * @date 2012 2012-11-29
 */
package com.szkingdom.frame.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * 自定义DAO通用返回对象，可继续扩展
 * <ul>
 *      <li>id: 可以用来设置/获取记录的自增长ID</li>
 *      <li>statusCode：数据库操作返回的状态码</li>
 *      <li>object：查询记录时返回的对象</li>
 *      <li>list：查询记录时返回的对象List集合</li>
 *      <li>map：查询记录时返回的对象Map集合</li>
 * </ul>
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.common.Response
 * 
 */
public class Response {
	private int id;
	private int statusCode = StatusCode.SUCCESS;
	private StringBuffer message = new StringBuffer();
	private Object object;
	private List<?> list = new ArrayList<Object>();
	private Set<?> set = new HashSet<Object>();
	private Map<String, ?> map = new HashMap<String, Object>();

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取状态码
	 * 
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 设置状态码
	 * 
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 获取消息
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message.toString();
	}

	/**
	 * 设置消息
	 * 
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message.append(message);
	}

	/**
	 * 获取对象
	 * 
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * 设置对象
	 * 
	 * @param object
	 *            the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * 获取List数据集
	 * 
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 设置List数据集
	 * 
	 * @param list
	 *            the list to set
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * 获取Set数据集
	 * 
	 * @return the list
	 */
	public Set<?> getSet() {
		return set;
	}

	/**
	 * 设置Set数据集
	 * 
	 * @param list
	 *            the list to set
	 */
	public void setSet(Set<?> set) {
		this.set = set;
	}

	/**
	 * 获取Map数据集
	 * 
	 * @return the map
	 */
	public Map<String, ?> getMap() {
		return map;
	}

	/**
	 * 设置Map数据集
	 * 
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<String, ?> map) {
		this.map = map;
	}

}
