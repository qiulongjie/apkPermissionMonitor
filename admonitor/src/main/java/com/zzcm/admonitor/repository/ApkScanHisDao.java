package com.zzcm.admonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zzcm.admonitor.entity.ApkScanHis;

public interface ApkScanHisDao extends PagingAndSortingRepository<ApkScanHis, Long>, JpaSpecificationExecutor<ApkScanHis>{
	
	@Modifying
	@Query("select apkName from ApkScanHis ")
	public List<String> getApkScanHisNames();

}
