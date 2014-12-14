package com.szkingdom.frame.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;

/**
 * <pre>
 * 字符串工具类
 * </pre>
 * 
 * @author yisin
 * @date 2012-11-30 上午10:15:30
 * @see com.szkingdom.frame.util.StringUtil
 * 
 */
public final class StringUtil {
	private static ILogger log = LogFactory.getDefaultLogger(StringUtil.class);

	private static final int byteMaxhex = 255;
	private static final Pattern regInteger = Pattern.compile("\\d+");
	private static final Pattern isNumber = Pattern.compile("-{0,1}[0-9]+[.]{0,1}[0-9]*");
	private static final Pattern isLowcase = Pattern.compile("[a-z]+");
	private static final Pattern isUppercase = Pattern.compile("[A-Z]+");
	private static final char[] charArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 字符串是否是数字
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param ch
	 *            字符
	 * @return boolean true:字符是数字 false:字符不是数字
	 */
	public static boolean isDigit(char ch) {
		int temp = ch & byteMaxhex;
		return temp >= 48 && temp <= 57;
	}

	/**
	 * 字符串是否是数字串
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param string
	 *            字符串
	 * @return boolean true:数字 false：非数字
	 */
	public static boolean isDigit(String string) {
		boolean bool = false;
		if (!isEmpty(string)) {
			Matcher matcher = regInteger.matcher(string);
			bool = matcher.matches();
		}
		return bool;
	}

	/**
	 * 字符是否是小写
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param ch
	 *            字符
	 * @return boolean true:小写字符 false:大写字符
	 */
	public static boolean isLowerCase(char ch) {
		int temp = ch & byteMaxhex;
		return temp >= 97 && temp <= 122;
	}

	/**
	 * 字符串是否是小写字符
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param string
	 *            字符串
	 * @return boolean true:字符串为小写字符串 false:字符串为大写字符串
	 */
	public static boolean isLowerCase(String string) {
		Matcher matcher = isLowcase.matcher(string);
		return matcher.matches();
	}

	/**
	 * 字符是否是大写字符
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param ch
	 *            字符
	 * @return boolean true:大写字符 false:小写字符
	 */
	public static boolean isUpperCase(char ch) {
		int temp = ch & byteMaxhex;
		return temp >= 65 && temp <= 90;
	}

	/**
	 * 字符串是否是大写字符串
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param string
	 *            字符串
	 * @return boolean true:字符串为大写字符串 false:小写字符串
	 */
	public static boolean isUpperCase(String string) {
		Matcher matcher = isUppercase.matcher(string);
		return matcher.matches();
	}

	/**
	 * 转换为无符号字符串
	 * 
	 * @author yisin
	 * @date 2012-11-30 上午11:20:03
	 * @param i
	 * @param shift
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#toUnsignedString
	 */
	private static String toUnsignedString(long i, int shift) {
		char[] chs = new char[64];
		int charPosition = 64;
		int radix = 1 << shift;
		int value = radix - 1;
		do {
			chs[--charPosition] = charArray[(int) (i & value)];
			i >>>= shift;
		} while (i > 0);
		return new String(chs, charPosition, 64 - charPosition);
	}

	/**
	 * int值转换为十六进制
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param i
	 *            int值
	 * @return String 十六进制字符串
	 */
	public static String toHexString(int i) {
		return toUnsignedString(i, 4);
	}

	/**
	 * long值转换为十六进制
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param i
	 *            long值
	 * @return String 十六进制字符串
	 */
	public static String toHexString(long i) {
		return toUnsignedString(i, 4);
	}

	/**
	 * int值转换为八进制
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param i
	 *            int值
	 * @return String 八进制字符串
	 */
	public static String toOctalString(int i) {
		return toUnsignedString(i, 3);
	}

	/**
	 * long值转换为八进制
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param i
	 *            long值
	 * @return String 八进制字符串
	 */
	public static String toOctalString(long i) {
		return toUnsignedString(i, 3);
	}

	/**
	 * int值转换为二进制
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param i
	 *            int值
	 * @return String 二进制字符串
	 */
	public static String toBinaryString(int i) {
		return toUnsignedString(i, 1);
	}

	/**
	 * long值转换为二进制
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param i
	 *            long值
	 * @return String 二进制字符串
	 */
	public static String toBinaryString(long i) {
		return toUnsignedString(i, 1);
	}

	/**
	 * 判断对象是否为Null
	 * 
	 * @author admin
	 * @date 2012-11-30 上午10:30:34
	 * @param obj
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#isNull
	 */
	public static boolean isNull(Object obj) {
		return null == obj;
	}

