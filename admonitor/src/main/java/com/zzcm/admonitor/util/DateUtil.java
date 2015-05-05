package com.zzcm.admonitor.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static String getYestDay(){
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
	 
		
		return dateString;
	}
	public static String getToday(){
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		//calendar.add(calendar.DATE);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
	 
		
		return dateString;
	}
	
	public static String getTodayTime(){
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		//calendar.add(calendar.DATE);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static void main(String[] args) {
		String string = "oicq.wlogin_sdk.permission.WloginProvider.READ,oicq.wlogin_sdk.permission.WloginProvider.WRITE,com.qq.AppService.permission.out.IPC_SERVICE,com.qq.superuser.READ_PERMISSION,com.qq.AppService.permission.out.CACHE_READ_PERMISSION,com.qq.AppService.permission.out.CACHE_WRITE_PERMISSION,com.android.launcher.permission.READ_SETTINGS,com.android.launcher.permission.WRITE_SETTINGS,com.android.launcher.permission.INSTALL_SHORTCUT,com.android.launcher.permission.UNINSTALL_SHORTCUT,android.permission.BROADCAST_STICKY,android.permission.RECEIVE_BOOT_COMPLETED,android.permission.BATTERY_STATS,android.permission.READ_CONTACTS,android.permission.WRITE_CONTACTS,android.permission.WRITE_CALL_LOG,android.permission.READ_CALL_LOG,android.permission.SEND_SMS,android.permission.WRITE_SMS,android.permission.READ_SMS,android.permission.INTERNET,android.permission.RECEIVE_SMS,android.permission.READ_PHONE_STATE,android.permission.SET_WALLPAPER,android.permission.ACCESS_WIFI_STATE,android.permission.CHANGE_WIFI_STATE,android.permission.ACCESS_NETWORK_STATE,android.permission.CALL_PHONE,android.permission.WAKE_LOCK,android.permission.DISABLE_KEYGUARD,android.permission.VIBRATE,android.permission.WRITE_SETTINGS,android.permission.READ_CALENDAR,android.permission.WRITE_CALENDAR,com.android.browser.permission.READ_HISTORY_BOOKMARKS,com.android.browser.permission.WRITE_HISTORY_BOOKMARKS,android.permission.GET_ACCOUNTS,android.permission.RESTART_PACKAGES,android.permission.BLUETOOTH,android.permission.FLASHLIGHT,android.permission.CAMERA,android.permission.GET_TASKS,android.permission.READ_EXTERNAL_STORAGE,android.permission.WRITE_EXTERNAL_STORAGE,android.permission.GET_PACKAGE_SIZE,android.permission.EXPAND_STATUS_BAR,android.permission.ACCESS_COARSE_LOCATION,android.permission.CLEAR_APP_CACHE,android.permission.WRITE_EXTERNAL_STORAGE,com.tencent.permission.VIRUS_SCAN,android.permission.SYSTEM_ALERT_WINDOW,android.permission.CHANGE_NETWORK_STATE,android.permission.ACCESS_MTK_MMHW,android.permission.ACCESS_CACHE_FILESYSTEM,android.permission.CHANGE_WIFI_MULTICAST_STATE,android.permission.DIAGNOSTIC,android.permission.UPDATE_APP_OPS_STATS";
		String[] arr = string.split(",");
		for(String s : arr){
			System.out.println(s);
		}
	}
}
