/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermission.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotDataService;
import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.config.AccessLimit;
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

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.achievement.usermission.entity.AchCardMission;
import com.jeesite.modules.achievement.usermission.service.AchCardMissionService;

import java.util.List;

/**
 * 绩效卡关键任务Controller
 * @author PhilipGuan
 * @version 2018-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/cardMission")
public class AchCardMissionController extends BaseController {

	@Autowired
	private AchCardMissionService achCardMissionService;

	@Autowired
	private AchCardScoreService achCardScoreService;
	@Autowired
	private AchCardService achCardService;
	@Autowired
	private AchCardSnapshotDataService achCardSnapshotDataService;

	@Autowired
	private AmSeqService amSeqService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchCardMission get(String missionCode, boolean isNewRecord) {
		return achCardMissionService.get(missionCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("achievement:achCardMission:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchCardMission achCardMission, Model model) {
		model.addAttribute("achCardMission", achCardMission);
		return "modules/achievement/achCardMissionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("achievement:achCardMission:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchCardMission> listData(AchCardMission achCardMission, HttpServletRequest request, HttpServletResponse response) {

		Page<AchCardMission> page = achCardMissionService.findPage(new Page<AchCardMission>(request, response), achCardMission); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("achievement:achCardMission:view")
	@RequestMapping(value = "form")
	public String form(AchCardMission achCardMission, Model model) {
		model.addAttribute("achCardMission", achCardMission);
		return "modules/achievement/achCardMissionForm";
	}

	/**
	 * 保存绩效卡关键任务
	 */
	@PostMapping(value = "save")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo save(@Validated AchCardMission achCardMission, HttpServletRequest request) {
		//String code = amSeqService.getCode("JXK", "yyyyMM");
		//achCardMission.setMissionCode(code);
		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

		if(StringUtils.isBlank(achCardMission.getMissionCode())) achCardMission.setIsNewRecord(true);

		//创建时默认写入登录者ID
		if(achCardMission.getIsNewRecord())
			achCardMission.setUserId(userData.getCurrentUser().getUserid());
		else achCardMission.setUpdateBy(userData.getCurrentUser().getUserid());

		achCardMissionService.save(achCardMission);
		return ReturnDate.success();
	}

	/**
	 * 删除绩效卡关键任务
	 */
	@RequestMapping(value = "delete")
	@IsFileter(isFile="true")
	@AccessLimit(limit = 1,sec = 2)
	@ResponseBody
	public ReturnInfo delete(AchCardMission data, HttpServletRequest request) {
		try {
			if(data.getMissionCode() == null || data.getMissionCode().isEmpty())
				return  ReturnDate.error(400, "参数错误");
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			AchCardMission query = new AchCardMission();
			query.setMissionCode(data.getMissionCode());
			query.setUserId(userData.getCurrentUser().getUserid());
			achCardMissionService.delete(query);
			achCardScoreService.updateMissionScore(data.getCardCode());
			return ReturnDate.success();
		}
		catch (Exception e){
			return  ReturnDate.error(400, e.getMessage());

		}

	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getMissions")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getMissions(AchCardMission data, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			AchCard card = achCardService.get(data.getCardCode());
			if(card == null) return ReturnDate.error(400, "绩效卡不存在");

			AchCardMission query = new AchCardMission();
			query.setCardCode(data.getCardCode());

			//没有代理人，就获取本人的，否则直接按照绩效卡号取，用于防止没有权限的人
			userData.setQueryWithRequestUserIdIfNotSelf(query::setUserId, data.getUserId());


			List<AchCardMission> list = achCardMissionService.findList(query);
			//HR初审-面谈中需要取快照(本人)
			AchCardSnapshotData snapshot = achCardSnapshotDataService.getSnapshotData(card.getCardCode(), card.getDataStatus(), !userData.hasOtherUser);
			if(snapshot != null && snapshot.getAchCardData() != null)
				list = snapshot.getAchCardMissions();

			return ReturnDate.successObject(list);
		}
		catch (Exception e){

		}
		return ReturnDate.error(400, "获取任务列表失败");

	}
}