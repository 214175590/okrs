/*
 * @describe 
 * @fileName ydtx.smms.web.system.role.action.RoleAction
 * @company 深圳元道通信技术有限公司
 * @author yisin
 * @date 2013 2013-1-6
 */
package com.szkingdom.web.common.datas.action;

import java.util.List;

import com.szkingdom.bean.pojo.common.Datas;
import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.common.reflect.YXObject;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.web.common.datas.IDatasService;
import com.szkingdom.web.common.frame.BaseAction;

/**
 * <pre>
 * 角色管理模块Action
 * </pre>
 * 
 * @author yisin
 * @date 2013-1-6 下午12:28:17
 * @see ydtx.smms.web.common.datas.action.DatasAction
 */
public class DatasAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static ILogger logger = LogFactory
			.getDefaultLogger(DatasAction.class);
	private IDatasService service = (IDatasService) SpringContainer
			.getBeanByName("datasService");

	/**
	 * 页面跳转（新增/修改..页面）
	 * 
	 * @author yisin
	 * @date 2012-12-29 上午10:53:02
	 * @see ydtx.smms.web.system.role.action.RoleAction#pageJump
	 */
	public String pageJump() {
		String result = null;
		if (genre != null) {
			if (genre.equals(GlobalConstants.GENRE_ADD)) {// 请求跳转到新增页面
				result = verifyAction(2);
				if (result == null) {
					result = GlobalConstants.RETURN_PAGE;
					//
				}
			} else if (genre.equals(GlobalConstants.GENRE_EDIT)) {// 请求跳转到修改页面
				result = verifyAction(3);
				if (result == null) {
					result = GlobalConstants.RETURN_PAGE;

				}
			} else {
				result = GlobalConstants.RETURN_ERROR404;
			}
			/*
			 * else if (genre.equals(GlobalConstants.GENRE_AUDIT)) {// 请求跳转到审核页面
			 * result = verifyAction(6); if (result == null) { result = "audit";
			 * // } }
			 */
		}
		return result;
	}

	/**
	 * 加载或搜索功能记录
	 * 
	 * @author yisin
	 * @date 2012-12-29 上午10:50:21
	 * @see ydtx.smms.web.system.role.action.RoleAction#selectData
	 */
	public void selectData() {
		logger.info("Into method#loadAndSearch");
		if (!verifyUser(1)) {
			return;// 没有权限，直接返回。
		}
		Datas data = new Datas();
		// 填充参数
		YXObject.setValue(data, request);
		Response res = service.selectData(data);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			logger.info("Out method#loadAndSearch data");
			List list = res.getList();
			outListToJson(list, list.size(), "loadAndSearch", 1, null);
		} else {
			logger.info("Out method#loadAndSearch []");
			outString("[]");
		}
	}

	/**
	 * 增加角色
	 * 
	 * @author yisin
	 * @date 2012-12-29 上午10:50:43
	 * @see ydtx.smms.web.system.role.action.RoleAction#insertData
	 */
	public void insertData() {
		logger.info("Into method#insertData");
		if (!verifyUser(2)) {
			return;// 没有权限，直接返回。
		}
		Datas data = new Datas();
		// 填充参数
		YXObject.setValue(data, request);
		// 默认状态为“有效”
		Response res = service.insertData(data);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson("add", "success");
		} else {
			outJson("add", "failed");
		}
	}

	/**
	 * 修改角色
	 * 
	 * @author yisin
	 * @date 2012-12-29 上午10:50:43
	 * @see ydtx.smms.web.system.role.action.RoleAction#updateData
	 */
	public void updateData() {
		logger.info("Into method#updateData");
		if (!verifyUser(3)) {
			return;// 没有权限，直接返回。
		}
		Datas data = new Datas();
		// 填充参数
		YXObject.setValue(data, request);
		Response res = service.updateData(data);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson("edit", "success");
		} else {
			outJson("edit", "failed");
		}
	}

	/**
	 * 删除角色
	 * 
	 * @author yisin
	 * @date 2012-12-29 上午10:50:43
	 * @see ydtx.smms.web.system.role.action.RoleAction#deleteData
	 */
	public void deleteData() {
		logger.info("Into method#deleteData");
		if (!verifyUser(4)) {
			return;// 没有权限，直接返回。
		}
		Datas data = new Datas();
		// 填充参数
		YXObject.setValue(data, request);
		Response res = service.deleteData(data);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson("del", "success");
		} else {
			outJson("del", "failed");
		}
	}

}
