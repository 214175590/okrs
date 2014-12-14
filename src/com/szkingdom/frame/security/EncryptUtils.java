package com.szkingdom.frame.security;

import java.security.MessageDigest;

import com.szkingdom.frame.util.StringUtil;

/**
 * 
 * <pre>
 * 加密工具类
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 下午02:52:39
 * @see com.szkingdom.frame.security.EncryptUtils
 * 
 */
public class EncryptUtils {
	private EncryptUtils() {

	}

	public static String md5(String sourceStr) {
		MessageDigest md;
		String encryptString = "";
		byte[] bytes = null;
		try {
			md = MessageDigest.getInstance("MD5");
			bytes = md.digest(sourceStr.getBytes());
			encryptString = new String(bytes, "UTF-8");
		} catch (Exception e) {
			encryptString = sourceStr;
		}
		return encryptString;
	}

	public static String toHexString(String sourceStr) {
		if (StringUtil.isEmpty(sourceStr)) {
			return sourceStr;
		}
		StringBuffer strBuff = new StringBuffer("");
		for (char ch : sourceStr.toCharArray()) {
			strBuff.append(StringUtil.toHexString(ch));
		}
		return strBuff.toString();
	}

	public static void main(String[] args) {
		System.out.println(EncryptUtils.toHexString("abcDDD"));
	}
}
