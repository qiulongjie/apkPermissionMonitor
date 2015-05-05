package com.zzcm.admonitor.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * properties文件工具类
 * 
 * @author qiulongjie
 *
 */
public class PropertyUtil {

	/**
	 * 写入properties信息
	 * 
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			fis.close();
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			prop.store(fos, "Update '" + parameterName + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}
	}

	// 根据key读取value
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 读取properties的全部信息
	 * 
	 * @param filePath
	 */
	public static List<Map<String, String>> readAllProperties(String filePath) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			in.close();
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				Map<String, String> map = new HashMap<String, String>();
				String key = (String) en.nextElement();
				String value = props.getProperty(key);
				map.put(key, value);
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String path = "src\\main\\resources\\apkFileUtil.properties";
		List<Map<String, String>> list = readAllProperties(path);
		String value = readValue(path, "jdbc.url");
		writeProperties(path, "test", "test");
		System.out.println(ResourceBundle.getBundle("apkFileUtil").getString("test"));
	}
}
