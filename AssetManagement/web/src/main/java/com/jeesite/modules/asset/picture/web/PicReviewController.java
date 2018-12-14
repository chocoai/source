/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.picture.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.picture.entity.PicReview;
import com.jeesite.modules.asset.picture.service.PicReviewService;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 图片审核单Controller
 * @author Scarllet
 * @version 2018-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/picture/picReview")
public class PicReviewController extends BaseController {
	private static final String CK_PER_FIX = "PR";

	@Autowired
	private PicReviewService picReviewService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmFileUploadService uploadService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PicReview get(String reviewCode, boolean isNewRecord) {
		return picReviewService.get(reviewCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("picture:picReview:view")
	@RequestMapping(value = {"list", ""})
	public String list(PicReview picReview, Model model) {
		model.addAttribute("picReview", picReview);
		return "asset/picture/picReviewList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("picture:picReview:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PicReview> listData(PicReview picReview, HttpServletRequest request, HttpServletResponse response) {
		Page<PicReview> page = picReviewService.findPage(new Page<PicReview>(request, response), picReview);
		return page;
	}

	/*@RequiresPermissions("picture:picReview:view")
	@RequestMapping(value = "getImg")
	@ResponseBody
	public String getImg(PicReview picReview, HttpServletRequest request, HttpServletResponse response, String code) {
		List<FileUpload> passedlist = picReviewService.findPictureId(code);
		JSONObject jsonObject = null;
		JSONArray jsonArray = new JSONArray();
		if (null != passedlist && passedlist.size() > 0) {
			jsonObject = new JSONObject();
			for (int i = 0; i < passedlist.size(); i++) {
				jsonObject.put("id", passedlist.get(i).getId());
				if (null == passedlist.get(i).getRemarks() || "".equals(passedlist.get(i).getRemarks().trim())) {
					jsonObject.put("status", "o");
					jsonObject.put("remarks", "");
				} else {
					jsonObject.put("status", passedlist.get(i).getRemarks().substring(0, 1));
					jsonObject.put("remarks", passedlist.get(i).getRemarks().substring(1));
				}
				jsonArray.add(jsonObject.toString());
			}
		}
		return jsonArray.toString();
	}
*/


	/*@RequiresPermissions(value={"picture:picReview:edit","picture:picReview:check"},logical=Logical.OR)
	@RequestMapping(value = "saveImg")
	@ResponseBody
	public String saveImgRemarks(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String imgsInfo = request.getParameter("picdata");
		if (null != imgsInfo && !"".equals(imgsInfo) && imgsInfo.startsWith("[")) {
			JSONArray jsonArray = JSONArray.fromObject(imgsInfo);
			JSONObject jsonObject = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				String remarks = jsonObject.getString("status");
				String mark = jsonObject.getString("remarks");
				if (!"".equals(mark.trim())) {
					remarks = remarks + mark;
				}
				picReviewService.updateFileUploadRemarks(code, jsonObject.getString("id"), remarks);
			}
		}
		return "";
	}*/
	/**
	 * 保存图片状态（审核、未审核）和批注信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"picture:picReview:edit","picture:picReview:check"},logical=Logical.OR)
	@RequestMapping(value = "saveImg")
	public String saveImgRemarks(HttpServletRequest request, HttpServletResponse response) {
		String imgsInfo = request.getParameter("picdata");
		if (null != imgsInfo && !"".equals(imgsInfo) && imgsInfo.startsWith("[")) {
			JSONArray jsonArray = JSONArray.fromObject(imgsInfo);
			JSONObject jsonObject = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				String picStatus = jsonObject.getString("status");
				String picRemarks = jsonObject.getString("remarks");
				String id=jsonObject.getString("id");
				AmFileUpload amFileUpload=new AmFileUpload();
				amFileUpload.setId(id);
				amFileUpload=uploadService.get(amFileUpload);
				if(amFileUpload!=null){
					/*amFileUpload.setPicRemarks(picRemarks);
					amFileUpload.setStatus(picStatus);
					uploadService.update(amFileUpload);*/
					uploadService.updatePicRemark(id,picStatus,picRemarks);

				}
			}
			return renderResult(Global.TRUE,"保存成功");
		}
		return renderResult(Global.TRUE,"保存失败");
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("picture:picReview:view")
	@RequestMapping(value = "form")
	public String form(PicReview picReview, Model model) {
		if (picReview.getIsNewRecord()) {
			picReview.setReviewCode(amSeqService.getSeq(CK_PER_FIX));
			picReview.setReviewDate(new Date());
			picReview.setReviewStatus("0");
		}
		String statusName = amUtilService.findDictLabel(picReview.getReviewStatus(), "am_document_status");
		picReview.setReviewStatusName(statusName);
		model.addAttribute("picReview", picReview);
		return "asset/picture/picReviewForm";
	}

	/**
	 * 保存图片审核单
	 */
	@RequiresPermissions(value={"picture:picReview:edit","picture:picReview:check","picture:picReview:review"},logical=Logical.OR)
	@PostMapping(value = "save")
	@ResponseBody
	public String save(PicReview picReview, String reviewStatusR) {
		if (picReview.getIsNewRecord()) {
			picReview.setReviewCode(amSeqService.getCode(CK_PER_FIX));
		}
		if (reviewStatusR != null && reviewStatusR.length() > 0) {
			picReview.setReviewStatus(reviewStatusR);
		}
		if ("".equals(picReview.getReviewType())) {
			return renderResult(Global.FALSE, "方案类型不能为空！");
		}
		picReviewService.save(picReview);
		return renderResult(Global.TRUE, "保存图片审核单成功！");
	}

	/**
	 * 删除图片审核单
	 */
	@RequiresPermissions("picture:picReview:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(PicReview picReview, String reviewCode) {
		String message = "";
		if (reviewCode== null || "".equals(reviewCode)) {
			return renderResult(Global.FALSE, "没有可删除的图片审核单据！");
		}else{
			 message=picReviewService.deleteData(picReview,reviewCode);
			 if("".equals(message)){
			 	return renderResult(Global.TRUE, "删除图片审核单成功！");
			 }
			return renderResult(Global.TRUE, message);
		}
	}


}