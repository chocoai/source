/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ampersonnel.web;

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
import com.jeesite.modules.asset.ampersonnel.entity.AmPersonnel;
import com.jeesite.modules.asset.ampersonnel.service.AmPersonnelService;

/**
 * 有效人员维护表Controller
 * @author mclaran
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ampersonnel/amPersonnel")
public class AmPersonnelController extends BaseController {

	@Autowired
	private AmPersonnelService amPersonnelService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmPersonnel get(String personnelCode, boolean isNewRecord) {
		return amPersonnelService.get(personnelCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmPersonnel amPersonnel, Model model) {
		model.addAttribute("amPersonnel", amPersonnel);
		return "asset/ampersonnel/amPersonnelList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmPersonnel> listData(AmPersonnel amPersonnel, HttpServletRequest request, HttpServletResponse response) {
		Page<AmPersonnel> page = amPersonnelService.findPage(new Page<AmPersonnel>(request, response), amPersonnel); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:view")
	@RequestMapping(value = "form")
	public String form(AmPersonnel amPersonnel, Model model) {
		model.addAttribute("amPersonnel", amPersonnel);
		return "asset/ampersonnel/amPersonnelForm";
	}

	/**
	 * 保存有效人员维护表
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmPersonnel amPersonnel) {
		amPersonnelService.save(amPersonnel);
		return renderResult(Global.TRUE, "保存有效人员维护表成功！");
	}
	
	/**
	 * 停用有效人员维护表
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AmPersonnel amPersonnel) {
		amPersonnel.setStatus(AmPersonnel.STATUS_DISABLE);
		amPersonnelService.updateStatus(amPersonnel);
		return renderResult(Global.TRUE, "停用有效人员维护表成功");
	}
	
	/**
	 * 启用有效人员维护表
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AmPersonnel amPersonnel) {
		amPersonnel.setStatus(AmPersonnel.STATUS_NORMAL);
		amPersonnelService.updateStatus(amPersonnel);
		return renderResult(Global.TRUE, "启用有效人员维护表成功");
	}
	
	/**
	 * 删除有效人员维护表
	 */
	@RequiresPermissions("ampersonnel:amPersonnel:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmPersonnel amPersonnel) {
		amPersonnelService.delete(amPersonnel);
		return renderResult(Global.TRUE, "删除有效人员维护表成功！");
	}

	/*根据电话号码查询用户是否有权登录*/
	@RequestMapping(value = "check")
	@ResponseBody
	public String check(String phone){
		String msg="1";
		int count = amPersonnelService.check(phone);
		if(count==0){
			msg="0";
		}
		return renderResult(Global.TRUE, msg);
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public String update(String phone,boolean isNewRecord){
		String msg="1";
		if(isNewRecord){

			int count = amPersonnelService.check(phone);
			if(count==0){
				msg="0";
			}
		}else{
			msg="0";
		}

		return renderResult(Global.TRUE, msg);
	}

}