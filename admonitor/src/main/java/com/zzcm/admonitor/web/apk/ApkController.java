package com.zzcm.admonitor.web.apk;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zzcm.admonitor.entity.ApkInfo;
import com.zzcm.admonitor.entity.ApkPermission;
import com.zzcm.admonitor.rmi.PkgPermissionService;
import com.zzcm.admonitor.service.apk.ApkService;

@Controller
@RequestMapping(value = "/apk")
public class ApkController {

	private static final String PAGE_SIZE = "20";
	
	@Autowired
	private ApkService apkService;
	
	@Resource
	private PkgPermissionService pkgPermissionService;

	///********************  APK Start **********************
	@RequestMapping(value = "apkList", method = RequestMethod.GET)
	public String apkList(Model model,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType ){
		Page<ApkInfo> apkInfos = apkService.getApkInfos(pageNumber, pageSize, sortType);
		model.addAttribute("apkInfos", apkInfos);
		return "apk/apkList";
	}
	
	@RequestMapping(value = "apkT", method = RequestMethod.GET)
	@ResponseBody
	public String apkT(String pkgName,String apkPath){
		String data = pkgPermissionService.queryPkgPermission(pkgName, apkPath);
		System.out.println(data);
		return data;
	}
	
	@RequestMapping(value = "showPmsDetail/{id}", method = RequestMethod.GET)
	public String showPmsDetail(@PathVariable("id") Long id,Model model){
		ApkInfo apkInfo = apkService.getApkInfo(id);
		model.addAttribute("apkInfo", apkInfo);
		return "apk/showPmsDetail";
	}
	
	///********************  APK END *************************
	
	@RequestMapping(method = RequestMethod.GET)
	public String apkPermissionList(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
//		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
//				request, "search_");

		Page<ApkPermission> apkPermissions = apkService.getApkPermissions(pageNumber, pageSize, sortType);

		model.addAttribute("apkPermissions", apkPermissions);
		model.addAttribute("sortType", sortType);
//		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
//		model.addAttribute("searchParams", Servlets
//				.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "apk/apkPermissionList";
	}
	
	/**
	 * 转到添加apk权限界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "createPms", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("apkPms", new ApkPermission());
		model.addAttribute("action", "create");
		return "apk/apkPmsForm";
	}
	
	/**
	 * 添加apk权限
	 * @param permission
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ApkPermission permission, RedirectAttributes redirectAttributes) {
		boolean flag = apkService.saveOrUpdApkPms(permission);
		if(flag){
			redirectAttributes.addFlashAttribute("message", "添加"+permission.getPmsName()+"权限成功");
		}else{
			redirectAttributes.addFlashAttribute("error", permission.getPmsName()+"权限已经存在了哦!");
		}
		return "redirect:/apk/";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("apkPms", apkService.getApkPms(id));
		model.addAttribute("action", "update");
		return "apk/apkPmsForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("apkPms") ApkPermission apkPms, RedirectAttributes redirectAttributes) {
		apkService.saveOrUpdApkPms(apkPms);
		redirectAttributes.addFlashAttribute("message", "更新apk权限成功");
		return "redirect:/apk/";
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delPms(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		apkService.delApkPms(id);
		redirectAttributes.addFlashAttribute("message", "删除apk权限成功");
		return "redirect:/apk/";
	}
}
