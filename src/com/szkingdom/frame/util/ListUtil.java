package com.szkingdom.frame.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @简述：<一句话介绍>
 * @详述：<详细介绍>
 * @author admin
 * @since 1.0
 * @see com.wintao.daiwei.frame.common.actionListUtil.java
 */
public class ListUtil {

	/**
	 * @简述：<一句话介绍>
	 * @详述：<详细介绍> void
	 * @param args
	 * 
	 */
	public static void main(String[] args) {

	}

	public static List<Object> pickList(List<Object> list, int index, int count) {
		List<Object> newList = new ArrayList<Object>();
		if (list != null) {
			int k = list.size();
			int fromIndex = (index - 1) * count;
			int toIndex = index * count;
			toIndex = toIndex > k ? k : toIndex;
			newList = list.subList(fromIndex, toIndex);
		}
		return newList;
	}

}
