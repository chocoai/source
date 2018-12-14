/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermenu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.achievement.cardscoremodify.entity.AchCardScoreModify;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.util.DingDingAuth;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.select.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.achievement.usermenu.entity.AchUserMenu;
import com.jeesite.modules.achievement.usermenu.service.AchUserMenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效卡用户菜单Controller
 * @author Philip Guan
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/userMenu")
public class AchUserMenuController extends BaseController {

	@Autowired
	private AchUserMenuService achUserMenuService;

	@Autowired
	private DingUserService dingUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchUserMenu get(String userMenuCode, boolean isNewRecord) {
		return achUserMenuService.get(userMenuCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("ach:userMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchUserMenu achUserMenu, Model model) {
		model.addAttribute("basicUserInfo", achUserMenu);
		return "achievement/usermenu/basicUserInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ach:userMenu:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DingUser> listData(DingUser achUserMenu, HttpServletRequest request, HttpServletResponse response) {
		Page<DingUser> page = dingUserService.findPage(new Page<DingUser>(request, response), achUserMenu);
		return page;
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ach:userMenu:view")
	@RequestMapping(value = "listMenu")
	@ResponseBody
	public Page<AchUserMenu> listMenu(AchUserMenu achUserMenu, HttpServletRequest request, HttpServletResponse response) {
		Page<AchUserMenu> page = achUserMenuService.findPage(new Page<AchUserMenu>(request, response), achUserMenu);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ach:userMenu:view")
	@RequestMapping(value = "form")
	public String form(AchUserMenu achUserMenu, Model model) {
		model.addAttribute("achUserMenu", achUserMenu);
		return "modules/achievement/achUserMenuForm";
	}

	/**
	 * 保存绩效卡用户菜单
	 */
	@RequiresPermissions("ach:userMenu:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AchUserMenu achUserMenu) {
		achUserMenuService.save(achUserMenu);
		return renderResult(Global.TRUE, text("保存绩效卡用户菜单成功！"));
	}
	
	/**
	 * 删除绩效卡用户菜单
	 */
	@RequiresPermissions("ach:userMenu:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchUserMenu achUserMenu) {
		achUserMenuService.delete(achUserMenu);
		return renderResult(Global.TRUE, text("删除绩效卡用户菜单成功！"));
	}

	/**
	 * 保存绩效卡用户菜单
	 */
	@RequiresPermissions("ach:userMenu:edit")
	@PostMapping(value = "saveMenu")
	@ResponseBody
	public String saveMenu(@Validated @RequestBody List<AchUserMenu> list) {
		if(list == null || list.size() == 0) return renderResult(Global.TRUE, text("保存绩效卡用户菜单失败！参数无效"));
		List<String> userIds = list.stream().map(x->x.getUserId()).collect(Collectors.toList());
		if(userIds == null || userIds.size() == 0) return renderResult(Global.TRUE, text("保存绩效卡用户菜单失败！参数无效"));
		try{
			List<AchUserMenu> lx = list.stream().filter(x->x!=null).filter(x-> x.getDictCode()!=null).collect(Collectors.toList());
			achUserMenuService.save(userIds.get(0),lx );
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return renderResult(Global.TRUE, text("保存绩效卡用户菜单成功！"));
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

			AchUserMenu query = new AchUserMenu();
			query.setUserId(userData.getCurrentUser().getUserid());

			List<AchUserMenu> list = achUserMenuService.findList(query);

			if(list!=null && list.size() >0){
				List<String> s = list.stream().map(a -> a.getDictData()).filter(a->a!=null).map(a->a.getRemarks()).collect(Collectors.toList());
				return ReturnDate.successObject(s);
			}


			return ReturnDate.success();
		}
		catch (Exception e){
			return ReturnDate.error(400, "获取加减分列表失败:" + e.getMessage());
		}

	}
}