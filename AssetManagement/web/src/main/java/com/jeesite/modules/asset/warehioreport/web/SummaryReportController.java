/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.asset.warehioreport.entity.SummaryReport;
import com.jeesite.modules.asset.warehioreport.service.SummaryReportService;

/**
 * 耗材仓库进出明细表Controller
 * @author czy
 * @version 2018-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/warehioreport/summaryReport")
public class SummaryReportController extends BaseController {

	@Autowired
	private SummaryReportService summaryReportService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SummaryReport get(String id, boolean isNewRecord) {
		return summaryReportService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("warehioreport:summaryReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(SummaryReport summaryReport, Model model) {
		model.addAttribute("summaryReport", summaryReport);
		return "asset/warehioreport/summaryReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("warehioreport:summaryReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SummaryReport> listData(SummaryReport summaryReport, HttpServletRequest request, HttpServletResponse response) {
		Page<SummaryReport> page = summaryReportService.findPage(new Page<SummaryReport>(request, response), summaryReport);
		page.setCount(page.getList().size());
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("warehioreport:summaryReport:view")
	@RequestMapping(value = "form")
	public String form(SummaryReport summaryReport, Model model) {
		model.addAttribute("summaryReport", summaryReport);
		return "asset/warehioreport/summaryReportForm";
	}

	/**
	 * 保存耗材仓库进出汇总表
	 */
	@RequiresPermissions("warehioreport:summaryReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SummaryReport summaryReport) {
		summaryReportService.save(summaryReport);
		return renderResult(Global.TRUE, "保存耗材仓库进出汇总表成功！");
	}
	
}