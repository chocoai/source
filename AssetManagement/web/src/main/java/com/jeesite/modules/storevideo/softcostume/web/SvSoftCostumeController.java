/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.softcostume.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.storevideo.softcostume.entity.SvSoftDto;
import com.jeesite.modules.storevideo.softcostume.entity.SvSoftRlat;
import com.jeesite.modules.storevideo.softcostume.service.SvSoftRlatService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.storevideo.softcostume.entity.SvSoftCostume;
import com.jeesite.modules.storevideo.softcostume.service.SvSoftCostumeService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 软装方案管理Controller
 * @author len
 * @version 2019-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/softcostume/svSoftCostume")
public class SvSoftCostumeController extends BaseController {

	@Autowired
	private SvSoftCostumeService svSoftCostumeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvSoftCostume get(String softCostumeCode, boolean isNewRecord) {
		return svSoftCostumeService.get(softCostumeCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("softcostume:svSoftCostume:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvSoftCostume svSoftCostume, Model model) {
		model.addAttribute("svSoftCostume", svSoftCostume);
		return "storevideo/softcostume/svSoftCostumeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("softcostume:svSoftCostume:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvSoftCostume> listData(SvSoftCostume svSoftCostume, HttpServletRequest request, HttpServletResponse response) {
		Page<SvSoftCostume> page = svSoftCostumeService.findPage(new Page<SvSoftCostume>(request, response), svSoftCostume); 
		return page;
	}

	@RequiresPermissions("softcostume:svSoftCostume:view")
	@RequestMapping(value = "listDictData")
	@ResponseBody
	public Page<SvSoftRlat> listDictData(SvSoftRlat svSoftRlat, HttpServletRequest request, HttpServletResponse response){
		Page<SvSoftRlat> page =new Page<>(request, response);
		if(StringUtils.isBlank(svSoftRlat.getSoftCostumeCode())) {
			page.setList(null);
			page.setCount(0);
		}
		else{
			List<SvSoftRlat> list = svSoftRlatService.findList(svSoftRlat);
			page.setList(list);
			page.setCount(list.size());
		}
		return page;
	}
	/**
	 * 查看编辑表单
	 */
//	@RequiresPermissions("softcostume:svSoftCostume:view")
//	@RequestMapping(value = "form")
//	public String form(SvSoftCostume svSoftCostume, Model model) {
//		model.addAttribute("svSoftCostume", svSoftCostume);
//		return "storevideo/softcostume/svSoftCostumeForm";
//	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("softcostume:svSoftCostume:view")
	@RequestMapping(value = "form")
	public String form(SvSoftCostume svSoftCostume, Model model) {
		model.addAttribute("svSoftCostume", svSoftCostume);

		SvSoftRlat svSoftRlat = new SvSoftRlat();
		svSoftRlat.setSoftCostumeCode(svSoftCostume.getSoftCostumeCode());
		List<SvSoftRlat> list = svSoftRlatService.findList(svSoftRlat);

		SvSoftRlat SvSoftRlatSex = new SvSoftRlat();
		SvSoftRlatSex.setSoftCostumeCode(svSoftCostume.getSoftCostumeCode());
		SvSoftRlatSex.setDimensionId("sex");
		model.addAttribute("svSoftRlatSex", SvSoftRlatSex);

		SvSoftRlat SvSoftRlatAge = new SvSoftRlat();
		SvSoftRlatAge.setSoftCostumeCode(svSoftCostume.getSoftCostumeCode());
		SvSoftRlatAge.setDimensionId("age");
		model.addAttribute("svSoftRlatAge", SvSoftRlatAge);

		SvSoftRlat SvSoftRlatCategory = new SvSoftRlat();
		SvSoftRlatCategory.setSoftCostumeCode(svSoftCostume.getSoftCostumeCode());
		SvSoftRlatCategory.setDimensionId("category");
		model.addAttribute("svSoftRlatCategory", SvSoftRlatCategory);

		SvSoftRlat SvSoftRlatStyle = new SvSoftRlat();
		SvSoftRlatStyle.setSoftCostumeCode(svSoftCostume.getSoftCostumeCode());
		SvSoftRlatStyle.setDimensionId("style");
		model.addAttribute("svSoftRlatStyle", SvSoftRlatStyle);

		SvSoftRlat SvSoftRlatProduct = new SvSoftRlat();
		SvSoftRlatProduct.setSoftCostumeCode(svSoftCostume.getSoftCostumeCode());
		SvSoftRlatProduct.setDimensionId("product");
		model.addAttribute("svSoftRlatProduct", SvSoftRlatProduct);

		List<SvSoftRlat> svSoftCostumeSexList = list.stream().filter(a->a.getDimensionId().equals("sex")).collect(Collectors.toList());
		model.addAttribute("svSoftCostumeSexList", svSoftCostumeSexList);

		List<SvSoftRlat> svSoftCostumeAgeList = list.stream().filter(a->a.getDimensionId().equals("age")).collect(Collectors.toList());
		model.addAttribute("svSoftCostumeAgeList", svSoftCostumeAgeList);

		List<SvSoftRlat> svSoftCostumeCategoryList  = list.stream().filter(a->a.getDimensionId().equals("category")).collect(Collectors.toList());
		model.addAttribute("svSoftCostumeCategoryList ", svSoftCostumeCategoryList );

		List<SvSoftRlat> svSoftCostumeSeriesGrid = list.stream().filter(a->a.getDimensionId().equals("age")).collect(Collectors.toList());
		model.addAttribute("svSoftCostumeSeriesList", svSoftCostumeSeriesGrid);



		List<SvSoftRlat> svSoftCostumeProductGrid = list.stream().filter(a->a.getDimensionId().equals("age")).collect(Collectors.toList());
		model.addAttribute("svSoftCostumeProductList", svSoftCostumeProductGrid);

		return "storevideo/softcostume/svSoftCostumeForm";
	}
	/**
	 * 保存软装方案管理
	 */
	@RequiresPermissions("softcostume:svSoftCostume:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvSoftCostume svSoftCostume) {
		svSoftCostumeService.save(svSoftCostume);
		return renderResult(Global.TRUE, text("保存软装方案管理成功！"));
	}

	@Autowired
	private SvSoftRlatService svSoftRlatService;

	@RequiresPermissions("softcostume:svSoftCostume:edit")
	@PostMapping(value = "saveData")
	@ResponseBody
	public String saveData(@Validated @RequestBody SvSoftDto data) {
		SvSoftCostume softCostume = data.getSvSoftCostume();
		softCostume.setIsNewRecord(StringUtils.isBlank(softCostume.getSoftCostumeCode()));
		List<SvSoftRlat> list = data.getSvSoftRlatList();
		svSoftCostumeService.save(softCostume);
		for(SvSoftRlat item : list){
			item.setSoftCostumeCode(softCostume.getSoftCostumeCode());
			item.setIsNewRecord(true);
		}

		svSoftRlatService.deleteAndInsert(softCostume.getSoftCostumeCode(), list);
		return renderResult(Global.TRUE, text("保存成功！"));
	}
	/**
	 * 删除软装方案管理
	 */
	@RequiresPermissions("softcostume:svSoftCostume:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SvSoftCostume svSoftCostume) {
		svSoftCostumeService.delete(svSoftCostume);
		return renderResult(Global.TRUE, text("删除软装方案管理成功！"));
	}
	
}