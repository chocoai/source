/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.web;

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
import com.jeesite.modules.fz.order.entity.FzNeigouOrderItem;
import com.jeesite.modules.fz.order.service.FzNeigouOrderItemService;

/**
 * 梵赞内购订单商品明细表Controller
 * @author easter
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/order/fzNeigouOrderItem")
public class FzNeigouOrderItemController extends BaseController {

	@Autowired
	private FzNeigouOrderItemService fzNeigouOrderItemService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzNeigouOrderItem get(String itemId, boolean isNewRecord) {
		return fzNeigouOrderItemService.get(itemId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	//@RequiresPermissions("order:fzNeigouOrderItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzNeigouOrderItem fzNeigouOrderItem, Model model) {
		model.addAttribute("fzNeigouOrderItem", fzNeigouOrderItem);
		return "fz/order/fzNeigouOrderItemList";
	}
	
	/**
	 * 查询列表数据
	 */
	//@RequiresPermissions("order:fzNeigouOrderItem:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzNeigouOrderItem> listData(FzNeigouOrderItem fzNeigouOrderItem, HttpServletRequest request, HttpServletResponse response) {
		Page<FzNeigouOrderItem> page = fzNeigouOrderItemService.findPage(new Page<FzNeigouOrderItem>(request, response), fzNeigouOrderItem); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:fzNeigouOrderItem:view")
	@RequestMapping(value = "form")
	public String form(FzNeigouOrderItem fzNeigouOrderItem, Model model) {
		model.addAttribute("fzNeigouOrderItem", fzNeigouOrderItem);
		return "fz/order/fzNeigouOrderItemForm";
	}

	/**
	 * 保存梵赞内购订单商品明细表
	 */
	@RequiresPermissions("order:fzNeigouOrderItem:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzNeigouOrderItem fzNeigouOrderItem) {
		fzNeigouOrderItemService.save(fzNeigouOrderItem);
		return renderResult(Global.TRUE, text("保存梵赞内购订单商品明细表成功！"));
	}
	
	/**
	 * 删除梵赞内购订单商品明细表
	 */
	@RequiresPermissions("order:fzNeigouOrderItem:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzNeigouOrderItem fzNeigouOrderItem) {
		fzNeigouOrderItemService.delete(fzNeigouOrderItem);
		return renderResult(Global.TRUE, text("删除梵赞内购订单商品明细表成功！"));
	}
	
}