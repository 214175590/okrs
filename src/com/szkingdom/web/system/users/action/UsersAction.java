/*
 * @describe 用户Action.java
 * @fileName com.szkingdom.web.system.users.action.UsersAction
 * @author hp
 * @date 2013-08-28 00:26:08
 */
package com.szkingdom.web.system.users.action;

import java.util.Date;
import java.util.List;

import com.szkingdom.bean.pojo.common.Datas;
import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.StatusCode;
import com.szkingdom.frame.common.reflect.YXObject;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.security.MD5Encrypt;
import com.szkingdom.frame.util.DateUtil;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.web.common.frame.BaseAction;
import com.szkingdom.web.system.users.IUsersService;

/**
 * <pre>
 * 描述：用户Action
 * </pre>
 * 
 * @author hp
 * @date 2013-08-28 00:26:08
 * @see com.szkingdom.web.system.users.action.UsersAction
 * 
 */
public class UsersAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static ILogger logger = LogFactory.getDefaultLogger(UsersAction.class);
	private IUsersService service = (IUsersService) SpringContainer.getBeanByName("usersService");

	/**
	 * 页面跳转（新增/修改..页面）
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#pageJump
	 */
	public String pageJump() {
		String result = null;
		if (genre != null) {
			if (genre.equals(GlobalConstants.GENRE_ADD)) {// 请求跳转到新增页面
				result = verifyAction(GlobalConstants.NUM_2);
				if (result == null) {
					result = GlobalConstants.RETURN_PAGE;
					//
				}
			} else if (genre.equals(GlobalConstants.GENRE_EDIT)) {// 请求跳转到修改页面
				result = verifyAction(GlobalConstants.NUM_3);
				if (result == null) {
					result = GlobalConstants.RETURN_PAGE;
					// 注入属性值
					String id = StringUtil.stringUncode(request.getParameter("id"));
					Response res = service.getDataById(id);
					if (res.getStatusCode() == StatusCode.SUCCESS) {
						Users users = (Users) res.getObject();
						request.setAttribute("users", users);
					}
				}
			} else if (genre.equals("privilege")) {// 请求跳转到分配权限页面
				result = verifyAction(GlobalConstants.NUM_6);
				if (result == null) {
					result = "privilege";
					// 注入属性值
					String id = StringUtil.stringUncode(request.getParameter("id"));
					Response res = service.getDataById(id);
					if (res.getStatusCode() == StatusCode.SUCCESS) {
						Users users = (Users) res.getObject();
						request.setAttribute("users", users);
					}
				}
			} else if (genre.equals("addPrivilege")) {// 请求跳转到审核页面
				result = verifyAction(GlobalConstants.NUM_6);
				if (result == null) {
					result = "addPrivilege";
					// 注入属性值
					String id = StringUtil.stringUncode(request.getParameter("id"));
					Response res = service.getDataById(id);
					if (res.getStatusCode() == StatusCode.SUCCESS) {
						Users users = (Users) res.getObject();
						request.setAttribute("users", users);
					}
				}
			} else {
				result = "error404";
			}
		}
		return result;
	}

	/**
	 * 加载或搜索功能记录
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#loadAndSearch
	 */
	public void loadAndSearch() {
		logger.info("Into method#loadAndSearch");
		if (!verifyUser(GlobalConstants.NUM_1)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValueForSearch(users, request);
		Response res = service.loadAndSearch(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			logger.info("Out method#loadAndSearch data");
			List list = res.getList();
			if (!isExport()) {
				Datas data = new Datas();
				data.setParamObject(users);
				data.setMapper("UsersMapper.loadAndSearchCount");
				res = dataService.excsql(data);
				if (res.getStatusCode() == StatusCode.SUCCESS) {
					List<Users> flist = (List<Users>) res.getList();
					users = flist.get(0);
				}
				outListToJson(list, users.getSize(), "loadAndSearch", 1, null);
			} else {
				exportExcel(list);
			}
		} else {
			logger.info("Out method#loadAndSearch []");
			if (!isExport()) {
				outString("[]");
			} else {
				outExportError();
			}
		}
	}

	/**
	 * 高级查找
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#advancedSearch
	 */
	public void advancedSearch() {
		logger.info("Into method#advancedSearch");
		if (!verifyUser(GlobalConstants.NUM_1)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValueForSearch(users, request);
		Response res = service.advancedSearch(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			List list = res.getList();
			if (!isExport()) {
				Datas data = new Datas();
				data.setParamObject(users);
				data.setMapper("UsersMapper.advancedSearchCount");
				res = dataService.excsql(data);
				if (res.getStatusCode() == StatusCode.SUCCESS) {
					List<Users> flist = (List<Users>) res.getList();
					users = flist.get(0);
				}
				outListToJson(list, users.getSize(), "loadAndSearch", 1, null);
			} else {
				exportExcel(list);
			}
		} else {
			logger.info("Out method#advancedSearch []");
			if (!isExport()) {
				outString("[]");
			} else {
				outExportError();
			}
		}
	}

	/**
	 * 精确查询
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#selectDataForAccurate
	 */
	public void selectDataForAccurate() {
		logger.info("Into method#selectDataForAccurate");
		if (!verifyUser(GlobalConstants.NUM_1)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValue(users, request);
		Response res = service.selectDataForAccurate(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			logger.info("Out method#selectDataForAccurate data");
			List list = res.getList();
			outListToJson(list, list.size(), "selectDataForAccurate", 1, null);
		} else {
			logger.info("Out method#selectDataForAccurate []");
			outString("[]");
		}
	}

	/**
	 * 增加用户
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#insertData
	 */
	public void insertData() {
		logger.info("Into method#insertData");
		if (!verifyUser(GlobalConstants.NUM_2)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValue(users, request);
		users.setId(StringUtil.getUniqueId("U"));
		users.setPassword(MD5Encrypt.getInstance().md5(users.getUserName(), 32));
		users.setRegisterDate(DateUtil.format(new Date()));
		users.setOnlines(2);
		Response res = service.insertData(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson(GlobalConstants.GENRE_ADD, GlobalConstants.RETURN_SUCCESS);
		} else {
			outJson(GlobalConstants.GENRE_ADD, GlobalConstants.RETURN_FAILED);
		}
		// 插入操作日志
		operateLogger("新增用户[Users]信息到表[ts_user]", users);
	}

	/**
	 * 修改用户
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#updateData
	 */
	public void updateData() {
		logger.info("Into method#updateData");
		if (!verifyUser(GlobalConstants.NUM_3)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValue(users, request);
		Response res = service.updateData(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson(GlobalConstants.GENRE_EDIT, GlobalConstants.RETURN_SUCCESS);
		} else {
			outJson(GlobalConstants.GENRE_EDIT, GlobalConstants.RETURN_FAILED);
		}
		// 插入操作日志
		operateLogger("修改用户[Users]信息到表[ts_user]", users);
	}
	
	public void updatePassword() {
		logger.info("Into method#updateData");
		if (null == user) {
			outString("loginTimeout");
			return;
		}
		Users users = new Users();
		users.setId(request.getParameter("id"));
		String oldpassword = StringUtil.stringUncode(StringUtil.excNullToString(request.getParameter("oldpassword")));
		String newpassword = StringUtil.stringUncode(StringUtil.excNullToString(request.getParameter("newpassword")));
		if(!MD5Encrypt.getInstance().md5(oldpassword, 32).equals(user.getPassword())){
			outString("pwdError");
			return;
		}
		users.setPassword(MD5Encrypt.getInstance().md5(newpassword, 32));
		Response res = service.updateData(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson(GlobalConstants.GENRE_EDIT, GlobalConstants.RETURN_SUCCESS);
		} else {
			outJson(GlobalConstants.GENRE_EDIT, GlobalConstants.RETURN_FAILED);
		}
		// 插入操作日志
		operateLogger("修改用户[Users]信息到表[ts_user]", users);
	}

	/**
	 * 删除用户
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#deleteData
	 */
	public void deleteData() {
		logger.info("Into method#deleteData");
		if (!verifyUser(GlobalConstants.NUM_4)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValue(users, request);
		Response res = service.getDataById(users.getId());
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			users = (Users) res.getObject();
			res = service.deleteData(users);
			if (res.getStatusCode() == StatusCode.SUCCESS) {
				
				
				outJson(GlobalConstants.GENRE_DELETE, GlobalConstants.RETURN_SUCCESS);
			} else {
				outJson(GlobalConstants.GENRE_DELETE, GlobalConstants.RETURN_FAILED);
			}
		} else {
			outJson(GlobalConstants.GENRE_DELETE, GlobalConstants.RETURN_FAILED);
		}
		// 插入操作日志
		operateLogger("删除用户[Users]信息到表[ts_user]", users);
	}

	/**
	 * 审核用户 （不需要的可以删除）
	 * 
	 * @author hp
	 * @date 2013-08-28 00:26:08
	 * @see com.szkingdom.web.system.users.action.UsersAction#resetPassword
	 */
	public void resetPassword() {
		logger.info("Into method#resetPassword");
		if (!verifyUser(GlobalConstants.NUM_3)) {
			return;// 没有权限，直接返回。
		}
		Users users = new Users();
		// 填充参数
		YXObject.setValue(users, request);
		users.setPassword(MD5Encrypt.getInstance().md5(users.getUserName(), 32));
		Response res = service.updateData(users);
		if (res.getStatusCode() == StatusCode.SUCCESS) {
			outJson(GlobalConstants.GENRE_AUDIT, GlobalConstants.RETURN_SUCCESS);
		} else {
			outJson(GlobalConstants.GENRE_AUDIT, GlobalConstants.RETURN_FAILED);
		}
		// 插入操作日志
		operateLogger("重置用户密码[Users]信息到表[ts_user]", users);
	}

}
