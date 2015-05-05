package com.zzcm.admonitor.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.zzcm.admonitor.entity.ApkInfo;

public class ApkUtil {
	
	private static final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");
	
	// ==================   ****  my *****
	
	/**
	 * 获取权限列表
	 * @param apkFilePath
	 * @return
	 */
	public static List<String> getApkPermissions(String apkFilePath){
		List<String> permissions = new ArrayList<String>();
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try{
			document = builder.build(getXmlInputStream(apkFilePath));
		}catch (Exception e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();//跟节点-->manifest
		List listPermission = root.getChildren("uses-permission");//子节点是个集合
		for(Object object : listPermission){
			String permission = ((Element)object).getAttributeValue("name", NS);
			permissions.add(permission);
		}
		return permissions;
	}
	
	/**
	 * 获取权限列表
	 * @param apkFile
	 * @return
	 */
	public static List<String> getApkPermissions(File apkFile){
		List<String> permissions = new ArrayList<String>();
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try{
			document = builder.build(getXmlInputStream(apkFile));
		}catch (Exception e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();//跟节点-->manifest
		List listPermission = root.getChildren("uses-permission");//子节点是个集合
		for(Object object : listPermission){
			String permission = ((Element)object).getAttributeValue("name", NS);
			permissions.add(permission);
		}
		return permissions;
	}
	
	/**
	 * 读取apk包中的清单文件
	 * @param apkFile
	 * @return
	 */
	private static InputStream getXmlInputStream(File apkFile) {
		InputStream inputStream = null;
		InputStream xmlInputStream = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(apkFile);
			ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
			inputStream = zipFile.getInputStream(zipEntry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startPrinf(inputStream);
			xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			try {
				inputStream.close();
				zipFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return xmlInputStream;
	}
	// ==================   ****  my *****
	
	/**
	 * 根据file获取apk信息
	 * @param apkFile
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ApkInfo getApkInfo(File apkFile){
		ApkInfo apkInfo = null;
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try{
			InputStream in = getXmlInputStream(apkFile);
			if( in == null ){
				return null;
			}
			apkInfo = new ApkInfo();
			
			document = builder.build(in);
		
			Element root = document.getRootElement();//跟节点-->manifest
			
			apkInfo.setVersionCode(root.getAttributeValue("versionCode",NS));
			apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
			apkInfo.setApkPackage(root.getAttributeValue("package", NS));
			Element elemUseSdk = root.getChild("uses-sdk");//子节点-->uses-sdk
			apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
			
			List listPermission = root.getChildren("uses-permission");//子节点是个集合
			List<String> pms = new ArrayList<String>();
			for(Object object : listPermission){
				String permission = ((Element)object).getAttributeValue("name", NS);
				pms.add(permission);
			}
			// 去重
			List<String> list = new ArrayList<String>(new LinkedHashSet<String>(pms));
			apkInfo.setPermissions(StrUtil.listToString(list));
			apkInfo.setPmsCount(list.size());
			
			String s = root.getAttributes().toString();
			String c[] = s.split(",");
			String versionCode = null;
			String versionName = null;
			String packageName = null;
			for(String a: c){
				if(a.contains("versionCode")){
					versionCode = a.substring(a.indexOf("versionCode=\"")+13, a.lastIndexOf("\""));
				}
				if(a.contains("versionName")){
					versionName = a.substring(a.indexOf("versionName=\"")+13, a.lastIndexOf("\""));
				}
				if(a.contains("package")){
					packageName = a.substring(a.indexOf("package=\"")+9, a.lastIndexOf("\""));
				}
			}
			if(apkInfo.getApkPackage() == null || "".equals(apkInfo.getApkPackage())){
				apkInfo.setApkPackage(packageName);
			}
			if(apkInfo.getVersionCode() == null || "".equals(apkInfo.getVersionCode())){
				apkInfo.setVersionCode(versionCode);
			}
			if(apkInfo.getVersionName() == null || "".equals(apkInfo.getVersionName())){
				apkInfo.setVersionName(versionName);
			}
			apkInfo.setApkSize(apkFile.length());
			apkInfo.setLastModify(new Date(apkFile.lastModified()));
			apkInfo.setApkName(apkFile.getName());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return apkInfo;
	}
	
	/**
	 * 根据apk文件路径获取inputstream
	 * @param apkPath
	 * @return
	 */
	private static InputStream getXmlInputStream(String apkPath) {
		InputStream inputStream = null;
		InputStream xmlInputStream = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(apkPath);
			ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
			inputStream = zipFile.getInputStream(zipEntry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startPrinf(inputStream);
			xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			try {
				inputStream.close();
				zipFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return xmlInputStream;
	}
	
	/**
	 * 根据网络url获取apk文件信息
	 * @param url
	 * @return
	 */
	public static ApkInfo getApkInfoByUrl(String url){
		ApkInfo apkInfo = null ;
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try{
			InputStream in = getXmlInputStreamByUrl(url);
			if( in == null ){
				return null;
			}
			apkInfo = new ApkInfo();
			
			document = builder.build(in);
			
			Element root = document.getRootElement();//跟节点-->manifest
			
			apkInfo.setVersionCode(root.getAttributeValue("versionCode",NS));
			apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
			apkInfo.setApkPackage(root.getAttributeValue("package", NS));
			Element elemUseSdk = root.getChild("uses-sdk");//子节点-->uses-sdk
			apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
			List listPermission = root.getChildren("uses-permission");//子节点是个集合
			List<String> pms = new ArrayList<String>();
			for(Object object : listPermission){
				String permission = ((Element)object).getAttributeValue("name", NS);
				pms.add(permission);
			}
			// 去重
			List<String> list = new ArrayList<String>(new LinkedHashSet<String>(pms));
			apkInfo.setPermissions(StrUtil.listToString(list));
			apkInfo.setPmsCount(list.size());
			
			String s = root.getAttributes().toString();
			String c[] = s.split(",");
			String versionCode = null;
			String versionName = null;
			String packageName = null;
			for(String a: c){
				if(a.contains("versionCode")){
					versionCode = a.substring(a.indexOf("versionCode=\"")+13, a.lastIndexOf("\""));
				}
				if(a.contains("versionName")){
					versionName = a.substring(a.indexOf("versionName=\"")+13, a.lastIndexOf("\""));
				}
				if(a.contains("package")){
					packageName = a.substring(a.indexOf("package=\"")+9, a.lastIndexOf("\""));
				}
			}
			if(apkInfo.getApkPackage() == null || "".equals(apkInfo.getApkPackage())){
				apkInfo.setApkPackage(packageName);
			}
			if(apkInfo.getVersionCode() == null || "".equals(apkInfo.getVersionCode())){
				apkInfo.setVersionCode(versionCode);
			}
			if(apkInfo.getVersionName() == null || "".equals(apkInfo.getVersionName())){
				apkInfo.setVersionName(versionName);
			}
//			apkInfo.setApkSize(apkFile.length());
//			apkInfo.setLastModify(new Date(apkFile.lastModified()));
			apkInfo.setApkName(url.substring(url.lastIndexOf("/")+1));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return apkInfo;
	}
	
	
	/**
	 * 从网络url中获取inputstream
	 * @param url
	 * @return
	 */
	private static InputStream getXmlInputStreamByUrl(String url) {
		InputStream xmlInputStream = null;
		try {
			URL u = new URL(url);
			u.openStream();
			ZipInputStream zipInputStream = new ZipInputStream(u.openStream());
			while(true){
				ZipEntry zipEntry = zipInputStream.getNextEntry();
				if(zipEntry == null){
					break;
				}
				if(zipEntry.getName().equals("AndroidManifest.xml")){
					AXMLPrinter xmlPrinter = new AXMLPrinter();
					xmlPrinter.startPrinf(zipInputStream);
					xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
					
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlInputStream;
	}
	
	
	
	public static void main(String[] args) {
		String url = "http://192.168.0.220/iad/apk/2015/3/ced652400d7c4d02a6edb8b4ccda3c6d.apk";
		System.out.println(url.substring(url.lastIndexOf("/")+1));
		ApkInfo info = getApkInfoByUrl(url);
		System.out.println(info);
	}
}
