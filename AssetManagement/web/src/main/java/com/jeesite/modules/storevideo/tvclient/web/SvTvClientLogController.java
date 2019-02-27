/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.web;

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
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClientLog;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientLogService;

/**
 * 电视客户端日志Controller
 * @author Philip Guan
 * @version 2019-02-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/tvclientlog")
public class SvTvClientLogController extends BaseController {

	@Autowired
	private SvTvClientLogService svTvClientLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvTvClientLog get(String logCode, boolean isNewRecord) {
		return svTvClientLogService.get(logCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:tvclientlog:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvTvClientLog svTvClientLog, Model model) {
		model.addAttribute("svTvClientLog", svTvClientLog);
		return "storevideo/tvclient/svTvClientLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:tvclientlog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvTvClientLog> listData(SvTvClientLog svTvClientLog, HttpServletRequest request, HttpServletResponse response) {
		Page<SvTvClientLog> page = svTvClientLogService.findPage(new Page<SvTvClientLog>(request, response), svTvClientLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:tvclientlog:view")
	@RequestMapping(value = "form")
	public String form(SvTvClientLog svTvClientLog, Model model) {
		model.addAttribute("svTvClientLog", svTvClientLog);
		return "modules/storevideo/svTvClientLogForm";
	}

	/**
	 * 保存电视客户端日志
	 */
	@RequiresPermissions("sv:tvclientlog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvTvClientLog svTvClientLog) {
		svTvClientLogService.save(svTvClientLog);
		return renderResult(Global.TRUE, text("保存电视客户端日志成功！"));
	}
	
}