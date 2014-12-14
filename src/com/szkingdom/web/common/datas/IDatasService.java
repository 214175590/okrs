/*
 * @describe 
 * @fileName ydtx.smms.web.common.datas.IDatasService
 * @company 深圳元道通信技术有限公司
 * @author yisin
 * @date 2013 2013-1-6
 */
package com.szkingdom.web.common.datas;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.szkingdom.bean.pojo.common.Datas;
import com.szkingdom.frame.common.Response;

/**
 * <pre>
 * 动态对象处理service接口
 * </pre>
 * 
 * @author yisin
 * @date 2013-1-6 上午11:58:46
 * @see ydtx.smms.web.common.datas.IDatasService
 * 
 */
public interface IDatasService {

	/**
	 * 加载全部/模糊搜索对象数据
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasService#selectData
	 */
	public Response selectData(Datas data);

	/**
	 * 新增对象记录
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasService#insertData
	 */
	public Response insertData(Datas data);

	/**
	 * 修改对象信息
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasService#updateData
	 */
	public Response updateData(Datas data);

	/**
	 * 删除对象
	 * 
	 * @author yisin
	 * @date 2013-1-4 下午12:29:53
	 * @param Role
	 * @return Response
	 * @see ydtx.smms.web.common.datas.IDatasService#deleteData
	 */
	public Response deleteData(Datas data);

	/**
	 * 动态执行已有的Mapper中的sql
	 * 
	 * @author yisin
	 * @date 2013-1-10 下午05:18:54
	 * @param data
	 * @return Response
	 * @throws SQLException
	 * @see ydtx.smms.web.common.datas.IDatasService#excsql
	 */
	public Response excsql(Datas data);
}
