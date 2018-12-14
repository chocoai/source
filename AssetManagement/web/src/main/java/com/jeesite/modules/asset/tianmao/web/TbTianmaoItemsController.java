/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.tianmao.entity.TbTianmaoItems;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
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
 * tb_tianmao_itemsController
 * @author jace
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbTianmaoItems")
public class TbTianmaoItemsController extends BaseController {

	@Autowired
	private TbTianmaoItemsService tbTianmaoItemsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbTianmaoItems get(String id, boolean isNewRecord) {
		return tbTianmaoItemsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:tbTianmaoItems:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbTianmaoItems tbTianmaoItems, Model model) {
		model.addAttribute("tbTianmaoItems", tbTianmaoItems);
		return "asset/tianmao/tbTianmaoItemsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tianmao:tbTianmaoItems:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbTianmaoItems> listData(TbTianmaoItems tbTianmaoItems, HttpServletRequest request, HttpServletResponse response) {
		Page<TbTianmaoItems> page = tbTianmaoItemsService.findPage(new Page<TbTianmaoItems>(request, response), tbTianmaoItems);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:tbTianmaoItems:view")
	@RequestMapping(value = "form")
	public String form(TbTianmaoItems tbTianmaoItems, Model model) {
		model.addAttribute("tbTianmaoItems", tbTianmaoItems);
		return "asset/tianmao/tbTianmaoItemsForm";
	}

	/**
	 * 保存tb_tianmao_items
	 */
	@RequiresPermissions("tianmao:tbTianmaoItems:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbTianmaoItems tbTianmaoItems) {
		tbTianmaoItemsService.save(tbTianmaoItems);
		return renderResult(Global.TRUE, "保存tb_tianmao_items成功！");
	}
	
	/**
	 * 删除tb_tianmao_items
	 */
	@RequiresPermissions("tianmao:tbTianmaoItems:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbTianmaoItems tbTianmaoItems) {
		tbTianmaoItemsService.delete(tbTianmaoItems);
		return renderResult(Global.TRUE, "删除tb_tianmao_items成功！");
	}
	
}