package com.zzcm.admonitor.rmi;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.zzcm.admonitor.service.apk.ApkService;

@Service("pkgPermissionServiceImpl")
public class PkgPermissionServiceImpl implements PkgPermissionService {

	@Resource
	private ApkService apkService;
	
	@Override
	public String queryPkgPermission(String pkgName,String apkPath) {
		PkgPermission pkgPms = apkService.getPkgPermission(pkgName,apkPath);
		Gson gson = new Gson();
		String json = gson.toJson(pkgPms);
		return json;
	}

}
