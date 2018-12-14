/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.asset.fault.entity.BugRegistrationPicture;
import com.jeesite.modules.asset.fault.service.BugRegistrationPictureService;
import com.jeesite.modules.asset.fault.service.FaultRegistrationService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.fault.entity.BugRegistration;
import com.jeesite.modules.asset.fault.service.BugRegistrationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 线上bug登记单Controller
 * @author Scarlett
 * @version 2018-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/fault/bugRegistration")
public class BugRegistrationController extends BaseController {

	@Autowired
	private BugRegistrationService bugRegistrationService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private BugRegistrationPictureService pictureService;
	@Autowired
	FaultRegistrationService faultRegistrationService;
	private static final String CK_PER_FIX = "BUG";
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BugRegistration get(String bugCode, boolean isNewRecord) {
		return bugRegistrationService.get(bugCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fault:bugRegistration:view")
	@RequestMapping(value = {"list", ""})
	public String list(BugRegistration bugRegistration, Model model) {
		model.addAttribute("bugRegistration", bugRegistration);
		return "asset/fault/bugRegistrationList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fault:bugRegistration:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BugRegistration> listData(BugRegistration bugRegistration, HttpServletRequest request, HttpServletResponse response) {
		Page<BugRegistration> page = bugRegistrationService.findPage(new Page<BugRegistration>(request, response), bugRegistration); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fault:bugRegistration:view")
	@RequestMapping(value = "form")
	public String form(BugRegistration bugRegistration, Model model) {
		if (bugRegistration.getIsNewRecord()) {
			bugRegistration.setBugCode(amSeqService.getSeq(CK_PER_FIX));
			bugRegistration.setBugDate(new Date());
			model.addAttribute("picInfo","");
		}else{
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			BugRegistrationPicture pic =null;
			List<BugRegistrationPicture> picsList =pictureService.findPicsByBugCode(bugRegistration.getBugCode());
			if (picsList != null && picsList.size() > 0) {
				for (int i = 0; i < picsList.size(); i++) {
					pic=picsList.get(i);
					jsonObject = new JSONObject();
					jsonObject.put("bugpicCode",pic.getBugpicCode());
					jsonObject.put("savePath",pic.getSavePath());
					jsonObject.put("location",pic.getLocation());
					jsonObject.put("registrationCode",pic.getRegistrationCode());
					jsonArray.add(jsonObject);
				}
				model.addAttribute("picInfo", jsonArray.toString());
			}else{
				model.addAttribute("picInfo","");
			}
		}
		model.addAttribute("bugRegistration", bugRegistration);
		return "asset/fault/bugRegistrationForm";
	}

	/**
	 * 保存线上bug登记单并绑定登记单号到相应的图片
	 */
	@RequiresPermissions("fault:bugRegistration:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BugRegistration bugRegistration,String bugpicCodes) {
		if (bugRegistration.getIsNewRecord()) {
			bugRegistration.setBugCode(amSeqService.getCode(CK_PER_FIX));
		}
		bugRegistrationService.save(bugRegistration,bugpicCodes);
		return renderResult(Global.TRUE, text("成功保存线上bug登记单！"));
	}
	/**
	 * 获取,上传完成后将code传给前端
	 */
	@RequiresPermissions("fault:bugRegistration:edit")
	@PostMapping(value = "uploadPicture")
	@ResponseBody
	public String uploadPicture(@RequestBody String uploadInfo){
		JSONArray jsonArray=JSONArray.fromObject(uploadInfo);
		int size=jsonArray.size();
		if(null!=jsonArray &&jsonArray.size()>0){
			String []codes=new String[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++){
				BugRegistrationPicture picture=(BugRegistrationPicture) JSONObject.toBean(jsonArray.getJSONObject(i),BugRegistrationPicture.class);
				if(null!=picture){
					picture.setIsNewRecord(true);
					pictureService.insert(picture);
					codes[i]=picture.getBugpicCode();
				}
			}
			String faultpicCodes=StringUtils.join(codes,",");
			return renderResult(Global.TRUE,faultpicCodes);
		}
		return renderResult(Global.FALSE,"图片保存失败");
	}
	
	/**
	 * 删除线上bug登记单
	 */
	@RequiresPermissions("fault:bugRegistration:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String bugRegistrationCode) {
		//bugRegistrationService.delete(bugRegistration);
		//return renderResult(Global.TRUE, text("删除线上bug登记单成功！"));
		if (bugRegistrationCode== null || "".equals(bugRegistrationCode)) {
			return renderResult(Global.FALSE, "没有可删除的线上bug登记单！");
		}else {
			Boolean flag = bugRegistrationService.deleteData(bugRegistrationCode);
			if (flag) {
				return renderResult(Global.TRUE, "成功删除线上bug登记单！");
			}
			return renderResult(Global.TRUE, "线上bug登记单删除失败！");
		}
	}

	/**
	 * 后台删除图片
	 * @param info
	 * @return
	 */
	@RequiresPermissions("fault:bugRegistration:edit")
	@PostMapping(value = "deletePicture")
	@ResponseBody
	public String deletePicture(@RequestBody String info){
		JSONObject jsonObject=JSONObject.fromObject(info);
		String faultpicCode=jsonObject.getString("bugpicCode");
		String message="";
		if (faultpicCode != null || !"".equals(faultpicCode)) {
			String[] str = faultpicCode.split(",");
			BugRegistrationPicture pic=null;
			List<String> keys=new ArrayList<String>();
			for (int i = 0; i < str.length; i++) {
				String bugPicCode = str[i];
				try {
					pic = new BugRegistrationPicture();
					pic.setBugpicCode(bugPicCode);
					pic = pictureService.get(pic);
					if(pic!=null){
						String path=faultRegistrationService.getAliPath(pic.getSavePath());
						if(null!=path &&!"".equals(path)) {
							keys.add(path);
						}
						pictureService.delete(bugPicCode);
					}
				} catch (Exception e) {
					message=message+bugPicCode+",";
				}
			}
			try {
				//删除阿里云图片
				amUtilService.deletePicAli(keys);
			} catch (OSSException e) {
				e.printStackTrace();
			} catch (ClientException e) {
				e.printStackTrace();
			}
			if(!"".equals(message)){
				return renderResult(Global.FALSE, "图片"+message+"删除失败！");
			}
			return renderResult(Global.TRUE, "成功删除图片！");
		}else{
			return renderResult(Global.FALSE, "没有图片需要删除！");
		}
	}
	
}