/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.asset.ding.entity.AccessDepartMent;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.fz.report.entity.FzLoginReport;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.fz.report.entity.FzDepartmentRecord;
import com.jeesite.modules.fz.report.service.FzDepartmentRecordService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 梵赞部门的赞赏数据Controller
 * @author easter
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/report/fzDepartmentRecord")
public class FzDepartmentRecordController extends BaseController {

	@Autowired
	private FzDepartmentRecordService fzDepartmentRecordService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzDepartmentRecord get(String department, boolean isNewRecord) {
		return fzDepartmentRecordService.get(department, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("report:fzDepartmentRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzDepartmentRecord fzDepartmentRecord, Model model,String fztypeSelectName) {
 		model.addAttribute("fzDepartmentRecord", fzDepartmentRecord);
 		if(fztypeSelectName == null || "".equals(fztypeSelectName)){
			fztypeSelectName = "该部门赞赏最多的,该部门赞赏最少的,人均部门内赞赏/获赞次数,人均跨部门赞赏次数,人均跨部门获赞次数,人均总赞赏次数,人均总获赞次数,最爱用的赞赏类型";
		}
		model.addAttribute("fztypeSelectName", fztypeSelectName);
		return "fz/report/fzDepartmentRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("report:fzDepartmentRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<FzDepartmentRecord> listData(FzDepartmentRecord fzDepartmentRecord) {
		try { 
			List<FzDepartmentRecord> list = fzDepartmentRecordService.findByMonth(fzDepartmentRecord);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("report:fzDepartmentRecord:view")
	@RequestMapping(value = "form")
	public String form(FzDepartmentRecord fzDepartmentRecord, Model model) {
		model.addAttribute("fzDepartmentRecord", fzDepartmentRecord);
		return "fz/report/fzDepartmentRecordForm";
	}

	/**
	 * 保存梵赞部门的赞赏数据
	 */
	@RequiresPermissions("report:fzDepartmentRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzDepartmentRecord fzDepartmentRecord) {
		fzDepartmentRecordService.save(fzDepartmentRecord);
		return renderResult(Global.TRUE, text("保存梵赞部门的赞赏数据成功！"));
	}

	@RequiresPermissions("report:fzDepartmentRecord:view")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(FzDepartmentRecord record, HttpServletResponse response) {
		try {
			List<FzDepartmentRecord> reportList = fzDepartmentRecordService.findByMonth(record);
			String fileName = "一级部门统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			try(ExcelExport ee = new ExcelExport("一级部门统计", FzDepartmentRecord.class)){
				ee.setDataList(reportList).write(response, fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}