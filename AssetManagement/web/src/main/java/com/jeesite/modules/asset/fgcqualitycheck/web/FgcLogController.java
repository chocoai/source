/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.web;

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
import com.jeesite.modules.asset.fgcqualitycheck.entity.FgcLog;
import com.jeesite.modules.asset.fgcqualitycheck.service.FgcLogService;

/**
 * 梵工厂反写日志表Controller
 * @author len
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/fgcqualitycheck/fgcLog")
public class FgcLogController extends BaseController {

	@Autowired
	private FgcLogService fgcLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FgcLog get(String logId, boolean isNewRecord) {
		return fgcLogService.get(logId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fgcqualitycheck:fgcLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(FgcLog fgcLog, Model model) {
		model.addAttribute("fgcLog", fgcLog);
		return "asset/fgcqualitycheck/fgcLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fgcqualitycheck:fgcLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FgcLog> listData(FgcLog fgcLog, HttpServletRequest request, HttpServletResponse response) {
		Page<FgcLog> page = fgcLogService.findPage(new Page<FgcLog>(request, response), fgcLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fgcqualitycheck:fgcLog:view")
	@RequestMapping(value = "form")
	public String form(FgcLog fgcLog, Model model) {
		model.addAttribute("fgcLog", fgcLog);
		return "asset/fgcqualitycheck/fgcLogForm";
	}

	/**
	 * 保存梵工厂反写日志表
	 */
	@RequiresPermissions("fgcqualitycheck:fgcLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FgcLog fgcLog) {
		fgcLogService.save(fgcLog);
		return renderResult(Global.TRUE, text("保存梵工厂反写日志表成功！"));
	}
	
}