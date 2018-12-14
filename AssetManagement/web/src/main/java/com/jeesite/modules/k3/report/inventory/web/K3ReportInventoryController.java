/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.inventory.web;

import javax.servlet.http.*;

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
import com.jeesite.modules.k3.report.inventory.entity.K3ReportInventory;
import com.jeesite.modules.k3.report.inventory.service.K3ReportInventoryService;


/**
 * 即时库存Controller
 * @author Albert
 * @version 2018-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/inventory/k3ReportInventory")
public class K3ReportInventoryController extends BaseController {

	@Autowired
	private K3ReportInventoryService k3ReportInventoryService;

	@Autowired
	private UserDataPermissionUnit userDataPermissionUnit;

	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public K3ReportInventory get(String fid, boolean isNewRecord) {
		return k3ReportInventoryService.get(fid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("inventory:k3ReportInventory:view")
	@RequestMapping(value = {"list", ""})
	public String list(K3ReportInventory k3ReportInventory, Model model) {
		model.addAttribute("k3ReportInventory", k3ReportInventory);
		return "k3/report/inventory/k3ReportInventoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("inventory:k3ReportInventory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<K3ReportInventory> listData(K3ReportInventory k3ReportInventory, HttpServletRequest request, HttpServletResponse response) {
		String param=userDataPermissionUnit.getParam(request);
		k3ReportInventory.setFilterParam(param);
		Page<K3ReportInventory> page = k3ReportInventoryService.findPage(new Page<K3ReportInventory>(request, response), k3ReportInventory); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("inventory:k3ReportInventory:view")
	@RequestMapping(value = "form")
	public String form(K3ReportInventory k3ReportInventory, Model model) {
		model.addAttribute("k3ReportInventory", k3ReportInventory);
		return "k3/report/inventory/k3ReportInventoryForm";
	}

	/**
	 * 保存即时库存
	 */
	@RequiresPermissions("inventory:k3ReportInventory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated K3ReportInventory k3ReportInventory) {
		k3ReportInventoryService.save(k3ReportInventory);
		return renderResult(Global.TRUE, text("保存即时库存成功！"));
	}

	@RequiresPermissions("inventory:k3ReportInventory:export")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(K3ReportInventory k3ReportInventory, HttpServletRequest request,HttpServletResponse response) {
		FilteredRequest filteredRequest = new FilteredRequest(request);
		String param = userDataPermissionUnit.getParam(filteredRequest);
		k3ReportInventory.setFilterParam(param);
		Page<K3ReportInventory> pageRequest = new Page<K3ReportInventory>(filteredRequest,response);
		//pageRequest.setPageSize(1000000);
		pageRequest.setPageSize(2000);
		Page<K3ReportInventory> page = k3ReportInventoryService.findPage(pageRequest, k3ReportInventory);
		if (page.getList() != null && page.getList().size() > 0){
			String fileName = "即时库存_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			try(ExcelExport ee = new ExcelExport("即时库存", K3ReportInventory.class)){
				ee.setDataList(page.getList()).write(response, fileName);
			}
		}
	}
}