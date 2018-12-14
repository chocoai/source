/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.material.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.k3.report.filter.common.FilteredRequest;
import com.jeesite.modules.userroleconfig.UserDataPermissionUnit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.k3.report.material.entity.K3ReportMaterial;
import com.jeesite.modules.k3.report.material.service.K3ReportMaterialService;

/**
 * 物料Controller
 * @author Albert
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/material/k3ReportMaterial")
public class K3ReportMaterialController extends BaseController {

	@Autowired
	private K3ReportMaterialService k3ReportMaterialService;

	@Autowired
	private UserDataPermissionUnit userDataPermissionUnit;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public K3ReportMaterial get(String materialCode, boolean isNewRecord) {
		return k3ReportMaterialService.get(materialCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("material:k3ReportMaterial:view")
	@RequestMapping(value = {"list", ""})
	public String list(K3ReportMaterial k3ReportMaterial, Model model) {
		model.addAttribute("k3ReportMaterial", k3ReportMaterial);
		return "k3/report/material/k3ReportMaterialList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("material:k3ReportMaterial:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<K3ReportMaterial> listData(K3ReportMaterial k3ReportMaterial, HttpServletRequest request, HttpServletResponse response) {
		String param = userDataPermissionUnit.getParam(request);
		k3ReportMaterial.setFilterParam(param);
		Page<K3ReportMaterial> page = k3ReportMaterialService.findPage(new Page<K3ReportMaterial>(request, response), k3ReportMaterial);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("material:k3ReportMaterial:view")
	@RequestMapping(value = "form")
	public String form(K3ReportMaterial k3ReportMaterial, Model model) {
		model.addAttribute("k3ReportMaterial", k3ReportMaterial);
		return "k3/report/material/k3ReportMaterialForm";
	}

	/**
	 * 保存物料
	 */
	@RequiresPermissions("material:k3ReportMaterial:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated K3ReportMaterial k3ReportMaterial) {
		k3ReportMaterialService.save(k3ReportMaterial);
		return renderResult(Global.TRUE, text("保存物料成功！"));
	}

	/**
	 * 导出物料
	 */
	@RequiresPermissions("material:k3ReportMaterial:export")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(K3ReportMaterial k3ReportMaterial, HttpServletRequest request,HttpServletResponse response) {
		FilteredRequest filteredRequest = new FilteredRequest(request);
		String param = userDataPermissionUnit.getParam(filteredRequest);
		k3ReportMaterial.setFilterParam(param);
		Page<K3ReportMaterial> pageRequest = new Page<K3ReportMaterial>(filteredRequest,response);
		pageRequest.setPageSize(2000);
		Page<K3ReportMaterial> page = k3ReportMaterialService.findPage(pageRequest, k3ReportMaterial);
		if (page.getList() != null && page.getList().size() > 0){
			String fileName = "物料_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			try(ExcelExport ee = new ExcelExport("物料", K3ReportMaterial.class)){
				ee.setDataList(page.getList()).write(response, fileName);
			}
		}
	}
	
}