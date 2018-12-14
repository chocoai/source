/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.synthetical.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.dict.DictService;
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
import com.jeesite.modules.achievement.synthetical.entity.AchSynthetical;
import com.jeesite.modules.achievement.synthetical.service.AchSyntheticalService;

import java.util.Date;
import java.util.List;
import com.jeesite.modules.util.dict.DictService;

/**
 * 绩效综合管理Controller
 * @author len
 * @version 2018-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/synthetical/achSynthetical")
public class AchSyntheticalController extends BaseController {

	@Autowired
	private AchSyntheticalService achSyntheticalService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchSynthetical get(String syntheticalCode, boolean isNewRecord) {
		return achSyntheticalService.get(syntheticalCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("synthetical:achSynthetical:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchSynthetical achSynthetical, Model model) {
		model.addAttribute("achSynthetical", achSynthetical);
		return "achievement/synthetical/achSyntheticalList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("synthetical:achSynthetical:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchSynthetical> listData(AchSynthetical achSynthetical, HttpServletRequest request, HttpServletResponse response) {
		Page<AchSynthetical> page = achSyntheticalService.findPage(new Page<AchSynthetical>(request, response), achSynthetical); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("synthetical:achSynthetical:view")
	@RequestMapping(value = "form")
	public String form(AchSynthetical achSynthetical, Model model) {




		model.addAttribute("disabledStatus", achSynthetical.getDataType() != null && achSynthetical.getDataType().equals("2")?"\" disabled cc=\"":"");

		List<DictData> dictTargetList = DictService.getChildrenByDesc("ach_examine_group", "职场综合");
		model.addAttribute("scoreGroupList", dictTargetList);

		if (achSynthetical.getIsNewRecord()) {
			achSynthetical.setDataType("0");
			achSynthetical.setDisableStatus("0");
		}
		// 禁用状态默认否
		String disableVal = DictUtils.getDictLabel("sys_yes_no", achSynthetical.getDisableStatus(), "否");
		achSynthetical.setDisableVal(disableVal);

		String dataVal = DictUtils.getDictLabel("supplier_status", achSynthetical.getDataType(), "创建");
		achSynthetical.setDataVal(dataVal);
		String examineVal = DictUtils.getDictLabel("ach_examine_type", achSynthetical.getExamineType(), "");
		achSynthetical.setExamineVal(examineVal);
		if ("2".equals(achSynthetical.getDataType())) {
			achSynthetical.setAudit(true);
		} else {
			achSynthetical.setAudit(false);
		}
		model.addAttribute("achSynthetical", achSynthetical);
		return "achievement/synthetical/achSyntheticalForm";
	}

	/**
	 * 保存绩效综合管理
	 */
	@RequiresPermissions("synthetical:achSynthetical:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AchSynthetical achSynthetical, String flag) {
		if ("1".equals(flag)) {
			// 审核人
			achSynthetical.setAuditBy(UserUtils.getUser().getUserName());
			// 审核时间
			achSynthetical.setAuditDate(new Date());
			achSynthetical.setDataType("1");
		} else if ("2".equals(flag)) {
			// 审核人
			achSynthetical.setAuditBy(UserUtils.getUser().getUserName());
			// 审核时间
			achSynthetical.setAuditDate(new Date());
			achSynthetical.setDataType("2");
		}
		if (achSynthetical.getIsNewRecord()) {
			achSynthetical.setCreateBy(UserUtils.getUser().getUserName());
		}
		achSyntheticalService.save(achSynthetical);
		return renderResult(Global.TRUE, text("保存绩效综合管理成功！"));
	}
	
	/**
	 * 停用绩效综合管理
	 */
	@RequiresPermissions("synthetical:achSynthetical:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AchSynthetical achSynthetical) {
		achSynthetical.setDisableStatus(AchSynthetical.STATUS_DELETE);
		achSynthetical.setDisableBy(UserUtils.getUser().getUserName());
		achSynthetical.setDisableDate(new Date());
		achSyntheticalService.save(achSynthetical);
		return renderResult(Global.TRUE, text("停用绩效综合管理成功"));
	}
	
	/**
	 * 启用绩效综合管理
	 */
	@RequiresPermissions("synthetical:achSynthetical:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AchSynthetical achSynthetical) {
		achSyntheticalService.enable(achSynthetical.getSyntheticalCode());
		return renderResult(Global.TRUE, text("启用绩效综合管理成功"));
	}
	
	/**
	 * 删除绩效综合管理
	 */
	@RequiresPermissions("synthetical:achSynthetical:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchSynthetical achSynthetical) {
		achSyntheticalService.delete(achSynthetical);
		return renderResult(Global.TRUE, text("删除绩效综合管理成功！"));
	}
}