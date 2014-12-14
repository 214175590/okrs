/*
 * 类名：com.szkingdom.frame.security.Base64.java
 * 简述：base64编码
 * 作者：yisin
 * 版本：1.0
 */

package com.szkingdom.frame.security;

import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;

/**
 * <p>
 * 该类提供了使用base64的方式进行编码和解码的工具，可以进行简单的加/解密
 * </p>
 * <p>
 * base64原理：base64将三个字节转换成4个字节进行加密。
 * <ul>
 * <li>例如编码前原字节为111111111111111111111111 编码后字节为00111111 00111111 00111111
 * 00111111</li>
 * </ul>
 * </p>
 * 
 * @author yisin
 * @version 1.0
 * @since 1.0
 */
public class Base64 {

	private static final ILogger logger = LogFactory
			.getRunningLogger(DESEncrypt.class);
	// 编码表
	public static final char[] BASE64_ENCODE = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	/**
	 * 解码表 解码表的规则:编码表字符的ascii值对应解码表的索引，解码表的指定索引的值为编码表字符的
	 * 索引值。例如：编码表中的字符B对应编码表的索引为2,ascii值为98,那么在解码表中索引为98的 值为2,以此类推。
	 */
	public static final byte[] BASE64_DECODE = { 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64,// 1
			64, 64, 64, 64, 64, 64, 64, 64, 64, 64,// 2
			64, 64, 64, 64, 64, 64, 64, 64, 64, 64,// 3
			64, 64, 64, 64, 64, 64, 64, 64, 64, 64,// 4
			64, 64, 64, 64, 64, 64, 64, 64, 64, 64,// 5
			64, 64, 64, 64, 64, 64, 64, 64, 64, 64,// 6
			64, 64, 43, 47, 64, 0, 1, 2, 3, 4,// 7
			5, 6, 7, 8, 9, 10, 11, 12, 13, 14,// 8
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24,// 9
			25, 64, 64, 64, 64, 64, 64, 26, 27, 28,// 10
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38,// 11
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48,// 12
			49, 50, 51, 64, 64, 64, 64, 64 // 13
	};

	private static Base64 base64Enc = new Base64();

	// 构造器
	private Base64() {

	}

	public static Base64 getInstance() {
		return base64Enc;
	}

	/**
	 * <pre>
	 * 编码字符串
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param sourceStr
	 *            源字符串（待加密的字符串）
	 * @return String 加密后的字符串
	 * 
	 */
	public String encode(String sourceStr) {
		if (StringUtil.isEmpty(sourceStr)) {
			logger.info("The sourceStr string is null.");
			return "";
		}
		return base64Encode(sourceStr.getBytes());
	}

	/**
	 * <pre>
	 * 解码字符串
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
		return new String(base64Decode(destStr));
	}

	private String base64Encode(byte[] b) {
		// 判断字节数组的合法性，如果字节数组为空，直接返回
		if (null == b || 0 == b.length) {
			return "";
		}
		int len = b.length;

		// 根据编码后的字符的总长度构造StringBuffer对象
		StringBuffer strBuffer = new StringBuffer((len % 3 == 0 ? len / 3
				: len / 3 + 1) * 4);
		int code = 0;

		// 循环字节数组
		for (int i = 0; i < len; i++) {
			// 对字节进行移位处理，移位后的字节要保持成00000000111111111111111111111111
			code |= (b[i] << (16 - i % 3 * 8)) & (0XFF << (16 - i % 3 * 8));
			// 判断每3个字节或字节长度－1的时候进行处理
			if (i % 3 == 2 || i == len - 1) {
				// 将移位后的字节再次移位再进行移位拆分，拆分成4个字节
				strBuffer.append(BASE64_ENCODE[(code & 0XFC0000) >>> 18]);
				strBuffer.append(BASE64_ENCODE[(code & 0X3F000) >>> 12]);
				strBuffer.append(BASE64_ENCODE[(code & 0XFC0) >>> 6]);
				strBuffer.append(BASE64_ENCODE[(code & 0X3F)]);
			}
		}

		/**
		 * base64编码后由3个字节变成4个字，这两个判断是为了 判断字节数组编码后最后4个字节包含几个A，如果，包含0，就要将该0 替换成=字符
		 */
		if (len % 3 > 0) {
			strBuffer.setCharAt(strBuffer.length() - 1, '=');
		}
		if (len % 3 == 1) {
			strBuffer.setCharAt(strBuffer.length() - 2, '=');
		}

		return strBuffer.toString();
	}

	private byte[] base64Decode(String encode) {
		// 如果传入的串为空直接返回一个空的字节数组
		if (null == encode || "".equals(encode.trim())) {
			return new byte[0];
		}
		// 判断串长度是否为4的整数倍，如果不是，则说明参数传入的是非法的串
		if (encode.length() % 4 != 0) {
			throw new IllegalArgumentException("The encoe string must be 4 * n");
		}

		// 以下判断在传入的串中有几个=号
		int pad = 0;
		if (encode.charAt(encode.length() - 1) == '=') {
			pad++;
		}
		if (encode.charAt(encode.length() - 2) == '=') {
			pad++;
		}

		// 计算编码前的串的长度
		int len = (encode.length() / 4) * 3 - pad;

		// 构造编码前的字节数组
		byte[] b = new byte[len];

		int j = 0;
		int ch0, ch1, ch2, ch3;
		for (int i = 0; i < encode.length(); i += 4) {
			j = i % 3 * 4;
			ch0 = BASE64_DECODE[encode.charAt(i)] << 18;
			ch1 = BASE64_DECODE[encode.charAt(i + 1)] << 12;
			ch2 = BASE64_DECODE[encode.charAt(i + 2)] << 6;
			ch3 = BASE64_DECODE[encode.charAt(i + 3)];
			int tmp = ch0 | ch1 | ch2 | ch3;
			b[j] = (byte) ((tmp & 0XFF0000) >>> 16);
			if (i < encode.length() - 4) {
				b[j + 1] = (byte) ((tmp & 0XFF00) >>> 8);
				b[j + 2] = (byte) ((tmp & 0XFF));
			} else {
				if (j + 1 < len) {
					b[j + 1] = (byte) ((tmp & 0XFF00) >>> 8);
				}
				if (j + 2 < len) {
					b[j + 2] = (byte) ((tmp & 0XFF));
				}

			}
		}

		return b;
	}

	public static void main(String[] args) {
		System.out.println("ED6852536990C0E00841F7B45CBC88B7");
		System.out.println(Base64.getInstance().decode(
				"RUQ2fXU2fXc2fX82f393f39/f39/f39/f39/f39/f39="));
		System.out.println(Base64.getInstance().encode(
				"ED6852536990C0E00841F7B45CBC88B7"));
	}
}
