/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.log.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.util.DingDingAuth;
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
import com.jeesite.modules.achievement.log.entity.AchLog;
import com.jeesite.modules.achievement.log.service.AchLogService;

/**
 * 绩效卡操作日志Controller
 * @author Philip Guan
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/log")
public class AchLogController extends BaseController {

	@Autowired
	private AchLogService achLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchLog get(String code, boolean isNewRecord) {
		return achLogService.get(code, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("ach:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchLog achLog, Model model) {
		model.addAttribute("achLog", achLog);
		return "achievement/log/achLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ach:log:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchLog> listData(AchLog achLog, HttpServletRequest request, HttpServletResponse response) {
		Page<AchLog> page = achLogService.findPage(new Page<AchLog>(request, response), achLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ach:log:view")
	@RequestMapping(value = "form")
	public String form(AchLog achLog, Model model) {
		model.addAttribute("achLog", achLog);
		return "achievement/log/achLogForm";
	}

	/**
	 * 保存绩效卡操作日志
	 */
	@RequiresPermissions("ach:log:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AchLog achLog) {
		achLogService.save(achLog);
		return renderResult(Global.TRUE, text("保存绩效卡操作日志成功！"));
	}

	/**
	 * 保存绩效卡
	 */
	@PostMapping(value = "add")
	@ResponseBody
	public ReturnInfo add(AchLog achLog, HttpServletRequest request) {

		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

		achLog.setUserId(userData.getCurrentUser().getUserid());
		achLog.setUserName(userData.getCurrentUser().getName());
		achLogService.save(achLog);

		return ReturnDate.success();
	}
}