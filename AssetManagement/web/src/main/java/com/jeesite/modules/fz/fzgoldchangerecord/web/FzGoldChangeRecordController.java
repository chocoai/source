/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgoldchangerecord.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.fz.config.AccessLimit;
import com.jeesite.modules.fz.config.IsFileter;
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
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import com.jeesite.modules.fz.fzgoldchangerecord.service.FzGoldChangeRecordService;

/**
 * 梵钻变更记录Controller
 * @author dwh
 * @version 2018-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/fzgoldchangerecord/fzGoldChangeRecord")
public class FzGoldChangeRecordController extends BaseController {

	@Autowired
	private FzGoldChangeRecordService fzGoldChangeRecordService;

	@Autowired
	private AmUtilService amUtilService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzGoldChangeRecord get(String recordCode, boolean isNewRecord) {
		return fzGoldChangeRecordService.get(recordCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fzgoldchangerecord:fzGoldChangeRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzGoldChangeRecord fzGoldChangeRecord, Model model) {
		model.addAttribute("fzGoldChangeRecord", fzGoldChangeRecord);
		return "fz/fzgoldchangerecord/fzGoldChangeRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fzgoldchangerecord:fzGoldChangeRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzGoldChangeRecord> listData(FzGoldChangeRecord fzGoldChangeRecord, HttpServletRequest request, HttpServletResponse response) {
		Page<FzGoldChangeRecord> page = fzGoldChangeRecordService.findPage(new Page<FzGoldChangeRecord>(request, response), fzGoldChangeRecord); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fzgoldchangerecord:fzGoldChangeRecord:view")
	@RequestMapping(value = "form")
	public String form(FzGoldChangeRecord fzGoldChangeRecord, Model model) {
		model.addAttribute("fzGoldChangeRecord", fzGoldChangeRecord);
		return "fz/fzgoldchangerecord/fzGoldChangeRecordForm";
	}

	/**
	 * 保存梵钻变更记录
	 */
	@RequiresPermissions("fzgoldchangerecord:fzGoldChangeRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzGoldChangeRecord fzGoldChangeRecord) {
		fzGoldChangeRecordService.save(fzGoldChangeRecord);
		return renderResult(Global.TRUE, text("保存梵钻变更记录成功！"));
	}
	
	/**
	 * 删除梵钻变更记录
	 */
	@RequiresPermissions("fzgoldchangerecord:fzGoldChangeRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzGoldChangeRecord fzGoldChangeRecord) {
		fzGoldChangeRecordService.delete(fzGoldChangeRecord);
		return renderResult(Global.TRUE, text("删除梵钻变更记录成功！"));
	}
	/**
	 * 新增梵钻变更记录
	 */
	@PostMapping(value = "saveRecord")
	@AccessLimit(limit = 1,sec = 2)
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo saveRecord(FzGoldChangeRecord fzGoldChangeRecord) {
		if (fzGoldChangeRecord.getGoldType()==null||"".equals(fzGoldChangeRecord.getGoldType())){
			return ReturnDate.error(900,"梵钻类型为空");
		}
		fzGoldChangeRecordService.save(fzGoldChangeRecord);
		return ReturnDate.success("新增成功");
	}

	/**
	 * 查询数据接口
	 */
	@RequestMapping(value = "getListData")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getListData(FzGoldChangeRecord fzGoldChangeRecord, HttpServletRequest request, HttpServletResponse response) {
		Page<FzGoldChangeRecord> page = fzGoldChangeRecordService.findPage(new Page<FzGoldChangeRecord>(request, response), fzGoldChangeRecord);
		if (ParamentUntil.isBackList(page.getList())){
			for (int i=0;i<page.getList().size();i++){
				String goldType = amUtilService.findDictLabel(page.getList().get(i).getGoldType(), "fz_gold_type");
				String 	inOrOut = amUtilService.findDictLabel(page.getList().get(i).getInOrOut(), "fz_in_or_out");
				page.getList().get(i).setGoldTypeName(goldType);
				page.getList().get(i).setInOrOutName(inOrOut);
			}
		}
		return ReturnDate.success(page.getList());
	}
}