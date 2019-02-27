/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.archives.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
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
import com.jeesite.modules.storevideo.archives.entity.SvArchives;
import com.jeesite.modules.storevideo.archives.service.SvArchivesService;

/**
 * 产品推送基础档案Controller
 * @author len
 * @version 2019-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/archives/svArchives")
public class SvArchivesController extends BaseController {

	@Autowired
	private SvArchivesService svArchivesService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvArchives get(String archivesCode, boolean isNewRecord) {
		return svArchivesService.get(archivesCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("archives:svArchives:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvArchives svArchives, Model model) {
		model.addAttribute("svArchives", svArchives);
		return "storevideo/archives/svArchivesList";
	}
	@RequiresPermissions("archives:svArchives:view")
	@RequestMapping(value = {"goodsList", ""})
	public String goodsList(TbProduct tbProduct, Model model, String selectData) {
		model.addAttribute("tbProduct", tbProduct);
		model.addAttribute("selectData", selectData);
		return "storevideo/archives/goodsSelect";
	}
	@Autowired
	private TbProductService tbProductService;
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("archives:svArchives:view")
	@RequestMapping(value = "goodsListData")
	@ResponseBody
	public Page<TbProduct> goodsListData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
		Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct);
		return page;
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("archives:svArchives:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvArchives> listData(SvArchives svArchives, HttpServletRequest request, HttpServletResponse response) {
		Page<SvArchives> page = svArchivesService.findPage(new Page<SvArchives>(request, response), svArchives); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("archives:svArchives:view")
	@RequestMapping(value = "form")
	public String form(SvArchives svArchives, Model model) {
		TbProduct tbProduct = tbProductService.get(svArchives.getNumIid());
		svArchives.setTbProduct(tbProduct);
		model.addAttribute("svArchives", svArchives);
		return "storevideo/archives/svArchivesForm";
	}

	/**
	 * 保存产品推送基础档案
	 */
	@RequiresPermissions("archives:svArchives:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvArchives svArchives) {
		svArchivesService.save(svArchives);
		return renderResult(Global.TRUE, text("保存产品推送基础档案成功！"));
	}
	
	/**
	 * 删除产品推送基础档案
	 */
	@RequiresPermissions("archives:svArchives:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SvArchives svArchives) {
		svArchivesService.delete(svArchives);
		return renderResult(Global.TRUE, text("删除产品推送基础档案成功！"));
	}
	
}