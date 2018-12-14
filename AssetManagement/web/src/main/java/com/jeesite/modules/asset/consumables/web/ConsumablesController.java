/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.consumables.entity.*;
import com.jeesite.modules.asset.consumables.service.AmInstorageService;
import com.jeesite.modules.asset.consumables.service.AmOutStorageService;
import com.jeesite.modules.asset.util.service.AmSeqService;
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
import com.jeesite.modules.asset.consumables.service.ConsumablesService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 耗材档案Controller
 * @author dwh
 * @version 2018-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/consumables/consumables")
public class ConsumablesController extends BaseController {

	private final String HC_PER_FIX="HC";
	private final String SP_PER_FIX="SP";
	@Autowired
	private ConsumablesService consumablesService;

	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmInstorageService amInstorageService;
	@Autowired
	private AmOutStorageService amOutStorageService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Consumables get(String consumablesCode, boolean isNewRecord) {
		return consumablesService.get(consumablesCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("consumables:consumables:view")
	@RequestMapping(value = {"list", ""})
	public String list(Consumables consumables, Model model) {
		model.addAttribute("consumables", consumables);
		return "asset/consumables/consumablesList";
	}



	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("consumables:consumables:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Consumables> listData(Consumables consumables,Boolean isAll, HttpServletRequest request, HttpServletResponse response) {
		consumables.getCategory().setIsQueryChildren(true);			//当它为true时，sql语句后面会带上 parent_codes like 条件，点击上一级会把下一级也带进去
		if (isAll == null || !isAll) {
			this.consumablesService.addDataScopeFilter(consumables, "2");
		}
		Page<Consumables> page = consumablesService.findPage(new Page<Consumables>(request, response), consumables);
		for (int i =0;i<page.getList().size();i++){
			Consumables consumable=page.getList().get(i);
			String measureUnitName= amUtilService.findDictLabel(consumable.getMeasureUnit(),"sys_measure_unit");
			consumable.setMeasureValue(measureUnitName);
			String imgPath=amUtilService.getImgPathAli(consumable.getConsumablesCode(),"consumables_image");//获取照片路径
			consumable.setImgPath(imgPath);
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("consumables:consumables:view")
	@RequestMapping(value = "form")
	public String form(Consumables consumables, Model model,boolean isNewRecord) {
		Date now=new Date();
		if (isNewRecord){
//			String code=amSeqService.getSeq(HC_PER_FIX);
			String barCode=amSeqService.getSeq(SP_PER_FIX);
			consumables.setCreateDate(now);
			consumables.setUpdateDate(now);
//			consumables.setConsumablesCode(code);
			consumables.setBarCode(barCode);
		}
		model.addAttribute("consumables", consumables);
		return "asset/consumables/consumablesForm";
	}

	/**
	 * 保存耗材档案
	 */
	@RequiresPermissions("consumables:consumables:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Consumables consumables) {
		if (consumables.getIsNewRecord()){
//			String code=amSeqService.getCode(HC_PER_FIX);
			String barCode=amSeqService.getCode(SP_PER_FIX);
//			consumables.setConsumablesCode(code);
			consumables.setBarCode(barCode);
		}
//		consumablesService.updateStatus(consumables);
		consumablesService.save(consumables);
		return renderResult(Global.TRUE, "保存耗材档案成功！");
	}
	
	/**
	 * 删除耗材档案
	 */
	@RequiresPermissions("consumables:consumables:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Consumables consumables) {
		AmInstorageDetails amInstorageDetails=new AmInstorageDetails();
		amInstorageDetails.setConsumablesCode(consumables.getConsumablesCode());
		List<AmInstorageDetails> amInstorageDetailsList=amInstorageService.getDetailList(amInstorageDetails);
		if (amInstorageDetailsList!=null&&amInstorageDetailsList.size()>0){
			return renderResult(Global.FALSE, "删除档案失败！入库单已产生业务");
		}
		AmOutStorageDetails amOutStorageDatails=new AmOutStorageDetails();
		amOutStorageDatails.setConsumablesCode(consumables.getConsumablesCode());
		List<AmOutStorageDetails> amOutStorageDetailsList=amOutStorageService.getDetailList(amOutStorageDatails);
		if (amOutStorageDetailsList!=null&&amOutStorageDetailsList.size()>0){
			return renderResult(Global.FALSE, "删除档案失败！出库单已产生业务");
		}

		consumablesService.delete(consumables);
		return renderResult(Global.TRUE, "删除耗材档案成功！");
	}
	@RequiresPermissions("consumables:consumables:view")
	@RequestMapping(value = "index")
	public String index(Consumables consumables, Model model) {
		return "asset/consumables/consumablesIndex";
	}
}