	/**
	 * 判断对象是否为空或空字符串
	 * 
	 * @author admin
	 * @date 2012-11-30 上午10:30:47
	 * @param obj
	 * @return boolean (true:空，false:非空)
	 * @see com.szkingdom.frame.util.StringUtil#isEmpty
	 */
	public static boolean isEmpty(Object obj) {
		boolean bool = true;
		if (null != obj) {
			if (obj instanceof String) {
				if (!"".equals(obj.toString().trim())) {
					bool = false;
				}
			} else {
				bool = false;
			}
		}
		return bool;
	}

	/**
	 * 产生唯一ID
	 * 
	 * @param param
	 * @return
	 */
	public static String getUniqueId(String param) {
		if (null != param) {
			param = param + System.currentTimeMillis() + (int) (Math.random() * 100);
		}
		return param;
	}

	/**
	 * 产生唯一ID
	 * 
	 * @param param
	 * @return
	 */
	public static int getUniqueId(int i) {
		int id = 1;
		id = StringUtil.stringToInt(i + "" + (int) (Math.random() * 100) + (int) (Math.random() * 100) + (int) (Math.random() * 100) + (int) (Math.random() * 100));
		return id;
	}

	/**
	 * 处理Null或空字符串
	 * 
	 * @author admin
	 * @date 2012-11-30 上午10:45:17
	 * @param string
	 * @return String
	 * @see com.szkingdom.frame.util.StringUtil#nullToString
	 */
	public static String excNullToString(String string) {
		return excNullToString(string, GlobalConstants.EMPTY_STRING);
	}

	/**
	 * 处理Null或空字符串
	 * 
	 * @author admin
	 * @date 2012-11-30 上午10:45:55
	 * @param string
	 * @param added
	 * @return String
	 * @see com.szkingdom.frame.util.StringUtil#nullToString
	 */
	public static String excNullToString(String string, String added) {
		if (isNull(string)) {
			string = added;
		}
		return string;
	}

	/**
	 * 处理Null或空对象
	 * 
	 * @author admin
	 * @date 2012-11-30 上午10:45:17
	 * @param Object
	 * @return Object
	 * @see com.szkingdom.frame.util.StringUtil#excNullToObject
	 */
	public static Object excNullToObject(Object obj) {
		return excNullToObject(obj, new Object());
	}

	/**
	 * 处理Null或空对象
	 * 
	 * @author admin
	 * @date 2012-11-30 上午10:45:55
	 * @param Object
	 *            obj
	 * @param Object
	 *            added
	 * @return Object
	 * @see com.szkingdom.frame.util.StringUtil#excNullToObject
	 */
	public static Object excNullToObject(Object obj, Object added) {
		if (isNull(obj)) {
			obj = added;
		}
		return obj;
	}
	
	/**
	 * 将字Object转换为字符串
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param Object
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#objectToString
	 */
	public static String objectToString(Object obj) {
		return objectToString(obj, null);
	}
	
	/**
	 * 将字Object转换为字符串
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param Object
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#objectToString
	 */
	public static String objectToString(Object obj, String added) {
		String result = added;
		if(obj != null){
			result = (String) obj;
		}
		return result;
	}

	/**
	 * 将字符串转换成整数类型，如果为空则转换成0
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#stringToInt
	 */
	public static int stringToInt(String string) {
		return stringToInt(string, GlobalConstants.NUM_0);
	}

	/**
	 * 将字符串转换成整数类型，如果为空则转换成指定值
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#stringToInt
	 */
	public static int stringToInt(String string, int added) {
		int result = 0;
		try {
			result = Integer.parseInt(string);
		} catch (Exception e) {
			result = added;
			// log.info("Parameter string is empty or is digital type, default to int。");
		}
		return result;
	}

	/**
	 * 将object转换成整数类型，如果为空则转换成0
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param Object
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#objectToInt
	 */
	public static int objectToInt(Object obj) {
		return objectToInt(obj, GlobalConstants.NUM_0);
	}

	/**
	 * 将object转换成整数类型，如果为空则转换成指定值
	 * 
	 * @author admin
	 * @date Object-11-30 上午11:29:11
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#objectToInt
	 */
	public static int objectToInt(Object obj, int added) {
		int result = 0;
		try {
			if (obj != null) {
				result = Integer.parseInt(obj.toString());
			}
		} catch (Exception e) {
			result = added;
		}
		return result;
	}

