package com.szkingdom.frame.common.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import com.szkingdom.frame.util.DateUtil;
import com.szkingdom.frame.util.StringUtil;

/**
 * @简述：<通过反射获取实体所有属性、属性值等操作工具类>
 * @author admin
 * @date 2012-5-2 下午03:43:41
 * @see com.wintao.daiwei.frame.common.reflect.YXObject.java
 */
public class YXObject {

	/**
	 * @描述：<此方法功能介绍>
	 * @param void
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * 
	 * @描述：<判断对象类型，并返回String值>
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static String excType(Object obj) {
		String methodValue = null;
		if (obj instanceof String) {
			methodValue = StringUtil.excNullToString((String) obj, "");
		} else if (obj instanceof Integer) {
			methodValue = String.valueOf(obj);
		} else if (obj instanceof Long) {
			methodValue = String.valueOf(obj);
		} else if (obj instanceof Double) {
			methodValue = String.valueOf(obj);
		} else if (obj instanceof Float) {
			methodValue = String.valueOf(obj);
		} else if (obj instanceof Date) {
			methodValue = DateUtil.format((Date) obj);
		} else if (obj instanceof Timestamp) {
			methodValue = DateUtil.format((Timestamp) obj);
		} else if (obj instanceof Byte) {
			if (obj != null) {
				methodValue = (String) obj.toString();
			}
		}
		return methodValue;
	}

	/**
	 * 
	 * @描述：<判断对象类型，并返回String值>
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static Object excType(Class obj, String value) {
		String type = obj.getName();
		value = StringUtil.stringUncode(value);
		if (type.equals("java.lang.String")) {
			return value;
		} else if (type.equals("int")) {
			return StringUtil.stringToInt(value);
		} else if (type.equals("long")) {
			return StringUtil.stringToLong(value);
		} else if (type.equals("float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("byte")) {
			if (value == null) {
				return null;
			}
			return value.getBytes();
		} else if (type.indexOf("[B") != -1) {
			if (value == null) {
				return null;
			}
			return value.getBytes();
		} else if (type.indexOf("java.util.List") != -1) {
			if (value == null) {
				return null;
			}
			return value;
		} else if (type.equals("java.util.Date")) {
			if (value == null || value.trim().equals("")) {
				return null;
			}
			return new Timestamp(DateUtil.parse(value).getTime());
		} else if (type.equals("java.sql.Timestamp")) {
			if (value == null || value.trim().equals("")) {
				return null;
			}
			return DateUtil.parseToTimestamp(value, "yyyy-mm-dd hh:mm:ss[.f]");
		} else if (type.equals("java.sql.Blob")) {
			if (value == null) {
				return null;
			}
			byte[] by = value.getBytes();
			try {
				Blob blob = new SerialBlob(by);
				return blob;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 填充值（针对搜索增加%%）
	 * 
	 * @author yisin
	 * @date 2013-1-22 下午04:42:39
	 * @param obj
	 * @return
	 * @see com.szkingdom.frame.common.reflect.YXObject#excTypeBySearch
	 */
	public static Object excTypeBySearch(String mname, Class obj, String value) {
		String type = obj.getName();
		value = StringUtil.stringUncode(value);
		if (type.equals("java.lang.String")) {
			if (!mname.equals("orderby")) {
				value = value == null ? null : "%" + value + "%";
			}
			return value;
		} else if (type.equals("int")) {
			return StringUtil.stringToInt(value);
		} else if (type.equals("long")) {
			return StringUtil.stringToLong(value);
		} else if (type.equals("float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("byte")) {
			if (value == null) {
				return null;
			}
			return value.getBytes();
		} else if (type.indexOf("[B") != -1) {
			if (value == null) {
				return null;
			}
			return value.getBytes();
		} else if (type.indexOf("java.util.List") != -1) {
			if (value == null) {
				return null;
			}
			return value;
		} else if (type.equals("java.util.Date")) {
			if (value == null || value.trim().equals("")) {
				return null;
			}
			return new Timestamp(DateUtil.parse(value).getTime());
		} else if (type.equals("java.sql.Timestamp")) {
			if (value == null || value.trim().equals("")) {
				return null;
			}
			return DateUtil.parseToTimestamp(value, "yyyy-mm-dd hh:mm:ss[.f]");
		} else if (type.equals("java.sql.Blob")) {
			if (value == null) {
				return null;
			}
			byte[] by = value.getBytes();
			try {
				Blob blob = new SerialBlob(by);
				return blob;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @描述：<判断对象类型，并返回String值>
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static Object excTypeTest(Class obj) {
		String type = obj.getName();
		String value = "" + (int) (Math.random() * 99 + 1);
		if (type.equals("java.lang.String")) {
			return "测试数据" + value;
		} else if (type.equals("int")) {
			return StringUtil.stringToInt(value);
		} else if (type.equals("float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("java.util.Date")) {
			if (value != null) {
				return new Date();
			}
		} else if (type.equals("java.sql.Timestamp")) {
			if (value != null) {
				return new Timestamp(new Date().getTime());
			}
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @author yisin
	 * @date 2013-4-19 下午06:36:16
	 * @param obj
	 * @param value
	 * @return
	 * @see com.szkingdom.frame.common.reflect.YXObject#excTypeTest
	 */
	public static Object excTypeValue(Class obj, String value) {
		String type = obj.getName();
		if (type.equals("java.lang.String")) {
			return "测试数据" + value;
		} else if (type.equals("int")) {
			return StringUtil.stringToInt(value);
		} else if (type.equals("float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("java.util.Date")) {
			if (value != null) {
				return new Date();
			}
		} else if (type.equals("java.sql.Timestamp")) {
			if (value != null) {
				return new Timestamp(new Date().getTime());
			}
		}
		return null;
	}

	/**
	 * 
	 * @描述：<判断对象类型，并返回String值>
	 * @param obj
	 *            Object
	 * @return Class
	 */
	public static Class typeClass(String obj) {
		Class cla = null;
		if (obj.equals("java.lang.String")) {
			cla = String.class;
		} else if (obj.equals("int")) {
			cla = int.class;
		} else if (obj.equals("long")) {
			cla = long.class;
		} else if (obj.equals("float")) {
			cla = float.class;
		} else if (obj.equals("double")) {
			cla = double.class;
		} else if (obj.equals("java.lang.Double")) {
			cla = Double.class;
		} else if (obj.equals("java.lang.Float")) {
			cla = Float.class;
		} else if (obj.equals("java.util.Date")) {
			cla = Date.class;
		} else if (obj.equals("java.sql.Timestamp")) {
			cla = Timestamp.class;
		} else if (obj.equals("java.sql.Blob")) {
			cla = Blob.class;
		} else if (obj.equals("byte")) {
			cla = byte.class;
		} else if (obj.equals("[B")) {
			cla = byte[].class;
		} else if (obj.equals("java.util.List")) {
			cla = List.class;
		}
		return cla;
	}

	/**
	 * 
	 * @描述：<获取所有属性名，并放到String[]中>
	 * @param f
	 *            java.lang.reflect.Field
	 * @return String[]
	 */
	public static String[] fieldByName(Field[] f) {
		String[] name = new String[f.length];
		for (int i = 0; i < f.length; i++) {
			name[i] = f[i].getName();
		}
		return name;
	}

	/**
	 * 
	 * @描述：<获取所有属性值，并放到Object[]中>
	 * @param f
	 *            java.lang.reflect.Field
	 * @param o
	 *            Object
	 * @return Object[]
	 */
	public static Object[] fieldByValue(Field[] f, Object o) {
		Object[] value = new Object[f.length];
		for (int i = 0; i < f.length; i++) {
			try {
				value[i] = f[i].get(o);
			} catch (Exception e) {
			}
		}
		return value;
	}

	/**
	 * 
	 * @描述：<将对象的属性作为key，属性值作为value 保存到Map中>
	 * @param Map
	 *            <String,Object>
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getValue(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] f = obj.getClass().getDeclaredFields();
		String fname[] = YXObject.fieldByName(f);
		String tou = "", wei = "", getMethod = "";
		for (int i = 0; i < fname.length; i++) {
			tou = fname[i].substring(0, 1);
			wei = fname[i].substring(1);
			getMethod = "get" + tou.toUpperCase() + wei;
			try {
				Method method = obj.getClass().getMethod(getMethod);
				Object vas = method.invoke(obj);
				map.put(fname[i], vas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 从request中获取参数并填充对象
	 * 
	 * @author yisin
	 * @date 2013-1-22 下午04:40:50
	 * @param obj
	 * @param request
	 * @see com.szkingdom.frame.common.reflect.YXObject#setValue
	 */
	public static void setValue(Object obj, HttpServletRequest request) {
		String columns = request.getParameter("columns");
		Field[] f = obj.getClass().getDeclaredFields();
		String fname = "";
		String tou = "", wei = "", setMethod = "";
		for (int i = 0; i < f.length; i++) {
			fname = f[i].getName();
			tou = fname.substring(0, 1);
			wei = fname.substring(1);
			setMethod = "set" + tou.toUpperCase() + wei;
			String value = request.getParameter(fname);
			String type = f[i].getType().getName();
			if (fname.equals("fromIndex")) {
				if (!StringUtil.isEmpty(columns)) {
					value = "0";
				}
			} else if (fname.equals("toIndex")) {
				if (!StringUtil.isEmpty(columns)) {
					value = "65000";
				}
			}
			if (type.equals("java.lang.String")) {
				value = StringUtil.stringUncode(value);
			}
			try {
				Method method = obj.getClass().getMethod(setMethod,
						new Class[] { typeClass(type) });
				method.invoke(obj,
						new Object[] { excType(typeClass(type), value) });
			} catch (Exception e) {
				System.err.println("缺少" + setMethod + "(" + typeClass(type)
						+ " " + value + ")" + "方法！ " + e.getMessage());
			}
		}
	}

	/**
	 * 从request中获取参数并填充对象(针对模糊搜索 增加 %%)
	 * 
	 * @author yisin
	 * @date 2013-1-22 下午04:40:50
	 * @param obj
	 * @param request
	 * @see com.szkingdom.frame.common.reflect.YXObject#setValueForSearch
	 */
	public static void setValueForSearch(Object obj, HttpServletRequest request) {
		String columns = request.getParameter("columns");
		Field[] f = obj.getClass().getDeclaredFields();
		String fname = "";
		String setMethod = "";
		for (int i = 0; i < f.length; i++) {
			fname = f[i].getName();
			setMethod = "set" + StringUtil.firstCharToUpperCase(fname);
			String value = request.getParameter(fname);
			if (fname.equals("fromIndex")) {
				if (!StringUtil.isEmpty(columns)) {
					value = "0";
				}
			} else if (fname.equals("toIndex")) {
				if (!StringUtil.isEmpty(columns)) {
					value = "65000";
				}
			}
			String type = f[i].getType().getName();
			if (type.equals("java.lang.String")) {
				value = StringUtil.stringUncode(value);
			}
			try {
				Method method = obj.getClass().getMethod(setMethod,
						new Class[] { typeClass(type) });
				method.invoke(
						obj,
						new Object[] { excTypeBySearch(fname, typeClass(type),
								value) });
			} catch (Exception e) {
				System.err.println("缺少" + setMethod + "(" + typeClass(type)
						+ " " + value + ")" + "方法！ " + e.getMessage());
			}
		}
	}

	/**
	 * 测试用
	 */
	public static void setValueTest(Object obj) {
		Field[] f = obj.getClass().getDeclaredFields();
		String fname = "";
		String tou = "", wei = "", setMethod = "";
		for (int i = 0; i < f.length; i++) {
			fname = f[i].getName();
			tou = fname.substring(0, 1);
			wei = fname.substring(1);
			setMethod = "set" + tou.toUpperCase() + wei;
			String type = f[i].getType().getName();
			try {
				Method method = obj.getClass().getMethod(setMethod,
						new Class[] { typeClass(type) });
				method.invoke(obj,
						new Object[] { excTypeTest(typeClass(type)) });
			} catch (Exception e) {
				System.err.println("缺少" + setMethod + "(" + typeClass(type)
						+ " )" + "方法！ " + e);
			}
		}
	}

	/**
	 * 给对象某属性设置值
	 * 
	 * @author yisin
	 * @date 2013-4-19 下午06:37:33
	 * @param obj
	 * @param field
	 * @param value
	 * @see com.szkingdom.frame.common.reflect.YXObject#setValueForField
	 */
	public static void setValueForField(Object obj, String field, String value) {
		Field[] f = obj.getClass().getDeclaredFields();
		String fname = "";
		String setMethod = "";
		for (int i = 0; i < f.length; i++) {
			fname = f[i].getName();
			if (fname.equals(field)) {
				setMethod = "set" + StringUtil.firstCharToUpperCase(field);
				String type = f[i].getType().getName();
				try {
					Method method = obj.getClass().getMethod(setMethod,
							new Class[] { typeClass(type) });
					method.invoke(
							obj,
							new Object[] { excTypeValue(typeClass(type), value) });
				} catch (Exception e) {
					System.err.println("缺少" + setMethod + "(" + typeClass(type)
							+ " )" + "方法！ " + e);
				}
			}
		}
	}

}
