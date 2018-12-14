/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.config.AccessLimit;
import com.jeesite.modules.fz.config.IsFileter;
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
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationType;
import com.jeesite.modules.fz.appreciation.service.FzAppreciationTypeService;

import java.util.List;

/**
 * 赞赏类型Controller
 * @author dwh
 * @version 2018-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/appreciation/fzAppreciationType")
public class FzAppreciationTypeController extends BaseController {
	private static final String ZSTYPE_PER_FIX = "LX";
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private FzAppreciationTypeService fzAppreciationTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzAppreciationType get(String typeCode, boolean isNewRecord) {
		return fzAppreciationTypeService.get(typeCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzAppreciationType fzAppreciationType, Model model) {
		model.addAttribute("fzAppreciationType", fzAppreciationType);
		return "fz/appreciation/fzAppreciationTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzAppreciationType> listData(FzAppreciationType fzAppreciationType, HttpServletRequest request, HttpServletResponse response) {
		Page<FzAppreciationType> page = fzAppreciationTypeService.findPage(new Page<FzAppreciationType>(request, response), fzAppreciationType); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:view")
	@RequestMapping(value = "form")
	public String form(FzAppreciationType fzAppreciationType, Model model) {
		String code = null;
		if (fzAppreciationType.getIsNewRecord()) {
			code = amSeqService.getSeq(ZSTYPE_PER_FIX);
			fzAppreciationType.setTypeCode(code);
		}
			model.addAttribute("fzAppreciationType", fzAppreciationType);
		return "fz/appreciation/fzAppreciationTypeForm";
	}

	/**
	 * 保存赞赏类型
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzAppreciationType fzAppreciationType) {
		if (fzAppreciationType.getIsNewRecord()) {
			String code = amSeqService.getCode(ZSTYPE_PER_FIX);
			fzAppreciationType.setTypeCode(code);
		}
		fzAppreciationTypeService.save(fzAppreciationType);
		return renderResult(Global.TRUE, "保存赞赏类型成功！");
	}
	
	/**
	 * 停用赞赏类型
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(FzAppreciationType fzAppreciationType) {
		fzAppreciationType.setStatus(FzAppreciationType.STATUS_DISABLE);
		fzAppreciationTypeService.updateStatus(fzAppreciationType);
		return renderResult(Global.TRUE, "停用赞赏类型成功");
	}
	
	/**
	 * 启用赞赏类型
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(FzAppreciationType fzAppreciationType) {
		fzAppreciationType.setStatus(FzAppreciationType.STATUS_NORMAL);
		fzAppreciationTypeService.updateStatus(fzAppreciationType);
		return renderResult(Global.TRUE, "启用赞赏类型成功");
	}
	
	/**
	 * 删除赞赏类型
	 */
	@RequiresPermissions("appreciation:fzAppreciationType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzAppreciationType fzAppreciationType) {
		fzAppreciationTypeService.delete(fzAppreciationType);
		return renderResult(Global.TRUE, "删除赞赏类型成功！");
	}
	/**
	 * 查询列表接口
	 */
	@RequestMapping(value = "getList")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getList() {
		List<FzAppreciationType> fzAppreciationTypes= fzAppreciationTypeService.findList(new FzAppreciationType());
		return ReturnDate.success(fzAppreciationTypes);
	}

}