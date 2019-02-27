/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.storevideo.ovopark.api.ApiService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkDevice;
import com.jeesite.modules.storevideo.ovopark.service.SvOvoparkDeviceService;

import java.util.List;
import java.util.Map;

/**
 * 万店掌设备Controller
 * @author Philip Guan
 * @version 2019-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/ovopark/device")
public class SvOvoparkDeviceController extends BaseController {

	@Autowired
	private SvOvoparkDeviceService svOvoparkDeviceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvOvoparkDevice get(Long deviceId, boolean isNewRecord) {
		return svOvoparkDeviceService.get(deviceId==null?null:deviceId.toString(), isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:ovopark:device:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvOvoparkDevice svOvoparkDevice, Model model) {
		model.addAttribute("svOvoparkDevice", svOvoparkDevice);
		return "storevideo/ovopark/device/svOvoparkDeviceList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:ovopark:device:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvOvoparkDevice> listData(SvOvoparkDevice svOvoparkDevice, HttpServletRequest request, HttpServletResponse response) {
		Page<SvOvoparkDevice> page = svOvoparkDeviceService.findPage(new Page<SvOvoparkDevice>(request, response), svOvoparkDevice); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:ovopark:device:view")
	@RequestMapping(value = "form")
	public String form(SvOvoparkDevice svOvoparkDevice, Model model) {
		model.addAttribute("svOvoparkDevice", svOvoparkDevice);
		return "storevideo/ovopark/device/svOvoparkDeviceForm";
	}

	/**
	 * 保存万店掌设备
	 */
	@RequiresPermissions("sv:ovopark:device:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvOvoparkDevice svOvoparkDevice) {
		//svOvoparkDeviceService.save(svOvoparkDevice);
        Map<String, String> map = MapUtils.newHashMap();
        map.put("deviceId", svOvoparkDevice.getDeviceId().toString());
        map.put("groupid", svOvoparkDevice.getGroupId().toString());
        ApiService.bindDevice(map);
        syncData();
		return renderResult(Global.TRUE, text("绑定设备成功！"));
	}

	@RequiresPermissions("sv:ovopark:edit")
	@GetMapping("syncData")
	@ResponseBody
	public String syncData(){
		List<SvOvoparkDevice> list = ApiService.getDevices();
		if(ListUtils.isNotEmpty(list)){
			svOvoparkDeviceService.deleteAll();
			for (SvOvoparkDevice svOvoparkDevice : list){
				svOvoparkDevice.setIsNewRecord(true);
				//svOvoparkDevice.setDeviceCode(UUID.randomUUID().toString());
			}
			svOvoparkDeviceService.insertBatch(list);
			return renderResult(Global.TRUE, text("同步人脸设备成功！"));
		}
		return renderResult(Global.FALSE, text("同步人脸设备失败！"));
	}
	
}