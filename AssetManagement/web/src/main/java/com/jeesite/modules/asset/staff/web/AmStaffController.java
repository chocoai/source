/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryOrder;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.asset.consumables.entity.AmOutStorage;
import com.jeesite.modules.asset.consumables.service.AmOutStorageService;
import com.jeesite.modules.sys.entity.Post;
import com.jeesite.modules.sys.service.PostService;
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
import com.jeesite.modules.asset.staff.entity.AmStaff;
import com.jeesite.modules.asset.staff.service.AmStaffService;

import java.util.List;

/**
 * 员工资料表Controller
 * @author czy
 * @version 2018-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/staff/amStaff")
public class AmStaffController extends BaseController {

	@Autowired
	private AmStaffService amStaffService;
	@Autowired
	private PostService postService;
	@Autowired
	private AmOutStorageService amOutStorageService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmStaff get(String staffCode, boolean isNewRecord) {
		return amStaffService.get(staffCode, isNewRecord);
	}

	@RequiresPermissions("staff:amStaff:view")
	@RequestMapping(value = "index")
	public String index(AmStaff amStaff, Model model) {
		return "asset/staff/amStaffIndex";
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("staff:amStaff:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmStaff amStaff, Model model) {
		Post post = new Post();
		model.addAttribute("postList",postService.findList(post)); // 查询岗位信息
		model.addAttribute("amStaff", amStaff);
		return "asset/staff/amStaffList";
	}

	/**
	 * 查询列表
	 */
//	@RequiresPermissions("staff:amStaff:view")
	@RequestMapping(value = {"staffSelect", ""})
	public String staffSelect(AmStaff amStaff, Model model,String selectData) {
		model.addAttribute("selectData", selectData);
		Post post = new Post();
		model.addAttribute("postList",postService.findList(post)); // 查询岗位信息
		model.addAttribute("amStaff", amStaff);
		return "asset/staff/amStaffSelect";
	}
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("staff:amStaff:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmStaff> listData(AmStaff amStaff, HttpServletRequest request, HttpServletResponse response) {
		amStaff.getOffice().setIsQueryChildren(true);
		Page<AmStaff> page = amStaffService.findPage(new Page<AmStaff>(request, response), amStaff);
		// 列表页面岗位的获取
		for (int i = 0; i < page.getList().size(); i++){
			AmStaff staff = page.getList().get(i);
			List<String> list = amStaffService.findPostName(staff.getStaffCode());
			String postName = list.toString();
			postName = postName.replace("[","");
			postName = postName.replace("]","");
			staff.setPostName(postName);
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("staff:amStaff:view")
	@RequestMapping(value = "form")
	public String form(AmStaff amStaff, Model model) {
		Post post = new Post();
		model.addAttribute("postList",postService.findList(post)); // 获取岗位信息
		// 获取当前用户所拥有的岗位
		if (StringUtils.isNotBlank(amStaff.getStaffCode())){
			amStaff.setAmStaffPostList(amStaffService.findAmStaffPostList(amStaff));
		}
		model.addAttribute("amStaff", amStaff);
		return "asset/staff/amStaffForm";
	}

	/**
	 * 保存员工资料表
	 */
	@RequiresPermissions("staff:amStaff:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmStaff amStaff) {
//		amStaffService.updateStatus(amStaff);
		amStaffService.save(amStaff);
		return renderResult(Global.TRUE, "保存员工资料表成功！");
	}
	
	/**
	 * 停用员工资料表
	 */
	@RequiresPermissions("staff:amStaff:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AmStaff amStaff) {
		amStaff.setStatus(AmStaff.STATUS_DISABLE);
		amStaffService.updateStatus(amStaff);
		return renderResult(Global.TRUE, "停用员工资料表成功");
	}
	
	/**
	 * 启用员工资料表
	 */
	@RequiresPermissions("staff:amStaff:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AmStaff amStaff) {
		amStaff.setStatus(AmStaff.STATUS_NORMAL);
		amStaffService.updateStatus(amStaff);
		return renderResult(Global.TRUE, "启用员工资料表成功");
	}
	
	/**
	 * 删除员工资料表
	 */
	@RequiresPermissions("staff:amStaff:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmStaff amStaff) {
		//删除员工前判断该员工有没有出库单据
		AmOutStorage amOutStorage=new AmOutStorage();
		amOutStorage.setStaffCode(amStaff.getStaffCode());
		List<AmOutStorage> amOutStorageList=amOutStorageService.findList(amOutStorage);
		if (amOutStorageList!=null&&amOutStorageList.size()>0){
			return renderResult(Global.FALSE, "删除员工资料表失败！出库单据发生业务");
		}
		amStaffService.delete(amStaff);
		return renderResult(Global.TRUE, "删除员工资料表成功！");
	}
}