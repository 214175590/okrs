/*
 * 作者：yisin
 * 描述：ActionSupport的封装类
 * 创建时间：2012-12-11 上午10:01:49
 * @Service用于标注业务层组件 
 * @Controller用于标注控制层组件(如struts的action) 
 * @Respository用于标注数据访问组件，即DAO组件 
 */
package com.szkingdom.web.common.frame;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.Response;
import com.szkingdom.frame.common.SpringContainer;
import com.szkingdom.frame.common.reflect.YXObject;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.IMyLogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.log.impl.LoggerBean;
import com.szkingdom.frame.log.impl.MyLogger;
import com.szkingdom.frame.util.ListUtil;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.frame.util.excel.ExcelUtil;
import com.szkingdom.web.common.datas.IDatasService;

/**
 * 
 * <pre>
 * 自定义Action基类，封装部分属性、函数，为开发提供方便
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 上午10:01:49
 * @see com.szkingdom.web.system.BaseAction
 * 
 */
public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static ILogger logger = LogFactory.getDefaultLogger(BaseAction.class);
	protected IDatasService dataService = (IDatasService) SpringContainer.getBeanByName("datasService");
	private static IMyLogger operate = new MyLogger();

	/**
	 * ServletOutputStream
	 */
	protected ServletOutputStream out;

	/**
	 * HttpServletRequest
	 */
	protected HttpServletRequest request;

	/**
	 * HttpServletResponse
	 */
	protected HttpServletResponse response;

	/**
	 * HttpSession
	 */
	protected HttpSession session;

	/**
	 * 当前登录用户信息
	 */
	protected Users user = null;

	/**
	 * 操作类型<br>
	 * add：增，edit：改，delete：删，export：导出，audit：审核....<br>
	 * 建议使用常量类属性：GlobalConstants.GENRE_类型
	 */
	public String genre = null;

	/**
	 * 当前页码，默认为1
	 */
	public int pageIndex = 1;

	/**
	 * 每页显示记录数量，默认为10
	 */
	public int dataCount = 10;

	/**
	 * 功能点ID
	 */
	public String functionId = "";

	/**
	 * 需要导出的字段集
	 */
	private String columns = null;

	/**
	 * Excel文件名称
	 */
	private String excelname = null;

	public BaseAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		initParameter();
		getUsers();
	}

	/**
	 * <pre>
	 * 获取当前环境信息
	 * </pre>
	 * 
	 * @author yisin
	 * @return Locale 当前系统环境信息对象
	 */
	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}

	public void initParameter() {
		pageIndex = StringUtil.stringToInt(request.getParameter("pageIndex"), pageIndex);
		dataCount = StringUtil.stringToInt(request.getParameter("dataCount"), dataCount);
		functionId = StringUtil.excNullToString(request.getParameter("functionId"), "");
		genre = StringUtil.excNullToString(request.getParameter(GlobalConstants.GENRE), "");
		setColumns(StringUtil.stringUncode(request.getParameter("columns")));
		request.setAttribute(GlobalConstants.GENRE, genre);
		request.setAttribute("functionId", functionId);
		request.setAttribute("pageIndex", pageIndex);
	}

	/**
	 * 获取登录用户信息对象
	 * 
	 * @author yisin
	 * @date 2012-12-11 上午10:28:12
	 * @return UserInfo
	 * @see com.szkingdom.web.system.BaseAction#getUserInfo
	 */
	protected Users getUsers() {
		if (null == user) {
			user = (Users) session.getAttribute(GlobalConstants.SESSION_KEY_USER);
		}
		return user;
	}

	/**
	 * 判断
	 * 
	 * @author yisin
	 * @date 2013-2-1 上午11:24:40
	 * @return
	 * @see com.szkingdom.web.system.BaseAction#isDeveloper
	 */
	protected boolean isDeveloper() {
		boolean bool = false;
		if (getUsers() != null) {
			bool = user.getUserName().equals("yisin");
		}
		return bool;
	}

	/**
	 * 
	 * @描述：<判断指定用户是否拥有指定功能点的指定操作权限>
	 * @param boolean
	 * @param manageId
	 *            用户ID
	 * @param fid
	 *            功能点ID
	 * @param pid
	 *            权限ID
	 * @return
	 */
	public boolean isHavePrivilege(String userId, String fid, int pid) {
		boolean bool = true;
		return bool;
	}

	/**
	 * 验证用户是否登录、是否超时、是否有权限访问/操作此功能点
	 * 
	 * @author admin
	 * @date 2012-12-11 上午10:32:47
	 * @param privilegeId
	 *            1：查看/搜索，2：增加/导入，3：修改，4：删除，5：下载，6：审核
	 * @return boolean
	 * @see com.szkingdom.web.system.BaseAction#verifyUser
	 */
	protected boolean verifyUser(int privilegeId) {
		boolean bool = true;
		if (null == user) {
			bool = false;
			outString("loginTimeout");
		} else {
			if (!isDeveloper()) {
				bool = isHavePrivilege(user.getId(), functionId, privilegeId);
				if (!bool) {
					logger.info(user.getUserName() + " In functionId=" + functionId + " Not Permissions!!");
					outString("noPermissions");
				}
			}
		}
		return bool;
	}

	/**
	 * 验证用户是否登录、是否超时、是否有权限访问/操作此功能点,然后做出跳转
	 * 
	 * @author admin
	 * @date 2012-12-11 上午10:32:47
	 * @param privilegeId
	 *            1：查看/搜索，2：增加/导入，3：修改，4：删除，5：下载，6：审核
	 * @return String 返回Null 代表通过
	 * @see com.szkingdom.web.system.BaseAction#verifyUser
	 */
	protected String verifyAction(int privilegeId) {
		String result = null;
		if (null == user) {
			result = "loginTimeout";
		} else {
			if (!isDeveloper()) {
				String fid = StringUtil.excNullToString(request.getParameter("functionId"), "");
				boolean bool = isHavePrivilege(user.getId(), fid, privilegeId);
				if (!bool) {
					logger.info(user.getUserName() + " In functionId=" + fid + " Not Permissions!!");
					result = "noPermissions";
				}
			}
		}
		return result;
	}

	/**
	 * 插入操作日志到数据库
	 * 
	 * @author yisin
	 * @date 2013-1-11 下午02:06:27
	 * @param operateContent
	 *            操作内容
	 * @param Object
	 *            操作的对象
	 * @see com.szkingdom.web.system.BaseAction#operateLogger
	 */
	protected void operateLogger(String operateContent, Object obj) {
		if (user != null && !isDeveloper()) {
			StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
			String className = stack.getClassName();
			String methodName = stack.getMethodName();
			LoggerBean loggerBean = new LoggerBean();
			loggerBean.setAccount(user.getUserName());
			loggerBean.setUserIp(user.getUserIp());
			loggerBean.setOperationDate(new Date());
			StringBuffer sb = new StringBuffer("，数据：{");
			Field[] f = obj.getClass().getDeclaredFields();
			String fname = "";
			String tou = "", wei = "", getMethod = "";
			for (int i = 0; i < f.length; i++) {
				fname = f[i].getName();
				tou = fname.substring(0, 1);
				wei = fname.substring(1);
				getMethod = "get" + tou.toUpperCase() + wei;
				try {
					Method method = obj.getClass().getMethod(getMethod);
					Object vue = method.invoke(obj);
					if (vue == null) {
						vue = "null";
					}
					sb.append(fname + ":" + vue.toString() + ", ");
				} catch (Exception e) {
					System.err.println("缺少" + getMethod + "()" + "方法！ " + e.getMessage());
				}
			}
			sb.append("}， 来源：" + className + "#" + methodName);
			loggerBean.setOperationDesc(operateContent + sb.toString());
			if (loggerBean.getOperationDesc().length() >= 1000 && loggerBean.getOperationDesc().length() <= 2000) {
				loggerBean.setOperationDesc(StringUtils.rightPad(loggerBean.getOperationDesc(), 2008));
			}
			operate.operateLog(loggerBean);
		}
	}

	/**
	 * 将数据封装为JSON格式输出到前端
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午03:36:39
	 * @param genre
	 *            操作类型
	 * @param flag
	 *            标识/key
	 * @see com.szkingdom.web.system.BaseAction#outJson
	 */
	public void outJson(String genre, String flag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(GlobalConstants.GENRE, genre);
		map.put("flag", flag);
		JSONObject json = JSONObject.fromObject(map);
		outString(json.toString());
		map.clear();
		map = null;
	}

	/**
	 * 将数据封装为JSON格式输出到前端
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午03:35:48
	 * @param genre
	 *            操作类型
	 * @param flag
	 *            标识/key
	 * @param msg
	 *            消息
	 * @see com.szkingdom.web.system.BaseAction#outJson
	 */
	public void outJson(String genre, String flag, String msg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(GlobalConstants.GENRE, genre);
		map.put("flag", flag);
		map.put("msg", msg);
		JSONObject json = JSONObject.fromObject(map);
		outString(json.toString());
		map.clear();
		map = null;
	}

	public void outJson(Object obj, String genre) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", obj);
		map.put("genre", genre);
		JSONObject json = JSONObject.fromObject(map);
		outString(json.toString());
		map.clear();
		map = null;
	}

	/**
	 * 输出字符串到客户端
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午03:35:13
	 * @param str
	 * @see com.szkingdom.web.system.BaseAction#outString
	 */
	public void outString(String str) {
		try {
			str = str == null ? "" : str;
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			out.write(str.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void outString(String str, String characterEncoding) {
		try {
			str = str == null ? "" : str;
			response.setCharacterEncoding(characterEncoding);
			out = response.getOutputStream();
			out.write(str.getBytes(characterEncoding));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @描述：<pre>将List中的对象 以一定格式封装到JSON对象中（<b>也可重写此方法</b>）</pre>
	 * 
	 *                    <pre>
	 * JSON Object中已包含的参数： 
	 * 			<li>genre 类型</li> 
	 * 			<li>size 当前页记录数量</li> 
	 * 			<li>allDataCount 总记录数量</li> 
	 * 			<li>allPageCount 总页码数量</li>
	 * </pre>
	 * 
	 *                    传入的参数：<br/>
	 * @param list0
	 *            java.util.List
	 * @param allSize
	 *            记录总数，用来分页
	 * @param genre
	 *            操作类型 String
	 * @param flag
	 *            标识（0：不分页，1：分页） Integer
	 * @param map
	 *            Map<String, Object> 额外的参数<key,value>
	 *            <hr>
	 * @return void
	 */
	public void outListToJson1(List<Object> list, int allSize, String genre, int flag, Map<String, Object> map) {
		int allDataCount = allSize;
		Map<String, String> listMap = new HashMap<String, String>();
		listMap.put(GlobalConstants.GENRE, genre);
		if (flag == 0) {// 不分页
			listMap.put("size", String.valueOf(allDataCount));
			for (int i = 0; i < allDataCount; i++) {
				Object obj = list.get(i);
				try {
					getAttr1(listMap, i, obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (flag == 1) {// 分页
			int allPageCount = allDataCount % dataCount == 0 ? (allDataCount / dataCount) : (allDataCount / dataCount + 1);
			if (pageIndex > allPageCount) {
				pageIndex = allPageCount;
				if (pageIndex < 1) {
					pageIndex = 1;
				}
			}
			// List<Object> list = list0; //ListUtil.pickList(list0, pageIndex,
			// dataCount);
			int k = list.size();
			listMap.put("size", String.valueOf(k));
			listMap.put("allDataCount", String.valueOf(allDataCount));
			listMap.put("allPageCount", String.valueOf(allPageCount));
			Object obj = null;
			for (int i = 0; i < k; i++) {
				obj = list.get(i);
				try {
					getAttr1(listMap, i, obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (flag == 2) {
			int allPageCount = allSize;
			if (pageIndex > allPageCount) {
				pageIndex = allPageCount;
				if (pageIndex < 1) {
					pageIndex = 1;
				}
			}
			// List<Object> list = list0; //ListUtil.pickList(list0, pageIndex,
			// dataCount);
			int k = list.size();
			listMap.put("size", String.valueOf(k));
			listMap.put("allDataCount", String.valueOf(allDataCount));
			listMap.put("allPageCount", String.valueOf(allPageCount));
			Object obj = null;
			for (int i = 0; i < k; i++) {
				obj = list.get(i);
				try {
					getAttr1(listMap, i, obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (map != null) {
			for (Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator(); ite.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) ite.next();
				if (entry != null) {
					listMap.put((String) entry.getKey(), YXObject.excType(entry.getValue()));
				}
			}
		}
		JSONObject json = JSONObject.fromObject(listMap);
		outString(json.toString());

		listMap = null;
		list = null;
	}

	/**
	 * 
	 * @描述：<pre>获取对象属性值，并添加到Map中</pre>
	 * @param listMap
	 *            Map<String, String> listMap
	 * @param index
	 *            int
	 * @param obj
	 *            Object
	 * @return void
	 */
	public static void getAttr1(Map<String, String> listMap, int index, Object obj) {
		Field[] f = obj.getClass().getDeclaredFields();
		String fname[] = YXObject.fieldByName(f);
		String tou = "", wei = "", getMethod = "";
		Object vas = null;
		for (int i = 0; i < fname.length; i++) {
			tou = fname[i].substring(0, 1);
			wei = fname[i].substring(1);
			getMethod = "get" + tou.toUpperCase() + wei;
			try {
				Method method = obj.getClass().getMethod(getMethod);
				vas = method.invoke(obj);
				listMap.put(fname[i] + index, YXObject.excType(vas));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		vas = null;
		tou = null;
		wei = null;
		getMethod = null;
	}

	/**
	 * 
	 * @描述：<pre>将List中的对象 以一定格式封装到JSON对象中（<b>也可重写此方法</b>）</pre>
	 * 
	 *                    <pre>
	 * JSON Object中已包含的参数： 
	 * 			<li>genre 类型</li> 
	 * 			<li>size 当前页记录数量</li> 
	 * 			<li>allDataCount 总记录数量</li> 
	 * 			<li>allPageCount 总页码数量</li>
	 * </pre>
	 * 
	 *                    传入的参数：<br/>
	 * @param list0
	 *            java.util.List
	 * @param allSize
	 *            记录总数，用来分页
	 * @param genre
	 *            操作类型 String
	 * @param flag
	 *            标识（0：不分页，1：分页） Integer
	 * @param map
	 *            Map<String, Object> 额外的参数<key,value>
	 *            <hr>
	 * @return void
	 */
	public void outListToJson(List<Object> list, int allSize, String genre, int flag, Map<String, Object> map) {
		try {
			int allDataCount = allSize;
			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put(GlobalConstants.GENRE, genre);
			if (flag == 0) {// 不分页
				listMap.put("size", String.valueOf(allDataCount));
				for (int i = 0; i < allDataCount; i++) {
					Object obj = list.get(i);
					try {
						if (obj != null) {
							getAttr(listMap, i, obj);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (flag == 1) {// 分页
				int allPageCount = allDataCount % dataCount == 0 ? (allDataCount / dataCount) : (allDataCount / dataCount + 1);
				/*
				 * if (pageIndex > allPageCount) { pageIndex = allPageCount; if
				 * (pageIndex < 1) { pageIndex = 1; } }
				 */
				// List<Object> list = list0; //ListUtil.pickList(list0,
				// pageIndex,
				// dataCount);
				int k = list.size();
				listMap.put("size", String.valueOf(k));
				listMap.put("allDataCount", String.valueOf(allDataCount));
				listMap.put("allPageCount", String.valueOf(allPageCount));
				Object obj = null;
				for (int i = 0; i < k; i++) {
					obj = list.get(i);
					try {
						if (obj != null) {
							getAttr(listMap, i, obj);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (map != null) {
				for (Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator(); ite.hasNext();) {
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>) ite.next();
					if (entry != null) {
						listMap.put((String) entry.getKey(), YXObject.excType(entry.getValue()));
					}
				}
			}
			JSONObject json = JSONObject.fromObject(listMap);
			outString(json.toString());
			listMap = null;
			list = null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @描述：<pre>获取对象属性值，并添加到Map中</pre>
	 * @param listMap
	 *            Map<String, String> listMap
	 * @param index
	 *            int
	 * @param obj
	 *            Object
	 * @return void
	 */
	public static void getAttr(Map<String, Object> listMap, int index, Object obj) {
		Field[] f = obj.getClass().getDeclaredFields();
		String fname[] = YXObject.fieldByName(f);
		String tou = "", wei = "", getMethod = "";
		Object vas = null;
		for (int i = 0; i < fname.length; i++) {
			tou = fname[i].substring(0, 1);
			wei = fname[i].substring(1);
			getMethod = "get" + tou.toUpperCase() + wei;
			try {
				Method method = obj.getClass().getMethod(getMethod);
				vas = method.invoke(obj);
				listMap.put(fname[i] + index, YXObject.excType(vas));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		vas = null;
		tou = null;
		wei = null;
		getMethod = null;
	}

	public void outObjectToJson(Response res, String genre, int flag, Map<String, Object> map) {
		List<Object> list0 = (List<Object>) res.getList();
		int allDataCount = 0;
		if (list0 != null) {
			allDataCount = list0.size();
		}
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("genre", genre);
		if (flag == 0) {// 不分页
			listMap.put("size", String.valueOf(allDataCount));
			for (int i = 0; i < allDataCount; i++) {
				Object obj = list0.get(i);
				try {
					getAttr(listMap, i, obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (flag == 1) {// 分页
			int allPageCount = allDataCount % dataCount == 0 ? (allDataCount / dataCount) : (allDataCount / dataCount + 1);
			if (pageIndex > allPageCount) {
				pageIndex = allPageCount;
				if (pageIndex < 1) {
					pageIndex = 1;
				}
			}
			List<Object> list = ListUtil.pickList(list0, pageIndex, dataCount);
			int k = list.size();
			listMap.put("size", String.valueOf(k));
			listMap.put("allDataCount", String.valueOf(allDataCount));
			listMap.put("allPageCount", String.valueOf(allPageCount));
			Object obj = null;
			for (int i = 0; i < k; i++) {
				obj = list.get(i);
				try {
					getAttr(listMap, i, obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (map != null) {
			for (Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator(); ite.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) ite.next();
				if (entry != null) {
					listMap.put((String) entry.getKey(), YXObject.excType(entry.getValue()));
				}
			}
		}
		JSONObject json = JSONObject.fromObject(listMap);
		outString(json.toString());
		res = null;
		listMap = null;
		list0 = null;
	}

	/**
	 * 导出Excel2007
	 * 
	 * @author yisin
	 * @date 2013-4-19 下午03:14:09
	 * @param list
	 * @see com.szkingdom.web.system.BaseAction#exportExcel
	 */
	public void exportExcel(List<Object> list) {
		ExcelUtil eu = new ExcelUtil();
		eu.createExcel2007(response, getExcelname(), getColumns(), list);
	}

	/**
	 * 是否为导出数据请求
	 * 
	 * @return true是/false否
	 */
	public boolean isExport() {
		return !StringUtil.isEmpty(columns);
	}

	/**
	 * 获取需要带出的列及描述
	 * 
	 * @return String[][], 二维数组[feild][text]
	 */
	public String[][] getColumns() {
		String fields[][] = null;
		if (!StringUtil.isEmpty(columns)) {
			columns = StringUtil.stringUncode(columns);
			String[] column = columns.split(",");
			fields = new String[column.length][2];
			String[] field = null;
			for (int i = 0, k = column.length; i < k; i++) {
				field = column[i].split(":");
				if (!StringUtil.isEmpty(column[i])) {
					fields[i] = field;
				}
			}
		}
		return fields;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}

	/**
	 * @return the excelname
	 */
	public String getExcelname() {
		if (StringUtil.isEmpty(excelname)) {
			excelname = StringUtil.getUniqueId("F" + functionId);
		} else {
			excelname += StringUtil.getUniqueId("");
		}
		return excelname;
	}

	public void outExportError() {
		String html = "<html>\n<body>\n<script>\nalert('数据导出失败！');\n";
		html += "if(parent.window['DomTime'] != undefined){\n";
		html += "\tparent.closeCoverPanel('ExportPanelDiv');\n";
		html += "}\n";
		html += "</script>\n</body>\n</html>";
		outString(html);
	}
}
