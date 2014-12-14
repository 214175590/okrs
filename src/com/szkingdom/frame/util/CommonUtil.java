package com.szkingdom.frame.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;

/**
 * <pre>
 * 描述：一些公共常用的方法工具类
 * </pre>
 * 
 * @author yisin
 * @date 2013-4-24 上午10:39:05
 * @see com.szkingdom.frame.util.CommonUtil
 * 
 */
public class CommonUtil {
	private static ILogger logger = LogFactory
			.getDefaultLogger(CommonUtil.class);

	/**
	 * 主函数
	 * 
	 * @author yisin
	 * @date 2013-4-24 上午10:39:05
	 * @param args
	 * @see com.szkingdom.frame.util.CommonUtil#main
	 */
	public static void main(String[] args) {
		System.out.println(StringUtil.replaceBr("dqwdqwdqwdqw<br>剑客的蔷薇几度花"));
	}

	/**
	 * 计算当前使用内存（字节数）
	 * 
	 * @author yisin
	 * @date 2013-4-24 上午10:43:30
	 * @return
	 * @see com.szkingdom.frame.util.CommonUtil#getUseMemory
	 */
	public static long getUseMemory() {
		long beforeMemory = Runtime.getRuntime().totalMemory();
		long afterMemory = Runtime.getRuntime().freeMemory();
		return beforeMemory - afterMemory;
	}

	/**
	 * 计算对象占用内存大小（字节数）
	 * 
	 * @author yisin
	 * @date 2013-4-24 上午10:45:31
	 * @return
	 * @see com.szkingdom.frame.util.CommonUtil#getBeanMemory
	 */
	public static long getObjectMemory(Object obj) {
		long size = 0l;
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(obj);
			os.flush();
			size = bs.size();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return size;
	}

	/**
	 * 计算List中的所有对象共占用的内存（字节数）
	 * 
	 * @author yisin
	 * @date 2013-4-24 上午11:22:05
	 * @param list
	 * @return
	 * @see com.szkingdom.frame.util.CommonUtil#getObjectMemoryByList
	 */
	public static long getObjectMemoryByList(List<Object> list) {
		long all = 0;
		for (Object obj : list) {
			all += getObjectMemory(obj);
		}
		return all;
	}

	/**
	 * Excel导入需要
	 * 
	 * @author 黄建波
	 */
	/**
	 * @简述：<去掉非空字符串的两边空格>
	 * @param obj
	 * @return
	 * 
	 */
	public static String trim(String obj) {
		if (obj != null) {
			obj = obj.trim();
		}
		return obj;
	}
	
	 
	public static String coverHtmlByScript(String str){
		if(!StringUtil.isEmpty(str)){
			try {
				// 替换回车符
				str = str.replaceAll("æapos;", "'");
				str = str.replaceAll("æquot;", "\"");
				str = str.replaceAll("ælt;", "<");
				str = str.replaceAll("ʃ", "%");
				str = str.replaceAll("θ", "+");
				str = StringUtil.replaceBr(str);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return str;
	}
	
	
	public static String coverScriptByHtml(String str){
		if(!StringUtil.isEmpty(str)){
			try {
				// 替换回车符
				str = StringUtil.replaceEnter(str);
				str = str.replaceAll("'", "&apos;");
				str = str.replaceAll("\"", "&quot;");
				str = str.replaceAll("<", "&lt;");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return str;
	}
	
}
