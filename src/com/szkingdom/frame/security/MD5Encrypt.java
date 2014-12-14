package com.szkingdom.frame.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * <pre>
 * MD5加解密工具类
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 下午02:53:18
 * @see com.szkingdom.frame.security.MD5Encrypt
 * 
 */
public class MD5Encrypt {
	private static MD5Encrypt md5 = new MD5Encrypt();

	private MD5Encrypt() {

	}

	public static MD5Encrypt getInstance() {
		return md5;
	}

	public String encrypt(String sourceStr) {
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

	/**
	 * 
	 * MD5加密
	 * 
	 * @author yisin
	 * @date 2012-11-01
	 * @param plainText
	 *            需要加密的字符串
	 * @param number
	 *            加密位16,32
	 * @return String 已加密字符串
	 * 
	 */
	public String md5(String plainText, int number) {
		String result = plainText;
		if (plainText != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(plainText.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				if (number == 16) {// 16位的加密
					result = buf.toString().substring(8, 24);
				} else if (number == 32) {// 32位的加密
					result = buf.toString();
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		 System.out.println(MD5Encrypt.getInstance().md5("19860918", 32));
	}
}
