/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.expendrecord.web;

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
import com.jeesite.modules.fz.expendrecord.entity.FzExpenditureRecord;
import com.jeesite.modules.fz.expendrecord.service.FzExpenditureRecordService;

/**
 * 梵钻支出表Controller
 * @author len
 * @version 2018-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/expendrecord/fzExpenditureRecord")
public class FzExpenditureRecordController extends BaseController {

	@Autowired
	private FzExpenditureRecordService fzExpenditureRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzExpenditureRecord get(String recordCode, boolean isNewRecord) {
		return fzExpenditureRecordService.get(recordCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("expendrecord:fzExpenditureRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzExpenditureRecord fzExpenditureRecord, Model model) {
		model.addAttribute("fzExpenditureRecord", fzExpenditureRecord);
		return "fz/expendrecord/fzExpenditureRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("expendrecord:fzExpenditureRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzExpenditureRecord> listData(FzExpenditureRecord fzExpenditureRecord, HttpServletRequest request, HttpServletResponse response) {
		Page<FzExpenditureRecord> page = fzExpenditureRecordService.findPage(new Page<FzExpenditureRecord>(request, response), fzExpenditureRecord); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("expendrecord:fzExpenditureRecord:view")
	@RequestMapping(value = "form")
	public String form(FzExpenditureRecord fzExpenditureRecord, Model model) {
		model.addAttribute("fzExpenditureRecord", fzExpenditureRecord);
		return "fz/expendrecord/fzExpenditureRecordForm";
	}

	/**
	 * 保存梵钻支出表
	 */
	@RequiresPermissions("expendrecord:fzExpenditureRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzExpenditureRecord fzExpenditureRecord) {
		fzExpenditureRecordService.save(fzExpenditureRecord);
		return renderResult(Global.TRUE, text("保存梵钻支出表成功！"));
	}
}