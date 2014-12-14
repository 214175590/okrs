/*
 * @describe 对象管理dao接口,IDatasDao.java
 * @fileName ydtx.smms.web.common.datas.IDatasDao
 * @company 深圳元道通信技术有限公司
 * @author yisin
 * @date 2013 2013-1-6
 */
package com.szkingdom.web.common.datas;

import java.sql.SQLException;

import com.szkingdom.bean.pojo.common.Datas;
import com.szkingdom.frame.common.Response;

/**
 * <pre>
 * 动态对象dao接口
 * </pre>
 * 
 * @author yisin
 * @date 2013-1-6 上午10:35:46
 * @see ydtx.smms.web.common.datas.IDatasDao
 * 
 */
public interface IDatasDao {

	/**
	 * 加载全部/模糊搜索对象数据
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasDao#selectData
	 */
	public Response selectData(Datas data) throws SQLException;

	/**
	 * 新增对象记录
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasDao#insertData
	 */
	public Response insertData(Datas data) throws SQLException;

	/**
	 * 修改对象信息
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasDao#updateData
	 */
	public Response updateData(Datas data) throws SQLException;

	/**
	 * 删除对象
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasDao#deleteData
	 */
	public Response deleteData(Datas data) throws SQLException;

	/**
	 * 动态执行已有的Mapper中的sql
	 * 
	 * @author yisin
	 * @date 2013-1-10 下午05:18:54
	 * @param data
	 * @return Response
	 * @throws SQLException
	 * @see ydtx.smms.web.common.datas.IDatasDao#excsql
	 */
	public Response excsql(Datas data) throws SQLException;

}