	/**
	 * 将字符串转换成整数类型，如果为空则转换成0
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#stringToInt
	 */
	public static long stringToLong(String string) {
		return stringToLong(string, GlobalConstants.NUM_0);
	}

	/**
	 * 将字符串转换成整数类型，如果为空则转换成指定值
	 * 
	 * @author admin
	 * @date 2012-11-30 上午11:29:11
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#stringToLong
	 */
	public static long stringToLong(String string, long added) {
		long result = 0;
		try {
			result = Long.parseLong(string);
		} catch (Exception e) {
			result = added;
			// log.info("Parameter string is empty or is digital type, default to long。");
		}
		return result;
	}

	/**
	 * 将字符串转换成float类型
	 * 
	 * @author yisin
	 * @date 2012-12-11 上午11:58:44
	 * @param string
	 * @return float
	 * @see com.szkingdom.frame.util.StringUtil#stringToFloat
	 */
	public static float stringToFloat(String string) {
		return stringToFloat(string, 0.0f);
	}

	/**
	 * 将字符串转换成float类型,如果为空则转为指定的值
	 * 
	 * @author yisin
	 * @date 2012-12-11 上午11:58:44
	 * @param string
	 * @return float
	 * @see com.szkingdom.frame.util.StringUtil#stringToFloat
	 */
	public static float stringToFloat(String string, float added) {
		float result = 0.0f;
		try {
			result = Float.parseFloat(string);
		} catch (Exception e) {
			result = added;
			// log.info("Parameter string is empty or is digital type, default to float。");
		}
		return result;
	}

	/**
	 * 将字符串转换成double类型,如果为空则转为指定的值
	 * 
	 * @author yisin
	 * @date 2012-12-11 上午11:58:44
	 * @param string
	 * @return double
	 * @see com.szkingdom.frame.util.StringUtil#stringToDouble
	 */
	public static double stringToDouble(String string) {
		return stringToDouble(string, 0.0d);
	}

	/**
	 * 将字符串转换成double类型,如果为空则转为指定的值
	 * 
	 * @author yisin
	 * @date 2012-12-11 上午11:58:44
	 * @param string
	 * @return double
	 * @see com.szkingdom.frame.util.StringUtil#stringToDouble
	 */
	public static double stringToDouble(String string, double added) {
		double result = 0.0d;
		try {
			result = Double.parseDouble(string);
		} catch (Exception e) {
			result = added;
			// log.info("Parameter string is empty or is digital type, default to double。");
		}
		return result;
	}

	/**
	 * 判断是否为数值类型（整数、小数、负数）
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午12:00:25
	 * @param string
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#isNumbers
	 */
	public static boolean isNumbers(String string) {
		boolean bool = false;
		try {
			Matcher matcher = isNumber.matcher(string);
			bool = matcher.matches();
		} catch (Exception e) {
			log.error("参数字符串为空！", e);
		}
		return bool;
	}

