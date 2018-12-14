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
import com.jeesite.modules.asset.wechat.entity.WechatK3Mechanism;
import com.jeesite.modules.asset.wechat.service.WechatK3MechanismService;

/**
 * js_wechat_k3_mechanismController
 * @author jace
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wechatK3Mechanism")
public class WechatK3MechanismController extends BaseController {

	@Autowired
	private WechatK3MechanismService wechatK3MechanismService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WechatK3Mechanism get(Long id, boolean isNewRecord) {
		return wechatK3MechanismService.get(String.valueOf(id), isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("wechat:wechatK3Mechanism:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatK3Mechanism wechatK3Mechanism, Model model) {
		model.addAttribute("wechatK3Mechanism", wechatK3Mechanism);
		return "asset/wechat/wechatK3MechanismList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("wechat:wechatK3Mechanism:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WechatK3Mechanism> listData(WechatK3Mechanism wechatK3Mechanism, HttpServletRequest request, HttpServletResponse response) {
		Page<WechatK3Mechanism> page = wechatK3MechanismService.findPage(new Page<WechatK3Mechanism>(request, response), wechatK3Mechanism); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("wechat:wechatK3Mechanism:view")
	@RequestMapping(value = "form")
	public String form(WechatK3Mechanism wechatK3Mechanism, Model model) {
		model.addAttribute("wechatK3Mechanism", wechatK3Mechanism);
		return "asset/wechat/wechatK3MechanismForm";
	}

	/**
	 * 保存js_wechat_k3_mechanism
	 */
	@RequiresPermissions("wechat:wechatK3Mechanism:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WechatK3Mechanism wechatK3Mechanism) {
		wechatK3MechanismService.save(wechatK3Mechanism);
		return renderResult(Global.TRUE, "保存js_wechat_k3_mechanism成功！");
	}
	
	/**
	 * 删除js_wechat_k3_mechanism
	 */
	@RequiresPermissions("wechat:wechatK3Mechanism:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(WechatK3Mechanism wechatK3Mechanism) {
		wechatK3MechanismService.delete(wechatK3Mechanism);
		return renderResult(Global.TRUE, "删除js_wechat_k3_mechanism成功！");
	}
	
}