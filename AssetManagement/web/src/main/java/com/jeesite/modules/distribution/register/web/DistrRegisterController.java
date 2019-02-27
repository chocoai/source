/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.register.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.distribution.register.entity.DistrRegister;
import com.jeesite.modules.distribution.register.service.DistrRegisterService;

import java.util.Date;

/**
 * 分销注册申请Controller
 * @author len
 * @version 2019-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/register/distrRegister")
public class DistrRegisterController extends BaseController {

	@Autowired
	private DistrRegisterService distrRegisterService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private OfficeService officeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DistrRegister get(String registerCode, boolean isNewRecord) {
		return distrRegisterService.get(registerCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("register:distrRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(DistrRegister distrRegister, Model model) {
		model.addAttribute("distrRegister", distrRegister);
		return "distribution/register/distrRegisterList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("register:distrRegister:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DistrRegister> listData(DistrRegister distrRegister, HttpServletRequest request, HttpServletResponse response) {
		Page<DistrRegister> page = distrRegisterService.findPage(new Page<DistrRegister>(request, response), distrRegister); 
		return page;
	}


	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("register:distrRegister:view")
	@RequestMapping(value = "form")
	public String form(DistrRegister distrRegister, Model model) {
		distrRegister.setStatusLabel(DictUtils.getDictLabel("distr_register_status", distrRegister.getRegisterStatus(), ""));
		if ("0".equals(distrRegister.getRegisterStatus())) {
//			if (distrRegister.getOffice() == null) {
//				distrRegister.setOfficeCode("FXS0001");
//				distrRegister.setOffice(officeService.get("FXS0001"));
//			}
			distrRegister.setCreated(false);
		} else {
			distrRegister.setCreated(true);
		}
		model.addAttribute("distrRegister", distrRegister);
		return "distribution/register/distrRegisterForm";
	}

	@Autowired
	private UserService userService;
	/**
	 * 保存分销注册申请
	 */
	@RequiresPermissions("register:distrRegister:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DistrRegister distrRegister, String flag) {
		// 审核
		if ("1".equals(flag)) {
			distrRegister.setRegisterStatus(flag);
			// 记录审核人 审核人登录账号 审核时间
			distrRegister.setAuditBy(UserUtils.getUser().getUserName());
			distrRegister.setAuditCode(UserUtils.getUser().getLoginCode());
			distrRegister.setAuditTime(new Date());
		} else if ("2".equals(flag)) {
			// 驳回
			distrRegister.setRegisterStatus(flag);
			// 记录驳回人 驳回人登录账号 驳回时间
			distrRegister.setRejectBy(UserUtils.getUser().getUserName());
			distrRegister.setRejectCode(UserUtils.getUser().getLoginCode());
			distrRegister.setRejectTime(new Date());
		}
		distrRegisterService.save(distrRegister);
		return renderResult(Global.TRUE, text("保存分销注册申请成功！"));
	}
	
	/**
	 * 删除分销注册申请
	 */
	@RequiresPermissions("register:distrRegister:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DistrRegister distrRegister) {
		distrRegisterService.delete(distrRegister);
		return renderResult(Global.TRUE, text("删除分销注册申请成功！"));
	}


//	@RequiresPermissions("register:distrRegister:edit")
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo register(DistrRegister distrRegister) {
		distrRegister.setIsNewRecord(true);
		// ZC+yyyyMMdd+6位流水号
		distrRegister.setRegisterCode(amSeqService.getCode("ZC", "yyyyMMdd", 6));
		// 状态设置为0  创建状态
		distrRegister.setRegisterStatus(DistrRegister.STATUS_NORMAL);
		// 创建时间取当前
		distrRegister.setCreateTime(new Date());
		distrRegisterService.save(distrRegister);
		return ReturnDate.success();
	}

}