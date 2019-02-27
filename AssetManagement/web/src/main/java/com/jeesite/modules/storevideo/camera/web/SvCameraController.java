/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.camera.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.storevideo.video.entity.SvVideo;
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
import com.jeesite.modules.storevideo.camera.entity.SvCamera;
import com.jeesite.modules.storevideo.camera.service.SvCameraService;

/**
 * 摄像头Controller
 * @author AlbertFeng
 * @version 2019-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/camera/svCamera")
public class SvCameraController extends BaseController {

	@Autowired
	private SvCameraService svCameraService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvCamera get(String cameraCode, boolean isNewRecord) {
		return svCameraService.get(cameraCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("camera:svCamera:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvCamera svCamera, Model model) {
		model.addAttribute("svCamera", svCamera);
		return "storevideo/camera/svCameraList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("camera:svCamera:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvCamera> listData(SvCamera svCamera, HttpServletRequest request, HttpServletResponse response) {
		Page<SvCamera> page = svCameraService.findPage(new Page<SvCamera>(request, response), svCamera); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("camera:svCamera:view")
	@RequestMapping(value = "form")
	public String form(SvCamera svCamera, Model model) {
		model.addAttribute("svCamera", svCamera);
		return "storevideo/camera/svCameraForm";
	}

	/**
	 * 保存摄像头
	 */
	@RequiresPermissions("camera:svCamera:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvCamera svCamera) {
		try{
			if(svCamera.getIsNewRecord()){
			    svCamera.setCameraCode(svCamera.getDeviceMac());
            }
			svCameraService.save(svCamera);

		}catch (Exception ex){
			ex.printStackTrace();
		}
		return renderResult(Global.TRUE, text("保存摄像头成功！"));
	}
	
	/**
	 * 删除摄像头
	 */
	@RequiresPermissions("camera:svCamera:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SvCamera svCamera) {
		svCameraService.delete(svCamera);
		return renderResult(Global.TRUE, text("删除摄像头成功！"));
	}

}