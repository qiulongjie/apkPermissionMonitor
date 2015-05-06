package com.zzcm.admonitor.service.apk;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zzcm.admonitor.entity.ApkInfo;
import com.zzcm.admonitor.entity.ApkPermission;
import com.zzcm.admonitor.entity.ApkScanHis;
import com.zzcm.admonitor.repository.ApkDao;
import com.zzcm.admonitor.repository.ApkInfoDao;
import com.zzcm.admonitor.repository.ApkPermissionDao;
import com.zzcm.admonitor.repository.ApkScanHisDao;
import com.zzcm.admonitor.rmi.PkgPermission;
import com.zzcm.admonitor.rmi.PkgPermissionInfo;
import com.zzcm.admonitor.util.ApkUtil;

@Component
@Transactional
public class ApkService {
	
	@Resource
	private ApkDao apkDao;
	
	@Autowired
	private ApkInfoDao apkInfoDao;
	
	@Autowired
	private ApkPermissionDao apkPermissionDao;
	
	@Autowired
	private ApkScanHisDao apkScanHisDao;
	
	// ************************   APK Start ******************
	
	/**
	 * 获取apk信息
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<ApkInfo> getApkInfos(int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		//System.out.println(apkInfoDao.getClass().getName());
		return apkInfoDao.findAll(pageRequest);
	}
	
	/**
	 * 添加apk信息
	 * @param apkInfoList
	 */
	public void addApkInfos(List<ApkInfo> apkInfoList) {
		List<ApkScanHis> apkScanHis = getScanHis(apkInfoList);
		apkScanHisDao.save(apkScanHis);
		
		for(ApkInfo info : apkInfoList){
			setApkPmsCount(info);//设置权限数据
		}
		apkDao.addApkInfos(apkInfoList);
		//apkInfoDao.save(apkInfoList);
	}
	
	/**
	 * 从apk信息中获取扫描历史信息
	 * @param apkInfoList
	 * @return
	 */
	private List<ApkScanHis> getScanHis(List<ApkInfo> apkInfoList) {
		List<ApkScanHis> list = new ArrayList<ApkScanHis>();
		Date curDate = new Date();
		for(ApkInfo info : apkInfoList){
			ApkScanHis his = new ApkScanHis();
			his.setApkName(info.getApkName());
			his.setScanTime(curDate);
			list.add(his);
		}
		return list;
	}

	/**
	 * 统计apk各权限的总数 包含高位权限 普通权限 未知权限的总数
	 * @param info
	 */
	private void setApkPmsCount(ApkInfo info){
		String pms = info.getPermissions();
		int pmsDangerCount = 0;
		int pmsNormalCount = 0;
		int pmsUnknowCount = 0;
		if(pms != null && pms.length() != 0){
			pms = "'"+pms.replaceAll(",", "','")+"'";
			Map<String, Object> map = apkDao.getApkPmsCount(pms);
			pmsDangerCount = Integer.parseInt( map.get("pms_danger_count").toString());
			pmsNormalCount = Integer.parseInt( map.get("pms_normal_count").toString());
		}
		pmsUnknowCount = info.getPmsCount() - pmsDangerCount - pmsNormalCount;
		info.setPmsDangerCount(pmsDangerCount);
		info.setPmsNormalCount(pmsNormalCount);
		info.setPmsUnknowCount(pmsUnknowCount);
	}
	
	/**
	 * 从权限集合列表中提取权限名
	 * @param pms
	 * @return
	 */
	private List<String> getPmsStr(List<ApkPermission> pms){
		List<String> list = new ArrayList<String>();
		for(ApkPermission p : pms){
			list.add(p.getPmsName());
		}
		return list;
	}
	/**
	 * 获取apk详细信息
	 * @param id
	 * @return
	 */
	public ApkInfo getApkInfo(Long id) {
		ApkInfo info = apkInfoDao.findOne(id);
		String pms = info.getPermissions();
		if (pms != null && pms.length() != 0) {
			// 全部 权限数组 
			String[] pmsArr = pms.split(",");
			pms = "'" + pms.replaceAll(",", "','") + "'";
			//在数据库中能查询到的权限
			List<ApkPermission> list = apkDao.getApkPms(pms);
			List<String> pmsTemp = getPmsStr(list);
			// 在数据库查中不存在的权限封装成-->未知权限
			for (String p : pmsArr) {
				if(!pmsTemp.contains(p.trim())){
					// 封装未知权限
					ApkPermission ap = new ApkPermission();
					ap.setPmsName(p);
					ap.setId(1000L);
					ap.setDanger_Flag(3);
					ap.setName("未知权限");
					ap.setPmsDesc("未知权限");
					list.add(ap); // 添加到权限集合中 
				}
			}
			info.setApkPermissions(list);
		}
		return info;
	}
	
	// ************************   APK END ******************

	//**************************** apk permission **********
	
	/**
	 * 返回所有apk权限
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<ApkPermission> getApkPermissions(int pageNumber, int pageSize,
			String sortType) {
//		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
//		return apkPermissionDao.findAll(pageRequest);
		//com.zzcm.admonitor.service.apk.PageImpl<T>
		return new PageImpl<ApkPermission>(apkDao.getApkPms((pageNumber-1)*pageSize, pageSize), 
				                           apkDao.getApkPmsCount(), 
				                           pageNumber-1, 
				                           pageSize);
	}
	
	/**
	 * 保存或更新apk权限
	 */
	public boolean saveOrUpdApkPms(ApkPermission apkPermission) {
		// 检查该权限是否已经存在？？
		List<ApkPermission> pms = apkPermissionDao.getApkPmsByName(apkPermission.getPmsName());
		if( pms != null && pms.size()>0 ){
			return false; //如果已经存在了
		}
		apkPermissionDao.save(apkPermission);
		return true;
	}
	
