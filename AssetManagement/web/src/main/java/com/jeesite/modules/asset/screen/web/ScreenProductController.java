/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.service.AmSeqService;
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
import com.jeesite.modules.asset.screen.entity.ScreenProduct;
import com.jeesite.modules.asset.screen.service.ScreenProductService;

/**
 * 零售家产品详情Controller
 * @author len
 * @version 2019-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/screen/screenProduct")
public class ScreenProductController extends BaseController {

	@Autowired
	private ScreenProductService screenProductService;
	@Autowired
	private AmSeqService amSeqService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ScreenProduct get(String productCode, boolean isNewRecord) {
		return screenProductService.get(productCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("screen:screenProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScreenProduct screenProduct, Model model) {
		model.addAttribute("screenProduct", screenProduct);
		return "asset/screen/screenProductList";
	}

	@RequiresPermissions("screen:screenProduct:view")
	@RequestMapping(value = {"productSelect", ""})
	public String productSelect(ScreenProduct screenProduct, Model model, String selectData) {
		model.addAttribute("selectData", selectData);
		model.addAttribute("screenProduct", screenProduct);
		return "asset/screen/productSelect";
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("screen:screenProduct:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ScreenProduct> listData(ScreenProduct screenProduct, HttpServletRequest request, HttpServletResponse response) {
		Page<ScreenProduct> page = screenProductService.findPage(new Page<ScreenProduct>(request, response), screenProduct); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("screen:screenProduct:view")
	@RequestMapping(value = "form")
	public String form(ScreenProduct screenProduct, Model model) {
		if (screenProduct.getIsNewRecord()) {
			screenProduct.setProductCode(amSeqService.getCode("CP", "yyyyMMdd", 4));
		}
		model.addAttribute("screenProduct", screenProduct);
		return "asset/screen/screenProductForm";
	}

	/**
	 * 保存零售家产品详情
	 */
	@RequiresPermissions("screen:screenProduct:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ScreenProduct screenProduct) {
		screenProductService.save(screenProduct);
		return renderResult(Global.TRUE, text("保存零售家产品详情成功！"));
	}
	
	/**
	 * 删除零售家产品详情
	 */
	@RequiresPermissions("screen:screenProduct:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ScreenProduct screenProduct) {
		screenProductService.delete(screenProduct);
		return renderResult(Global.TRUE, text("删除零售家产品详情成功！"));
	}
	
}