/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.group.web;

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
import com.jeesite.modules.asset.group.entity.GroupPurchase;
import com.jeesite.modules.asset.group.service.GroupPurchaseService;

import java.util.Date;
import java.util.List;

/**
 * 团购信息表Controller
 * @author len
 * @version 2018-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/group/groupPurchase")
public class GroupPurchaseController extends BaseController {

	@Autowired
	private GroupPurchaseService groupPurchaseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public GroupPurchase get(String purchaseCode, boolean isNewRecord) {
		return groupPurchaseService.get(purchaseCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("group:groupPurchase:view")
	@RequestMapping(value = {"list", ""})
	public String list(GroupPurchase groupPurchase, Model model) {
		model.addAttribute("groupPurchase", groupPurchase);
		return "asset/group/groupPurchaseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("group:groupPurchase:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<GroupPurchase> listData(GroupPurchase groupPurchase, HttpServletRequest request, HttpServletResponse response) {
		Page<GroupPurchase> page = groupPurchaseService.findPage(new Page<GroupPurchase>(request, response), groupPurchase); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("group:groupPurchase:view")
	@RequestMapping(value = "form")
	public String form(GroupPurchase groupPurchase, Model model) {
		model.addAttribute("groupPurchase", groupPurchase);
		return "asset/group/groupPurchaseForm";
	}

	/**
	 * 保存团购信息表
	 */
	@RequiresPermissions("group:groupPurchase:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated GroupPurchase groupPurchase) {
		if (groupPurchase.getGroupPhone().length() != 11) {
			return renderResult(Global.FALSE, text("请输入11位的手机号！"));
		}
		if (groupPurchase.getIsNewRecord()) {
			groupPurchase.setCreateTime(new Date());
		} else {
			GroupPurchase purchase = new GroupPurchase();
			purchase.setGroupPhone(groupPurchase.getGroupPhone());
			List<GroupPurchase> purchaseList = groupPurchaseService.findList(purchase);
			if (purchaseList != null && purchaseList.size() > 0) {
				for (GroupPurchase groupPurchase1 : purchaseList) {
					if (groupPurchase.getPurchaseCode().equals(groupPurchase1.getPurchaseCode())) {
						continue;
					}
					return renderResult(Global.FALSE, text("存在相同的团长手机号,团长:"+ groupPurchase1.getWangCode()));
				}
			}
			String msg = groupPurchaseService.getMembers(groupPurchase);
			if (!"".equals(msg)) {
				return renderResult(Global.FALSE, text("存在同样的旺旺id！"));
			}
		}
		groupPurchaseService.save(groupPurchase);
		return renderResult(Global.TRUE, text("保存团购信息表成功！"));
	}
	
	/**
	 * 删除团购信息表
	 */
	@RequiresPermissions("group:groupPurchase:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(GroupPurchase groupPurchase) {
		groupPurchaseService.delete(groupPurchase);
		return renderResult(Global.TRUE, text("删除团购信息表成功！"));
	}
	
}