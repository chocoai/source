/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.department.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.amspecimen.entity.AmSpecimen;
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
import com.jeesite.modules.asset.department.entity.DepartmentTok3;
import com.jeesite.modules.asset.department.service.DepartmentTok3Service;

/**
 * 部门关联K3关系表Controller
 * @author Scarlett
 * @version 2018-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/department/departmentTok3")
public class DepartmentTok3Controller extends BaseController {

	@Autowired
	private DepartmentTok3Service departmentTok3Service;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DepartmentTok3 get(String departmentCode, boolean isNewRecord) {
		return departmentTok3Service.get(departmentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("department:departmentTok3:view")
	@RequestMapping(value = {"list", ""})
	public String list(DepartmentTok3 departmentTok3, Model model) {
		model.addAttribute("departmentTok3", departmentTok3);
		return "asset/department/departmentTok3List";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("department:departmentTok3:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DepartmentTok3> listData(DepartmentTok3 departmentTok3, HttpServletRequest request, HttpServletResponse response) {
		Page<DepartmentTok3> page = departmentTok3Service.findPage(new Page<DepartmentTok3>(request, response), departmentTok3); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("department:departmentTok3:view")
	@RequestMapping(value = "form")
	public String form(DepartmentTok3 departmentTok3, Model model) {
		model.addAttribute("departmentTok3", departmentTok3);
		return "asset/department/departmentTok3Form";
	}

	/**
	 * 保存部门关联K3关系表
	 */
	@RequiresPermissions("department:departmentTok3:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save( DepartmentTok3 departmentTok3) {
		departmentTok3Service.save(departmentTok3);
		return renderResult(Global.TRUE, "保存部门关联K3关系表成功！");
	}
	
	/**
	 * 删除部门关联K3关系表
	 */
	@RequiresPermissions("department:departmentTok3:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DepartmentTok3 departmentTok3) {
		departmentTok3Service.delete(departmentTok3);
		return renderResult(Global.TRUE, "删除部门关联K3关系表成功！");
	}
	/**
	 * 物理删除
	 */
	@RequiresPermissions("department:departmentTok3:edit")
	@RequestMapping(value = "deleteDb")
	@ResponseBody
	public String deleteDb(String ids) {
		if (ids != null && ids.length() > 0) {
			String[] codeList = ids.split(",");
			for (int i = 0; i < codeList.length; i++) {
             departmentTok3Service.deleteData(codeList[i]);
			}
			return renderResult(Global.TRUE, "成功删除部门关联K3关系记录！");
		}else{
			return renderResult(Global.FALSE, "未找到需要删除的部门关联K3关系记录！");
		}

	}
}