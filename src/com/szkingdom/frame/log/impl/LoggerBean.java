/*
 * @describe 日志通用实体Bean
 * @fileName com.szkingdom.frame.log.impl.LoggerBean
 * @author yisin
 * @date 2012 2012-11-29
 */
package com.szkingdom.frame.log.impl;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @描述：日志通用实体Bean
 * </pre>
 * 
 * @author admin
 * @date 2012 2012-11-29
 * @see com.szkingdom.bean.pojo.system.LoggerBean
 * 
 */
public class LoggerBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int logid;
	private String logids;
	private int genre; // 日志类型 0：登陆日志，1：操作日志
	private String account; // 当前操作用户账号
	private String accountType; // 当前操作用户账号类型（smms，other）
	private String name; // 当前操作用户姓名
	private String userIp; // 当前操作用户IP地址
	private String operationType; // 当前操作类型
	private Date operationDate; // 当前操作时间
	private Date operationBgDate; // 操作时间段开始时间
	private Date operationEdDate; // 操作时间段结束时间
	private int functionId; // 当前操作的功能点ID
	private String functionName; // 当前操作的功能名称
	private String beanName; // 当前操作的实体
	private String tableName; // 当前操作的数据库表名
	private String see; // 日志记录点
	private String operationDesc; // 当前操作具体内容描述
	private int logresult; // 登陆结果
	private String orderField; // 排序字段 默认(order by operationDate desc)

	/**
	 * @return the logid
	 */
	public int getLogid() {
		return logid;
	}

	/**
	 * @param logid
	 *            the logid to set
	 */
	public void setLogid(int logid) {
		this.logid = logid;
	}

	/**
	 * @return the logids
	 */
	public String getLogids() {
		return logids;
	}

	/**
	 * @param logids
	 *            the logids to set
	 */
	public void setLogids(String logids) {
		this.logids = logids;
	}

	/**
	 * 日志类型 0：登陆日志，1：操作日志
	 * 
	 * @return the genre
	 */
	public int getGenre() {
		return genre;
	}

	/**
	 * 日志类型 0：登陆日志，1：操作日志
	 * 
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(int genre) {
		this.genre = genre;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the functionId
	 */
	public int getFunctionId() {
		return functionId;
	}

	/**
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the beanName
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param beanName
	 *            the beanName to set
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 用户账号
	 * 
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userIp
	 */
	public String getUserIp() {
		return userIp;
	}

	/**
	 * @param userIp
	 *            the userIp to set
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType
	 *            the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the operationDate
	 */
	public Date getOperationDate() {
		return operationDate;
	}

	/**
	 * @param operationDate
	 *            the operationDate to set
	 */
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * @return the operationDesc
	 */
	public String getOperationDesc() {
		if (null == operationDesc) {
			if (this.genre == 0) {
				operationDesc = "请求：" + this.operationType;
			} else if (this.genre == 1) {
				operationDesc = "日志记录点：" + this.see + "\n";
				operationDesc += "操作的功能点：" + this.functionName + "\n";
				operationDesc += "操作的实体Bean：" + this.beanName + "\n";
				operationDesc += "操作的数据库表：" + this.tableName;
			}
		}
		return operationDesc;
	}

	/**
	 * @param operationDesc
	 *            the operationDesc to set
	 */
	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	/**
	 * @return the see
	 */
	public String getSee() {
		return see;
	}

	/**
	 * @param see
	 *            the see to set
	 */
	public void setSee(String see) {
		this.see = see;
	}

	/**
	 * 0：成功，1：失败
	 * 
	 * @return the logresult
	 */
	public int getLogresult() {
		return logresult;
	}

	/**
	 * 0：成功，1：失败
	 * 
	 * @param logresult
	 *            the logresult to set
	 */
	public void setLogresult(int logresult) {
		this.logresult = logresult;
	}

	/**
	 * @return the orderField
	 */
	public String getOrderField() {
		return orderField;
	}

	/**
	 * @param orderField
	 *            the orderField to set
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**
	 * @return the operationBgDate
	 */
	public Date getOperationBgDate() {
		return operationBgDate;
	}

	/**
	 * @param operationBgDate
	 *            the operationBgDate to set
	 */
	public void setOperationBgDate(Date operationBgDate) {
		this.operationBgDate = operationBgDate;
	}

	/**
	 * @return the operationEdDate
	 */
	public Date getOperationEdDate() {
		return operationEdDate;
	}

	/**
	 * @param operationEdDate
	 *            the operationEdDate to set
	 */
	public void setOperationEdDate(Date operationEdDate) {
		this.operationEdDate = operationEdDate;
	}

}
