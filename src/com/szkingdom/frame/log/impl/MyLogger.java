package com.szkingdom.frame.log.impl;

import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.IMyLogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.log.dao.ILoggerDao;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.log.impl.MyLogger
 * 
 */
public class MyLogger implements IMyLogger {
	private static ILogger logger = LogFactory.getDefaultLogger(MyLogger.class);

	public int RunLog(LoggerBean bean) {
		logger.info(bean.getOperationDesc());
		return 0;
	}

	public Response operateLog(LoggerBean bean) {
		Response res = new Response();
		ILoggerDao sac = (ILoggerDao) SpringContainer
				.getBeanByName("loggerDao");
		try {
			res = sac.saveOperateLog(bean);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	public Response loginLog(LoggerBean bean) {
		Response res = new Response();
		ILoggerDao sac = (ILoggerDao) SpringContainer
				.getBeanByName("loggerDao");
		try {
			res = sac.saveLoginLog(bean);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 查询登陆日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:42:14
	 * @see com.szkingdom.frame.log.IMyLogger#findLoginLogs(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response findLoginLogs(LoggerBean bean) {
		Response res = new Response();
		ILoggerDao sac = (ILoggerDao) SpringContainer
				.getBeanByName("loggerDao");
		try {
			res = sac.findLoginLogs(bean);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 查询操作日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:42:14
	 * @see com.szkingdom.frame.log.IMyLogger#findOperationLogs(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response findOperationLogs(LoggerBean bean) {
		Response res = new Response();
		ILoggerDao sac = (ILoggerDao) SpringContainer
				.getBeanByName("loggerDao");
		try {
			res = sac.findOperationLogs(bean);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 删除登陆日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:42:14
	 * @see com.szkingdom.frame.log.IMyLogger#deleteLoginLog(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response deleteLoginLog(LoggerBean bean) {
		Response res = new Response();
		ILoggerDao sac = (ILoggerDao) SpringContainer
				.getBeanByName("loggerDao");
		try {
			res = sac.deleteLoginLog(bean);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 删除操作日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:42:14
	 * @see com.szkingdom.frame.log.IMyLogger#deleteOperateLog(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response deleteOperateLog(LoggerBean bean) {
		Response res = new Response();
		ILoggerDao sac = (ILoggerDao) SpringContainer
				.getBeanByName("loggerDao");
		try {
			res = sac.deleteOperateLog(bean);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

}
