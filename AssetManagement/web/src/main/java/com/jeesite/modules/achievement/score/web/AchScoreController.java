/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.score.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
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
import com.jeesite.modules.achievement.score.entity.AchScore;
import com.jeesite.modules.achievement.score.service.AchScoreService;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 加减分管理Controller
 * @author len
 * @version 2018-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/score/achScore")
public class AchScoreController extends BaseController {

	@Autowired
	private AchScoreService achScoreService;
	@Autowired
	private AmSeqService amSeqService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchScore get(String billCode, boolean isNewRecord) {
		return achScoreService.get(billCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("score:achScore:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchScore achScore, Model model) {
		model.addAttribute("achScore", achScore);
		return "achievement/score/achScoreList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("score:achScore:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchScore> listData(AchScore achScore, HttpServletRequest request, HttpServletResponse response) {
		Page<AchScore> page = achScoreService.findPage(new Page<AchScore>(request, response), achScore); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("score:achScore:view")
	@RequestMapping(value = "form")
	public String form(AchScore achScore, Model model) {
		if (achScore.getIsNewRecord()) {
			achScore.setDataType("0");
		}
		// 数据状态
		String dataVal = DictUtils.getDictLabel("supplier_status", achScore.getDataType(), "创建");
		achScore.setDataVal(dataVal);
		String scoreVal = DictUtils.getDictLabel("ach_add_sub_type", achScore.getScoreType(), "加分");
		achScore.setScoreVal(scoreVal);
		// 审核状态
		if ("2".equals(achScore.getDataType())) {
			achScore.setAudit(true);
		} else {
			achScore.setAudit(false);
		}
		model.addAttribute("achScore", achScore);
		return "achievement/score/achScoreForm";
	}

	/**
	 * 保存加减分管理
	 */
	@RequiresPermissions("score:achScore:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AchScore achScore, String flag) {
		if (achScore.getIsNewRecord()) {
			achScore.setBillCode(amSeqService.getAchCode("JKF"));
		}
		if ("1".equals(flag)) {
			// 审核人
			achScore.setAuditBy(UserUtils.getUser().getUserName());
			// 审核时间
			achScore.setAuditDate(new Date());
			achScore.setDataType("1");
		} else if ("2".equals(flag)) {
			achScore.setDataType("2");
			// 审核人
			achScore.setAuditBy(UserUtils.getUser().getUserName());
			// 审核时间
			achScore.setAuditDate(new Date());
		}
		if (achScore.getIsNewRecord()) {
			achScore.setCreateBy(UserUtils.getUser().getUserName());
		}
		achScoreService.save(achScore);
		return renderResult(Global.TRUE, text("保存加减分管理成功！"));
	}
	
	/**
	 * 停用加减分管理
	 */
	@RequiresPermissions("score:achScore:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AchScore achScore) {
		achScore.setStatus(AchScore.STATUS_DISABLE);
		achScoreService.updateStatus(achScore);
		return renderResult(Global.TRUE, text("停用加减分管理成功"));
	}
	
	/**
	 * 启用加减分管理
	 */
	@RequiresPermissions("score:achScore:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AchScore achScore) {
		achScore.setStatus(AchScore.STATUS_NORMAL);
		achScoreService.updateStatus(achScore);
		return renderResult(Global.TRUE, text("启用加减分管理成功"));
	}
	
	/**
	 * 删除加减分管理
	 */
	@RequiresPermissions("score:achScore:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchScore achScore) {
		achScoreService.delete(achScore);
		return renderResult(Global.TRUE, text("删除加减分管理成功！"));
	}


	/**
	 * 导入用户数据
	 */
	@ResponseBody
	@RequiresPermissions("score:achScore:edit")
	@PostMapping(value = "importData")
	public String importData(MultipartFile file, String updateSupport) {
		try {
			boolean isUpdateSupport = Global.YES.equals(updateSupport);
			String message = achScoreService.importData(file, isUpdateSupport);
			return renderResult(Global.TRUE, "posfull:"+message);
		} catch (Exception ex) {
			return renderResult(Global.FALSE, "posfull:"+ex.getMessage());
		}
	}

	/**
	 * 下载导入用户数据模板
	 */
	@RequiresPermissions("score:achScore:view")
	@RequestMapping(value = "importTemplate")
	public void importTemplate(HttpServletResponse response) {
		// 随机查一条数据作为模板
		List<AchScore> list = achScoreService.selectTemp();
		String fileName = "加扣分管理模板"+ DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("加扣分管理数据" , AchScore.class, ExcelField.Type.IMPORT)){
			ee.setDataList(list).write(response, fileName);
		}
	}
}