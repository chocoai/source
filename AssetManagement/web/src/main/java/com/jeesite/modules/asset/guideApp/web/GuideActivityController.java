/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.asset.guideApp.dao.GuideCommentDao;
import com.jeesite.modules.asset.guideApp.entity.GuideComment;
import com.jeesite.modules.asset.guideApp.entity.GuideFaq;
import com.jeesite.modules.asset.guideApp.entity.GuideImg;
import com.jeesite.modules.asset.guideApp.service.GuideActivityService;
import com.jeesite.modules.asset.guideApp.service.GuideImgService;
import com.jeesite.modules.asset.guideApp.service.GuideService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.guideApp.entity.GuideActivity;

import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 导购活动表Controller
 * @author len
 * @version 2018-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/guide/guideActivity")
public class GuideActivityController extends BaseController {

	@Autowired
	private GuideActivityService guideActivityService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private GuideCommentDao guideCommentDao;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public GuideActivity get(String activityCode, boolean isNewRecord) {
		return guideActivityService.get(activityCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("guide:guideActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(GuideActivity guideActivity, Model model) {
		model.addAttribute("guideActivity", guideActivity);
		return "asset/guide/guideActivityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("guide:guideActivity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<GuideActivity> listData(GuideActivity guideActivity, HttpServletRequest request, HttpServletResponse response) {
		Page<GuideActivity> page = guideActivityService.findPage(new Page<GuideActivity>(request, response), guideActivity); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("guide:guideActivity:view")
	@RequestMapping(value = "form")
	public String form(GuideActivity guideActivity, Model model) {
		if (guideActivity.getIsNewRecord()) {
			guideActivity.setActivityCode(amSeqService.getActivityCode("HD"));
		}
		if (StringUtils.isNotEmpty(guideActivity.getBannerImage())) {
			JSONObject json = new JSONObject();
			json.put("activityCode", guideActivity.getActivityCode());
			json.put("savePath", guideActivity.getBannerImage());
			json.put("location", "1");
			model.addAttribute("picInfo", json.toString());
		} else {
			model.addAttribute("picInfo", "");
		}
		model.addAttribute("guideActivity", guideActivity);
		return "asset/guide/guideActivityForm";
	}

	/**
	 * 保存导购活动表
	 */
	@RequiresPermissions("guide:guideActivity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated GuideActivity guideActivity) {
		if (guideActivity.getStartTime().compareTo(guideActivity.getEndTime()) > 0) {
			return renderResult(Global.FALSE, text("开始时间不能大于结束时间！"));
		}
		// 获取当前用户名
		String userName = UserUtils.getUser().getUserName();
		// 是创建设置创建人和创建时间
		if (guideActivity.getIsNewRecord()) {
			guideActivity.setCreateBy(userName);
			guideActivity.setCreateTime(new Date());
		} else {
			// 编辑就是更新更新人和更新时间
			guideActivity.setUpdateBy(userName);
			guideActivity.setUpdateTime(new Date());
		}
		// 如果图片路径为null 设置为空
		if (guideActivity.getBannerImage() == null) {
			guideActivity.setBannerImage("");
		}
		guideActivityService.save(guideActivity);
		return renderResult(Global.TRUE, text("保存导购活动表成功！"));
	}
	
	/**
	 * 删除导购活动表
	 */
	@RequiresPermissions("guide:guideActivity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(GuideActivity guideActivity) {
		guideActivityService.delete(guideActivity);
		return renderResult(Global.TRUE, text("删除导购活动表成功！"));
	}

	@Autowired
	private GuideImgService guideImgService;
	/**
	 * 上传图片
	 * @param uploadInfo
	 * @return
	 */
	@RequiresPermissions("guide:guideActivity:edit")
	@PostMapping(value = "uploadPicture")
	@ResponseBody
	public String uploadPicture(@RequestBody String uploadInfo) {
		JSONArray jsonArray = JSONArray.parseArray(uploadInfo);
		// 活动编码
		String activityCode = jsonArray.getJSONObject(0).get("activityCode").toString();
		// 图片上传地址
		String savePath = jsonArray.getJSONObject(0).get("savePath").toString();
		GuideImg guideImg = guideImgService.get(activityCode);
		if (guideImg == null) {
			guideImg = new GuideImg();
			guideImg.setActivityCode(activityCode);
			guideImg.setBannerUrl(savePath);
			guideImgService.insert(guideImg);
		} else {
			guideImg.setBannerUrl(savePath);
			guideImgService.save(guideImg);
		}
		return renderResult(Global.TRUE, activityCode);
	}

	@RequiresPermissions("guide:guideActivity:edit")
	@PostMapping(value = "deletePicture")
	@ResponseBody
	public String deletePicture(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		// 活动编码
		String activityCode = jsonObject.get("activityCode").toString();
		// 根据活动编码获取对象
		GuideImg guideImg = guideImgService.get(activityCode);
		List<String> imgList = ListUtils.newArrayList();
		if (guideImg != null) {
			imgList.add(guideImg.getBannerUrl());
			// 把图片记录删除
			guideImgService.delete(guideImg);
			//删除阿里云图片
			amUtilService.deletePicAli(imgList);
			return renderResult(Global.TRUE, "图片删除成功！");
		} else {
			return renderResult(Global.TRUE, "图片删除失败！");
		}
	}

	/**
	 * 查询活动列表接口
	 * @return
	 */
	@RequiresPermissions(value = {"guide:guideActivity:view","order:amOrder:view"},logical = Logical.OR)
	@GetMapping(value = "queryActivity")
	@ResponseBody
	public ReturnInfo queryActivity () {
		// 查询所有状态是有效的活动
		List<GuideActivity> guideActivityList = guideActivityService.selectList();
		return ReturnDate.success(guideActivityList);
	}

	/**
	 * 查询活动详情页接口
	 * @return
	 */
	@RequiresPermissions(value = {"guide:guideActivity:edit","order:amOrder:view"},logical = Logical.OR)
	@GetMapping(value = "queryForm")
	@ResponseBody
	public ReturnInfo queryForm(GuideActivity guideActivity) {
		// 用户类型employee代表员工 none是管理员
		if ("employee".equals(UserUtils.getUser().getUserType()) ) {
			// A1000000是 门店的officeCode
			if (EmpUtils.getOffice().getParentCodes().contains(",A1000000,")) {
				guideActivity.setCanComment(true);
			}
		}
		Collection<String> roleList = UserUtils.getAuthInfo().getRoles();
		// 如果包含活动组的角色 设置为true
		if (roleList.contains("HDZ0001")) {
			guideActivity.setActivityGroup(true);
		} else {
			guideActivity.setActivityGroup(false);
		}
		return ReturnDate.success(guideActivity);
	}

	@Autowired
	private GuideService guideService;
	/**
	 * 新增评论接口（导购员评论活动 提交问题）
	 * @param info
	 * @return
	 */
	@RequiresPermissions(value = {"guide:guideActivity:edit","order:amOrder:view"},logical = Logical.OR)
	@PostMapping(value = "addComment")
	@ResponseBody
	public ReturnInfo addComment(@RequestBody String info) {
		if("none".equals(UserUtils.getUser().getUserType())) {
			return ReturnDate.error(11010, "您不能评论！");
		} else {
			if (!EmpUtils.getOffice().getParentCodes().contains(",A1000000,")) {
				return ReturnDate.error(11010, "您不能评论！");
			}
		}
		JSONObject jsonObject = JSONObject.parseObject(info);
		// 活动编码
		String activityCode = jsonObject.get("activityCode").toString();
		// 评论内容
		String question = jsonObject.get("question").toString();
		GuideActivity guideActivity = guideActivityService.get(activityCode);
		GuideComment guideComment = new GuideComment();
		guideComment.setActivityCode(guideActivity);
		// 提问人
		String askBy = UserUtils.getUser().getUserName();
		// 提问人id
		String askId = UserUtils.getUser().getUserCode();
		// 根据用户账号获取当前用户的treeNames
		// 提问人所在门店
		String shop = guideService.selectShop(askId);
		if (StringUtils.isNotEmpty(shop)) {
			guideComment.setAskShop(shop);
		}
		// 问题
		guideComment.setQuestion(question);
		guideComment.setAskBy(askBy);
		guideComment.setAskId(askId);
		guideComment.setAskTime(new Date());
		guideComment.setIsNewRecord(true);
		// 新增评论
		guideActivityService.insertComment(guideComment);
		return ReturnDate.success();
	}


	/**
	 * 回复评论编辑接口（活动组回复导购的提问，或者删除回复再次编辑回复）
	 * @param comment
	 * @param flag
	 */
	@RequiresPermissions("guide:guideActivity:reply")
	@PostMapping(value = "editComment")
	@ResponseBody
	public ReturnInfo editComment(@RequestBody String comment, String flag) {
		JSONObject jsonObject = JSONObject.parseObject(comment);
		// 评论编码
		String commentCode = jsonObject.get("commentCode").toString();
		GuideComment guideComment = guideActivityService.getGuideComment(commentCode);
		// 回复人id
		String answerId = UserUtils.getUser().getUserCode();
		// 0代表编辑回复
		if ("0".equals(flag)) {
			if (StringUtils.isNotEmpty(guideComment.getAnswerId())) {
				return ReturnDate.error(11011, "已存在回复内容！");
			}
			// 回复内容
			String answer = jsonObject.get("answer").toString();
			// 回复人
			String answerBy = UserUtils.getUser().getUserName();
			guideComment.setAnswer(answer);
			guideComment.setAnswerBy(answerBy);
			guideComment.setAnswerId(answerId);
			guideComment.setAnswerTime(new Date());
			guideActivityService.updateComment(guideComment);
			return ReturnDate.success();
		} else {
			// 1代表删除
			if (!answerId.equals(guideComment.getAnswerId())) {
				return ReturnDate.error(11010, "只有回复人自己才可删除此回复！");
			}
			// 1代表删除
			guideActivityService.deleteAnswer(commentCode);
			return ReturnDate.success();
		}
	}

	/**
	 * 关键字查询常见问题接口；
	 *
	 * 后台根据常见问题的问题模糊匹配问题包含关键字的q&a；返回给前台；
	 * @param activityCode
	 * @param keyword
	 * @return
	 */
	@RequiresPermissions(value = {"guide:guideActivity:edit","order:amOrder:view"},logical = Logical.OR)
	@GetMapping(value = "getFAQ")
	@ResponseBody
	public ReturnInfo getFAQ(String activityCode, String keyword) {
		List<GuideFaq> guideFaqList = guideActivityService.selectFAQ(activityCode, keyword);
		return ReturnDate.success(guideFaqList);
	}


	/**
	 * 获取表中最新一条数据的创建时间
	 * @return
	 */
	@RequiresPermissions(value = {"guide:guideActivity:view","order:amOrder:view"},logical = Logical.OR)
	@ResponseBody
	@RequestMapping(value = "newCreateTime", method = RequestMethod.GET)
	public ReturnInfo newCreateTime() {
		return ReturnDate.success(DateUtils.formatDateTime(guideActivityService.getNewTime()));
	}


	/**
	 * 获取表中最新一条数据的创建时间
	 * @return
	 */
	@RequiresPermissions(value = "guide:guideActivity:export")
	@RequestMapping(value = "exportData")
	public void exportData(GuideActivity guideActivity, HttpServletResponse response) {
		GuideComment guideComment = new GuideComment();
		guideComment.setActivityCode(guideActivity);
		List<GuideComment> list = guideCommentDao.findList(guideComment);
		String fileName = "评论" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("评论", GuideComment.class)){
			ee.setDataList(list).write(response, fileName);
		}
	}
}