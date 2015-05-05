package com.zzcm.admonitor.rmi;

import java.util.ArrayList;
import java.util.List;

/**
 * 包权限信息
 * 
 * @author Administrator
 *
 */
public class PkgPermission {

	/**
	 * 包名
	 */
	private String pkgName;

	/**
	 * 权限总数
	 */
	private int totalCount;

	/**
	 * 普通权限数
	 */
	private int normalCount;

	/**
	 * 高风险权限数
	 */
	private int highRiskCount;

	/**
	 * 未知权限数
	 */
	private int unknowCount;

	/**
	 * 权限详情
	 */
	private List<PkgPermissionInfo> permissionInfos = new ArrayList<PkgPermissionInfo>();

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}

	public int getHighRiskCount() {
		return highRiskCount;
	}

	public void setHighRiskCount(int highRiskCount) {
		this.highRiskCount = highRiskCount;
	}

	public int getUnknowCount() {
		return unknowCount;
	}

	public void setUnknowCount(int unknowCount) {
		this.unknowCount = unknowCount;
	}

	public List<PkgPermissionInfo> getPermissionInfos() {
		return permissionInfos;
	}

	public void setPermissionInfos(List<PkgPermissionInfo> permissionInfos) {
		this.permissionInfos = permissionInfos;
	}

}