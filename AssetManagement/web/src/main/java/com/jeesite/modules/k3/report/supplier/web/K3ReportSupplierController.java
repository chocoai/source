/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.supplier.web;

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
import com.jeesite.modules.k3.report.supplier.entity.K3ReportSupplier;
import com.jeesite.modules.k3.report.supplier.service.K3ReportSupplierService;

/**
 * 供应商Controller
 * @author Albert
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/supplier/k3ReportSupplier")
public class K3ReportSupplierController extends BaseController {

	@Autowired
	private K3ReportSupplierService k3ReportSupplierService;

	@Autowired
	private UserDataPermissionUnit userDataPermissionUnit;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public K3ReportSupplier get(String supplierCode, boolean isNewRecord) {
		return k3ReportSupplierService.get(supplierCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("supplier:k3ReportSupplier:view")
	@RequestMapping(value = {"list", ""})
	public String list(K3ReportSupplier k3ReportSupplier, Model model) {
		model.addAttribute("k3ReportSupplier", k3ReportSupplier);
		return "k3/report/supplier/k3ReportSupplierList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("supplier:k3ReportSupplier:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<K3ReportSupplier> listData(K3ReportSupplier k3ReportSupplier, HttpServletRequest request, HttpServletResponse response) {
		String param = userDataPermissionUnit.getParam(request);
		k3ReportSupplier.setFilterParam(param);
		Page<K3ReportSupplier> page = k3ReportSupplierService.findPage(new Page<K3ReportSupplier>(request, response), k3ReportSupplier);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("supplier:k3ReportSupplier:view")
	@RequestMapping(value = "form")
	public String form(K3ReportSupplier k3ReportSupplier, Model model) {
		model.addAttribute("k3ReportSupplier", k3ReportSupplier);
		return "k3/report/supplier/k3ReportSupplierForm";
	}

	/**
	 * 保存供应商
	 */
	@RequiresPermissions("supplier:k3ReportSupplier:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated K3ReportSupplier k3ReportSupplier) {
		k3ReportSupplierService.save(k3ReportSupplier);
		return renderResult(Global.TRUE, text("保存供应商成功！"));
	}

	/**
	 * 导出供应商
	 */
	@RequiresPermissions("supplier:k3ReportSupplier:export")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(K3ReportSupplier k3ReportSupplier, HttpServletRequest request,HttpServletResponse response) {
		FilteredRequest filteredRequest = new FilteredRequest(request);
		String param = userDataPermissionUnit.getParam(filteredRequest);
		k3ReportSupplier.setFilterParam(param);
		Page<K3ReportSupplier> pageRequest = new Page<K3ReportSupplier>(filteredRequest,response);
		pageRequest.setPageSize(2000);
		Page<K3ReportSupplier> page = k3ReportSupplierService.findPage(pageRequest, k3ReportSupplier);
		if (page.getList() != null && page.getList().size() > 0){
			String fileName = "供应商_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			try(ExcelExport ee = new ExcelExport("供应商", K3ReportSupplier.class)){
				ee.setDataList(page.getList()).write(response, fileName);
			}
		}
	}
	
}