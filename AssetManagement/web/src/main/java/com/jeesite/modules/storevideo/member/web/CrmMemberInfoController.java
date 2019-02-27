/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.storevideo.member.entity.CrmMemberInfo;
import com.jeesite.modules.storevideo.member.service.CrmMemberInfoService;

/**
 * 淘宝会员信息Controller
 * @author Albert Feng
 * @version 2019-02-16
 */
@Controller
@RequestMapping(value = "${adminPath}/member/crmMemberInfo")
public class CrmMemberInfoController extends BaseController {

	@Autowired
	private CrmMemberInfoService crmMemberInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CrmMemberInfo get(String memberCode, boolean isNewRecord) {
		return crmMemberInfoService.get(memberCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("member:crmMemberInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmMemberInfo crmMemberInfo, Model model) {
		model.addAttribute("crmMemberInfo", crmMemberInfo);
		return "storevideo/member/crmMemberInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("member:crmMemberInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CrmMemberInfo> listData(CrmMemberInfo crmMemberInfo, HttpServletRequest request, HttpServletResponse response) {
		Page<CrmMemberInfo> page = crmMemberInfoService.findPage(new Page<CrmMemberInfo>(request, response), crmMemberInfo); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("member:crmMemberInfo:view")
	@RequestMapping(value = "form")
	public String form(CrmMemberInfo crmMemberInfo, Model model) {
		model.addAttribute("crmMemberInfo", crmMemberInfo);
		return "storevideo/member/crmMemberInfoForm";
	}

	/**
	 * 保存淘宝会员信息
	 */
	@RequiresPermissions("member:crmMemberInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CrmMemberInfo crmMemberInfo) {
		crmMemberInfoService.save(crmMemberInfo);
		return renderResult(Global.TRUE, text("保存淘宝会员信息成功！"));
	}
	
	/**
	 * 删除淘宝会员信息
	 */
	@RequiresPermissions("member:crmMemberInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CrmMemberInfo crmMemberInfo) {
		crmMemberInfoService.delete(crmMemberInfo);
		return renderResult(Global.TRUE, text("删除淘宝会员信息成功！"));
	}

	/**
	 * 绑定淘宝会员信息
	 */
	@PostMapping(value = "bindMemberInfo")
	@ResponseBody
	public ReturnInfo bindMemberInfo(@RequestBody CrmMemberInfo crmMemberInfo) {
		try {
			//TODO:未考虑会员解绑
			if (crmMemberInfo.getMobile().isEmpty() || crmMemberInfo.getTaobaoNick().isEmpty()){
				return ReturnDate.error(10002,"手机号码和淘宝昵称参数缺失");
			}
			//查询数据是否已存在，若已存在，则更新信息
			CrmMemberInfo entity = crmMemberInfoService.getMemberInfoByMobile(crmMemberInfo.getMobile());
			if (entity == null){
				crmMemberInfo.setIsNewRecord(true);
			}else{
				crmMemberInfo.setMemberCode(entity.getMemberCode());
				crmMemberInfo.setCreateBy(entity.getCreateBy());
				crmMemberInfo.setCreateByName(entity.getCreateByName());
				crmMemberInfo.setCreateDate(entity.getCreateDate());
			}
			crmMemberInfoService.save(crmMemberInfo);
			return ReturnDate.success(0,"成功","");

		}catch (Exception ex){
			return ReturnDate.error(-1,ex.getMessage());
		}
	}



	
}