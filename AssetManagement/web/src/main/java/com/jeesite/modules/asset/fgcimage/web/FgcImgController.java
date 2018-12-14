/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcimage.web;

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
import com.jeesite.modules.asset.fgcimage.entity.FgcImg;
import com.jeesite.modules.asset.fgcimage.service.FgcImgService;

/**
 * 梵工厂图片表Controller
 * @author len
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/fgcimage/fgcImg")
public class FgcImgController extends BaseController {

	@Autowired
	private FgcImgService fgcImgService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FgcImg get(String id, boolean isNewRecord) {
		return fgcImgService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fgcimage:fgcImg:view")
	@RequestMapping(value = {"list", ""})
	public String list(FgcImg fgcImg, Model model) {
		model.addAttribute("fgcImg", fgcImg);
		return "asset/fgcimage/fgcImgList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fgcimage:fgcImg:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FgcImg> listData(FgcImg fgcImg, HttpServletRequest request, HttpServletResponse response) {
		Page<FgcImg> page = fgcImgService.findPage(new Page<FgcImg>(request, response), fgcImg); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fgcimage:fgcImg:view")
	@RequestMapping(value = "form")
	public String form(FgcImg fgcImg, Model model) {
		model.addAttribute("fgcImg", fgcImg);
		return "asset/fgcimage/fgcImgForm";
	}

	/**
	 * 保存梵工厂图片表
	 */
	@RequiresPermissions("fgcimage:fgcImg:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FgcImg fgcImg) {
		fgcImgService.save(fgcImg);
		return renderResult(Global.TRUE, "保存梵工厂图片表成功！");
	}
	
	/**
	 * 删除梵工厂图片表
	 */
	@RequiresPermissions("fgcimage:fgcImg:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FgcImg fgcImg) {
		fgcImgService.delete(fgcImg);
		return renderResult(Global.TRUE, "删除梵工厂图片表成功！");
	}
	
}