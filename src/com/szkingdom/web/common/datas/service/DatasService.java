/*
 * @describe 
 * @fileName com.szkingdom.web.common.datas.service.DatasService.java
 * @company 深圳元道通信技术有限公司
 * @author yisin
 * @date 2013 2013-1-6
 */
package com.szkingdom.web.common.datas.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.szkingdom.bean.pojo.common.Datas;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.web.common.datas.IDatasDao;
import com.szkingdom.web.common.datas.IDatasService;

/**
 * <pre>
 * 描述：
 * </pre>
 * 
 * @author yisin
 * @date 2013-1-6 上午11:59:56
 * @see com.szkingdom.web.common.datas.service.DatasService
 * 
 */
@Service("datasService")
public class DatasService implements IDatasService {
	private static ILogger logger = LogFactory
			.getDefaultLogger(DatasService.class);

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午11:59:57
	 * @see com.szkingdom.web.system.data.IdataService#selectData(com.szkingdom.bean.pojo.common.Datas)
	 */
	public Response selectData(Datas data) {
		logger.debug("In com.szkingdom.web.common.datas.service.DatasService#selectData");
		Response res = new Response();
		try {
			IDatasDao rdao = (IDatasDao) SpringContainer
					.getBeanByName("datasDao");
			res = rdao.selectData(data);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午11:59:57
	 * @see com.szkingdom.web.system.data.IdataService#insertData(com.szkingdom.bean.pojo.common.Datas)
	 */
	public Response insertData(Datas data) {
		logger.debug("In com.szkingdom.web.common.datas.service.DatasService#insertData");
		Response res = new Response();
		try {
			IDatasDao rdao = (IDatasDao) SpringContainer
					.getBeanByName("datasDao");
			res = rdao.insertData(data);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午11:59:57
	 * @see com.szkingdom.web.system.data.IdataService#updateData(com.szkingdom.bean.pojo.common.Datas)
	 */
	public Response updateData(Datas data) {
		logger.debug("In com.szkingdom.web.common.datas.service.DatasService#updateData");
		Response res = new Response();
		try {
			IDatasDao rdao = (IDatasDao) SpringContainer
					.getBeanByName("datasDao");
			res = rdao.updateData(data);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午11:59:57
	 * @see com.szkingdom.web.system.data.IdataService#deleteData(com.szkingdom.bean.pojo.common.Datas)
	 */
	public Response deleteData(Datas data) {
		logger.debug("In com.szkingdom.web.common.datas.service.DatasService#deleteData");
		Response res = new Response();
		try {
			IDatasDao rdao = (IDatasDao) SpringContainer
					.getBeanByName("datasDao");
			res = rdao.deleteData(data);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-10 下午05:19:42
	 * @see com.szkingdom.web.common.datas.IDatasService#excsql(com.szkingdom.bean.pojo.common.Datas)
	 */
	public Response excsql(Datas data) {
		logger.debug("In com.szkingdom.web.common.datas.service.DatasService#excsql");
		Response res = new Response();
		try {
			IDatasDao rdao = (IDatasDao) SpringContainer
					.getBeanByName("datasDao");
			res = rdao.excsql(data);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}
	
}
