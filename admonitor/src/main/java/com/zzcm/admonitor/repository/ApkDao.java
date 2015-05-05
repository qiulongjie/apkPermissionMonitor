package com.zzcm.admonitor.repository;

import java.util.List;
import java.util.Map;

import com.zzcm.admonitor.entity.ApkInfo;
import com.zzcm.admonitor.entity.ApkPermission;
import com.zzcm.admonitor.rmi.PkgPermission;

public interface ApkDao {

	/**
	 * 批量添加apk信息到数据库
	 * @param apkInfoList
	 */
	public void addApkInfos(List<ApkInfo> apkInfoList);
	
	/**
	 * 根据权限集查找权限
	 * @param pmsStr 查询语句中的in语句的条件
	 * @return
	 */
	public List<ApkPermission> getApkPms(String pmsStr);
	
	/**
	 * 查询权限集的高危总数和普通权限总数
	 * @param pmsStr
	 * @return
	 */
	public Map<String, Object> getApkPmsCount(String pmsStr);
	
	/**
	 * 分页查找权限
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<ApkPermission> getApkPms(int pageNumber, int pageSize);
	
	/**
	 * 统计权限总数
	 * @return
	 */
	public Long getApkPmsCount();
	
	/**
	 * 根据包名查找apk信息
	 * @param pkgName
	 * @return
	 */
	public PkgPermission getPkgByName(String pkgName);
	
	public List<Map<String,Object>> query(String sql ,Object[] params);
}
