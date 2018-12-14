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
import com.jeesite.modules.asset.wechat.entity.WechatK3User;
import com.jeesite.modules.asset.wechat.service.WechatK3UserService;

/**
 * js_wechat_k3_userController
 * @author jace
 * @version 2018-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wechatK3User")
public class WechatK3UserController extends BaseController {

	@Autowired
	private WechatK3UserService wechatK3UserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WechatK3User get(String id, String openId, boolean isNewRecord) {
		return wechatK3UserService.get(new Class<?>[]{String.class, String.class},
				new Object[]{id, openId}, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("wechat:wechatK3User:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatK3User wechatK3User, Model model) {
		model.addAttribute("wechatK3User", wechatK3User);
		return "asset/wechat/wechatK3UserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("wechat:wechatK3User:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WechatK3User> listData(WechatK3User wechatK3User, HttpServletRequest request, HttpServletResponse response) {
		Page<WechatK3User> page = wechatK3UserService.findPage(new Page<WechatK3User>(request, response), wechatK3User); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("wechat:wechatK3User:view")
	@RequestMapping(value = "form")
	public String form(WechatK3User wechatK3User, Model model) {
		model.addAttribute("wechatK3User", wechatK3User);
		return "asset/wechat/wechatK3UserForm";
	}

	/**
	 * 保存js_wechat_k3_user
	 */
	@RequiresPermissions("wechat:wechatK3User:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WechatK3User wechatK3User) {
		wechatK3UserService.save(wechatK3User);
		return renderResult(Global.TRUE, "保存js_wechat_k3_user成功！");
	}
	
	/**
	 * 删除js_wechat_k3_user
	 */
	@RequiresPermissions("wechat:wechatK3User:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(WechatK3User wechatK3User) {
		wechatK3UserService.delete(wechatK3User);
		return renderResult(Global.TRUE, "删除js_wechat_k3_user成功！");
	}
	
}