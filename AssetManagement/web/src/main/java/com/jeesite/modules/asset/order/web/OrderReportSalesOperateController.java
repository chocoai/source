/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.TimeUtils;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
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
import com.jeesite.modules.asset.order.entity.OrderReportSalesOperate;
import com.jeesite.modules.asset.order.service.OrderReportSalesOperateService;

import java.util.Calendar;
import java.util.Date;

/**
 * 梵导购操作统计报表Controller
 * @author Albert
 * @version 2019-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderReportSalesOperate")
public class OrderReportSalesOperateController extends BaseController {

	@Autowired
	private OrderReportSalesOperateService orderReportSalesOperateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public OrderReportSalesOperate get(String reportId, boolean isNewRecord) {
		return orderReportSalesOperateService.get(reportId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:orderReportSalesOperate:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderReportSalesOperate orderReportSalesOperate, Model model) {
		model.addAttribute("orderReportSalesOperate", orderReportSalesOperate);
		return "asset/order/orderReportSalesOperateList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:orderReportSalesOperate:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<OrderReportSalesOperate> listData(OrderReportSalesOperate orderReportSalesOperate, HttpServletRequest request, HttpServletResponse response) {
		//默认查询近七天数据
		//当前时间
		Date date = new Date();
		if (orderReportSalesOperate.getDate_gte() == null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH,-6);
			orderReportSalesOperate.setDate_gte(calendar.getTime());
		}
		if (orderReportSalesOperate.getDate_lte() == null){
			orderReportSalesOperate.setDate_lte(date);
		}
		Page<OrderReportSalesOperate> page = orderReportSalesOperateService.findPage(new Page<OrderReportSalesOperate>(request, response), orderReportSalesOperate); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:orderReportSalesOperate:view")
	@RequestMapping(value = "form")
	public String form(OrderReportSalesOperate orderReportSalesOperate, Model model) {
		model.addAttribute("orderReportSalesOperate", orderReportSalesOperate);
		return "asset/order/orderReportSalesOperateForm";
	}

	/**
	 * 保存梵导购操作统计报表
	 */
	@RequiresPermissions("order:orderReportSalesOperate:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated OrderReportSalesOperate orderReportSalesOperate) {
		orderReportSalesOperateService.save(orderReportSalesOperate);
		return renderResult(Global.TRUE, text("保存梵导购操作统计报表成功！"));
	}
	
}