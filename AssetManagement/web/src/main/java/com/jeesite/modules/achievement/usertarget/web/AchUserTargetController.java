/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usertarget.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotDataService;
import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import com.jeesite.modules.achievement.usertarget.service.AchUserTargetService;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
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

import java.util.List;

/**
 * 绩效指标管理Controller
 * @author PhilipGuan
 * @version 2018-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/userTarget")
public class AchUserTargetController extends BaseController {

	@Autowired
	private AchUserTargetService achUserTargetService;
	@Autowired
	private AchCardScoreService achCardScoreService;
    @Autowired
    private AchCardService achCardService;
    @Autowired
    private AchCardSnapshotDataService achCardSnapshotDataService;

	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchUserTarget get(String userTargetCode, boolean isNewRecord) {
		return achUserTargetService.get(userTargetCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("achievement:achUserTarget:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchUserTarget achUserTarget, Model model) {
		model.addAttribute("achUserTarget", achUserTarget);
		return "modules/achievement/achUserTargetList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("achievement:achUserTarget:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchUserTarget> listData(AchUserTarget achUserTarget, HttpServletRequest request, HttpServletResponse response) {
		Page<AchUserTarget> page = achUserTargetService.findPage(new Page<AchUserTarget>(request, response), achUserTarget); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("achievement:achUserTarget:view")
	@RequestMapping(value = "form")
	public String form(AchUserTarget achUserTarget, Model model) {
		model.addAttribute("achUserTarget", achUserTarget);
		return "modules/achievement/achUserTargetForm";
	}

	/**
	 * 保存绩效指标管理
	 */
	@PostMapping(value = "save")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo save(@Validated AchUserTarget achUserTarget, HttpServletRequest request) {

		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

		if(StringUtils.isBlank(achUserTarget.getUserTargetCode())) achUserTarget.setIsNewRecord(true);


		//创建时默认写入登录者ID
		if(achUserTarget.getIsNewRecord()){
			achUserTarget.setUserId(userData.getCurrentUser().getUserid());
		}
		else
		{
			achUserTarget.setUpdateBy(userData.getCurrentUser().getUserid());
		}

		achUserTargetService.save(achUserTarget);
		return ReturnDate.success();
	}
	
	/**
	 * 删除绩效指标管理
	 */
	@RequestMapping(value = "delete")
	@IsFileter(isFile="true")
	@AccessLimit(limit = 1,sec = 2)
	@ResponseBody
	public ReturnInfo delete(AchUserTarget achUserTarget, HttpServletRequest request) {
		try {
			if(achUserTarget.getUserTargetCode() == null || achUserTarget.getUserTargetCode().isEmpty())
				return  ReturnDate.error(400, "参数错误");


			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			AchUserTarget query = new AchUserTarget();
			query.setUserTargetCode(achUserTarget.getUserTargetCode());
			query.setUserId(userData.getCurrentUser().getUserid());
			achUserTargetService.delete(query);
			achCardScoreService.updateTargerScore(achUserTarget.getCardCode());
			return ReturnDate.success();
		}
		catch (Exception e){
			return ReturnDate.error(400, e.getMessage());

		}

	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getTargets")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getTargets(AchUserTarget data, HttpServletRequest request, HttpServletResponse response) {
		try {

			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");


            AchCard card = achCardService.get(data.getCardCode());
            if(card == null) return ReturnDate.error(400, "绩效卡不存在");

			AchUserTarget query = new AchUserTarget();
			query.setCardCode(data.getCardCode());

			//没有代理人，就获取本人的，否则直接按照绩效卡号取，用于防止没有权限的人
			if(userData.isBoss) query.setUserId(data.getUserId());
			else userData.setQueryWithRequestUserIdIfNotSelf(query::setUserId, data.getUserId());

			List<AchUserTarget> list = achUserTargetService.findList(query);
            //HR初审-面谈中需要取快照(本人)
            AchCardSnapshotData snapshot = achCardSnapshotDataService.getSnapshotData(card.getCardCode(), card.getDataStatus(), !userData.hasOtherUser);
            if(snapshot != null && snapshot.getAchCardData() != null)
                list = snapshot.getAchUserTargets();

			return ReturnDate.successObject(list);
		}
		catch (Exception e){

		}
		return ReturnDate.error(400, "获取指标列表失败");

	}
}