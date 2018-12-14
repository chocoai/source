/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.service.TbItemImgsService;
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
 * tb_item_imgsController
 * @author jace
 * @version 2018-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbItemImgs")
public class TbItemImgsController extends BaseController {

	@Autowired
	private TbItemImgsService tbItemImgsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbItemImgs get(String id, boolean isNewRecord) {
		return tbItemImgsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:tbItemImgs:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbItemImgs tbItemImgs, Model model) {
		model.addAttribute("tbItemImgs", tbItemImgs);
		return "asset/tianmao/tbItemImgsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tianmao:tbItemImgs:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbItemImgs> listData(TbItemImgs tbItemImgs, HttpServletRequest request, HttpServletResponse response) {
		Page<TbItemImgs> page = tbItemImgsService.findPage(new Page<TbItemImgs>(request, response), tbItemImgs);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:tbItemImgs:view")
	@RequestMapping(value = "form")
	public String form(TbItemImgs tbItemImgs, Model model) {
		model.addAttribute("tbItemImgs", tbItemImgs);
		return "asset/tianmao/tbItemImgsForm";
	}

	/**
	 * 保存tb_item_imgs
	 */
	@RequiresPermissions("tianmao:tbItemImgs:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbItemImgs tbItemImgs) {
		tbItemImgsService.save(tbItemImgs);
		return renderResult(Global.TRUE, "保存tb_item_imgs成功！");
	}
	
	/**
	 * 删除tb_item_imgs
	 */
	@RequiresPermissions("tianmao:tbItemImgs:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbItemImgs tbItemImgs) {
		tbItemImgsService.delete(tbItemImgs);
		return renderResult(Global.TRUE, "删除tb_item_imgs成功！");
	}
	
}