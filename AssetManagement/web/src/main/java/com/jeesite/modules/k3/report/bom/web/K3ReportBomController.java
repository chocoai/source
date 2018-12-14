/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.bom.web;

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
import com.jeesite.modules.k3.report.bom.entity.K3ReportBom;
import com.jeesite.modules.k3.report.bom.service.K3ReportBomService;

/**
 * 物料清单Controller
 * @author Albert
 * @version 2018-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/bom/k3ReportBom")
public class K3ReportBomController extends BaseController {

	@Autowired
	private K3ReportBomService k3ReportBomService;

	@Autowired
	private UserDataPermissionUnit userDataPermissionUnit;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public K3ReportBom get(String fid, boolean isNewRecord) {
		return k3ReportBomService.get(fid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bom:k3ReportBom:view")
	@RequestMapping(value = {"list", ""})
	public String list(K3ReportBom k3ReportBom, Model model) {
		model.addAttribute("k3ReportBom", k3ReportBom);
		return "k3/report/bom/k3ReportBomList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bom:k3ReportBom:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<K3ReportBom> listData(K3ReportBom k3ReportBom, HttpServletRequest request, HttpServletResponse response) {
		String param=userDataPermissionUnit.getParam(request);
		k3ReportBom.setFilterParam(param);
		Page<K3ReportBom> page = k3ReportBomService.findPage(new Page<K3ReportBom>(request, response), k3ReportBom); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bom:k3ReportBom:view")
	@RequestMapping(value = "form")
	public String form(K3ReportBom k3ReportBom, Model model) {
		model.addAttribute("k3ReportBom", k3ReportBom);
		return "k3/report/bom/k3ReportBomForm";
	}

	/**
	 * 保存物料清单
	 */
	@RequiresPermissions("bom:k3ReportBom:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated K3ReportBom k3ReportBom) {
		k3ReportBomService.save(k3ReportBom);
		return renderResult(Global.TRUE, text("保存物料清单成功！"));
	}
	
	/**
	 * 删除物料清单
	 */
	@RequiresPermissions("bom:k3ReportBom:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(K3ReportBom k3ReportBom) {
		k3ReportBomService.delete(k3ReportBom);
		return renderResult(Global.TRUE, text("删除物料清单成功！"));
	}

	@RequiresPermissions("bom:k3ReportBom:export")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(K3ReportBom k3ReportBom, HttpServletRequest request,HttpServletResponse response) {
		FilteredRequest filteredRequest = new FilteredRequest(request);
		String param = userDataPermissionUnit.getParam(filteredRequest);
		k3ReportBom.setFilterParam(param);
		Page<K3ReportBom> pageRequest = new Page<K3ReportBom>(filteredRequest,response);
		pageRequest.setPageSize(1000000);
		Page<K3ReportBom> page = k3ReportBomService.findPage(pageRequest, k3ReportBom);
		if (page.getList() != null && page.getList().size() > 0){
			String fileName = "物料清单_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			try(ExcelExport ee = new ExcelExport("物料清单", K3ReportBom.class)){
				ee.setDataList(page.getList()).write(response, fileName);
			}
		}
	}
	
}