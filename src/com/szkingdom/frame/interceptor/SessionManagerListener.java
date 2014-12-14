/*
 * 文件名：com.szkingdom.frame.interceptor.MySessionListener.java
 * 简述：session 监听器
 * 详述： 该类主要监听当session销毁时清除掉ValidCodeManager中map对应的值
 * 修改内容：[新增]
 * 修改时间：2011-11-16
 * 修改人：yanxuefeng
 * 版本：1.0
 * 
 */
package com.szkingdom.frame.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.DateUtil;
import com.szkingdom.web.common.frame.ValidCodeManager;
import com.szkingdom.web.system.users.IUsersService;

/**
 * <pre>
 * 简述:session 监听器
 * 详述: 该类主要监听当session销毁时清除掉ValidCodeManager中map对应的值
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.interceptor.SessionManagerListener
 */
public class SessionManagerListener implements HttpSessionListener {
	// 日志对象
	private static ILogger logger = LogFactory.getRunningLogger(SessionManagerListener.class);

	/**
	 * <pre>
	 * 监听session对象，当创建session时执行
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param event
	 *            session监听事件对象
	 * 
	 */
	public void sessionCreated(HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		logger.info("Create new session " + sessionId);
	}

	/**
	 * <pre>
	 * 监听session销毁，当session销毁时，将ValidCodeManager中map对应的session值去掉
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param event
	 *            session监听事件
	 * 
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		// 得到被销毁的sessionid
		String sessionId = session.getId();

		// 调用ValidCodeManager对象的remove方法，remove掉该map中的值
		ValidCodeManager.remove(sessionId);
		Object obj = session.getAttribute(GlobalConstants.SESSION_KEY_USER);
		if (obj != null) {
			Users users = (Users) obj;
			logger.info("Destroyed session: " + sessionId + ", user: " + users.getUserName());
			setUserOnlineStatus(users.getId(), 2);
		}
		Enumeration en = session.getAttributeNames();
		while (en.hasMoreElements()) {
			Object ele = en.nextElement();
			if (ele != null) {
				session.removeAttribute(ele.toString());
			}
		}
		logger.info("Execute the session Destroy success.");
	}

	public boolean setUserOnlineStatus(String userId, int status) {
		boolean bool = false;
		try {
			// 修改用户在线状态
			IUsersService usersService = (IUsersService) SpringContainer.getBeanByName("usersService");
			Users users22 = new Users();
			users22.setId(userId);
			users22.setOnlines(status);
			users22.setLastLoginDate(DateUtil.format(new Date()));
			Response res = usersService.updateData(users22);
			if (res.getStatusCode() == StatusCode.SUCCESS) {
				bool = true;
			}
		} catch (Exception e) {
			logger.error("修改用户在线状态时报错：" + e.getMessage());
		}
		return bool;
	}

}
