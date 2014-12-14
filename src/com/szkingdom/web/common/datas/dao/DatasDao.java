/*
 * @describe 
 * @fileName com.szkingdom.web.common.datas.dao.DatasDao.java
 * @company 深圳元道通信技术有限公司
 * @author yisin
 * @date 2013 2013-1-6
 */
package com.szkingdom.web.common.datas.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.szkingdom.bean.pojo.common.Datas;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.web.common.datas.IDatasDao;
import com.zte.zxtswp.lib.auth.model.Role;

/**
 * <pre>
 * 角色管理dao
 * </pre>
 * 
 * @author yisin
 * @date 2013-1-6 上午10:42:54
 * @see com.szkingdom.web.common.datas.dao.DatasDao
 * 
 */
@Repository("datasDao")
public class DatasDao extends SqlSessionDaoSupport implements IDatasDao {
	private static ILogger logger = LogFactory.getDefaultLogger(DatasDao.class);

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午10:42:54
	 * @see com.szkingdom.web.common.datas.dao.DatasDao#selectData(Role)
	 */
	public Response selectData(Datas data) throws SQLException {
		logger.debug("In com.szkingdom.web.common.datas.dao.DatasDao#selectData");
		Response res = new Response();
		List<Role> list = getSqlSession().selectList("DatasMapper.selectData",
				data);
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
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午10:42:54
	 * @see com.szkingdom.web.common.datas.dao.DatasDao#insertData(Role)
	 */
	public Response insertData(Datas data) throws SQLException {
		logger.debug("In com.szkingdom.web.common.datas.dao.DatasDao#insertData");
		Response res = new Response();
		int status = getSqlSession().insert("DatasMapper.insertData", data);
		// 如果需要带出主键ID
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午10:42:54
	 * @see com.szkingdom.web.common.datas.dao.DatasDao#updateData(Role)
	 */
	public Response updateData(Datas data) throws SQLException {
		logger.debug("In com.szkingdom.web.common.datas.dao.DatasDao#updateData");
		Response res = new Response();
		int status = getSqlSession().update("DatasMapper.updateData", data);
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-6 上午10:42:54
	 * @see com.szkingdom.web.common.datas.dao.DatasDao#deleteData(Role)
	 */
	public Response deleteData(Datas data) throws SQLException {
		logger.debug("In com.szkingdom.web.common.datas.dao.DatasDao#deleteData");
		Response res = new Response();
		int status = getSqlSession().delete("DatasMapper.deleteData", data);
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-1-10 下午05:20:12
	 * @see com.szkingdom.web.common.datas.IDatasDao#excsql(com.szkingdom.bean.pojo.common.Datas)
	 */
	public Response excsql(Datas data) throws SQLException {
		logger.debug("In com.szkingdom.web.common.datas.dao.DatasDao#excsql");
		Response res = new Response();
		List<Object> list = getSqlSession().selectList(data.getMapper(),
				data.getParamObject());
		if (list != null) {
			res.setStatusCode(StatusCode.SUCCESS);
			res.setList(list);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

}
