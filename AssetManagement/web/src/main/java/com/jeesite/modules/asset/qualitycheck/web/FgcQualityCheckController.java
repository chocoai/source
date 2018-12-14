/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.qualitycheck.web;

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
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheck;
import com.jeesite.modules.asset.qualitycheck.service.FgcQualityCheckService;

/**
 * 质检单Controller
 * @author Albert
 * @version 2018-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/qualitycheck/fgcQualityCheck")
public class FgcQualityCheckController extends BaseController {
	@Autowired
	private FgcQualityCheckService fgcQualityCheckService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FgcQualityCheck get(String id, boolean isNewRecord) {
		return fgcQualityCheckService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("qualitycheck:fgcQualityCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(FgcQualityCheck fgcQualityCheck, Model model) {
		model.addAttribute("fgcQualityCheck", fgcQualityCheck);
		return "asset/qualitycheck/fgcQualityCheckList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("qualitycheck:fgcQualityCheck:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FgcQualityCheck> listData(FgcQualityCheck fgcQualityCheck, HttpServletRequest request, HttpServletResponse response) {
		Page<FgcQualityCheck> page = fgcQualityCheckService.findPage(new Page<FgcQualityCheck>(request, response), fgcQualityCheck); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("qualitycheck:fgcQualityCheck:view")
	@RequestMapping(value = "form")
	public String form(FgcQualityCheck fgcQualityCheck, Model model) {
		model.addAttribute("fgcQualityCheck", fgcQualityCheck);
		return "asset/qualitycheck/fgcQualityCheckForm";
	}

	/**
	 * 保存质检单
	 */
	@RequiresPermissions("qualitycheck:fgcQualityCheck:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(FgcQualityCheck fgcQualityCheck) {
		fgcQualityCheckService.save(fgcQualityCheck);
		return renderResult(Global.TRUE, "保存质检单成功！");
	}
	
	/**
	 * 删除质检单
	 */
	@RequiresPermissions("qualitycheck:fgcQualityCheck:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FgcQualityCheck fgcQualityCheck) {
		fgcQualityCheckService.delete(fgcQualityCheck);
		return renderResult(Global.TRUE, "删除质检单成功！");
	}
	
}