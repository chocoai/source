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
import com.jeesite.modules.fz.order.entity.FzNeigouOrder;
import com.jeesite.modules.fz.order.service.FzNeigouOrderService;

/**
 * 梵赞兑换订单表Controller
 * @author easter
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/order/fzNeigouOrder")
public class FzNeigouOrderController extends BaseController {

	@Autowired
	private FzNeigouOrderService fzNeigouOrderService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzNeigouOrder get(String orderId, boolean isNewRecord) {
		return fzNeigouOrderService.get(orderId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:fzNeigouOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzNeigouOrder fzNeigouOrder, Model model) {
		model.addAttribute("fzNeigouOrder", fzNeigouOrder);
		return "fz/order/fzNeigouOrderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:fzNeigouOrder:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzNeigouOrder> listData(FzNeigouOrder fzNeigouOrder, HttpServletRequest request, HttpServletResponse response) {
		Page<FzNeigouOrder> page = fzNeigouOrderService.findPage(new Page<FzNeigouOrder>(request, response), fzNeigouOrder); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:fzNeigouOrder:view")
	@RequestMapping(value = "form")
	public String form(FzNeigouOrder fzNeigouOrder, Model model) {
		model.addAttribute("fzNeigouOrder", fzNeigouOrder);
		return "fz/order/fzNeigouOrderForm";
	}

	/**
	 * 保存梵赞兑换订单表
	 */
	@RequiresPermissions("order:fzNeigouOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzNeigouOrder fzNeigouOrder) {
		fzNeigouOrderService.save(fzNeigouOrder);
		return renderResult(Global.TRUE, text("保存梵赞兑换订单表成功！"));
	}
	
	/**
	 * 删除梵赞兑换订单表
	 */
	@RequiresPermissions("order:fzNeigouOrder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzNeigouOrder fzNeigouOrder) {
		fzNeigouOrderService.delete(fzNeigouOrder);
		return renderResult(Global.TRUE, text("删除梵赞兑换订单表成功！"));
	}
	
}