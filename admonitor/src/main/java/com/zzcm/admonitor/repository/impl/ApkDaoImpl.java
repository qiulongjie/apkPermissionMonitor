package com.zzcm.admonitor.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.zzcm.admonitor.entity.ApkInfo;
import com.zzcm.admonitor.entity.ApkPermission;
import com.zzcm.admonitor.repository.ApkDao;
import com.zzcm.admonitor.rmi.PkgPermission;

@Repository
public class ApkDaoImpl implements ApkDao {
	
	@Resource 
	private JdbcTemplate jdbcTemplate;

	//批量添加apk信息到数据库
	public void addApkInfos(final List<ApkInfo> apkInfoList) {
		String sql = " insert into tb_apk_info (              "+
					"    apk_name,                           "+
					" 	apk_size ,                           "+
					" 	apk_package ,                        "+
					" 	last_modify ,                        "+
					" 	min_sdk_version,                     "+
					" 	version_name,                        "+
					" 	version_code,                         "+
					" 	permissions,                         "+ 
					"   pms_count,                           "+ 
					"   pms_danger_count,                    "+ 
					"   pms_normal_count,                    "+ 
					"   pms_unknow_count )                   "+ 
					"   values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, apkInfoList.get(index).getApkName());
				ps.setLong(2, apkInfoList.get(index).getApkSize());
				ps.setString(3, apkInfoList.get(index).getApkPackage());
				ps.setTimestamp(4, new java.sql.Timestamp(apkInfoList.get(index).getLastModify().getTime()));
				ps.setString(5, apkInfoList.get(index).getMinSdkVersion());
				ps.setString(6, apkInfoList.get(index).getVersionName());
				ps.setString(7, apkInfoList.get(index).getVersionCode());
				ps.setString(8, apkInfoList.get(index).getPermissions());
				ps.setInt(9, apkInfoList.get(index).getPmsCount()==null?0:apkInfoList.get(index).getPmsCount());
				ps.setInt(10, apkInfoList.get(index).getPmsDangerCount()==null?0:apkInfoList.get(index).getPmsDangerCount());
				ps.setInt(11, apkInfoList.get(index).getPmsNormalCount()==null?0:apkInfoList.get(index).getPmsNormalCount());
				ps.setInt(12, apkInfoList.get(index).getPmsUnknowCount()==null?0:apkInfoList.get(index).getPmsUnknowCount());
			}
			
			@Override
			public int getBatchSize() {
				return apkInfoList.size();
			}
		});
	}
	
	// 查询权限集的高危总数和普通权限总数
	public Map<String, Object> getApkPmsCount(String pmsStr){
		String sql = " select sum(case when danger_flag=1 then 1 else 0 end) pms_danger_count , \n"+
					"        sum(case when danger_flag=0 then 1 else 0 end) pms_normal_count   \n"+
					" from tb_apk_permission                                                   \n"+
					" where pms_name in ("+pmsStr+")";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	//根据权限集查找权限
	public List<ApkPermission> getApkPms(String pmsStr){
		String sql = "select id,pms_name,name,pms_desc,danger_flag from tb_apk_permission where pms_name in ("+pmsStr+")";
		List<ApkPermission> apkPermissions = jdbcTemplate.query(sql, new RowMapper<ApkPermission>(){

			@Override
			public ApkPermission mapRow(ResultSet rs, int index) throws SQLException {
				ApkPermission pms = new ApkPermission();
				pms.setId(rs.getLong(1));
				pms.setPmsName(rs.getString(2));
				pms.setName(rs.getString(3));
				pms.setPmsDesc(rs.getString(4));
				pms.setDanger_Flag(rs.getInt(5));
				return pms;
			}
			
		});
		return apkPermissions;
	}
	
	//分页查找权限
	public List<ApkPermission> getApkPms(int pageNumber, int pageSize){
		String sql = "select id,pms_name,name,pms_desc,danger_flag from tb_apk_permission limit ?,?";
		List<ApkPermission> apkPermissions = jdbcTemplate.query(sql,new Object[]{pageNumber,pageSize}, new RowMapper<ApkPermission>(){
			
			@Override
			public ApkPermission mapRow(ResultSet rs, int index) throws SQLException {
				ApkPermission pms = new ApkPermission();
				pms.setId(rs.getLong(1));
				pms.setPmsName(rs.getString(2));
				pms.setName(rs.getString(3));
				pms.setPmsDesc(rs.getString(4));
				pms.setDanger_Flag(rs.getInt(5));
				return pms;
			}
			
		});
		return apkPermissions;
	}

	public Long getApkPmsCount(){
		String sql = "select count(1) cnt from tb_apk_permission";
		return getCount(sql);
	}
	
	@SuppressWarnings("deprecation")
	public Long getCount(String sql){
		return jdbcTemplate.queryForLong(sql);
	}

	//根据包名查找apk信息
	@Override
	public PkgPermission getPkgByName(String pkgName) {
		
		return null;
	}
	
	public List<Map<String,Object>> query(String sql ,Object[] params){
		return jdbcTemplate.queryForList(sql, params);
	}
}
