/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.useragent.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.asset.ding.entity.DingUser;
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
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.achievement.useragent.entity.AchUserAgent;
import com.jeesite.modules.achievement.useragent.service.AchUserAgentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效卡用户代理Controller
 * @author Philip Guan
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/userAgent")
public class AchUserAgentController extends BaseController {

	@Autowired
	private AchUserAgentService achUserAgentService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchUserAgent get(String userAgentCode, boolean isNewRecord) {
		return achUserAgentService.get(userAgentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("ach:userAgent:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchUserAgent achUserAgent, Model model) {
		model.addAttribute("basicUserInfo", achUserAgent);
		return "achievement/useragent/basicUserInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ach:userAgent:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchUserAgent> listData(AchUserAgent achUserAgent, HttpServletRequest request, HttpServletResponse response) {
		Page<AchUserAgent> page = achUserAgentService.findPage(new Page<AchUserAgent>(request, response), achUserAgent); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ach:userAgent:view")
	@RequestMapping(value = "form")
	public String form(AchUserAgent achUserAgent, Model model) {
		model.addAttribute("achUserAgent", achUserAgent);
		return "modules/achievement/achUserAgentForm";
	}

	/**
	 * 保存绩效卡用户代理
	 */
	@RequiresPermissions("ach:userAgent:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AchUserAgent achUserAgent) {
		achUserAgentService.save(achUserAgent);
		return renderResult(Global.TRUE, text("保存绩效卡用户代理成功！"));
	}
	
	/**
	 * 删除绩效卡用户代理
	 */
	@RequiresPermissions("ach:userAgent:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchUserAgent achUserAgent) {
		achUserAgentService.delete(achUserAgent);
		return renderResult(Global.TRUE, text("删除绩效卡用户代理成功！"));
	}

	/**
	 * 保存用户代理
	 */
	@RequiresPermissions("ach:userAgent:edit")
	@PostMapping(value = "saveAgent")
	@ResponseBody
	public String saveAgent(@Validated @RequestBody List<AchUserAgent> list) {
		if(list == null || list.size() == 0) return renderResult(Global.TRUE, text("保存绩效卡用户代理失败！参数无效"));
		List<String> userIds = list.stream().map(x->x.getUserId()).collect(Collectors.toList());
		if(userIds == null || userIds.size() == 0) return renderResult(Global.TRUE, text("保存绩效卡用户代理失败！参数无效"));
		try{
			List<AchUserAgent> lx = list.stream().filter(x->x!=null).filter(x-> x.getAgentUserId()!=null).collect(Collectors.toList());
			achUserAgentService.save(userIds.get(0),lx);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return renderResult(Global.TRUE, text("保存绩效卡用户代理成功！"));
	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "listAll")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo listAll(HttpServletRequest request) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			AchUserAgent query = new AchUserAgent();
			if(userData.getBossUser() == null)
				query.setUserId(userData.getCurrentUser().getUserid());
			else
				query.setUserId(userData.getBossUser().getUserid());

			List<AchUserAgent> list = achUserAgentService.findList(query);

			if(list!=null && list.size() >0){
				List<DingUser> s = list.stream().map(a -> a.getDingUser()).collect(Collectors.toList());
				return ReturnDate.successObject(s);
			}


			return ReturnDate.success();
		}
		catch (Exception e){
			return ReturnDate.error(400, "获取用户代理列表失败:" + e.getMessage());
		}

	}
}