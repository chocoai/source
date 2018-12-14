/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.tianmao.entity.TbItem;
import com.jeesite.modules.asset.tianmao.service.TbItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * tb_itemController
 * @author jace
 * @version 2018-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbItem")
public class TbItemController extends BaseController {

	@Autowired
	private TbItemService tbItemService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbItem get(String numIid, boolean isNewRecord) {
		return tbItemService.get(numIid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:tbItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbItem tbItem, Model model) {
		model.addAttribute("tbItem", tbItem);
		return "asset/tianmao/tbItemList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tianmao:tbItem:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbItem> listData(TbItem tbItem, HttpServletRequest request, HttpServletResponse response) {
		Page<TbItem> page = tbItemService.findPage(new Page<TbItem>(request, response), tbItem);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:tbItem:view")
	@RequestMapping(value = "form")
	public String form(TbItem tbItem, Model model) {
		model.addAttribute("tbItem", tbItem);
		return "asset/tianmao/tbItemForm";
	}

	/**
	 * 保存tb_item
	 */
	@RequiresPermissions("tianmao:tbItem:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbItem tbItem) {
		tbItemService.save(tbItem);
		return renderResult(Global.TRUE, "保存tb_item成功！");
	}
	
	/**
	 * 删除tb_item
	 */
	@RequiresPermissions("tianmao:tbItem:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbItem tbItem) {
		tbItemService.delete(tbItem);
		return renderResult(Global.TRUE, "删除tb_item成功！");
	}
	
}