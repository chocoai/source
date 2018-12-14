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
import com.jeesite.modules.fz.report.entity.FzFollowReport;
import com.jeesite.modules.fz.report.service.FzFollowReportService;

import java.util.List;

/**
 * 跟赞数量统计表Controller
 * @author scarlett
 * @version 2018-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/report/fzFollowReport")
public class FzFollowReportController extends BaseController {

	@Autowired
	private FzFollowReportService fzFollowReportService;
	@Resource
	private RedisTemplate<String, List> redisTemplate;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FzFollowReport get(String userid, boolean isNewRecord) {
		return fzFollowReportService.get(userid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("report:fzFollowReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(FzFollowReport fzFollowReport, Model model) {
		model.addAttribute("fzFollowReport", fzFollowReport);
		return "fz/report/fzFollowReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("report:fzFollowReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FzFollowReport> listData(FzFollowReport fzFollowReport, HttpServletRequest request, HttpServletResponse response) {
		Page<FzFollowReport> page = fzFollowReportService.findPage(new Page<FzFollowReport>(request, response), fzFollowReport); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("report:fzFollowReport:view")
	@RequestMapping(value = "form")
	public String form(FzFollowReport fzFollowReport, Model model) {
		model.addAttribute("fzFollowReport", fzFollowReport);
		return "fz/report/fzFollowReportForm";
	}

	/**
	 * 保存跟赞数量统计表
	 */
	@RequiresPermissions("report:fzFollowReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FzFollowReport fzFollowReport) {
		fzFollowReportService.save(fzFollowReport);
		return renderResult(Global.TRUE, text("保存跟赞数量统计表成功！"));
	}
	
	/**
	 * 删除跟赞数量统计表
	 */
	@RequiresPermissions("report:fzFollowReport:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FzFollowReport fzFollowReport) {
		fzFollowReportService.delete(fzFollowReport);
		return renderResult(Global.TRUE, text("删除跟赞数量统计表成功！"));
	}
	/**
	 * 导出数据
	 */
	@RequiresPermissions("report:fzFollowReport:edit")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void exportData(FzFollowReport fzFollowReport, HttpServletResponse response){
		List<FzFollowReport> reportList = fzFollowReportService.findList(fzFollowReport);
		List<DingUserDepartment> dingUserDepartmentList = redisTemplate.opsForValue().get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取所有用户
		List<DingUser> dingUserList = redisTemplate.opsForValue().get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		for (FzFollowReport report : reportList) {
			DingUser dingUser = dingUserList.stream().filter(s ->s.getUserid().equals(report.getUserid())).findFirst().get();
			String department = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
			report.setDepartment(department);
		}
		String fileName = "跟赞数量统计（不包括赞赏）" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("跟赞数量统计（不包括赞赏）", FzFollowReport.class)){
			ee.setDataList(reportList).write(response, fileName);
		}
	}
	
}