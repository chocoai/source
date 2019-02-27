/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.storevideo.video.entity.SvFaceDetectLog;
import com.jeesite.modules.storevideo.video.service.SvFaceDetectLogService;

/**
 * 人脸识别记录Controller
 * @author Philip Guan
 * @version 2019-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/storevideo/svFaceDetectLog")
public class SvFaceDetectLogController extends BaseController {

	@Autowired
	private SvFaceDetectLogService svFaceDetectLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvFaceDetectLog get(String logCode, boolean isNewRecord) {
		return svFaceDetectLogService.get(logCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("storevideo:svFaceDetectLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvFaceDetectLog svFaceDetectLog, Model model) {
		model.addAttribute("svFaceDetectLog", svFaceDetectLog);
		return "modules/storevideo/svFaceDetectLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("storevideo:svFaceDetectLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvFaceDetectLog> listData(SvFaceDetectLog svFaceDetectLog, HttpServletRequest request, HttpServletResponse response) {
		Page<SvFaceDetectLog> page = svFaceDetectLogService.findPage(new Page<SvFaceDetectLog>(request, response), svFaceDetectLog); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("storevideo:svFaceDetectLog:view")
	@RequestMapping(value = "form")
	public String form(SvFaceDetectLog svFaceDetectLog, Model model) {
		model.addAttribute("svFaceDetectLog", svFaceDetectLog);
		return "modules/storevideo/svFaceDetectLogForm";
	}

	/**
	 * 保存人脸识别记录
	 */
	@RequiresPermissions("storevideo:svFaceDetectLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvFaceDetectLog svFaceDetectLog) {
		svFaceDetectLogService.save(svFaceDetectLog);
		return renderResult(Global.TRUE, text("保存人脸识别记录成功！"));
	}
	
	/**
	 * 删除人脸识别记录
	 */
	@RequiresPermissions("storevideo:svFaceDetectLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SvFaceDetectLog svFaceDetectLog) {
		svFaceDetectLogService.delete(svFaceDetectLog);
		return renderResult(Global.TRUE, text("删除人脸识别记录成功！"));
	}
	
}