///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.web;
//
//import com.jeesite.common.config.Global;
//import com.jeesite.common.entity.Page;
//import com.jeesite.common.web.BaseController;
//import com.jeesite.modules.asset.userroleconfig.entity.BasicUserInfo;
//import com.jeesite.modules.asset.userroleconfig.service.BasicUserInfoService;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 用户表Controller
// * @author dwh
// * @version 2018-07-18
// */
//@Controller
//@RequestMapping(value = "${adminPath}/userroleconfig/basicUserInfo")
//public class BasicUserInfoController extends BaseController {
//
//	@Autowired
//	private BasicUserInfoService basicUserInfoService;
//
//	/**
//	 * 获取数据
//	 */
//	@ModelAttribute
//	public BasicUserInfo get(String userCode, boolean isNewRecord) {
//		return basicUserInfoService.get(userCode, isNewRecord);
//	}
//
//	/**
//	 * 查询列表
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(BasicUserInfo basicUserInfo, Model model) {
//		model.addAttribute("basicUserInfo", basicUserInfo);
//		return "asset/userroleconfig/basicUserInfoList";
//	}
//
//	/**
//	 * 查询列表数据
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:view")
//	@RequestMapping(value = "listData")
//	@ResponseBody
//	public Page<BasicUserInfo> listData(BasicUserInfo basicUserInfo, HttpServletRequest request, HttpServletResponse response) {
//		Page<BasicUserInfo> page = basicUserInfoService.findPage(new Page<BasicUserInfo>(request, response), basicUserInfo);
//		return page;
//	}
//
//	/**
//	 * 查看编辑表单
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:view")
//	@RequestMapping(value = "form")
//	public String form(BasicUserInfo basicUserInfo, Model model) {
//		model.addAttribute("basicUserInfo", basicUserInfo);
//		return "asset/userroleconfig/basicUserInfoForm";
//	}
//
//	/**
//	 * 保存用户表
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:edit")
//	@PostMapping(value = "save")
//	@ResponseBody
//	public String save(@Validated BasicUserInfo basicUserInfo) {
//		basicUserInfoService.save(basicUserInfo);
//		return renderResult(Global.TRUE, "保存用户表成功！");
//	}
//
//	/**
//	 * 停用用户表
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:edit")
//	@RequestMapping(value = "disable")
//	@ResponseBody
//	public String disable(BasicUserInfo basicUserInfo) {
//		basicUserInfo.setStatus(BasicUserInfo.STATUS_DISABLE);
//		basicUserInfoService.updateStatus(basicUserInfo);
//		return renderResult(Global.TRUE, "停用用户表成功");
//	}
//
//	/**
//	 * 启用用户表
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:edit")
//	@RequestMapping(value = "enable")
//	@ResponseBody
//	public String enable(BasicUserInfo basicUserInfo) {
//		basicUserInfo.setStatus(BasicUserInfo.STATUS_NORMAL);
//		basicUserInfoService.updateStatus(basicUserInfo);
//		return renderResult(Global.TRUE, "启用用户表成功");
//	}
//
//	/**
//	 * 删除用户表
//	 */
//	@RequiresPermissions("userroleconfig:basicUserInfo:edit")
//	@RequestMapping(value = "delete")
//	@ResponseBody
//	public String delete(BasicUserInfo basicUserInfo) {
//		basicUserInfoService.delete(basicUserInfo);
//		return renderResult(Global.TRUE, "删除用户表成功！");
//	}
//
//}