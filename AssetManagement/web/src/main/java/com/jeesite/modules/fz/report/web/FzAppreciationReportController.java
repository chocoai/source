/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.fz.utils.common.Variable;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.jeesite.modules.fz.report.entity.FzAppreciationReport;
import com.jeesite.modules.fz.report.service.FzAppreciationReportService;

import java.util.List;

/**
 * 赠赞数量统计表Controller
 * @author scarlett
 * @version 2018-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/report/fzAppreciationReport")
public class FzAppreciationReportController extends BaseController {

	@Autowired
	private FzAppreciationReportService fzAppreciationReportService;
	@Resource
	private RedisTemplate<String, List> redisTemplate;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzAppreciationReport get(String userid, boolean isNewRecord) {
		return fzAppreciationReportService.get(userid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("report:fzAppreciationReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzAppreciationReport fzAppreciationReport, Model model) {
		model.addAttribute("fzAppreciationReport", fzAppreciationReport);
		return "fz/report/fzAppreciationReportList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("report:fzAppreciationReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzAppreciationReport> listData(FzAppreciationReport fzAppreciationReport, HttpServletRequest request, HttpServletResponse response) {
		Page<FzAppreciationReport> page = fzAppreciationReportService.findPage(new Page<FzAppreciationReport>(request, response), fzAppreciationReport);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("report:fzAppreciationReport:view")
	@RequestMapping(value = "form")
	public String form(FzAppreciationReport fzAppreciationReport, Model model) {
		model.addAttribute("fzAppreciationReport", fzAppreciationReport);
		return "fz/report/fzAppreciationReportForm";
	}

	/**
	 * 保存赠赞数量统计表
	 */
	@RequiresPermissions("report:fzAppreciationReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzAppreciationReport fzAppreciationReport) {
		fzAppreciationReportService.save(fzAppreciationReport);
		return renderResult(Global.TRUE, text("保存赠赞数量统计表成功！"));
	}
	/**
	 * 导出数据
	 */
	@RequiresPermissions("report:fzAppreciationReport:edit")
	@PostMapping(value = "exportData")
	@ResponseBody
	public void exportData(FzAppreciationReport fzAppreciationReport, HttpServletResponse response) {
		List<FzAppreciationReport> reportList = fzAppreciationReportService.findList(fzAppreciationReport);
		List<DingUserDepartment> dingUserDepartmentList = redisTemplate.opsForValue().get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取所有用户
		List<DingUser> dingUserList = redisTemplate.opsForValue().get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		for (FzAppreciationReport report : reportList) {
			DingUser dingUser = dingUserList.stream().filter(s ->s.getUserid().equals(report.getUserid())).findFirst().get();
			String department = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
			report.setDepartment(department);
		}
		String fileName = "赠赞数量统计（不包括跟赞）" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("赠赞数量统计（不包括跟赞）", FzAppreciationReport.class)){
			ee.setDataList(reportList).write(response, fileName);
		}

	}
	
}