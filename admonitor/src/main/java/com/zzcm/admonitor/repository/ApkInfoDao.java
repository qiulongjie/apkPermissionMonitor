package com.zzcm.admonitor.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zzcm.admonitor.entity.ApkInfo;

public interface ApkInfoDao extends PagingAndSortingRepository<ApkInfo, Long>, JpaSpecificationExecutor<ApkInfo>{

}
