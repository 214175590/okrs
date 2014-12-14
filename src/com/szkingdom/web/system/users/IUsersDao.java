/*
 * @describe 用户管理：dao接口
 * @fileName ydtx.smms.web.system.users.IUsersDao
 * @company 深圳元道通信技术有限公司
 * @author hp
 * @date 2013-08-28 00:26:08
 */
package com.szkingdom.web.system.users;

import java.sql.SQLException;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.Response;

/**
 * <pre>
 * 用户管理：dao接口
 * </pre>
 * 
 * @author hp
 * @date 2013-08-28 00:26:08
 * @see ydtx.smms.web.system.users.IUsersDao
 * 
 */
public interface IUsersDao {

	/**
	 * 根据ID查询记录
	 * 
	 * @author hp
	 * @date 2012-12-28 下午07:18:05
	 * @param int
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersDao#getDataById
	 */
	public Response getDataById(String id) throws SQLException;

	/**
	 * 加载全部/模糊搜索用户数据
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersDao#loadAndSearch
	 */
	public Response loadAndSearch(Users users) throws SQLException;

	/**
	 * 高级搜索用户数据
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return
	 * @see ydtx.smms.web.system.users.IUsersDao#advancedSearch
	 */
	public Response advancedSearch(Users users) throws SQLException;
	
	/**
	 * 精确查询用户数据
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return
	 * @see ydtx.smms.web.system.users.IUsersDao#selectDataForAccurate
	 */
	public Response selectDataForAccurate(Users users) throws SQLException;

	/**
	 * 新增用户记录
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersDao#insertData
	 */
	public Response insertData(Users users) throws SQLException;

	/**
	 * 修改用户信息
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersDao#updateData
	 */
	public Response updateData(Users users) throws SQLException;

	/**
	 * 删除用户
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersDao#deleteData
	 */
	public Response deleteData(Users users) throws SQLException;
	
	/**
	 * 审核用户（不需要的可以删除）
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersDao#auditingData
	 */
	public Response auditingData(Users users) throws SQLException;
}
