/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.asset.job.entity.SysJobLog;
import com.jeesite.modules.asset.job.service.SysJobLogService;
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
import com.jeesite.common.web.BaseController;

/**
 * 定时任务调度日志表Controller
 * @author len
 * @version 2018-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/job/sysJobLog")
public class SysJobLogController extends BaseController {

	@Autowired
	private SysJobLogService sysJobLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SysJobLog get(String jobLogId, boolean isNewRecord) {
		return sysJobLogService.get(jobLogId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("job:sysJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysJobLog sysJobLog, Model model) {
		model.addAttribute("sysJobLog", sysJobLog);
		return "asset/job/sysJobLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("job:sysJob:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysJobLog> listData(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response) {
		Page<SysJobLog> page = sysJobLogService.findPage(new Page<SysJobLog>(request, response), sysJobLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("job:sysJob:view")
	@RequestMapping(value = "form")
	public String form(SysJobLog sysJobLog, Model model) {
		model.addAttribute("sysJobLog", sysJobLog);
		return "asset/job/sysJobLogForm";
	}

	/**
	 * 保存定时任务调度日志表
	 */
	@RequiresPermissions("job:sysJob:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SysJobLog sysJobLog) {
		sysJobLogService.save(sysJobLog);
		return renderResult(Global.TRUE, text("保存定时任务调度日志表成功！"));
	}
	
}