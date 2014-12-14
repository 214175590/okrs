/*
 * @describe 用户管理：service接口文件
 * @fileName ydtx.smms.web.system.users.IUsersService
 * @company 深圳元道通信技术有限公司
 * @author  hp
 * @date 2013-08-28 00:26:08
 */
package com.szkingdom.web.system.users;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.Response;

/**
 * <pre>
 * 用户管理：service接口
 * </pre>
 * 
 * @author hp
 * @date 2013-08-28 00:26:08
 * @see ydtx.smms.web.system.users.IUsersService
 * 
 */
public interface IUsersService {

	/**
	 * 根据ID查询记录
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param int
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersService#getDataById
	 */
	public Response getDataById(String id);

	/**
	 * 加载全部/模糊搜索用户数据
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersService#loadAndSearch
	 */
	public Response loadAndSearch(Users users);

	/**
	 * 高级搜索用户数据
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return
	 * @see ydtx.smms.web.system.users.IUsersService#advancedSearch
	 */
	public Response advancedSearch(Users users);
	
	/**
	 * 精确查询用户数据
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return
	 * @see ydtx.smms.web.system.users.IUsersService#selectDataForAccurate
	 */
	public Response selectDataForAccurate(Users users);

	/**
	 * 新增用户记录
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersService#insertData
	 */
	public Response insertData(Users users);

	/**
	 * 修改用户信息
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersService#updateData
	 */
	public Response updateData(Users users);

	/**
	 * 删除用户
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersService#deleteData
	 */
	public Response deleteData(Users users);
	
	/**
	 * 审核用户
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @param Users
	 * @return Response
	 * @see ydtx.smms.web.system.users.IUsersService#auditingData
	 */
	public Response auditingData(Users users);

}
