package com.zzcm.admonitor.util;

import java.util.List;

/**
 * 字符工具类
 * 
 * @author qiulongjie
 *
 */
public class StrUtil {

	/**
	 * 把数组转换为一个用逗号分隔的字符串 ，以便于用in+String 查询
	 * @param arr
	 * @return
	 */
	public static String converToString(String[] arr) {
		StringBuilder sb = new StringBuilder();
		if (arr != null && arr.length > 0) {
			for(String s : arr ){
				sb.append(s).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 把list转换为一个用逗号分隔的字符串
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (String s : list) {
				sb.append(s).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
