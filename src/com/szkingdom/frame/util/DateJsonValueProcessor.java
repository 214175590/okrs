package com.szkingdom.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * <pre>
 * 描述：
 * </pre>
 * 
 * @author yisin
 * @date 2013-5-21 上午09:57:22
 * @see com.szkingdom.frame.util.DateJsonValueProcessor
 * 
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	private String format = "yyyy-MM-dd";

	public DateJsonValueProcessor() {
	}

	public DateJsonValueProcessor(String format) {
		this.format = format;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-5-21 上午09:57:22
	 * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object,
	 *      net.sf.json.JsonConfig)
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @author yisin
	 * @date 2013-5-21 上午09:57:22
	 * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String,
	 *      java.lang.Object, net.sf.json.JsonConfig)
	 */
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if (value == null) {
			return "";
		} else if (value instanceof Date) {
			String str = new SimpleDateFormat(format).format((Date) value);
			return str;
		}
		return value.toString();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
