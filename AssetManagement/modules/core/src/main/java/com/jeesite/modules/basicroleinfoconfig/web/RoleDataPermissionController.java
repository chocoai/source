/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.basicroleinfoconfig.entity.RoleDataPermission;
import com.jeesite.modules.basicroleinfoconfig.service.RoleDataPermissionService;
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
 * 角色数据权限Controller
 * @author dwh
 * @version 2018-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basicroleinfoconfig/roleDataPermission")
public class RoleDataPermissionController extends BaseController {

	@Autowired
	private RoleDataPermissionService roleDataPermissionService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public RoleDataPermission get(String permissionCode, boolean isNewRecord) {
		return roleDataPermissionService.get(permissionCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("basicroleinfoconfig:roleDataPermission:view")
	@RequestMapping(value = {"list", ""})
	public String list(RoleDataPermission roleDataPermission, Model model) {
		model.addAttribute("roleDataPermission", roleDataPermission);
		return "asset/basicroleinfoconfig/roleDataPermissionList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("basicroleinfoconfig:roleDataPermission:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<RoleDataPermission> listData(RoleDataPermission roleDataPermission, HttpServletRequest request, HttpServletResponse response) {
		Page<RoleDataPermission> page = roleDataPermissionService.findPage(new Page<RoleDataPermission>(request, response), roleDataPermission);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("basicroleinfoconfig:roleDataPermission:view")
	@RequestMapping(value = "form")
	public String form(RoleDataPermission roleDataPermission, Model model) {
		model.addAttribute("roleDataPermission", roleDataPermission);
		return "asset/basicroleinfoconfig/roleDataPermissionForm";
	}

	/**
	 * 保存角色数据权限
	 */
	@RequiresPermissions("basicroleinfoconfig:roleDataPermission:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated RoleDataPermission roleDataPermission) {
		roleDataPermissionService.save(roleDataPermission);
		return renderResult(Global.TRUE, "保存角色数据权限成功！");
	}
	/**
	 * 删除角色数据权限
	 */
	@RequiresPermissions("basicroleinfoconfig:roleDataPermission:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(RoleDataPermission roleDataPermission) {
		roleDataPermissionService.delete(roleDataPermission);
		return renderResult(Global.TRUE, "删除角色数据权限成功！");
	}

}