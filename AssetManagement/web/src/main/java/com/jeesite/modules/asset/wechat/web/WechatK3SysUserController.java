/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.web;

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
import com.jeesite.modules.asset.wechat.entity.WechatK3SysUser;
import com.jeesite.modules.asset.wechat.service.WechatK3SysUserService;

/**
 * js_wechat_k3_sys_userController
 * @author jace
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wechatK3SysUser")
public class WechatK3SysUserController extends BaseController {

	@Autowired
	private WechatK3SysUserService wechatK3SysUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WechatK3SysUser get(Long id, boolean isNewRecord) {
		return wechatK3SysUserService.get(String.valueOf(id), isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("wechat:wechatK3SysUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatK3SysUser wechatK3SysUser, Model model) {
		model.addAttribute("wechatK3SysUser", wechatK3SysUser);
		return "asset/wechat/wechatK3SysUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("wechat:wechatK3SysUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WechatK3SysUser> listData(WechatK3SysUser wechatK3SysUser, HttpServletRequest request, HttpServletResponse response) {
		Page<WechatK3SysUser> page = wechatK3SysUserService.findPage(new Page<WechatK3SysUser>(request, response), wechatK3SysUser); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("wechat:wechatK3SysUser:view")
	@RequestMapping(value = "form")
	public String form(WechatK3SysUser wechatK3SysUser, Model model) {
		model.addAttribute("wechatK3SysUser", wechatK3SysUser);
		return "asset/wechat/wechatK3SysUserForm";
	}

	/**
	 * 保存js_wechat_k3_sys_user
	 */
	@RequiresPermissions("wechat:wechatK3SysUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WechatK3SysUser wechatK3SysUser) {
		wechatK3SysUserService.save(wechatK3SysUser);
		return renderResult(Global.TRUE, "保存js_wechat_k3_sys_user成功！");
	}
	
	/**
	 * 删除js_wechat_k3_sys_user
	 */
	@RequiresPermissions("wechat:wechatK3SysUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(WechatK3SysUser wechatK3SysUser) {
		wechatK3SysUserService.delete(wechatK3SysUser);
		return renderResult(Global.TRUE, "删除js_wechat_k3_sys_user成功！");
	}
	
}