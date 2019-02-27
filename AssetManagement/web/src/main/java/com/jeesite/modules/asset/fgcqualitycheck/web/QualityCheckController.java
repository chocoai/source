/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
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
import com.jeesite.modules.asset.fgcqualitycheck.entity.QualityCheck;
import com.jeesite.modules.asset.fgcqualitycheck.service.QualityCheckService;

import java.util.List;

/**
 * 质检单Controller
 * @author len
 * @version 2018-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fgcqualitycheck/qualityCheck")
public class QualityCheckController extends BaseController {

	@Autowired
	private QualityCheckService qualityCheckService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public QualityCheck get(String fid, boolean isNewRecord) {
		return qualityCheckService.get(fid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fgcqualitycheck:qualityCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityCheck qualityCheck, Model model) {
		model.addAttribute("qualityCheck", qualityCheck);
		return "asset/fgcqualitycheck/qualityCheckList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fgcqualitycheck:qualityCheck:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<QualityCheck> listData(QualityCheck qualityCheck, HttpServletRequest request, HttpServletResponse response) {
		Page<QualityCheck> page = qualityCheckService.findPage(new Page<QualityCheck>(request, response), qualityCheck); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fgcqualitycheck:qualityCheck:view")
	@RequestMapping(value = "form")
	public String form(QualityCheck qualityCheck, Model model) {
		model.addAttribute("qualityCheck", qualityCheck);
		return "asset/fgcqualitycheck/qualityCheckForm";
	}

	/**
	 * 保存质检单
	 */
	@RequiresPermissions("fgcqualitycheck:qualityCheck:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated QualityCheck qualityCheck) {
		qualityCheckService.save(qualityCheck);
		return renderResult(Global.TRUE, "保存质检单成功！");
	}
	
	/**
	 * 删除质检单
	 */
	@RequiresPermissions("fgcqualitycheck:qualityCheck:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(QualityCheck qualityCheck) {
		qualityCheckService.delete(qualityCheck);
		return renderResult(Global.TRUE, "删除质检单成功！");
	}

	/**
	 * 删除质检单
	 */
	@RequiresPermissions("fgcqualitycheck:qualityCheck:delete")
	@RequestMapping(value = "deleteDb")
	@ResponseBody
	public String deleteDb(String fid) {
		String[] fids = fid.split(",");
		List<String> billNoList = ListUtils.newArrayList();
		if (fids.length > 0) {
			for (int i = 0;i < fids.length; i++) {
				billNoList.add(fids[i]);
			}
		}
		List<QualityCheck> qualityCheckList = qualityCheckService.selectByFid(billNoList);
		boolean isInit = qualityCheckList.stream().anyMatch(s ->!s.getDocumentStatus().equals("初始"));
		if (isInit) {
			return renderResult(Global.FALSE, "只能删除初始状态的质检单！");
		}
		qualityCheckService.deleteDb(billNoList);
		return renderResult(Global.TRUE, "删除质检单成功！");
	}
}