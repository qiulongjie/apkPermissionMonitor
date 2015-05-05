package com.zzcm.admonitor.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.zzcm.admonitor.entity.ApkInfo;
import com.zzcm.admonitor.service.apk.ApkService;
import com.zzcm.admonitor.util.ApkFileUtil;

/**
 * 定时扫描Apk任务类
 * 
 * @author qiulongjie
 *
 */
public class ApkScanJob {

	/**
	 * 线程池
	 */
	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ApkService apkService;

	/**
	 * 定时方法
	 */
	public void jobRun() {
		doJob();
	}

	/**
	 * 执行任务
	 */
	public void doJob() {
		taskExecutor.execute(new Runnable() {

			public void run() {
				List<String> hisList = apkService.getApkScanHis();
				List<ApkInfo> apkInfos = ApkFileUtil.getApkFileList(hisList);
				if (apkInfos != null && apkInfos.size() > 0) {
					System.out.println("find " + apkInfos.size() + " files");

					apkService.addApkInfos(apkInfos);

				} 
			}
			
		});
	}
}
