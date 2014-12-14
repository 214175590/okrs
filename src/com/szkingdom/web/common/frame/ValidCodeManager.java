package com.szkingdom.web.common.frame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.szkingdom.bean.pojo.system.Users;

/**
 * 
 * <pre>
 * 当使用验证码功能时，需要将验证码放到session中进行管理
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 下午04:11:21
 * @see java.util.ArrayList.ValidCodeManager
 * 
 */
public class ValidCodeManager {
	private static Map<String, Users> synchronizedMap = Collections
			.synchronizedMap(new HashMap<String, Users>());

	private static Map<String, Users> tempMap = Collections
			.synchronizedMap(new HashMap<String, Users>());

	private static Map<String, String[]> userToken = Collections
			.synchronizedMap(new HashMap<String, String[]>());

	/**
	 * <pre>
	 * 将Users存放到ValidCodeManager
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param key
	 *            该值必须为sessionId,在session销毁时由监听器统一删除
	 * @param Users
	 *            Users对象
	 * 
	 * 
	 */
	public static void put(String key, Users Users) {
		synchronizedMap.put(key, Users);
	}

	/**
	 * <pre>
	 * 从synchronizedMap中获取Users对象
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param key
	 *            该值必须为sessionId,在session销毁时由监听器统一删除
	 * @param Users
	 *            Users对象
	 * 
	 * 
	 */
	public static Users get(String key) {
		return synchronizedMap.get(key);
	}

	/**
	 * <pre>
	 * 判断synchronizedMap中是否包含key值
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param key
	 *            该值必须为sessionId,在session销毁时由监听器统一删除
	 * @return boolean true:包含 false:不包含
	 * 
	 * 
	 */
	public static boolean containsKey(String key) {
		return synchronizedMap.containsKey(key);
	}

	/**
	 * 从synchronizedMap中删除指定的Users对象
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午04:10:35
	 * @param sessionId
	 *            删除synchronizedMap中特定的对象对应的sessionId
	 * @return Users 对象
	 * @see com.szkingdom.web.system.ValidCodeManager#remove
	 */
	public static void remove(String sessionId) {
		if (synchronizedMap.containsKey(sessionId)) {
			synchronizedMap.remove(sessionId);
		}
	}

	/**
	 * 获取在线用户信息
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午04:10:07
	 * @return
	 * @see com.szkingdom.web.system.ValidCodeManager#getUserList
	 */
	public static List<Users> getUserList() {
		List<Users> list = new ArrayList<Users>();
		Set<String> set = synchronizedMap.keySet();
		Iterator<String> ito = set.iterator();
		Users u = null;
		tempMap.clear();
		while (ito.hasNext()) {
			Object obj = ito.next();
			if (obj != null) {
				u = synchronizedMap.get((String) obj);
				if (u != null && u.getUserName() != null
						&& u.getUserIp() != null) {
					// 去重
					tempMap.put(u.getUserName() + u.getUserIp(), u);
				}
			}
		}

		set = tempMap.keySet();
		ito = set.iterator();
		while (ito.hasNext()) {
			Object obj = ito.next();
			if (obj != null) {
				u = tempMap.get((String) obj);
				if (u != null) {
					list.add(u);
				}
			}
		}
		tempMap.clear();
		return list;
	}

	public static int getUserCount() {
		return getUserList().size();
	}

	public static void pushToken(String account, String sessionId, String token) {
		String[] str = { account, sessionId, token };
		userToken.put(account, str);
	}

	public static String[] getUserToken(String account) {
		return userToken.get(account);
	}

	public static void main(String[] args) {

	}
}
