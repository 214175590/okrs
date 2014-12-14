/*
 * @describe 用户Dao文件
 * @fileName com.szkingdom.web.system.users.dao.UsersDao
 * @company 深圳元道通信技术有限公司
 * @author hp
 * @date 2013-08-28 00:26:08
 */
package com.szkingdom.web.system.users.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.web.system.users.IUsersDao;

/**
 * <pre>
 * 用户 Dao
 * </pre>
 * 
 * @author hp
 * @date 2013-08-28 00:26:08
 * @see com.szkingdom.web.system.users.dao.UsersDao
 * 
 */
@Repository("usersDao")
public class UsersDao extends SqlSessionDaoSupport implements IUsersDao {
	private static ILogger logger = LogFactory.getDefaultLogger(UsersDao.class);

	/**
	 * <pre>
	 * 根据ID查询记录
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#getDataById(String)
	 */
	public Response getDataById(String id) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#getDataById");
		Response res = new Response();
		Users users = (Users) getSqlSession().selectOne(
				"UsersMapper.getDataById", id);
		if (users != null) {
			res.setStatusCode(StatusCode.SUCCESS);
			res.setObject(users);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 加载全部 / 模糊搜索用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#loadAndSearch(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response loadAndSearch(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#loadAndSearch");
		Response res = new Response();
		List<Users> list = getSqlSession().selectList(
				"UsersMapper.loadAndSearch", users);
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
	 * 高级搜索用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#advancedSearch(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response advancedSearch(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#advancedSearch");
		Response res = new Response();
		List<Users> list = getSqlSession().selectList(
				"UsersMapper.advancedSearch", users);
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
	 * 精确查询用户数据
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#selectDataForAccurate(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response selectDataForAccurate(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#selectDataForAccurate");
		Response res = new Response();
		List<Users> list = getSqlSession().selectList(
				"UsersMapper.selectDataForAccurate", users);
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
	 * 新增用户记录
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#insertData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response insertData(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#insertData");
		Response res = new Response();
		int status = getSqlSession().insert("UsersMapper.insertData", users);
		// 如果需要带出刚插入这条记录的ID
		// 则mapper中需要设置<insert id="insert" parameterType="Users"
		// useGeneratedKeys="true" keyProperty="id">，然后从实体id中获取
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 修改用户信息
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#updateData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response updateData(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#updateData");
		Response res = new Response();
		int status = getSqlSession().insert("UsersMapper.updateData", users);
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 删除用户
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#deleteData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response deleteData(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#deleteData");
		Response res = new Response();
		int status = getSqlSession().insert("UsersMapper.deleteData", users);
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}

	/**
	 * <pre>
	 * 审核用户
	 * </pre>
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.IUsersDao#auditingData(com.szkingdom.bean.pojo.system.Users)
	 */
	public Response auditingData(Users users) throws SQLException {
		logger.debug("In com.szkingdom.web.system.users.dao.UsersDao#auditingData");
		Response res = new Response();
		int status = getSqlSession().insert("UsersMapper.auditingData", users);
		if (status > 0) {
			res.setStatusCode(StatusCode.SUCCESS);
		} else {
			res.setStatusCode(StatusCode.FAILED);
		}
		return res;
	}
}
