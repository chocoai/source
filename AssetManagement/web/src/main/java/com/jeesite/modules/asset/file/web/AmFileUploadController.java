/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.file.web;

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
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;

/**
 * 文件上传表Controller
 * @author len
 * @version 2018-08-10
 */
@Controller
@RequestMapping(value = "${adminPath}/file/amFileUpload")
public class AmFileUploadController extends BaseController {

	@Autowired
	private AmFileUploadService amFileUploadService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmFileUpload get(String id, boolean isNewRecord) {
		return amFileUploadService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("file:amFileUpload:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmFileUpload amFileUpload, Model model) {
		model.addAttribute("amFileUpload", amFileUpload);
		return "asset/file/amFileUploadList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("file:amFileUpload:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmFileUpload> listData(AmFileUpload amFileUpload, HttpServletRequest request, HttpServletResponse response) {
		Page<AmFileUpload> page = amFileUploadService.findPage(new Page<AmFileUpload>(request, response), amFileUpload); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("file:amFileUpload:view")
	@RequestMapping(value = "form")
	public String form(AmFileUpload amFileUpload, Model model) {
		model.addAttribute("amFileUpload", amFileUpload);
		return "asset/file/amFileUploadForm";
	}

	/**
	 * 保存文件上传表
	 */
	@RequiresPermissions("file:amFileUpload:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmFileUpload amFileUpload) {
		amFileUploadService.save(amFileUpload);
		return renderResult(Global.TRUE, "保存文件上传表成功！");
	}
	
	/**
	 * 删除文件上传表
	 */
	@RequiresPermissions("file:amFileUpload:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmFileUpload amFileUpload) {
		amFileUploadService.delete(amFileUpload);
		return renderResult(Global.TRUE, "删除文件上传表成功！");
	}

}