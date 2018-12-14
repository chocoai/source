/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.trainning.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.picture.entity.PicReview;
import com.jeesite.modules.asset.trainning.entity.AmTrainningFeedback;
import com.jeesite.modules.asset.trainning.service.AmTrainningFeedbackService;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 培训反馈Controller
 * @author scarlett
 * @version 2018-06-11
 */
@Controller
@RequestMapping(value = "${adminPath}/trainning/amTrainningFeedback")
public class AmTrainningFeedbackController extends BaseController {

	@Autowired
	private AmTrainningFeedbackService amTrainningFeedbackService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmTrainningFeedback get(String trainningCode, boolean isNewRecord) {
		return amTrainningFeedbackService.get(trainningCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("trainning:amTrainningFeedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmTrainningFeedback amTrainningFeedback, Model model) {
		model.addAttribute("amTrainningFeedback", amTrainningFeedback);
		return "asset/trainning/amTrainningFeedbackList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("trainning:amTrainningFeedback:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmTrainningFeedback> listData(AmTrainningFeedback amTrainningFeedback, HttpServletRequest request, HttpServletResponse response) {
		Page<AmTrainningFeedback> page = amTrainningFeedbackService.findPage(new Page<AmTrainningFeedback>(request, response), amTrainningFeedback); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("trainning:amTrainningFeedback:view")
	@RequestMapping(value = "form")
	public String form(AmTrainningFeedback amTrainningFeedback, Model model) {
		model.addAttribute("amTrainningFeedback", amTrainningFeedback);
		return "asset/trainning/amTrainningFeedbackForm";
	}

	/**
	 * 保存培训反馈
	 */
	/*@RequiresPermissions("trainning:amTrainningFeedback:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmTrainningFeedback amTrainningFeedback) {
		amTrainningFeedbackService.save(amTrainningFeedback);
		return renderResult(Global.TRUE, "保存培训反馈成功！");
	}*/
	@PostMapping(value = "saveInfo/save")
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response, @RequestBody String trainningInfo) {
		JSONObject jsonObject=null;
		JSONObject jsonObject1=new JSONObject();
		try{
			jsonObject=JSONObject.fromObject(trainningInfo);
		}catch (JSONException e){
			jsonObject1.put("code","400");
			jsonObject1.put("message","数据类型错误，无法解析");
			return jsonObject1.toString();
		}
        String trainningCode=jsonObject.getString("trainningCode");
        String writtenBy=jsonObject.getString("writtenBy");
		int num=amTrainningFeedbackService.findHasSubmitted(trainningCode,writtenBy);
		if(num>0){
			jsonObject1.put("code","400");
			jsonObject1.put("message","您已对该课程进行过培训评分，不需重复提交。");
			return jsonObject1.toString();
		}
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate=null;
		Date editDate=new Date();
		try {
			beginDate=df.parse(jsonObject.getString("beginTime"));
		} catch (ParseException e) {
			jsonObject1.put("code","400");
			jsonObject1.put("message","提交失败，转换时间格式出错，请重试！");
			return jsonObject1.toString();
		}
		AmTrainningFeedback feedback=new AmTrainningFeedback();
		feedback.setTrainningCode(trainningCode);//培训单号
		feedback.setWrittenBy(writtenBy);//填写人员
        feedback.setTrainningCourse(jsonObject.getString("trainningCourse"));//培训课程
        feedback.setLecturer(jsonObject.getString("lecturer"));//讲师
		feedback.setBeginTime(beginDate);//开始时间
        feedback.setEditTime(editDate);//填写时间
		feedback.setTrainingMethod(jsonObject.getLong("trainingMethod"));//授课方式
		feedback.setTrainingApplication(jsonObject.getLong("trainingApplication"));//培训应用
		feedback.setComprehension(jsonObject.getLong("comprehension"));//课程理解
		feedback.setNotes(jsonObject.getString("notes"));//课程笔记
		amTrainningFeedbackService.save(feedback);
		jsonObject1.put("code","200");
		jsonObject1.put("message","成功提交课程效果评分表！");
		return jsonObject1.toString();
	}
	/**
	 * 删除培训反馈单
	 */
	@RequiresPermissions("trainning:amTrainningFeedback:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String pkey) {
		if (pkey != null && !"".equals(pkey)) {
           Boolean flag=amTrainningFeedbackService.deleteData(pkey);
           if(flag){
			   return renderResult(Global.TRUE, "成功删除培训反馈单！");
		   }
			return renderResult(Global.FALSE, "删除培训反馈单失败！");
		} else {
		return renderResult(Global.FALSE, "没有可删除的培训反馈单！");
	}
	}

}