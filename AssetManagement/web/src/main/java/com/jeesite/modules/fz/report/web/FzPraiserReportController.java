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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.fz.report.entity.FzPraiserReport;
import com.jeesite.modules.fz.report.service.FzPraiserReportService;

import java.util.List;

/**
 * 获赞者获赞数统计表Controller
 * @author len
 * @version 2018-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/report/fzPraiserReport")
public class FzPraiserReportController extends BaseController {

	@Autowired
	private FzPraiserReportService fzPraiserReportService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzPraiserReport get(String userId, boolean isNewRecord) {
		return fzPraiserReportService.get(userId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("report:fzPraiserReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzPraiserReport fzPraiserReport, Model model) {
		model.addAttribute("fzPraiserReport", fzPraiserReport);
		return "fz/report/fzPraiserReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("report:fzPraiserReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzPraiserReport> listData(FzPraiserReport fzPraiserReport, HttpServletRequest request, HttpServletResponse response) {
		Page<FzPraiserReport> page = fzPraiserReportService.findPage(new Page<FzPraiserReport>(request, response), fzPraiserReport); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("report:fzPraiserReport:view")
	@RequestMapping(value = "form")
	public String form(FzPraiserReport fzPraiserReport, Model model) {
		model.addAttribute("fzPraiserReport", fzPraiserReport);
		return "fz/report/fzPraiserReportForm";
	}

	/**
	 * 保存获赞者获赞数统计表
	 */
	@RequiresPermissions("report:fzPraiserReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzPraiserReport fzPraiserReport) {
		fzPraiserReportService.save(fzPraiserReport);
		return renderResult(Global.TRUE, text("保存获赞者获赞数统计表成功！"));
	}

	@RequiresPermissions("report:fzPraiserReport:view")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(FzPraiserReport fzPraiserReport, HttpServletResponse response) {
		List<FzPraiserReport> reportList = fzPraiserReportService.findList(fzPraiserReport);
		AccessDepartMent accessDepartMent = DepartmentUtil.access();
		for (FzPraiserReport report : reportList) {
			DingUser dingUser = accessDepartMent.getDingUserList().stream().filter(s ->s.getUserid().equals(report.getUserId())).findFirst().get();
			String department = DepartmentUtil.getDepartment(dingUser, accessDepartMent.getDingUserDepartmentList(), accessDepartMent.departmentList);
			report.setDepartment(department);
		}
		String fileName = "获赞数统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("用户数据", FzPraiserReport.class)){
			ee.setDataList(reportList).write(response, fileName);
		}
	}
}