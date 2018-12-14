/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzlogin.web;

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
import com.jeesite.modules.fz.fzlogin.entity.FzLoginRecord;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;

/**
 * 梵赞登录记录Controller
 * @author len
 * @version 2018-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/fzlogin/fzLoginRecord")
public class FzLoginRecordController extends BaseController {

	@Autowired
	private FzLoginRecordService fzLoginRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzLoginRecord get(String recordCode, boolean isNewRecord) {
		return fzLoginRecordService.get(recordCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fzlogin:fzLoginRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzLoginRecord fzLoginRecord, Model model) {
		model.addAttribute("fzLoginRecord", fzLoginRecord);
		return "fz/fzlogin/fzLoginRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fzlogin:fzLoginRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzLoginRecord> listData(FzLoginRecord fzLoginRecord, HttpServletRequest request, HttpServletResponse response) {
		Page<FzLoginRecord> page = fzLoginRecordService.findPage(new Page<FzLoginRecord>(request, response), fzLoginRecord); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fzlogin:fzLoginRecord:view")
	@RequestMapping(value = "form")
	public String form(FzLoginRecord fzLoginRecord, Model model) {
		model.addAttribute("fzLoginRecord", fzLoginRecord);
		return "fz/fzlogin/fzLoginRecordForm";
	}

	/**
	 * 保存梵赞登录记录
	 */
	@RequiresPermissions("fzlogin:fzLoginRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzLoginRecord fzLoginRecord) {
		fzLoginRecordService.save(fzLoginRecord);
		return renderResult(Global.TRUE, text("保存梵赞登录记录成功！"));
	}
	
}