package com.szkingdom.frame.log.dao;

import java.sql.SQLException;

import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.log.impl.LoggerBean;

/**
 * <pre>
 * 操作日志DAO接口
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.log.dao.IOperateDao
 * 
 */
public interface ILoggerDao {
	/**
	 * 
	 * 将操作日志插入数据库 <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29
	 * @param LoggerBean
	 * @return Response
	 * @throws SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#saveOperateLog
	 */
	public abstract Response saveOperateLog(LoggerBean bean)
			throws SQLException;

	/**
	 * 
	 * 将登陆日志插入数据库 <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29
	 * @param LoggerBean
	 * @return Response
	 * @throws SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#saveLoginLog
	 */
	public abstract Response saveLoginLog(LoggerBean bean) throws SQLException;

	/**
	 * 查询登陆日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:37:37
	 * @param LoggerBean
	 * @return Response
	 * @throws SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#findLoginLogs
	 */
	public abstract Response findLoginLogs(LoggerBean bean) throws SQLException;

	/**
	 * 查询操作日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:37:42
	 * @param LoggerBean
	 * @return Response
	 * @throws SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#findOperationLogs
	 */
	public abstract Response findOperationLogs(LoggerBean bean)
			throws SQLException;

	/**
	 * 删除登陆日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:41:43
	 * @param LoggerBean
	 * @return Response
	 * @throws SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#deleteLoginLog
	 */
	public abstract Response deleteLoginLog(LoggerBean bean)
			throws SQLException;

	/**
	 * 删除操作日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:41:46
	 * @param LoggerBean
	 * @return Response
	 * @throws SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#deleteOperateLog
	 */
	public abstract Response deleteOperateLog(LoggerBean bean)
			throws SQLException;
}
