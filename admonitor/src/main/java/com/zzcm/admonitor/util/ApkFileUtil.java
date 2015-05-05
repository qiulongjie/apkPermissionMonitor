package com.zzcm.admonitor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.zzcm.admonitor.entity.ApkInfo;

/**
 * 文件工具类
 * @author qiulongjie
 *
 */	
public class ApkFileUtil {
	private static String apkPath = ResourceBundle.getBundle("application").getString("apk.save.path");
	
	private static List<ApkInfo> apkInfoList = new ArrayList<ApkInfo>();
	
	private static List<String> apkScanHisList = null;
	
	/**
	 * 得到apk文件集
	 * @return
	 */
	public static synchronized List<ApkInfo> getApkFileList(List<String> hisList) {

		apkScanHisList = hisList;
		// 清空
		apkInfoList.clear();
		
		//遍历文件
		getLastApkList();
		
		return apkInfoList;
	}

	/**
	 * 获取文件列表 排除已经扫描过的
	 * @return
	 */
	private static void getLastApkList(){
		if(apkPath != null){
			File file = new File(apkPath);
			findApkFile(file);
		}
    }
	
	/**
	 * 递归遍历文件
	 * @param file
	 */
	private static void findApkFile(File file){
		if(file == null){
			return;
		}
		if( file.isFile() && file.getName().endsWith(".apk")){
			String fileName = file.getName();
			if(!apkScanHisList.contains(fileName)){
				ApkInfo info = ApkUtil.getApkInfo(file);
				if( info != null ){
					apkInfoList.add(info);// 获取并添加apk信息到集合中
				}
			}
		}else{
			File fileList[] = file.listFiles();
			if (fileList != null && fileList.length > 0 ) {
				for(File f : fileList){
					try {
						findApkFile(f);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
