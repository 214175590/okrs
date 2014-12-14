package com.szkingdom.web.common.frame;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.RootContext;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;

/**
 * <pre>
 * 简述:该类主要用于登录成功后的跳转处理
 * 详述:将用户请求再次转发出去
 * </pre>
 * 
 * @author yisin
 * @since 1.0
 */
@SuppressWarnings("serial")
public class WebFrameAction extends BaseAction {

	private static ILogger logger = LogFactory.getDefaultLogger(WebFrameAction.class);

	// 随机数(sessionId)
	private String randomId;
	//
	private String tbid;

	private String page;

	/**
	 * 
	 * @简述：<页面跳转>
	 * @详述：<左边树形结构菜单，点击跳转到相应功能点页面方法>
	 * @return
	 * 
	 */
	public String forwordJsp() {
		try {
			if (null == getUsers()) {
				return "loginTimeout";
			}
			String pageurl = request.getParameter("pageurl");
			if (pageurl != null) {
				pageurl = pageurl.replaceAll("[*]", "&");
			}
			logger.info(user.getUserName() + " Into tbid : Id=" + tbid + " ，URL = " + pageurl);
			
			page = pageurl;
			request.setAttribute("functionId", tbid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public void out(String fileName, String js) {
		try {
			String accBrowser = "msie";
			String userAgent = request.getHeader("User-Agent").toLowerCase();
			if (userAgent != null && userAgent.indexOf(";") != -1) {
				StringTokenizer st = new StringTokenizer(userAgent, ";");
				st.nextToken();
				// 得到用户的浏览器名
				accBrowser = st.nextToken();
				accBrowser = accBrowser == null ? "" : accBrowser.trim();
			} else {
				accBrowser = "chrome";
			}
			System.out.println(getUsers().getName() + "(" + getUsers().getUserName() + ")，浏览器：" + accBrowser);
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(js);
			pw.flush();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String[] splitScript(String script) {
		String[] str = { "before", "" };
		if (!StringUtil.isEmpty(script)) {
			if (script.indexOf("ʒ") != -1) {
				str = script.split("ʒ");
			} else if (script.length() > 5) {
				str[1] = script;
			}
		}
		return str;
	}

	public String getSessionCount() {
		/*
		 * int count = ValidCodeManager.getUserCount(); outString(count + "");
		 */
		return null;
	}

	public void getUserIPInfo() {
		try {
			String ip = request.getParameter("userIp");
			String path = getFileSavePath(request, 0) + RootContext.getProjectName() + "\\WEB-INF\\classes\\ipData";
			/*
			 * IPSeeker seek = new IPSeeker("QQWry.Dat", path); IPLocation loca
			 * = seek.getIPLocation(ip); outJson(loca.getCountry(),
			 * loca.getArea());
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getSessionUser() {
		List<Users> list0 = ValidCodeManager.getUserList();
		int allDataCount = 0;
		if (list0 != null) {
			allDataCount = list0.size();
		}
		Map<String, String> listMap = new HashMap<String, String>();
		listMap.put("genre", genre);
		listMap.put("size", String.valueOf(allDataCount));
		Users ui = null;
		for (int i = 0; i < allDataCount; i++) {
			ui = list0.get(i);
			listMap.put("id" + i, String.valueOf(ui.getId()));
			listMap.put("userName" + i, ui.getUserName());
			listMap.put("entrance" + i, ui.getEntrance());
			listMap.put("name" + i, ui.getName());
			listMap.put("userIp" + i, ui.getUserIp());
		}
		JSONObject json = JSONObject.fromObject(listMap);
		outString(json.toString());
	}

	public String getSystemIcon() {
		File file = new File(getFileSavePath(request, 0) + RootContext.getProjectName() + "\\images\\icons\\");
		if (!file.exists()) {
			file = new File(getFileSavePath(request, 1) + RootContext.getProjectName() + "\\images\\icons\\");
		}
		File[] files = file.listFiles();
		Map<String, String> listMap = new HashMap<String, String>();
		String fileName = null;
		int dataCount = 0;
		for (int i = 0; i < files.length; i++) {
			fileName = files[i].getName();
			if (fileName.indexOf("lower") == -1) {
				dataCount++;
				listMap.put("name" + dataCount, fileName);
				listMap.put("path" + dataCount, "images/icons/");
			}
		}
		listMap.put("size", String.valueOf(dataCount));
		JSONObject json = JSONObject.fromObject(listMap);
		outString(json.toString());
		return null;
	}

	public String getIcons() {
		File file = new File(getFileSavePath(request, 0) + RootContext.getProjectName() + "\\images\\icons\\");
		if (!file.exists()) {
			file = new File(getFileSavePath(request, 1) + RootContext.getProjectName() + "\\images\\icons\\");
		}
		File[] files = file.listFiles();
		int allDataCount = 0;
		if (files != null) {
			allDataCount = files.length;
		}
		Map<String, String> listMap = new HashMap<String, String>();
		listMap.put("size", String.valueOf(allDataCount));
		for (int i = 0; files != null && i < files.length; i++) {
			files[i].getName();
			listMap.put("name" + i, files[i].getName());
			listMap.put("path" + i, "images/icons/");
		}
		JSONObject json = JSONObject.fromObject(listMap);
		outString(json.toString());
		return null;
	}

	public String getFileSavePath(HttpServletRequest request, int genre) {
		String spath = request.getRealPath("");
		if (genre == 1) {
			spath = request.getRealPath("/");
		}
		int index = spath.lastIndexOf("\\");
		return spath.substring(0, index + 1);
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public String getTbid() {
		return tbid;
	}

	public void setTbid(String tbid) {
		this.tbid = tbid;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
