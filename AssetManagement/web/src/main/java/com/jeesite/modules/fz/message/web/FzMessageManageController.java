/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.message.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.HttpHelper;
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
import com.jeesite.modules.fz.message.entity.FzMessageManage;
import com.jeesite.modules.fz.message.service.FzMessageManageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 梵赞消息推送Controller
 * @author scarlett
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/message/fzMessageManage")
public class FzMessageManageController extends BaseController {

	@Autowired
	private FzMessageManageService fzMessageManageService;
	@Autowired
	private DingUserService dingUserService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzMessageManage get(String pkey, boolean isNewRecord) {
		return fzMessageManageService.get(pkey, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("message:fzMessageManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzMessageManage fzMessageManage, Model model) {
		model.addAttribute("fzMessageManage", fzMessageManage);
		return "fz/message/fzMessageManageList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("message:fzMessageManage:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzMessageManage> listData(FzMessageManage fzMessageManage, HttpServletRequest request, HttpServletResponse response) {
		Page<FzMessageManage> page = fzMessageManageService.findPage(new Page<FzMessageManage>(request, response), fzMessageManage);
		List<FzMessageManage> fzMessageManages=page.getList();
		if(ParamentUntil.isBackList(fzMessageManages)){
			for(FzMessageManage fzMessageManage1:fzMessageManages){
				List<String> userIdList = Arrays.asList(fzMessageManage1.getTouser().split(","));
                List<String> nameList=dingUserService.getNamesByUserIds(userIdList);
				fzMessageManage1.setTouser(StringUtils.join(nameList.toArray(), ","));
				if("否".equals(fzMessageManage1.getResult())){
					List<String> userIdList1 = Arrays.asList(fzMessageManage1.getRemarks().split(","));
					List<String> nameList1=dingUserService.getNamesByUserIds(userIdList1);
					fzMessageManage1.setRemarks(StringUtils.join(nameList1.toArray(), ","));
				}

			}

		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("message:fzMessageManage:view")
	@RequestMapping(value = "form")
	public String form(FzMessageManage fzMessageManage, Model model) {
		model.addAttribute("fzMessageManage", fzMessageManage);
		return "fz/message/fzMessageManageForm";
	}

	/**
	 * 保存梵赞消息推送
	 */
	@RequiresPermissions("message:fzMessageManage:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzMessageManage fzMessageManage) {
		//ListUtils.fzMessageManage.getTouser();
		String agendId = fzMessageManage.getAgendId();
		String title = fzMessageManage.getTitle();
		String singleUrl = fzMessageManage.getSingleurl();
		String text = fzMessageManage.getText();
		String singleTitle = fzMessageManage.getSingletitle();
		List<String> failedUserId=new ArrayList<>();
		List<String> userIdList = Arrays.asList(fzMessageManage.getTouser().split(","));
		if(ParamentUntil.isBackList(userIdList)){
			for(String userid:userIdList){
				try {
					Map map=HttpHelper.actionCardMessage(userid,agendId,title,text,singleUrl,singleTitle);
					if(map.containsKey("failed")){
						failedUserId.add(userid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(failedUserId!=null && failedUserId.size()>0){
			fzMessageManage.setRemarks(StringUtils.join(failedUserId.toArray(), ","));
			fzMessageManage.setResult("否");
		}else {
			fzMessageManage.setResult("是");
		}

		fzMessageManageService.save(fzMessageManage);
		return renderResult(Global.TRUE, text("保存梵赞消息推送成功！"));
	}

	/**
	 * 删除梵赞消息推送
	 */
	@RequiresPermissions("message:fzMessageManage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzMessageManage fzMessageManage) {
		fzMessageManageService.delete(fzMessageManage);
		return renderResult(Global.TRUE, text("删除梵赞消息推送成功！"));
	}

}