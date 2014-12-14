package com.szkingdom.frame.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yinsin.server.TcpServerSocket;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;

/**
 * @简述：<一句话介绍>
 * @详述：<详细介绍>
 * @author admin
 * @since 1.0
 * @see
 */
public class InitCustomData implements ServletContextListener {
	ILogger log = LogFactory.getDefaultLogger(InitCustomData.class);

	public void contextInitialized(ServletContextEvent arg0) {
		/*
		 * 待删除文件监听
		 */
		// Mytimer timer = new Mytimer();
		// timer.schedule(new MyTimerTask(), 1000 * 60 * 61);// 每60分钟监听一次
	}

	public void contextDestroyed(ServletContextEvent arg) {
		// 在服务器销毁之前把数据库同步
		/*String resource = arg.getServletContext().getRealPath("/")	+ "WEB-INF/classes/xpdb.h2.db";
		String target = "E:/workspace/YSMS/resources/xpdb.h2.db";
		try {
			FileUtil.copyFile(new File(resource), new File(target));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		log.info("服务销毁...");
	}
}
