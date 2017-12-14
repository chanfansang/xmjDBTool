package com.xmj.tool.db.util;

import java.util.ArrayList;
import java.util.List;


public class DataUtil {

	public static <T> List<Object> toList(T[] args) {
		ArrayList<Object> list = new ArrayList<Object>(args.length);
		for (Object t : args) {
			list.add(t);
		}
		return list;
	}
	
	public static boolean isLong(String str) {
		if (str.isEmpty())
			return false;
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
}
