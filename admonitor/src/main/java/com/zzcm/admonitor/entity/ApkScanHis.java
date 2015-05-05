package com.zzcm.admonitor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_apk_scan_his")
public class ApkScanHis extends IdEntity{

	private String apkName;
	private Date scanTime;
	
	public ApkScanHis() {
	}
	
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
}
