/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.pricelog.web;

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
import com.jeesite.modules.distribution.pricelog.entity.DistrPriceLog;
import com.jeesite.modules.distribution.pricelog.service.DistrPriceLogService;

/**
 * 分销价修改日志Controller
 * @author len
 * @version 2019-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/pricelog/distrPriceLog")
public class DistrPriceLogController extends BaseController {

	@Autowired
	private DistrPriceLogService distrPriceLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DistrPriceLog get(String logCode, boolean isNewRecord) {
		return distrPriceLogService.get(logCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pricelog:distrPriceLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(DistrPriceLog distrPriceLog, Model model) {
		model.addAttribute("distrPriceLog", distrPriceLog);
		return "distribution/pricelog/distrPriceLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pricelog:distrPriceLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DistrPriceLog> listData(DistrPriceLog distrPriceLog, HttpServletRequest request, HttpServletResponse response) {
		Page<DistrPriceLog> page = distrPriceLogService.findPage(new Page<DistrPriceLog>(request, response), distrPriceLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pricelog:distrPriceLog:view")
	@RequestMapping(value = "form")
	public String form(DistrPriceLog distrPriceLog, Model model) {
		model.addAttribute("distrPriceLog", distrPriceLog);
		return "distribution/pricelog/distrPriceLogForm";
	}

	/**
	 * 保存分销价修改日志
	 */
	@RequiresPermissions("pricelog:distrPriceLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DistrPriceLog distrPriceLog) {
		distrPriceLogService.save(distrPriceLog);
		return renderResult(Global.TRUE, text("保存分销价修改日志成功！"));
	}
	
}