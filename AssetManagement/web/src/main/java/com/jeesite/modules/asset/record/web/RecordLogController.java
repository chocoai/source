/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.record.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.record.service.RecordLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志管理Controller
 * @author scarlett
 * @version 2018-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/record/recordLog")
public class RecordLogController extends BaseController {

	@Autowired
	private RecordLogService recordLogService;
	@Autowired
	private AmqpTemplate template;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public RecordLog get(String logCode, boolean isNewRecord) {
		return recordLogService.get(logCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("record:recordLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(RecordLog recordLog, Model model) {
		model.addAttribute("recordLog", recordLog);
		return "asset/record/recordLogList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("record:recordLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<RecordLog> listData(RecordLog recordLog, HttpServletRequest request, HttpServletResponse response) {
		Page<RecordLog> page = recordLogService.findPage(new Page<RecordLog>(request, response), recordLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("record:recordLog:view")
	@RequestMapping(value = "form")
	public String form(RecordLog recordLog, Model model) {
		model.addAttribute("recordLog", recordLog);
		return "asset/record/recordLogForm";
	}

	/**
	 * 保存日志管理
	 */
	@RequiresPermissions("record:recordLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated RecordLog recordLog) {
		recordLogService.save(recordLog);
		return renderResult(Global.TRUE, text("保存日志管理成功！"));
	}

	/**
	 * 删除日志管理
	 */
	@RequiresPermissions("record:recordLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(RecordLog recordLog) {
		recordLogService.delete(recordLog);
		return renderResult(Global.TRUE, text("删除日志管理成功！"));
	}
	/**
	 * 插入日志
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	public String insertLog(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("title","test");
		jsonObject.put("type","test");
		// jsonObject.put("content","test");
		jsonObject.put("createTime","2018-07-11 00:00:00");
		jsonObject.put("content","test");
		jsonObject.put("level","test");
		jsonObject.put("path","test");
		template.convertAndSend("recordLog",jsonObject.toString());
//		template.convertAndSend("recordLog",2);
		return renderResult(Global.TRUE, text("成功插入日志！"));
	}


}