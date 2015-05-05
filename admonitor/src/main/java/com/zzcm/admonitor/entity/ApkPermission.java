/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzcm.admonitor.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_apk_permission")
public class ApkPermission extends IdEntity {
	private String pmsName;
	private String name;
	private String pmsDesc;
	private int danger_Flag;

	public ApkPermission() {
	}

	public ApkPermission(Long id) {
		this.id = id;
	}

	public String getPmsName() {
		return pmsName;
	}

	public void setPmsName(String pmsName) {
		this.pmsName = pmsName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPmsDesc() {
		return pmsDesc;
	}

	public void setPmsDesc(String pmsDesc) {
		this.pmsDesc = pmsDesc;
	}

	public int getDanger_Flag() {
		return danger_Flag;
	}

	public void setDanger_Flag(int danger_Flag) {
		this.danger_Flag = danger_Flag;
	}

}