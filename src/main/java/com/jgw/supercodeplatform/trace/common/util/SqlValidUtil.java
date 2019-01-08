package com.jgw.supercodeplatform.trace.common.util;

import java.util.regex.Pattern;

public class SqlValidUtil {

	private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(truncate|substr|ascii|declare|exec|master|drop|execute)\\b)";

	private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

	public static boolean isValid(String str) {
		if (sqlPattern.matcher(str).find()) {
			return false;
		}
		return true;

	}
	
	public static void main(String[] args) {
		String sql="truncate table zc_test";
		boolean flag=isValid(sql);
		System.out.println(flag);
	}
}