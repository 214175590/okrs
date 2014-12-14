/*
 * 类名：com.szkingdom.frame.security.DESEncrypt.java
 * 简述：DES加密和解密
 * 详述：使用DES加密算法加密和解密内容
 * 作者：yisin
 * 版本：1.0
 */

package com.szkingdom.frame.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;

/**
 * <pre>
 * 简述:DES加密和解密
 * 详述:使用DES加密算法加密和解密内容
 * </pre>
 * 
 * @author yisin
 * @version 1.0
 * @since 1.0
 */
public class DESEncrypt {
	private static final ILogger logger = LogFactory
			.getRunningLogger(DESEncrypt.class);

	/**
	 * 密钥值，最少为8位
	 */
	private static final String DES_SECRECT = "pyuandao";

	/**
	 * 加解密算法
	 */
	private static final String TRANSFORMATION = "DESede";

	/**
	 * 默认字符集
	 */
	private static final String DES_CHARSET = "UTF-8";
	private static boolean started = false;

	/**
	 * 加密器
	 */
	private static Cipher enCipher;

	/**
	 * 解密器
	 */
	private static Cipher deCipher;
	private static Object object = new Object();
	private static DESEncrypt encrypt = new DESEncrypt();

	private DESEncrypt() {
		init();
	}

	/**
	 * <pre>
	 * 初始化加密器和解密器
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param destStr
	 *            待解密的字符串
	 * @return String 源字符串
	 * 
	 */
	private void init() {
		try {
			enCipher = Cipher.getInstance(TRANSFORMATION);
			deCipher = Cipher.getInstance(TRANSFORMATION);
			enCipher.init(Cipher.ENCRYPT_MODE, getKey());
			deCipher.init(Cipher.DECRYPT_MODE, getKey());
			started = true;
		} catch (Exception e) {
			logger.error("init the Cipher failed.", e);
		}
	}

	public static DESEncrypt getInstance() {
		return encrypt;
	}

	/**
	 * <pre>
	 * 加密字符串
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param sourceStr
	 *            字符串,待加密的字符串
	 * @return String 加密后的字符串
	 * 
	 */
	public String encode(String sourceStr) {
		String encrptStr = "";
		if (StringUtil.isEmpty(sourceStr)) {
			logger.info("The sourceStr is empty.");
			return encrptStr;
		}
		// 锁定初始化部分，程序中只能初始化一次
		synchronized (object) {
			if (!started) {
				init();
			}
		}
		if (null == enCipher) {
			logger.error("The enCipher is null.");
			return encrptStr;
		}
		byte[] bytes = null;
		try {
			bytes = enCipher.doFinal(sourceStr.getBytes(DES_CHARSET));

			// 循环并取反
			for (int i = 0; bytes != null && i < bytes.length; i += 2) {
				if (i + 2 >= bytes.length) {
					break;
				}
				bytes[i] = (byte) (~bytes[i]);
			}

			// 使用base64进行编码操作
			encrptStr = new BASE64Encoder().encode(bytes);
		} catch (Exception e) {
			logger.error("encrypt failed.", e);
		}
		return encrptStr;
	}

	/**
	 * <pre>
	 * 解密字符串
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param destStr
	 *            待解密的字符串
	 * @return String 源字符串
	 * 
	 */
	public String decode(String destStr) {
		String sourceStr = "";
		if (StringUtil.isEmpty(destStr)) {
			logger.info("encryted string is null.");
			return sourceStr;
		}
		synchronized (object) {
			if (!started) {
				init();
			}
		}
		if (null == deCipher) {
			throw new NullPointerException("The enCipher object is null.");
		}
		byte[] bytes = null;
		try {
			bytes = new BASE64Decoder().decodeBuffer(destStr);
			for (int i = 0; bytes != null && i < bytes.length; i += 2) {
				if (i + 2 >= bytes.length) {
					break;
				}
				bytes[i] = (byte) (~bytes[i]);
			}
			byte[] tmpBytes = deCipher.doFinal(bytes);
			sourceStr = new String(tmpBytes, DES_CHARSET);
		} catch (Exception e) {
			logger.error("encrypt failed.", e);
		}
		return sourceStr;
	}

	private Key getKey() {
		Key key = null;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance(TRANSFORMATION);
			_generator.init(new SecureRandom(DES_SECRECT.getBytes()));
			key = _generator.generateKey();
		} catch (Exception e) {
			logger.error("Error initializing class. Cause: ", e);
		}
		return key;
	}

	public static void main(String[] args) {
	}
}
