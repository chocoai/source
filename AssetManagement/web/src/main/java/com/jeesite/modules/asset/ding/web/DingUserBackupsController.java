/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.web;

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
import com.jeesite.modules.asset.ding.entity.DingUserBackups;
import com.jeesite.modules.asset.ding.service.DingUserBackupsService;

/**
 * 钉钉用户表备份Controller
 * @author len
 * @version 2018-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/ding/dingUserBackups")
public class DingUserBackupsController extends BaseController {

	@Autowired
	private DingUserBackupsService dingUserBackupsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DingUserBackups get(String pkey, boolean isNewRecord) {
		return dingUserBackupsService.get(pkey, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("ding:dingUserBackups:view")
	@RequestMapping(value = {"list", ""})
	public String list(DingUserBackups dingUserBackups, Model model) {
		model.addAttribute("dingUserBackups", dingUserBackups);
		return "asset/ding/dingUserBackupsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ding:dingUserBackups:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DingUserBackups> listData(DingUserBackups dingUserBackups, HttpServletRequest request, HttpServletResponse response) {
		Page<DingUserBackups> page = dingUserBackupsService.findPage(new Page<DingUserBackups>(request, response), dingUserBackups); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ding:dingUserBackups:view")
	@RequestMapping(value = "form")
	public String form(DingUserBackups dingUserBackups, Model model) {
		model.addAttribute("dingUserBackups", dingUserBackups);
		return "asset/ding/dingUserBackupsForm";
	}

	/**
	 * 保存钉钉用户表备份
	 */
	@RequiresPermissions("ding:dingUserBackups:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DingUserBackups dingUserBackups) {
		dingUserBackupsService.save(dingUserBackups);
		return renderResult(Global.TRUE, text("保存钉钉用户表备份成功！"));
	}
	
}