/*
 * @describe 自定义状态码常量类
 * @fileName com.szkingdom.frame.common.StatusCode
 * @author admin
 * @date 2012 2012-11-29
 */
package com.szkingdom.frame.common;

/**
 * <pre>
 * 自定义状态码常量类，可继续扩展
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.common.StatusCode
 * 
 */
public final class StatusCode {
	/**
	 * 操作成功返回码
	 */
	public static final int SUCCESS = 0x2710;

	/**
	 * 操作失败返回码
	 */
	public static final int FAILED = 0x2711;

	/**
	 * 操作错误返回码
	 */
	public static final int ERROR = 0x2712;

	/**
	 * 参数为空
	 */
	public static final int PARAM_ISNULL = 0x2713;

	/**
	 * 未知错误
	 */
	public static final int RUNTIME_ERROR = 0x2714;

	/**
	 * 数据库异常
	 */
	public static final int DBSQL_ERROR = 0x2715;

	/**
	 * 用户名或密码不正确
	 */
	public static final int NAME_PASS_INCORECT = 0x2716;

	/**
	 * 密码不正确
	 */
	public static final int PASS_INCORECT = 0x2725;

	/**
	 * 账号不存在
	 */
	public static final int ACCOUNT_INCORECT = 0x2726;

	/**
	 * 帐号被冻结
	 */
	public static final int FREEZE_CODE = 0x2717;

	/**
	 * 帐号存在异常
	 */
	public static final int EXCEPTION_CODE = 0x2727;

	/**
	 * 验证码不正确
	 */
	public static final int INVALID_CODE = 0x2718;

	/**
	 * 当前操作失败
	 */
	public static final int OPERATION_FAILED = 0x2719;

	/**
	 * 当前用户 没有任务权限
	 */
	public static final int NOT_PERMISSION = 0x2720;

	/**
	 * 验证码已经过期
	 */
	public static final int VALID_HAS_EXPIRED = 0x2721;

	/**
	 * 密码已经过期
	 */
	public static final int PASSWORD_HAS_EXPIRED = 0x2722;

	/**
	 * 动态密码已经失效
	 */
	public static final int DYNPASSWORD_HAS_EXPIRED = 0x2723;

	/**
	 * 账号已经过期
	 */
	public static final int ACCOUNT_HAS_EXPIRED = 0x2724;

	/**
	 * 其他异常（错误信息是当前错误失败）
	 */
	public static final int OTHER_ERROR = 0x2af8;

	/**
	 * 404错误返回码
	 */
	public static final int E404 = 0x194;

	/**
	 * 500错误返回码
	 */
	public static final int E500 = 0x1f4;

	/**
	 * 501错误返回码
	 */
	public static final int E501 = 0x1f5;

	/**
	 * 400错误返回码
	 */
	public static final int E400 = 0x190;

	/**
	 * 操作成功返回码
	 */
	public static final int SUCCESS2 = 0x3710;
}
