package com.zzcm.admonitor.rmi;

/**
 * 权限详情信息
 * @author Administrator
 *
 */
public class PkgPermissionInfo {

	/**
	 * 权限
	 */
	private String permission;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 是否高危
	 */
	private String highRis;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHighRis() {
		return highRis;
	}

	public void setHighRis(String highRis) {
		this.highRis = highRis;
	}
	
}