	/**
	 * 将Unicode编码转换为正常字符
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午12:03:57
	 * @param param
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#stringUncode
	 */
	public static String stringUncode(String param) {
		if (param != null && !param.trim().equals("")) {
			try {
				param = URLDecoder.decode(param, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return param;
	}

	/**
	 * 将字符转换为Unicode编码
	 * 
	 * @author yisin
	 * @date 2012-12-11 下午12:04:05
	 * @param param
	 * @return
	 * @see com.szkingdom.frame.util.StringUtil#stringEncode
	 */
	public static String stringEncode(String param) {
		if (param != null && !param.trim().equals("")) {
			try {
				param = URLEncoder.encode(param, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return param;
	}

	/**
	 * 
	 * @简述：<将Object对象转换为boolean 判断是否为空，为空在则返回false>
	 * @详述：<详细介绍> boolean
	 * @param str
	 * @return
	 * 
	 */
	public static boolean parseBoolean(String str) {
		boolean bool = false;
		if (str != null && !"".equals(str.trim())) {
			bool = Boolean.parseBoolean(str);
		}
		return bool;
	}

	/**
	 * 
	 * @简述：<将Object对象转换为boolean 判断是否为空，为空在则返回false>
	 * @详述：<详细介绍> boolean
	 * @param str
	 * @return
	 * 
	 */
	public static boolean parseBoolean(Object obj) {
		boolean bool = false;
		if (obj != null) {
			bool = parseBoolean(obj.toString());
		}
		return bool;
	}

	/**
	 * 
	 * @简述：<将参数转换为UTF-8编码>
	 * @详述：<根据不同浏览器处理方式不一样>
	 * @param request
	 * @param param
	 * @return
	 * 
	 */
	public static String toUtf8(String param) {
		try {
			param = new String(param.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}

	/**
	 * 
	 * @简述：<将参数转换为GBK编码>
	 * @详述：<根据不同浏览器处理方式不一样>
	 * @param request
	 * @param param
	 * @return
	 * 
	 */
	public static String toGbk(String param) {
		try {
			param = new String(param.getBytes("GBK"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}

	/**
	 * 将字符串字母全部反转
	 * 
	 * @author yisin
	 * @date 2013-3-11 上午11:29:34
	 * @param String
	 *            字符串
	 * @return String 反转后的字符串
	 * @see com.szkingdom.frame.util.StringUtil#reversal
	 */
	public static String reversal(String param) {
		if (param != null && param.length() > 1) {
			StringBuffer sb = new StringBuffer();
			String[] str = param.split("");
			for (int i = (str.length - 1); i >= 0; i--) {
				sb.append(str[i]);
			}
			param = sb.toString();
		}
		return param;
	}

	/**
	 * 
	 * @简述：将字符串中包含的回车换行符\n 替换成"< b r >"
	 * @param content
	 * @return
	 * 
	 */
	public static String replaceEnter(String content) {
		if (content != null) {
			content = content.replaceAll("\n", "<BR>");
		}
		return content;
	}

	/**
	 * 
	 * @简述：将字符串中包含的"< b r >" 替换成回车换行符\n
	 * @param content
	 * @return
	 * 
	 */
	public static String replaceBr(String content) {
		if (content != null) {
			content = content.replaceAll("<BR>", "\n");
			content = content.replaceAll("<br>", "\n");
		}
		return content;
	}

	/**
	 * 将特殊符号替代符转回来
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceTeshu(String content, int flag) {
		if (content != null) {
			content = content.replaceAll("#BFH#", "%");
			content = content.replaceAll("#AND#", "&");
			content = content.replaceAll("#lt;", "&lt;");
			if (flag == 1) {
				content = content.replaceAll("&lt;", "<");
			}
		}
		return content;
	}

	public static String firstCharToUpperCase(String content) {
		if (!isEmpty(content)) {
			String tou = content.substring(0, 1);
			String wei = content.substring(1);
			content = tou.toUpperCase() + wei;
		}
		return content;
	}

	public static String firstCharToLowerCase(String content) {
		if (!isEmpty(content)) {
			String tou = content.substring(0, 1);
			String wei = content.substring(1);
			content = tou.toLowerCase() + wei;
		}
		return content;
	}

	/**
	 * 反格式化byte
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] hex2byte(String s) {
		byte[] src = s.toLowerCase().getBytes();
		byte[] ret = new byte[src.length / 2];
		for (int i = 0; i < src.length; i += 2) {
			byte hi = src[i];
			byte low = src[i + 1];
			hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a') : hi - '0');
			low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a') : low - '0');
			ret[i / 2] = (byte) (hi << 4 | low);
		}
		return ret;
	}

	/**
	 * 格式化byte
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] out = new char[b.length * 2];
		for (int i = 0; i < b.length; i++) {
			byte c = b[i];
			out[i * 2] = Digit[(c >>> 4) & 0X0F];
			out[i * 2 + 1] = Digit[c & 0X0F];
		}
		return new String(out);
	}

	/**
	 * 判断路径中是否有http字符
	 * 
	 * @param path
	 * @return
	 */
	public static boolean ifHaveHttp(String path) {
		boolean bool = false;
		if (null != path) {
			bool = path.toLowerCase().indexOf("http:") != -1 || path.toLowerCase().indexOf(":/") != -1;
		}
		return bool;
	}

	public static float rounded(float num, int len) {
		String str = String.valueOf(num);
		if (str.length() > 0 && str.lastIndexOf(".") != -1) {
			String zs = str.substring(0, str.lastIndexOf("."));
			String xs = str.substring(str.lastIndexOf(".") + 1);
			if (xs.length() > len) {
				xs = xs.substring(0, len);
			}
			str = zs + '.' + xs;
		}
		return StringUtil.stringToFloat(str);
	}

	public static void main(String[] args) {
		String s = "{\"touser\" : \"all_user\",\"fromuser\" : \"U1\",\"msg\" : \"Cfon\",\"time\" : \"Tue Sep 30 11:00:03 UTC 0800 2014\"}";
		JSONObject obj = JSONObject.fromObject(s);
		System.out.println(obj.get("touser"));
	}

}
