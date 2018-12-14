/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
import com.jeesite.modules.fz.report.entity.FzStatisticalItem;
import com.jeesite.modules.fz.report.service.FzStatisticalItemService;

import java.util.List;

/**
 * 一级部门赞赏统计项目Controller
 * @author easter
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/report/fzStatisticalItem")
public class FzStatisticalItemController extends BaseController {

	@Autowired
	private FzStatisticalItemService fzStatisticalItemService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzStatisticalItem get(Long id, boolean isNewRecord) {
		return fzStatisticalItemService.get(id+"", isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	//@RequiresPermissions("report:fzStatisticalItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzStatisticalItem fzStatisticalItem, Model model, String selectData, String checkbox, HttpServletRequest request) {
        String id = request.getParameter("id");
        model.addAttribute("selectData", selectData);
		model.addAttribute("fzStatisticalItem", fzStatisticalItem);
		model.addAttribute("checkbox", checkbox);
		return "fz/report/fzStatisticalItemList";
	}
	
	/**
	 * 查询列表数据
	 */
	//@RequiresPermissions("report:fzStatisticalItem:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<FzStatisticalItem> listData(FzStatisticalItem fzStatisticalItem, HttpServletRequest request, HttpServletResponse response) {
		List<FzStatisticalItem> list = fzStatisticalItemService.findList(fzStatisticalItem);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	//@RequiresPermissions("report:fzStatisticalItem:view")
	@RequestMapping(value = "form")
	public String form(FzStatisticalItem fzStatisticalItem, Model model) {
		model.addAttribute("fzStatisticalItem", fzStatisticalItem);
		return "fz/report/fzStatisticalItemForm";
	}

	/**
	 * 保存一级部门赞赏统计项目
	 */
	//@RequiresPermissions("report:fzStatisticalItem:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzStatisticalItem fzStatisticalItem) {
		fzStatisticalItemService.save(fzStatisticalItem);
		return renderResult(Global.TRUE, text("保存一级部门赞赏统计项目成功！"));
	}
	
	/**
	 * 删除一级部门赞赏统计项目
	 */
	//@RequiresPermissions("report:fzStatisticalItem:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzStatisticalItem fzStatisticalItem) {
		fzStatisticalItemService.delete(fzStatisticalItem);
		return renderResult(Global.TRUE, text("删除一级部门赞赏统计项目成功！"));
	}
	
}