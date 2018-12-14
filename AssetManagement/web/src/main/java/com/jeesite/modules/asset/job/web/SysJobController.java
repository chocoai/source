/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.job.entity.SysJob;
import com.jeesite.modules.asset.job.service.SysJobService;
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

/**
 * 定时任务调度表Controller
 * @author len
 * @version 2018-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/job/sysJob")
public class SysJobController extends BaseController {

	@Autowired
	private SysJobService sysJobService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SysJob get(String jobId, boolean isNewRecord) {
		return sysJobService.get(jobId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("job:sysJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysJob sysJob, Model model) {
		model.addAttribute("sysJob", sysJob);
		return "asset/job/sysJobList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("job:sysJob:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysJob> listData(SysJob sysJob, HttpServletRequest request, HttpServletResponse response) {
		Page<SysJob> page = sysJobService.findPage(new Page<SysJob>(request, response), sysJob); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("job:sysJob:view")
	@RequestMapping(value = "form")
	public String form(SysJob sysJob, Model model) {
		model.addAttribute("sysJob", sysJob);
		return "asset/job/sysJobForm";
	}

	/**
	 * 保存定时任务调度表
	 */
	@RequiresPermissions("job:sysJob:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SysJob sysJob) {
		if (sysJob.getIsNewRecord()) {
			sysJobService.insertJobCron(sysJob);
		} else {
			sysJobService.updateJobCron(sysJob);
		}
//		sysJobService.save(sysJob);
		return renderResult(Global.TRUE, text("保存定时任务调度表成功！"));
	}



	@RequiresPermissions("job:sysJob:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SysJob job) {
		boolean flag = sysJobService.deleteJob(job);
		if (flag) {
			return renderResult(Global.TRUE, text("任务删除成功"));
		} else {
			return renderResult(Global.FALSE, text("任务删除失败"));
		}
	}

	/**
	 * 恢复任务
	 * @param job
	 * @return
	 */
	@RequiresPermissions("job:sysJob:edit")
	@RequestMapping(value = "resume")
	@ResponseBody
	public String resumeTask(SysJob job) {
		boolean flag = sysJobService.resumeJob(job);
		if (flag) {
			return renderResult(Global.TRUE, text("任务开始成功"));
		} else {
			return renderResult(Global.FALSE, text("任务开始失败"));
		}
	}

	/**
	 * 暂停任务
	 * @param
	 * @return
	 */
	@RequiresPermissions("job:sysJob:edit")
	@RequestMapping(value = "pause")
	@ResponseBody
	public String pauseTask(SysJob job) {
		boolean flag = sysJobService.pauseJob(job);
		if (flag) {
			return renderResult(Global.TRUE, text("任务停止成功"));
		} else {
			return renderResult(Global.FALSE, text("任务停止失败"));
		}
	}


	/**
	 * 任务调度立即执行一次
	 */

	@RequestMapping("run")
	@ResponseBody
	public String run(SysJob job)
	{
		boolean flag = sysJobService.run(job);
		if (flag) {
			return renderResult(Global.TRUE, text("任务执行完成"));
		} else {
			return renderResult(Global.FALSE, text("任务执行失败"));
		}
	}

}