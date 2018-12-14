/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.service.AmUtilService;
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
import com.jeesite.modules.asset.warehioreport.entity.WarehIoReport;
import com.jeesite.modules.asset.warehioreport.service.WarehIoReportService;

/**
 * 耗材仓库进出明细表Controller
 * @author czy
 * @version 2018-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/warehioreport/warehIoReport")
public class WarehIoReportController extends BaseController {

	@Autowired
	private WarehIoReportService warehIoReportService;
	@Autowired
	private AmUtilService amUtilService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WarehIoReport get(String id, boolean isNewRecord) {
		return warehIoReportService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("warehioreport:warehIoReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(WarehIoReport warehIoReport, Model model) {
		model.addAttribute("warehIoReport", warehIoReport);
		return "asset/warehioreport/warehIoReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("warehioreport:warehIoReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WarehIoReport> listData(WarehIoReport warehIoReport, HttpServletRequest request, HttpServletResponse response) {
		Page<WarehIoReport> page = warehIoReportService.findPage(new Page<WarehIoReport>(request, response), warehIoReport);
		if (page.getList().size() > 0) {
			Double priceSum = new Double(0);		// 单价合计
			Double price = new Double(0);
			for (WarehIoReport warehIoReport1 : page.getList()) {
				if (warehIoReport1.getInstorageAmount() != null) {      // 入库金额
					if (warehIoReport1.getInstorageCount() == null) {   // 入库数量
						warehIoReport1.setPrice((double) 0);             // 单价
					} else {
						price = (double)Math.round((warehIoReport1.getInstorageAmount() / warehIoReport1.getInstorageCount()) * 100) / 100;
						warehIoReport1.setPrice(price);
					}
				}
				if (warehIoReport1.getOutstorageAmount() != null) {      // 出库金额
					if (warehIoReport1.getOutstorageCount() == null) {   // 出库数量
						warehIoReport1.setPrice((double) 0);             // 单价
					} else {
						price = (double)Math.round((warehIoReport1.getOutstorageAmount() / warehIoReport1.getOutstorageCount()) * 100) / 100;
						warehIoReport1.setPrice(price);
					}
				}
				warehIoReport1.setPhoto(amUtilService.getImgPath(warehIoReport1.getConsumablesCode()));
				if (warehIoReport1.getPrice() == null) {
					priceSum = priceSum + 0;
				} else {
					priceSum = (double)Math.round((priceSum + warehIoReport1.getPrice()) * 100) / 100;
				}
			}
			WarehIoReport warehIoRepor = warehIoReportService.findSum(warehIoReport);
			page.getList().get(0).setPriceSum(priceSum);       // 单价合计
			page.getList().get(0).setInCountSum(warehIoRepor.getInCountSum());   // 入库数量合计
			page.getList().get(0).setInAtmSum(warehIoRepor.getInAtmSum());       // 入库金额合计
			page.getList().get(0).setOutCountSum(warehIoRepor.getOutCountSum()); // 出库数量合计
			page.setCount(page.getList().size());
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("warehioreport:warehIoReport:view")
	@RequestMapping(value = "form")
	public String form(WarehIoReport warehIoReport, Model model) {
		model.addAttribute("warehIoReport", warehIoReport);
		return "asset/warehioreport/warehIoReportForm";
	}

	/**
	 * 保存耗材仓库进出明细表
	 */
	@RequiresPermissions("warehioreport:warehIoReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WarehIoReport warehIoReport) {
		warehIoReportService.save(warehIoReport);
		return renderResult(Global.TRUE, "保存耗材仓库进出明细表成功！");
	}

}