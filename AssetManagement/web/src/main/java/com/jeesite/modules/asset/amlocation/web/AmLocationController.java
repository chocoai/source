/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlocation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.jeesite.common.mybatis.mapper.query.QueryOrder;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.asset.consumables.entity.AmInstorage;
import com.jeesite.modules.asset.consumables.entity.AmInstorageDetails;
import com.jeesite.modules.asset.consumables.entity.AmOutStorage;
import com.jeesite.modules.asset.consumables.entity.AmOutStorageDetails;
import com.jeesite.modules.asset.consumables.service.AmInstorageService;
import com.jeesite.modules.asset.consumables.service.AmOutStorageService;
import com.jeesite.modules.asset.util.service.AmSeqService;
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
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.amlocation.service.AmLocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * 库位管理Controller
 * @author Mclaran
 * @version 2018-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/amlocation/amLocation")
public class AmLocationController extends BaseController {

	@Autowired
	private AmLocationService amLocationService;
	private final String KW = "KW";
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmInstorageService amInstorageService;
	@Autowired
	private AmOutStorageService amOutStorageService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmLocation get(String locationCode, boolean isNewRecord) {
		return amLocationService.get(locationCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("amlocation:amLocation:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmLocation amLocation, Model model) {
		model.addAttribute("amLocation", amLocation);
		return "asset/amlocation/amLocationList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("amlocation:amLocation:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmLocation> listData(AmLocation amLocation, HttpServletRequest request, HttpServletResponse response) {
		if(amLocation!=null){
			amLocation.getAmWarehouse().setIsQueryChildren(true);
		}
		Page<AmLocation> page = amLocationService.findPage(new Page<AmLocation>(request, response), amLocation); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("amlocation:amLocation:view")
	@RequestMapping(value = "form")
	public String form(AmLocation amLocation, Model model,boolean isNewRecord) {

		model.addAttribute("amLocation", amLocation);
		return "asset/amlocation/amLocationForm";
	}

	/**
	 * 保存库位管理
	 */
	@RequiresPermissions("amlocation:amLocation:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmLocation amLocation) {
		amLocationService.save(amLocation);
		return renderResult(Global.TRUE, "保存库位管理成功！");
	}
	
	/**
	 * 停用库位管理
	 */
	@RequiresPermissions("amlocation:amLocation:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AmLocation amLocation) {
		amLocation.setStatus(AmLocation.STATUS_DISABLE);
		amLocationService.updateStatus(amLocation);
		return renderResult(Global.TRUE, "停用库位管理成功");
	}
	
	/**
	 * 启用库位管理
	 */
	@RequiresPermissions("amlocation:amLocation:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AmLocation amLocation) {
		amLocation.setStatus(AmLocation.STATUS_NORMAL);
		amLocationService.updateStatus(amLocation);
		return renderResult(Global.TRUE, "启用库位管理成功");
	}
	
	/**
	 * 删除库位管理
	 */
	@RequiresPermissions("amlocation:amLocation:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmLocation amLocation) {
		//删除库位前判断是否有单据业务
		AmInstorageDetails amInstorageDetails=new AmInstorageDetails();
		amInstorageDetails.setLocationCode(amLocation.getLocationCode());
		List<AmInstorageDetails>  amInstorageDetailsList= amInstorageService.getDetailList(amInstorageDetails);
		if (amInstorageDetailsList!=null&&amInstorageDetailsList.size()>0){
			return renderResult(Global.FALSE, "删除库位管理失败！入库单据产生业务");
		}
		AmOutStorageDetails amOutStorageDetails=new AmOutStorageDetails();
		amOutStorageDetails.setLocationCode(amLocation.getLocationCode());
		List<AmOutStorageDetails>  amOutStorageDetailsList= amOutStorageService.getDetailList(amOutStorageDetails);
		if (amOutStorageDetailsList!=null&&amOutStorageDetailsList.size()>0){
			return renderResult(Global.FALSE, "删除库位管理失败！出库单据产生业务");
		}


		amLocationService.delete(amLocation);
		return renderResult(Global.TRUE, "删除库位管理成功！");
	}

	@RequiresPermissions("amlocation:amLocation:view")
	@RequestMapping(value = "index")
	public String index(Model model , AmLocation amLocation){
		//G:\AssetManagement\web\src\main\resources\views\asset\amlocation\amLocationIndex.html
		return "asset/amlocation/amLocationIndex";
	}
	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "selectAmLocation")
	public String selectAmLocation(AmLocation amLocation, Model model, String selectData,String warehouseCode) {
		model.addAttribute("selectData", selectData);
		model.addAttribute("warehouseCode",warehouseCode);
		model.addAttribute("amLocation", amLocation);
		return "asset/consumables/selectAmLocationList";
	}
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("amlocation:amLocation:view")
	@RequestMapping(value = "getLocationByWarehouseCode")
	@ResponseBody
	public Page<AmLocation> getLocationByWarehouseCode(AmLocation amLocation, HttpServletRequest request, HttpServletResponse response,String warehouseCode) {
		amLocation.getAmWarehouse().setIsQueryChildren(true);
		amLocation.setWarehouseCode(warehouseCode);
		Page<AmLocation> page = amLocationService.findPage(new Page<AmLocation>(request, response), amLocation);
		if (page.getList()!=null&&page.getList().size()>0){
			List<AmLocation> amLocationList=new ArrayList<>();
			for (int i=0;i<page.getList().size();i++){
				AmLocation location=page.getList().get(i);
				if (location.getStatus().equals("0")){
					amLocationList.add(location);
				}
			}
			page.setList(amLocationList);
		}
		return page;
	}
}