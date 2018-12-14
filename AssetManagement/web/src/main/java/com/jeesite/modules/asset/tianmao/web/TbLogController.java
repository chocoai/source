/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

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
import com.jeesite.modules.asset.tianmao.entity.TbLog;
import com.jeesite.modules.asset.tianmao.service.TbLogService;

/**
 * tb_logController
 * @author jace
 * @version 2018-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbLog")
public class TbLogController extends BaseController {

	@Autowired
	private TbLogService tbLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbLog get(String skuId, boolean isNewRecord) {
		return tbLogService.get(skuId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:tbLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbLog tbLog, Model model) {
		model.addAttribute("tbLog", tbLog);
		return "asset/tianmao/tbLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tianmao:tbLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbLog> listData(TbLog tbLog, HttpServletRequest request, HttpServletResponse response) {
		Page<TbLog> page = tbLogService.findPage(new Page<TbLog>(request, response), tbLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:tbLog:view")
	@RequestMapping(value = "form")
	public String form(TbLog tbLog, Model model) {
		model.addAttribute("tbLog", tbLog);
		return "asset/tianmao/tbLogForm";
	}

	/**
	 * 保存tb_log
	 */
	@RequiresPermissions("tianmao:tbLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbLog tbLog) {
		tbLogService.save(tbLog);
		return renderResult(Global.TRUE, "保存tb_log成功！");
	}
	
	/**
	 * 删除tb_log
	 */
	@RequiresPermissions("tianmao:tbLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbLog tbLog) {
		tbLogService.delete(tbLog);
		return renderResult(Global.TRUE, "删除tb_log成功！");
	}
	
}