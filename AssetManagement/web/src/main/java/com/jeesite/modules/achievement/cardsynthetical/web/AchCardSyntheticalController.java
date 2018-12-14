/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardsynthetical.web;

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
import com.jeesite.modules.achievement.cardsynthetical.entity.AchCardSynthetical;
import com.jeesite.modules.achievement.cardsynthetical.service.AchCardSyntheticalService;

import java.util.List;

/**
 * 绩效卡综合管理Controller
 * @author Philip Guan
 * @version 2018-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/cardSynthetical")
public class AchCardSyntheticalController extends BaseController {

	@Autowired
	private AchCardSyntheticalService achCardSyntheticalService;
    @Autowired
    private AchCardService achCardService;
    @Autowired
    private AchCardSnapshotDataService achCardSnapshotDataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchCardSynthetical get(String cardSyntheticalCode, boolean isNewRecord) {
		return achCardSyntheticalService.get(cardSyntheticalCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("achievement:achCardSynthetical:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchCardSynthetical achCardSynthetical, Model model) {
		model.addAttribute("achCardSynthetical", achCardSynthetical);
		return "modules/achievement/achCardSyntheticalList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("achievement:achCardSynthetical:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchCardSynthetical> listData(AchCardSynthetical achCardSynthetical, HttpServletRequest request, HttpServletResponse response) {
		Page<AchCardSynthetical> page = achCardSyntheticalService.findPage(new Page<AchCardSynthetical>(request, response), achCardSynthetical); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("achievement:achCardSynthetical:view")
	@RequestMapping(value = "form")
	public String form(AchCardSynthetical achCardSynthetical, Model model) {
		model.addAttribute("achCardSynthetical", achCardSynthetical);
		return "modules/achievement/achCardSyntheticalForm";
	}

	/**
	 * 保存绩效卡综合管理
	 */
	@PostMapping(value = "save")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo save(AchCardSynthetical achCardSynthetical, HttpServletRequest request) {
		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");
		achCardSynthetical.setIsNewRecord(StringUtils.isBlank(achCardSynthetical.getCardSyntheticalCode()));

		if(achCardSynthetical.getIsNewRecord())
			achCardSynthetical.setUserId(userData.getCurrentUser().getUserid());
		else
			achCardSynthetical.setUpdateBy(userData.getCurrentUser().getUserid());

		achCardSyntheticalService.save(achCardSynthetical);
		return ReturnDate.success();
	}
	
	/**
	 * 删除绩效卡综合管理
	 */
	@RequestMapping(value = "delete")
	@IsFileter(isFile="true")
	@AccessLimit(limit = 1,sec = 2)
	@ResponseBody
	public ReturnInfo delete(AchCardSynthetical achCardSynthetical, HttpServletRequest request) {
		try {
			if(achCardSynthetical.getCardSyntheticalCode() == null || achCardSynthetical.getCardSyntheticalCode().isEmpty())
				return  ReturnDate.error(400, "参数错误");

			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			if(StringUtils.isBlank(achCardSynthetical.getCardSyntheticalCode())) return ReturnDate.error(400, "参数错误");

			AchCardSynthetical query = new AchCardSynthetical();
			query.setCardSyntheticalCode(achCardSynthetical.getCardSyntheticalCode());
			userData.setQueryWithRequestUserIdIfNotSelf(query::setUserId, achCardSynthetical.getUserId());

			achCardSyntheticalService.delete(query);
			return ReturnDate.success();
		}
		catch (Exception e){
			return  ReturnDate.error(400, e.getMessage());

		}
	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getCardSyntheticals")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getCardSyntheticals(AchCardSynthetical data, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

            AchCard card = achCardService.get(data.getCardCode());
            if(card == null) return ReturnDate.error(400, "绩效卡不存在");

			AchCardSynthetical query = new AchCardSynthetical();
			query.setCardCode(data.getCardCode());

			//没有代理人，就获取本人的，否则直接按照绩效卡号取，用于防止没有权限的人
			userData.setQueryWithRequestUserIdIfNotSelf(query::setUserId, data.getUserId());

			List<AchCardSynthetical> list = achCardSyntheticalService.findList(query);
            //HR初审-面谈中需要取快照(本人)
            AchCardSnapshotData snapshot = achCardSnapshotDataService.getSnapshotData(card.getCardCode(), card.getDataStatus(), !userData.hasOtherUser);
            if(snapshot != null && snapshot.getAchCardData() != null)
                list = snapshot.getAchCardSyntheticals();


			return ReturnDate.successObject(list);
		}
		catch (Exception e){
			return ReturnDate.error(400, "获取综合指标列表失败:" + e.getMessage());
		}

	}
	
}