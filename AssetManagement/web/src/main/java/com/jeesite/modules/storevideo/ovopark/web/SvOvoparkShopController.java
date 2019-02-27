/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.storevideo.ovopark.api.ApiService;
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
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkShop;
import com.jeesite.modules.storevideo.ovopark.service.SvOvoparkShopService;

import java.util.List;

/**
 * 万店掌门店Controller
 * @author Philip Guan
 * @version 2019-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/ovopark/shop")
public class SvOvoparkShopController extends BaseController {

	@Autowired
	private SvOvoparkShopService svOvoparkShopService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvOvoparkShop get(String shopCode, boolean isNewRecord) {
		return svOvoparkShopService.get(shopCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:ovopark:shop:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvOvoparkShop svOvoparkShop, Model model) {
		model.addAttribute("svOvoparkShop", svOvoparkShop);
		return "storevideo/ovopark/shop/list";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:ovopark:shop:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvOvoparkShop> listData(SvOvoparkShop svOvoparkShop, HttpServletRequest request, HttpServletResponse response) {
		Page<SvOvoparkShop> page = svOvoparkShopService.findPage(new Page<SvOvoparkShop>(request, response), svOvoparkShop); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:ovopark:shop:view")
	@RequestMapping(value = "form")
	public String form(SvOvoparkShop svOvoparkShop, Model model) {
		model.addAttribute("svOvoparkShop", svOvoparkShop);
		return "storevideo/ovopark/shop/form";
	}

	/**
	 * 保存万店掌门店
	 */
	@RequiresPermissions("sv:ovopark:shop:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvOvoparkShop svOvoparkShop) {
		svOvoparkShopService.save(svOvoparkShop);
		return renderResult(Global.TRUE, text("保存万店掌门店成功！"));
	}
	
}