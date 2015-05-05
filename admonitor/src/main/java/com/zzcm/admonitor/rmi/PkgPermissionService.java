package com.zzcm.admonitor.rmi;

public interface PkgPermissionService {
	
	/**
	 * 根据包名
	 * @param pkgName
	 * @param apkPath
	 * @return
	 */
	public String queryPkgPermission(String pkgName , String apkPath);

}