	/**
	 * 删apk权限
	 */
	public void delApkPms(Long id) {
		apkPermissionDao.delete(id);
	}
	
	/**
	 * 获取apk权限
	 */
	public ApkPermission getApkPms(Long id) {
		return apkPermissionDao.findOne(id);
	}
	
	//**************************** apk permission **********
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} 
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 获取apk扫描历史
	 * @return
	 */
	public List<String> getApkScanHis() {
		return apkScanHisDao.getApkScanHisNames();
	}

	/**
	 * 根据包名查找apk信息
	 * @param apkName
	 * @param apkFilePath apk文件路径 如果在数据库中没有找到apkName，就扫描 解析这个文件并保存到数据库中
	 * @return
	 */
	public PkgPermission getPkgPermission(String apkName,String apkFilePath) {
		PkgPermission pkg = new PkgPermission();
		// 获取详细权限
		String sql = " select                         "+
					" apk_name pkgName,           "+            
					" permissions,                   "+      
					" pms_count totalCount,          "+                 
					" pms_danger_count highRiskCount,"+                    
					" pms_normal_count normalCount,  "+                  
					" pms_unknow_count unknowCount   "+
					" from tb_apk_info               "+
					" where apk_name = ?          ";
		List<Map<String, Object>> apk = apkDao.query(sql, new Object[]{apkName});
		if(!apk.isEmpty()){
			Map<String, Object> map = apk.get(0);
			
			pkg.setPkgName(String.valueOf(map.get("pkgName")));
			pkg.setTotalCount(Integer.valueOf(String.valueOf(map.get("totalCount"))));
			pkg.setHighRiskCount(Integer.valueOf(String.valueOf(map.get("highRiskCount"))));
			pkg.setNormalCount(Integer.valueOf(String.valueOf(map.get("normalCount"))));
			pkg.setUnknowCount(Integer.valueOf(String.valueOf(map.get("unknowCount"))));
			
			String pms = String.valueOf(map.get("permissions"));
			
			pkg.setPermissionInfos(getPermissionInfos(pms));
		}else{
			//如果在数据库中没有 并且有apk文件路径
			
			// 如果是网路url路径
			if(apkFilePath != null && apkFilePath.trim().length() > 0 && apkFilePath.startsWith("http:") && apkFilePath.endsWith(".apk")){
				ApkInfo apkInfo = ApkUtil.getApkInfoByUrl(apkFilePath);
				if( apkInfo == null ){
					return pkg;
				}
				List<ApkInfo> apkInfos = new ArrayList<ApkInfo>(1);
				apkInfos.add(apkInfo);
				addApkInfos(apkInfos);
				
				pkg.setPkgName(apkInfo.getApkName());
				pkg.setTotalCount(apkInfo.getPmsCount());
				pkg.setHighRiskCount(apkInfo.getPmsDangerCount());
				pkg.setNormalCount(apkInfo.getPmsNormalCount());
				pkg.setUnknowCount(apkInfo.getPmsUnknowCount());
				
				pkg.setPermissionInfos(getPermissionInfos(apkInfo.getPermissions()));
			}
			
			// 如果是本地磁盘路径
			if( apkFilePath != null && apkFilePath.trim().length() > 0 && apkFilePath.endsWith(".apk")){
				File file = new File(apkFilePath);
				// 文件存在
				if(file.exists()){
					ApkInfo apkInfo = ApkUtil.getApkInfo(file);
					if( apkInfo == null ){
						return pkg;
					}
					List<ApkInfo> apkInfos = new ArrayList<ApkInfo>(1);
					apkInfos.add(apkInfo);
					addApkInfos(apkInfos);
					
					pkg.setPkgName(apkInfo.getApkName());
					pkg.setTotalCount(apkInfo.getPmsCount());
					pkg.setHighRiskCount(apkInfo.getPmsDangerCount());
					pkg.setNormalCount(apkInfo.getPmsNormalCount());
					pkg.setUnknowCount(apkInfo.getPmsUnknowCount());
					
					pkg.setPermissionInfos(getPermissionInfos(apkInfo.getPermissions()));
				}
			}
		}
		return pkg;
	}
	
	/**
	 * 获取权限信息
	 * @param pms 权限字符串
	 * @return
	 */
	private List<PkgPermissionInfo> getPermissionInfos(String pms){
		// 全部 权限数组 
		String[] pmsArr = pms.split(",");
		pms = "'" + pms.replaceAll(",", "','") + "'";
		//在数据库中能查询到的权限
		List<ApkPermission> apkPmsList = apkDao.getApkPms(pms);
		List<PkgPermissionInfo> permissionInfos = new ArrayList<PkgPermissionInfo>();
		List<String> tempL = new ArrayList<String>();
		for(ApkPermission ap : apkPmsList){
			tempL.add(ap.getPmsName());
			PkgPermissionInfo pp =  new PkgPermissionInfo();
			pp.setPermission(ap.getPmsName());
			pp.setName(ap.getName());
			pp.setDesc(ap.getPmsDesc());
			pp.setHighRis(ap.getDanger_Flag() == 0 ? "no":"yes");
			permissionInfos.add(pp);
		}
		// 组装在数据库中没有查到的权限
		for(String p : pmsArr){
			if(!tempL.contains(p)){
				PkgPermissionInfo pp =  new PkgPermissionInfo();
				pp.setPermission(p);
				pp.setName("");
				pp.setDesc("");
				pp.setHighRis("");
				permissionInfos.add(pp);
			}
		}
		return permissionInfos;
	}

}
