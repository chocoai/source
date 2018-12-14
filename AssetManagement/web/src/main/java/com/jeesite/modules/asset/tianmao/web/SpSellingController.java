/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
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
import com.jeesite.modules.asset.tianmao.entity.SpSelling;
import com.jeesite.modules.asset.tianmao.service.SpSellingService;

/**
 * 出售中的商品列表Controller
 * @author dwh
 * @version 2018-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/spSelling")
public class SpSellingController extends BaseController {

	@Autowired
	private SpSellingService spSellingService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SpSelling get(String sellCode, boolean isNewRecord) {
		return spSellingService.get(sellCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:spSelling:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpSelling spSelling, Model model) {
		model.addAttribute("spSelling", spSelling);
		return "asset/tianmao/spSellingList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tianmao:spSelling:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SpSelling> listData(SpSelling spSelling, HttpServletRequest request, HttpServletResponse response) {
		Page<SpSelling> page = spSellingService.findPage(new Page<SpSelling>(request, response), spSelling); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:spSelling:view")
	@RequestMapping(value = "form")
	public String form(SpSelling spSelling, Model model) {
		model.addAttribute("spSelling", spSelling);
		return "asset/tianmao/spSellingForm";
	}

	/**
	 * 保存出售中的商品列表
	 */
	@RequiresPermissions("tianmao:spSelling:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SpSelling spSelling) {
		spSellingService.save(spSelling);
		return renderResult(Global.TRUE, "保存出售中的商品列表成功！");
	}
	
	/**
	 * 删除出售中的商品列表
	 */
	@RequiresPermissions("tianmao:spSelling:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SpSelling spSelling) {
		spSellingService.delete(spSelling);
		return renderResult(Global.TRUE, "删除出售中的商品列表成功！");
	}
	@RequestMapping(value = "synSellingYZY")
	@ResponseBody
	public ReturnInfo synSellingYZY() {
		boolean rst= false;
		try {
			rst = spSellingService.synSellingYZY();
		} catch (Exception e) {
			return ReturnDate.error(-1,"获取有赞云数据失败");
		}
		if (rst){
			return ReturnDate.success();
		}else {
			return ReturnDate.error(-1,"同步失败");
		}
	}
}