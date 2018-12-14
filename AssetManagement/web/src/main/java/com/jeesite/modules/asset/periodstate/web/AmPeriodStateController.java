/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.periodstate.web;

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
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;
import com.jeesite.modules.asset.periodstate.service.AmPeriodStateService;

/**
 * 数据期间表Controller
 * @author dwh
 * @version 2018-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/periodstate/amPeriodState")
public class AmPeriodStateController extends BaseController {

	@Autowired
	private AmPeriodStateService amPeriodStateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmPeriodState get(String periodStateCode, boolean isNewRecord) {
		return amPeriodStateService.get(periodStateCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("periodstate:amPeriodState:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmPeriodState amPeriodState, Model model) {
		model.addAttribute("amPeriodState", amPeriodState);
		return "asset/periodstate/amPeriodStateList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("periodstate:amPeriodState:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmPeriodState> listData(AmPeriodState amPeriodState, HttpServletRequest request, HttpServletResponse response) {
		Page<AmPeriodState> page = amPeriodStateService.findPage(new Page<AmPeriodState>(request, response), amPeriodState); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("periodstate:amPeriodState:view")
	@RequestMapping(value = "form")
	public String form(AmPeriodState amPeriodState, Model model) {
		model.addAttribute("amPeriodState", amPeriodState);
		return "asset/periodstate/amPeriodStateForm";
	}

	/**
	 * 保存数据期间表
	 */
	@RequiresPermissions("periodstate:amPeriodState:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmPeriodState amPeriodState) {
		amPeriodStateService.save(amPeriodState);
		return renderResult(Global.TRUE, "保存数据期间表成功！");
	}
	
	/**
	 * 删除数据期间表
	 */
	@RequiresPermissions("periodstate:amPeriodState:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmPeriodState amPeriodState) {
		amPeriodStateService.delete(amPeriodState);
		return renderResult(Global.TRUE, "删除数据期间表成功！");
	}
	
}