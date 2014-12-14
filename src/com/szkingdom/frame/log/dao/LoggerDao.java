package com.szkingdom.frame.log.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.log.impl.LoggerBean;

@Repository("loggerDao")
public class LoggerDao extends SqlSessionDaoSupport implements ILoggerDao {
	private static ILogger logger = LogFactory.getDefaultLogger(LoggerDao.class);
	/**
	 * 将登陆日志插入数据库<br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:10:28
	 * @param LoggerBean
	 * @returns int
	 * @exception SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao.saveLoginLog
	 * 
	 */
	public Response saveLoginLog(LoggerBean bean) throws SQLException {
		logger.debug("In com.szkingdom.frame.log.dao.LoggerDao#saveLoginLog");
		Response res = new Response();
		int status = getSqlSession().insert("LoggerMapper.saveLoginLog", bean);
		// 如果需要带出主键ID
		// *******
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * 将操作日志插入数据库<br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:10:28
	 * @param LoggerBean
	 * @returns int
	 * @exception SQLException
	 * @see com.szkingdom.frame.log.dao.ILoggerDao.saveOperateLog
	 * 
	 */
	public Response saveOperateLog(LoggerBean bean) throws SQLException {
		Response res = new Response();
		int status = getSqlSession()
				.insert("LoggerMapper.saveOperateLog", bean);
		// 如果需要带出主键ID
		// *******
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 查询登陆日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:46:21
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#findLoginLogs(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response findLoginLogs(LoggerBean bean) throws SQLException {
		Response res = new Response();
		List<LoggerBean> list = getSqlSession().selectList(
				"LoggerMapper.findLoginLogs", bean);
		// 如果需要带出主键ID
		// *******
		if (list != null) {
			res.setStatusCode(StatusCode.SUCCESS);
			res.setList(list);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 查询操作日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:46:21
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#findOperationLogs(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response findOperationLogs(LoggerBean bean) throws SQLException {
		Response res = new Response();
		List<LoggerBean> list = getSqlSession().selectList(
				"LoggerMapper.findOperationLogs", bean);
		// 如果需要带出主键ID
		// *******
		if (list != null) {
			res.setStatusCode(StatusCode.SUCCESS);
			res.setList(list);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 删除登陆日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:46:21
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#deleteLoginLog(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response deleteLoginLog(LoggerBean bean) throws SQLException {
		Response res = new Response();
		int status = getSqlSession()
				.delete("LoggerMapper.deleteLoginLog", bean);
		// 如果需要带出主键ID
		// *******
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 删除操作日志
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:46:21
	 * @see com.szkingdom.frame.log.dao.ILoggerDao#deleteOperateLog(com.szkingdom.bean.pojo.system.LoggerBean)
	 */
	public Response deleteOperateLog(LoggerBean bean) throws SQLException {
		Response res = new Response();
		int status = getSqlSession().delete("LoggerMapper.deleteOperateLog",
				bean);
		// 如果需要带出主键ID
		// *******
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}
}
