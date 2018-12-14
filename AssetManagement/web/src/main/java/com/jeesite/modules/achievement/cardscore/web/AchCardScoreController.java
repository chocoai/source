/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;
import com.jeesite.modules.achievement.cardSnapshot.service.AchCardSnapshotDataService;
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
import com.jeesite.modules.achievement.cardscore.entity.AchCardScore;
import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;

import java.util.List;

/**
 * 绩效卡评分Controller
 * @author Philip Guan
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/cardScore")
public class AchCardScoreController extends BaseController {

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
	public AchCardScore get(String cardScoreCode, boolean isNewRecord) {
		return achCardScoreService.get(cardScoreCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("achievement:achCardScore:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchCardScore achCardScore, Model model) {
		model.addAttribute("achCardScore", achCardScore);
		return "modules/achievement/achCardScoreList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("achievement:achCardScore:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchCardScore> listData(AchCardScore achCardScore, HttpServletRequest request, HttpServletResponse response) {
		Page<AchCardScore> page = achCardScoreService.findPage(new Page<AchCardScore>(request, response), achCardScore); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("achievement:achCardScore:view")
	@RequestMapping(value = "form")
	public String form(AchCardScore achCardScore, Model model) {
		model.addAttribute("achCardScore", achCardScore);
		return "modules/achievement/achCardScoreForm";
	}

	/**
	 * 保存绩效卡评分(不能新增)
	 */
	@PostMapping(value = "save")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo save(@Validated AchCardScore achCardScore, HttpServletRequest request) {
		if (StringUtils.isBlank(achCardScore.getCardScoreCode())) return ReturnDate.error(400, "参数错误");
		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");
		achCardScore.setUpdateBy(userData.getCurrentUser().getUserid());

		achCardScoreService.save(achCardScore);
		return ReturnDate.success();
	}
	
	/**
	 * 删除绩效卡评分
	 */
	@RequiresPermissions("achievement:achCardScore:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchCardScore achCardScore) {
		achCardScoreService.delete(achCardScore);
		return renderResult(Global.TRUE, text("删除绩效卡评分成功！"));
	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getList")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getList(AchCardScore data, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			AchCard card = achCardService.get(data.getCardCode());
			if(card == null) return ReturnDate.error(400, "绩效卡不存在");

			AchCardScore query = new AchCardScore();
			query.setCardCode(data.getCardCode());
			//配置搜索条件
			userData.setQueryWithRequestUserIdIfNotSelf(query::setUserId, data.getUserId());

			List<AchCardScore> list = achCardScoreService.findList(query);
			//HR初审-面谈中需要取快照(本人)
			AchCardSnapshotData snapshot = achCardSnapshotDataService.getSnapshotData(card.getCardCode(), card.getDataStatus(), !userData.hasOtherUser);
			if(snapshot != null && snapshot.getAchCardData() != null)
				list = snapshot.getAchCardScores();


			return ReturnDate.successObject(list);
		}
		catch (Exception e){

		}
		return ReturnDate.error(400, "获取任务列表失败");

	}
	
}