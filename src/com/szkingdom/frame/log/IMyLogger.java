package com.szkingdom.frame.log;

import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.log.impl.LoggerBean;

/**
 * <pre>
 * 日志操作接口
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.log.IMyLogger
 */
public interface IMyLogger {
	/**
	 * 运行日志 <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:32:29
	 * @param LoggerBean
	 * @return Response
	 * @see com.szkingdom.frame.log.IMyLogger#RunLog
	 */
	public abstract int RunLog(LoggerBean bean);

	/**
	 * 登陆日志 <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:32:29
	 * @param LoggerBean
	 * @return int
	 * @see com.szkingdom.frame.log.IMyLogger#loginLog
	 */
	public abstract Response loginLog(LoggerBean bean);

	/**
	 * 操作日志 <br>
	 * 
	 * @author yisin
	 * @date 2012-11-29 下午04:32:29
	 * @param LoggerBean
	 * @return Response
	 * @see com.szkingdom.frame.log.IMyLogger#operateLog
	 */
	public abstract Response operateLog(LoggerBean bean);

	/**
	 * 查询登陆日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:37:37
	 * @param LoggerBean
	 * @return Response
	 * @see com.szkingdom.frame.log.IMyLogger#findLoginLogs
	 */
	public abstract Response findLoginLogs(LoggerBean bean);

	/**
	 * 查询操作日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:37:42
	 * @param LoggerBean
	 * @return Response
	 * @see com.szkingdom.frame.log.IMyLogger#findOperationLogs
	 */
	public abstract Response findOperationLogs(LoggerBean bean);

	/**
	 * 删除登陆日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:41:43
	 * @param LoggerBean
	 * @return Response
	 * @see com.szkingdom.frame.log.IMyLogger#deleteLoginLog
	 */
	public abstract Response deleteLoginLog(LoggerBean bean);

	/**
	 * 删除操作日志
	 * 
	 * @author yisin
	 * @date 2012-12-26 下午04:41:46
	 * @param LoggerBean
	 * @return Response
	 * @see com.szkingdom.frame.log.IMyLogger#deleteOperateLog
	 */
	public abstract Response deleteOperateLog(LoggerBean bean);

}
