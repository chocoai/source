/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.asset.ding.entity.*;
import com.jeesite.modules.fz.utils.common.Variable;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
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
import com.jeesite.modules.fz.report.entity.FzLoginReport;
import com.jeesite.modules.fz.report.service.FzLoginReportService;

import java.util.List;

/**
 * 用户登录记录报表Controller
 * @author len
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/report/fzLoginReport")
public class FzLoginReportController extends BaseController {

	@Autowired
	private FzLoginReportService fzLoginReportService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzLoginReport get(String userId, boolean isNewRecord) {
		return fzLoginReportService.get(userId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("report:fzLoginReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzLoginReport fzLoginReport, Model model) {
		model.addAttribute("fzLoginReport", fzLoginReport);
		return "fz/report/fzLoginReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("report:fzLoginReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzLoginReport> listData(FzLoginReport fzLoginReport, HttpServletRequest request, HttpServletResponse response) {
		Page<FzLoginReport> page = fzLoginReportService.findPage(new Page<FzLoginReport>(request, response), fzLoginReport); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("report:fzLoginReport:view")
	@RequestMapping(value = "form")
	public String form(FzLoginReport fzLoginReport, Model model) {
		model.addAttribute("fzLoginReport", fzLoginReport);
		return "fz/report/fzLoginReportForm";
	}

	/**
	 * 保存用户登录记录报表
	 */
	@RequiresPermissions("report:fzLoginReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzLoginReport fzLoginReport) {
		fzLoginReportService.save(fzLoginReport);
		return renderResult(Global.TRUE, text("保存用户登录记录报表成功！"));
	}
	
	/**
	 * 删除用户登录记录报表
	 */
	@RequiresPermissions("report:fzLoginReport:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzLoginReport fzLoginReport) {
		fzLoginReportService.delete(fzLoginReport);
		return renderResult(Global.TRUE, text("删除用户登录记录报表成功！"));
	}

	@RequiresPermissions("report:fzLoginReport:view")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(FzLoginReport fzLoginReport, HttpServletResponse response) {
		List<FzLoginReport> reportList = fzLoginReportService.findList(fzLoginReport);
//		List<DingUserDepartment> dingUserDepartmentList = redisTemplate.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
//		// 获取缓存中所有部门
//		List<DepartmentData> departmentList = redisTemplate.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
//		// 获取所有用户
//		List<DingUser> dingUserList = redisTemplate.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		AccessDepartMent accessDepartMent = DepartmentUtil.access();
		for (FzLoginReport report : reportList) {
			DingUser dingUser = accessDepartMent.getDingUserList().stream().filter(s ->s.getUserid().equals(report.getUserId())).findFirst().get();
			String department = DepartmentUtil.getDepartment(dingUser, accessDepartMent.getDingUserDepartmentList(), accessDepartMent.departmentList);
			report.setDepartment(department);
		}
		String fileName = "用户登录记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("用户数据", FzLoginReport.class)){
			ee.setDataList(reportList).write(response, fileName);
		}
	}
}