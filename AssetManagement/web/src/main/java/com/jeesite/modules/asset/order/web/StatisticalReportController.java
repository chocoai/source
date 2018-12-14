/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.asset.util.TimeUtils;
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
import com.jeesite.modules.asset.order.entity.StatisticalReport;
import com.jeesite.modules.asset.order.service.StatisticalReportService;

/**
 * 订单业绩统计Controller
 * @author len
 * @version 2018-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/order/statisticalReport")
public class StatisticalReportController extends BaseController {

	@Autowired
	private StatisticalReportService statisticalReportService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public StatisticalReport get(String reportCode, boolean isNewRecord) {
		return statisticalReportService.get(reportCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:statisticalReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticalReport statisticalReport, Model model) {
		statisticalReport.setDate_gte(TimeUtils.getBeginDayOfMonth());
		statisticalReport.setDate_lte(TimeUtils.getEndDayOfMonth());
		model.addAttribute("statisticalReport", statisticalReport);
		return "asset/order/statisticalReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:statisticalReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<StatisticalReport> listData(StatisticalReport statisticalReport, HttpServletRequest request, HttpServletResponse response) {
		Page<StatisticalReport> page = statisticalReportService.findPage(new Page<StatisticalReport>(request, response), statisticalReport);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:statisticalReport:view")
	@RequestMapping(value = "form")
	public String form(StatisticalReport statisticalReport, Model model) {
		model.addAttribute("statisticalReport", statisticalReport);
		return "asset/order/statisticalReportForm";
	}

	/**
	 * 保存订单业绩统计
	 */
	@RequiresPermissions("order:statisticalReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated StatisticalReport statisticalReport) {
		statisticalReportService.save(statisticalReport);
		return renderResult(Global.TRUE, text("保存订单业绩统计成功！"));
	}

	/**
	 * 导出订单业绩统计
	 */
	@RequiresPermissions("order:statisticalReport:export")
	@PostMapping(value = "exportData")
	@ResponseBody
	public void exportData(StatisticalReport statisticalReport, HttpServletRequest request, HttpServletResponse response) {
		Page<StatisticalReport> page = statisticalReportService.findPage(new Page<StatisticalReport>(request, response), statisticalReport);
		if (page.getList() != null && page.getList().size() > 0){
			for (StatisticalReport statisticalReport1 : page.getList()) {
				statisticalReport1.setPaymentAmount(Double.parseDouble(NumberUtils.formatDouble(statisticalReport1.getPaymentAmount())));
				statisticalReport1.setCustomerPrice(Double.parseDouble(NumberUtils.formatDouble(statisticalReport1.getCustomerPrice())));
			}
			String fileName = "业绩统计_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			try(ExcelExport ee = new ExcelExport("业绩统计", StatisticalReport.class)){
				ee.setDataList(page.getList()).write(response, fileName);
			}
		}
	}
}