/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.locationreport.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.asset.util.service.AmUtilService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.locationreport.entity.LocationReport;
import com.jeesite.modules.asset.locationreport.service.LocationReportService;

/**
 * 库位管理Controller
 * @author Mclaran
 * @version 2018-05-07
 */
@Controller
@RequestMapping(value = "${adminPath}/locationreport/locationReport")
public class LocationReportController extends BaseController {

	@Autowired
	private LocationReportService locationReportService;
	@Autowired
	private AmUtilService amUtilService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public LocationReport get(String locationCode, boolean isNewRecord) {
		return locationReportService.get(locationCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("locationreport:locationReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(LocationReport locationReport, Model model) {
		model.addAttribute("locationReport", locationReport);
		return "asset/locationreport/locationReportList";
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("locationreport:locationReport:view")
	@RequestMapping(value = {"listSelect", ""})
	public String listSelect(LocationReport locationReport, Model model,String selectData, String warehouseCode) {
		locationReport.setWarehouseCode(warehouseCode);
		model.addAttribute("selectData", selectData);
		model.addAttribute("locationReport", locationReport);
		return "asset/locationreport/locationSelect";
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("locationreport:locationReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<LocationReport> listData(LocationReport locationReport, HttpServletRequest request, HttpServletResponse response) {
		//locationReport.;
		Page<LocationReport> page = locationReportService.findPage(new Page<LocationReport>(request, response), locationReport);
		Long stockSum =new Long(0);//库存合计
		Double stockAtmSum =new Double(0);//库存金额合计
        if(page.getList().size()>0){
		for (int i = 0;i<page.getList().size();i++){
            stockSum+=page.getList().get(i).getStock();
            stockAtmSum+=page.getList().get(i).getStockAtm();
			if(!"".equals(page.getList().get(i).getFilePath()) && null!=page.getList().get(i).getFilePath()){
				page.getList().get(i).setPhoto("/userfiles/fileupload"+"/"+page.getList().get(i).getFilePath()+page.getList().get(i).getFileId()+"."+page.getList().get(i).getFileExtension());
			}
			// 计量单位
			LocationReport locationReport1 = page.getList().get(i);
			String measureUnitName= amUtilService.findDictLabel(locationReport1.getMeasureUnit(),"sys_measure_unit");
			locationReport1.setMeasureValue(measureUnitName);

		}

			page.getList().get(0).setStockSum(stockSum);
			page.getList().get(0).setStockAtmSum(stockAtmSum);
		}
		return page;
	}

	/**
	 * 查看编辑表单
	@RequiresPermissions("locationreport:locationReport:view")
	@RequestMapping(value = "form")
	public String form(LocationReport locationReport, Model model) {
		model.addAttribute("locationReport", locationReport);
		return "asset/locationreport/locationReportForm";
	}

	*//**
	 * 保存报表-耗材库存(库位)
	 *//*
	@RequiresPermissions("locationreport:locationReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated LocationReport locationReport) {
		locationReportService.save(locationReport);
		return renderResult(Global.TRUE, "保存报表-耗材库存(库位)成功！");
	}*/
	
}