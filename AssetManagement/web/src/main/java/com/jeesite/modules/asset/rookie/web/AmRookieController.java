/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.rookie.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.datasource.DataSourceHolder;
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
import com.jeesite.modules.asset.rookie.entity.AmRookie;
import com.jeesite.modules.asset.rookie.service.AmRookieService;

/**
 * 菜鸟对接记录Controller
 * @author czy
 * @version 2018-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rookie/amRookie")
public class AmRookieController extends BaseController {

	@Autowired
	private AmRookieService amRookieService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmRookie get(String documentCode, boolean isNewRecord) {
	//	DataSourceHolder.setDataSourceName("ds2");
		return amRookieService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("rookie:amRookie:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmRookie amRookie, Model model) {
		model.addAttribute("amRookie", amRookie);
		return "asset/rookie/amRookieList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("rookie:amRookie:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmRookie> listData(AmRookie amRookie, HttpServletRequest request, HttpServletResponse response) {
		Page<AmRookie> page = amRookieService.findPage(new Page<AmRookie>(request, response), amRookie); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("rookie:amRookie:view")
	@RequestMapping(value = "form")
	public String form(AmRookie amRookie, Model model) {
		model.addAttribute("amRookie", amRookie);
		return "asset/rookie/amRookieForm";
	}

	/**
	 * 保存菜鸟对接记录
	 */
	@RequiresPermissions("rookie:amRookie:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmRookie amRookie) {
		amRookieService.save(amRookie);
		return renderResult(Global.TRUE, "保存菜鸟对接记录成功！");
	}
	
}