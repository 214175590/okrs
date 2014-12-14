/*
 * @describe 用户 Service
 * @fileName com.szkingdom.web.system.users.service.usersService.java
 * @company 深圳元道通信技术有限公司
 * @author hp
 * @date 2013-08-28 00:26:08
 */
package com.szkingdom.web.system.users.service;

import org.springframework.stereotype.Service;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.web.system.users.IUsersDao;
import com.szkingdom.web.system.users.IUsersService;

/**
 * <pre>
 * 用户 Service
 * </pre>
 * 
 * @author hp
 * @date 2013-08-28 00:26:08
 * @see com.szkingdom.web.system.users.service.UsersService
 * 
 */
@Service("usersService")
public class UsersService implements IUsersService {
	private static ILogger logger = LogFactory
			.getDefaultLogger(UsersService.class);

	/**
	 * <pre>
	 * 根据ID获取用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.service.UsersService#getDataById(String)
	 */
	public Response getDataById(String id) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#getDataById");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.getDataById(id);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 模糊搜索用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#loadAndSearch(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response loadAndSearch(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#loadAndSearch");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.loadAndSearch(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 高级搜索用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#advancedSearch(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response advancedSearch(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#advancedSearch");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.advancedSearch(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}
	
	/**
	 * <pre>
	 * 精确查询用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#selectDataForAccurate(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response selectDataForAccurate(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#selectDataForAccurate");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.selectDataForAccurate(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 新增用户记录
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#insertData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response insertData(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#insertData");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.insertData(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 修改用户记录
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#updateData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response updateData(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#updateData");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.updateData(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

	/**
	 * <pre>
	 * 删除用户记录
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#deleteData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response deleteData(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#deleteData");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.deleteData(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}
	
	/**
	 * <pre>
	 * 审核用户记录
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersService#auditingData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response auditingData(Users users) {
		logger.debug("In com.szkingdom.web.system.users.service.usersService#auditingData");
		Response res = new Response();
		try {
			IUsersDao rdao = (IUsersDao) SpringContainer.getBeanByName("usersDao");
			res = rdao.auditingData(users);
		} catch (Exception e) {
			res.setStatusCode(StatusCode.DBSQL_ERROR);
			logger.error("Record the Operating log failed, Cause: ", e);
		}
		return res;
	}

}
