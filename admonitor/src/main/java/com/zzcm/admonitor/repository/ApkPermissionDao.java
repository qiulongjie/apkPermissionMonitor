package com.zzcm.admonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zzcm.admonitor.entity.ApkPermission;

public interface ApkPermissionDao extends PagingAndSortingRepository<ApkPermission, Long>, JpaSpecificationExecutor<ApkPermission>{

	@Modifying
	@Query("from ApkPermission a where a.pmsName = ?1")
	List<ApkPermission> getApkPmsByName(String pmsName); 
}
