/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.web;

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
import com.jeesite.modules.asset.order.entity.AmOrderLog;
import com.jeesite.modules.asset.order.service.AmOrderLogService;

/**
 * 订单管理日志异常日志表Controller
 * @author len
 * @version 2018-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/order/amOrderLog")
public class AmOrderLogController extends BaseController {

	@Autowired
	private AmOrderLogService amOrderLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmOrderLog get(String logId, boolean isNewRecord) {
		return amOrderLogService.get(logId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:amOrderLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmOrderLog amOrderLog, Model model) {
		model.addAttribute("amOrderLog", amOrderLog);
		return "asset/order/amOrderLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:amOrderLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmOrderLog> listData(AmOrderLog amOrderLog, HttpServletRequest request, HttpServletResponse response) {
		Page<AmOrderLog> page = amOrderLogService.findPage(new Page<AmOrderLog>(request, response), amOrderLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:amOrderLog:view")
	@RequestMapping(value = "form")
	public String form(AmOrderLog amOrderLog, Model model) {
		model.addAttribute("amOrderLog", amOrderLog);
		return "asset/order/amOrderLogForm";
	}

	/**
	 * 保存订单管理日志异常日志表
	 */
	@RequiresPermissions("order:amOrderLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmOrderLog amOrderLog) {
		amOrderLogService.save(amOrderLog);
		return renderResult(Global.TRUE, text("保存订单管理日志异常日志表成功！"));
	}
	
	/**
	 * 删除订单管理日志异常日志表
	 */
	@RequiresPermissions("order:amOrderLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmOrderLog amOrderLog) {
		amOrderLogService.delete(amOrderLog);
		return renderResult(Global.TRUE, text("删除订单管理日志异常日志表成功！"));
	}
	
}