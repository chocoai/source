/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables_report.web;

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
import com.jeesite.modules.asset.consumables_report.entity.ConsumablesReport;
import com.jeesite.modules.asset.consumables_report.service.ConsumablesReportService;

import java.util.LinkedList;
import java.util.List;

/**
 * consumables_reportController
 * @author Mclaran
 * @version 2018-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/consumables_report/consumablesReport")
public class ConsumablesReportController extends BaseController {

	@Autowired
	private ConsumablesReportService consumablesReportService;

	/**
	 * 获取数据
	 */
	/*@ModelAttribute
	public ConsumablesReport get(String id, boolean isNewRecord) {
		return consumablesReportService.get(id, isNewRecord);
	}
	*/
	/**
	 * 查询列表
	 */
	@RequiresPermissions("consumables_report:consumablesReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ConsumablesReport consumablesReport, Model model) {

		//System.out.println(list.get(0).getWarehouseName());
		model.addAttribute("consumablesReport", consumablesReport);
		return "asset/consumables_report/consumablesReportList";
	}

	/*@RequiresPermissions("consumables_report:consumablesReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<ConsumablesReport> listData(ConsumablesReport consumablesReport, HttpServletRequest request, HttpServletResponse response){
		ConsumablesReport cons = new ConsumablesReport();
		Integer coumt = consumablesReportService.findCount();
		List<ConsumablesReport> list = consumablesReportService.findList(consumablesReport);
		cons.setStock(list.get(0).getStockSum());
		cons.setStockAtm(list.get(0).getStockAtmSum());
		list.add(cons);
		//request.setAttribute("coumt",coumt);
		//request.setAttribute("page",list.size());
		return list;
	}*/



	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("consumables_report:consumablesReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ConsumablesReport> listData(ConsumablesReport consumablesReport, HttpServletRequest request, HttpServletResponse response) {
		Page<ConsumablesReport> page = consumablesReportService.findPage(new Page<ConsumablesReport>(request, response), consumablesReport);

		Long stockSum =new Long(0);//库存合计
		Double stockAtmSum =new Double(0);//库存金额合计
		if(page.getList().size()>0){
		for (int i = 0;i<page.getList().size();i++){
			stockSum+=page.getList().get(i).getStock();
			stockAtmSum+=page.getList().get(i).getStockAtm();
			if(!"".equals(page.getList().get(i).getFilePath()) && null!=page.getList().get(i).getFilePath()){
				page.getList().get(i).setPhoto("/userfiles/fileupload"+"/"+page.getList().get(i).getFilePath()+page.getList().get(i).getFileId()+"."+page.getList().get(i).getFileExtension());
			}
		}

			page.getList().get(0).setStockSum(stockSum);
			page.getList().get(0).setStockAtmSum(stockAtmSum);
		}
		/*if(stockSum!=0){
			ConsumablesReport cons = new ConsumablesReport();
			cons.setWarehouseName("合计");
			cons.setStock(stockSum);
			cons.setStockAtm(stockAtmSum);
			page.getList().add(cons);
		}*/
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("consumables_report:consumablesReport:view")
	@RequestMapping(value = "form")
	public String form(ConsumablesReport consumablesReport, Model model) {
		model.addAttribute("consumablesReport", consumablesReport);
		return "asset/consumables_report/consumablesReportForm";
	}

	/**
	 * 保存consumables_report
	 */
/*	@RequiresPermissions("consumables_report:consumablesReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ConsumablesReport consumablesReport) {
		consumablesReportService.save(consumablesReport);
		return renderResult(Global.TRUE, "保存consumables_report成功！");
	}*/
/*	@RequiresPermissions("consumables_report:consumablesReport:view")
	@PostMapping(value = "findList")
	public String findList() {

		List list = consumablesReportService.findList();
		System.out.println("!!!!");
		return "";
	}*/
}