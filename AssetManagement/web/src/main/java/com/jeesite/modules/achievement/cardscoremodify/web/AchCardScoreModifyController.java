/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscoremodify.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.achievement.card.entity.CardUsers;
import com.jeesite.modules.achievement.cardscoremodify.entity.AchCardScoreModify;
import com.jeesite.modules.achievement.cardsynthetical.entity.AchCardSynthetical;
import com.jeesite.modules.achievement.score.entity.AchScore;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.config.AccessLimit;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.DingDingAuth;
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
import com.jeesite.modules.achievement.cardscoremodify.service.AchCardScoreModifyService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 加减分管理Controller
 * @author len
 * @version 2018-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/ach/cardScoreModify")
public class AchCardScoreModifyController extends BaseController {

	@Autowired
	private AchCardScoreModifyService achCardScoreModifyService;
	@Autowired
	private AmSeqService amSeqService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AchCardScoreModify get(String scoreModifyCode, boolean isNewRecord) {
		return achCardScoreModifyService.get(scoreModifyCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("ach:cardScoreModify:view")
	@RequestMapping(value = {"list", ""})
	public String list(AchCardScoreModify achScore, Model model) {
		model.addAttribute("achScore", achScore);
		return "achievement/cardscoremodify/cardScoreModifyList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ach:cardScoreModify:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AchCardScoreModify> listData(AchCardScoreModify achScore, HttpServletRequest request, HttpServletResponse response) {
		Page<AchCardScoreModify> page = achCardScoreModifyService.findPage(new Page<AchCardScoreModify>(request, response), achScore);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ach:cardScoreModify:view")
	@RequestMapping(value = "form")
	public String form(AchCardScoreModify achScore, Model model) {
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
		return "achievement/cardscoremodify/cardScoreModifyForm";
	}

	/**
	 * 保存加减分管理
	 */
	@RequiresPermissions("ach:cardScoreModify:edit")
	@PostMapping(value = "saveForm")
	@ResponseBody
	public String saveForm(@Validated AchCardScoreModify achScore, String flag) {
		if (achScore.getIsNewRecord()) {
			achScore.setScoreModifyCode(amSeqService.getAchCode("JKF"));
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
		achCardScoreModifyService.save(achScore);
		return renderResult(Global.TRUE, text("保存加减分管理成功！"));
	}

	/**
	 * 保存加减分管理
	 */
	@PostMapping(value = "save")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo save(@Validated AchCardScoreModify achScore, HttpServletRequest request) {
		CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
		if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

		if(StringUtils.isBlank(achScore.getScoreModifyCode())) achScore.setIsNewRecord(true);
		if (achScore.getIsNewRecord()) {
			achScore.setScoreModifyCode(amSeqService.getAchCode("JKF"));
			achScore.setCreateBy(userData.getCurrentUser().getUserid());
		}
		else achScore.setUpdateBy(userData.getCurrentUser().getUserid());

		achCardScoreModifyService.save(achScore);
		return ReturnDate.success();
	}

	/**
	 * 删除加减分管理
	 */
	@RequiresPermissions("ach:cardScoreModify:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AchCardScoreModify achScore) {
		achCardScoreModifyService.delete(achScore);
		return renderResult(Global.TRUE, text("删除加减分管理成功！"));
	}

	/**
	 * 删除加减分管理，只允许管理员请求
	 */
	@RequestMapping(value = "deleteData")
	@IsFileter(isFile="true")
	@AccessLimit(limit = 1,sec = 2)
	@ResponseBody
	public ReturnInfo deleteData(AchCardScoreModify achScore, HttpServletRequest request) {
		try {
			if(achScore.getSourceDepartCode() == null || achScore.getSourceDepartCode().isEmpty())
				return  ReturnDate.error(400, "参数错误");

			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.getCurrentUser() == null || userData.isManager.equals(false)) return ReturnDate.error(400, "用户没有权限删除");

			AchCardScoreModify query = new AchCardScoreModify();
			query.setScoreModifyCode(achScore.getScoreModifyCode());
			achCardScoreModifyService.delete(query);
			return ReturnDate.success();
		}
		catch (Exception e){
			return  ReturnDate.error(400, e.getMessage());

		}
	}


	/**
	 * 导入用户数据
	 */
	@ResponseBody
	@RequiresPermissions("ach:cardScoreModify:edit")
	@PostMapping(value = "importData")
	public String importData(MultipartFile file, String updateSupport) {
		try {
			boolean isUpdateSupport = Global.YES.equals(updateSupport);
			String message = achCardScoreModifyService.importData(file, isUpdateSupport);
			return renderResult(Global.TRUE, "posfull:"+message);
		} catch (Exception ex) {
			return renderResult(Global.FALSE, "posfull:"+ex.getMessage());
		}
	}

	/**
	 * 下载导入用户数据模板
	 */
	@RequiresPermissions("ach:cardScoreModify:edit")
	@RequestMapping(value = "importTemplate")
	public void importTemplate(HttpServletResponse response) {
		// 随机查一条数据作为模板
		List<AchCardScoreModify> list = achCardScoreModifyService.selectTemp();
		String fileName = "加扣分管理模板"+ DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("加扣分管理数据" , AchCardScoreModify.class, ExcelField.Type.IMPORT)){
			ee.setDataList(list).write(response, fileName);
		}
	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getList")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getList(AchCardScoreModify data, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			//自己的需要传卡号，第三者视角，需要传userid，如要看所有，则都不传
			AchCardScoreModify query = new AchCardScoreModify();
			query.setCardCode(data.getCardCode());	//可空，即是查所有
			query.setUserId(data.getUserId());	//可空，即是查所有
			query.setExamineMonth(data.getExamineMonth());


			//List<AchCardScoreModify> list = achCardScoreModifyService.findList(query);
			Page<AchCardScoreModify> pageList = achCardScoreModifyService.findPage(new Page<AchCardScoreModify>(request, response), query);


			return ReturnDate.successObject(pageList);
		}
		catch (Exception e){
			return ReturnDate.error(400, "获取加减分列表失败:" + e.getMessage());
		}

	}

	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "getMyList")
	@IsFileter(isFile="true")
	@ResponseBody
	public ReturnInfo getMyList(AchCardScoreModify data, HttpServletRequest request, HttpServletResponse response) {
		try {
			CardUsers userData = DingDingAuth.redisHelp.getUserInfo(request);
			if(userData.noCurrentUser()) return ReturnDate.error(400, "获取用户信息失败");

			//自己的需要传卡号，第三者视角，需要传userid，如要看所有，则都不传
			AchCardScoreModify query = new AchCardScoreModify();
			query.setCardCode(data.getCardCode());	//暂未使用
			query.setUserId(userData.getOtherOrCurrentUser().getUserid());
			query.setExamineMonth(data.getExamineMonth());


			//List<AchCardScoreModify> list = achCardScoreModifyService.findList(query);
			List<AchCardScoreModify> pageList = achCardScoreModifyService.getMyList(query.getUserId(), query.getExamineMonth());


			return ReturnDate.successObject(pageList);
		}
		catch (Exception e){
			return ReturnDate.error(400, "获取加减分列表失败:" + e.getMessage());
		}

	}
}