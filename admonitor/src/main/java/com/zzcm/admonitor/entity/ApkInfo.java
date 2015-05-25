package com.zzcm.admonitor.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_apk_info")
public class ApkInfo extends IdEntity{

	private String versionCode;
	private String versionName;
	private String apkPackage;
	private String minSdkVersion;
	private String apkName;
	private Long apkSize = 0L;
	private Date lastModify = new Date();
	private String permissions;
	private Integer pmsCount;
	private Integer pmsDangerCount;
	private Integer pmsNormalCount;
	private Integer pmsUnknowCount;
	//private Set<ApkPermission> apkPermissions = new LinkedHashSet<ApkPermission>();
	private List<ApkPermission> apkPermissions = new ArrayList<ApkPermission>();

	public ApkInfo() {
	}
	
	@Transient
	@JsonIgnore
	public List<ApkPermission> getApkPermissions() {
		return apkPermissions;
	}

	public void setApkPermissions(List<ApkPermission> apkPermissions) {
		this.apkPermissions = apkPermissions;
	}
	
	public Integer getPmsCount() {
		return pmsCount;
	}

	public void setPmsCount(Integer pmsCount) {
		this.pmsCount = pmsCount;
	}

	public Integer getPmsDangerCount() {
		return pmsDangerCount;
	}

	public void setPmsDangerCount(Integer pmsDangerCount) {
		this.pmsDangerCount = pmsDangerCount;
	}
	
	public Integer getPmsNormalCount() {
		return pmsNormalCount;
	}

	public void setPmsNormalCount(Integer pmsNormalCount) {
		this.pmsNormalCount = pmsNormalCount;
	}

	public Integer getPmsUnknowCount() {
		return pmsUnknowCount;
	}

	public void setPmsUnknowCount(Integer pmsUnknowCount) {
		this.pmsUnknowCount = pmsUnknowCount;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkPackage() {
		return apkPackage;
	}

	public void setApkPackage(String apkPackage) {
		this.apkPackage = apkPackage;
	}

	public String getMinSdkVersion() {
		return minSdkVersion;
	}

	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public Long getApkSize() {
		return apkSize;
	}

	public void setApkSize(Long apkSize) {
		this.apkSize = apkSize;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS", timezone = "GMT+08:00")
	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	@Override
	public String toString() {
		return " ==apkinfo== ["+"versionCode="+this.versionCode+
								"versionName="+this.versionName+
								"apkPackage="+this.apkPackage+
								"minSdkVersion="+this.minSdkVersion+
								"apkName="+this.apkName+
								"apkSize="+this.apkSize+
								"lastModify="+this.lastModify+
								"permissions="+this.permissions+
								"pmsCount="+this.pmsCount+
								"pmsDangerCount="+this.pmsDangerCount+
								"pmsNormalCount="+this.pmsNormalCount+
								"pmsUnknowCount="+this.pmsUnknowCount+
								"apkPermissions="+this.apkPermissions+"]";
	}

	// 多对多
//	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@JoinTable(name = "tb_apk_permission_relation", joinColumns = { @JoinColumn(name ="apk_id" )}, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
//	public Set<ApkPermission> getApkPermissions() {
//		return apkPermissions;
//	}
//
//	public void setApkPermissions(Set<ApkPermission> apkPermissions) {
//		this.apkPermissions = apkPermissions;
//	}
	
}
