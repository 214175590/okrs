/**
 * 文件名：com.szkingdom.frame.interceptor.SessionInterceptor.java
 * 简述：struts2拦截器
 * 详述：该拦截器主要用于处理session信息
 * 时间：2012-11-29 下午03:47:22
 * 修改人：yisin
 * 
 */
package com.szkingdom.frame.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.GlobalConstants;

/**
 * <pre>
 * 简述:struts2拦截器
 * 详述:该拦截器主要用于处理session信息
 * </pre>
 * 
 * @author yisin
 * @date 2012-11-29 下午03:49:44
 * @最后修改人：admin
 * @最后修改时间：2012-11-29 下午03:49:51
 * @see com.szkingdom.frame.interceptor.SessionInterceptor
 */
@SuppressWarnings("serial")
public class SessionInterceptor extends AbstractInterceptor {

	/**
	 * 标注没有登录的请求uri,需要都添加到该unLoginUri中去
	 */
	private static final List<String> actionNames = new ArrayList<String>();
	static {
		actionNames.add("com.wintao.daiwei.web.login.action.PortalLoginAction");
		actionNames.add("com.wintao.daiwei.web.login.action.WebFrameAction");
	}

	/**
	 * <pre>
	 * 拦截用户请求，首先判断用户session信息是否为空，
	 * 如果为空，再根据unLoginUri判断，如果在unLoginUri中不包含该请求直接跳转到
	 * 登录页面，否则放通
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param invocation
	 *            ActionInvocation对象
	 * @return String 结果类型
	 * 
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = ActionContext.getContext();
		// 获取httprequest对象
		HttpServletRequest request = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		// 得到action对象
		Action action = (Action) invocation.getAction();
		System.out.println("In........" + action.getClass().getName());
		// 根据类名判断，如果完整类名包含在actionNames中直接跳转
		if (actionNames.contains(action.getClass().getName())) {
			return invocation.invoke();
		} else {
			if (isSessionDestroy(request)) {
				return invocation.invoke();
			}
		}
		return Action.LOGIN;
	}

	private boolean isSessionDestroy(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Users Users = (Users) session
					.getAttribute(GlobalConstants.SESSION_KEY_USER);
			if (Users != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
