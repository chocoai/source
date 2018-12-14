/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardSnapshot.web;

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
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshot;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotService;

/**
 * 绩效卡快照Controller
 * @author Philip Guan
 * @version 2018-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/achievement/achCardSnapshot")
public class AchCardSnapshotController extends BaseController {

	@Autowired
	private AchCardSnapshotService achCardSnapshotService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchCardSnapshot get(String cardSnapshotCode, boolean isNewRecord) {
		return achCardSnapshotService.get(cardSnapshotCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("achievement:achCardSnapshot:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchCardSnapshot achCardSnapshot, Model model) {
		model.addAttribute("achCardSnapshot", achCardSnapshot);
		return "modules/achievement/achCardSnapshotList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("achievement:achCardSnapshot:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchCardSnapshot> listData(AchCardSnapshot achCardSnapshot, HttpServletRequest request, HttpServletResponse response) {
		Page<AchCardSnapshot> page = achCardSnapshotService.findPage(new Page<AchCardSnapshot>(request, response), achCardSnapshot); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("achievement:achCardSnapshot:view")
	@RequestMapping(value = "form")
	public String form(AchCardSnapshot achCardSnapshot, Model model) {
		model.addAttribute("achCardSnapshot", achCardSnapshot);
		return "modules/achievement/achCardSnapshotForm";
	}

	/**
	 * 保存绩效卡快照
	 */
	@RequiresPermissions("achievement:achCardSnapshot:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AchCardSnapshot achCardSnapshot) {
		achCardSnapshotService.save(achCardSnapshot);
		return renderResult(Global.TRUE, text("保存绩效卡快照成功！"));
	}
	
}