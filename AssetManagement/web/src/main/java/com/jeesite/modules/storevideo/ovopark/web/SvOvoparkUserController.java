/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.DateUtils;
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
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkUser;
import com.jeesite.modules.storevideo.ovopark.service.SvOvoparkUserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 万店掌用户Controller
 * @author Philip Guan
 * @version 2019-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/ovopark/user")
public class SvOvoparkUserController extends BaseController {

	@Autowired
	private SvOvoparkUserService svOvoparkUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvOvoparkUser get(String userId, boolean isNewRecord) {
		return svOvoparkUserService.get(userId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:ovopark:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvOvoparkUser svOvoparkUser, Model model) {
		model.addAttribute("svOvoparkUser", svOvoparkUser);
		return "storevideo/ovopark/user/userList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:ovopark:user:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvOvoparkUser> listData(SvOvoparkUser svOvoparkUser, HttpServletRequest request, HttpServletResponse response) {
		Page<SvOvoparkUser> page = svOvoparkUserService.findPage(new Page<SvOvoparkUser>(request, response), svOvoparkUser); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:ovopark:user:view")
	@RequestMapping(value = "form")
	public String form(SvOvoparkUser svOvoparkUser, Model model) {
	    if(svOvoparkUser.getOrgId() == null) svOvoparkUser.setOrgId(ApiService.orgidLong);
        if(svOvoparkUser.getDepartNo() == null) svOvoparkUser.setDepartNo(ApiService.orgidLong);
		model.addAttribute("svOvoparkUser", svOvoparkUser);
		return "storevideo/ovopark/user/userForm";
	}

	/**
	 * 保存万店掌用户
	 */
	@RequiresPermissions("sv:ovopark:user:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvOvoparkUser svOvoparkUser) {
		svOvoparkUserService.save(svOvoparkUser);
		ApiService.addUser(svOvoparkUser);
		return renderResult(Global.TRUE, text("保存万店掌用户成功！"));
	}

    @RequiresPermissions("sv:ovopark:edit")
    @GetMapping("syncData")
    @ResponseBody
    public String syncData(){

	    Map<String, String> map = MapUtils.newHashMap();

	    //获取最新的一条数据
        Page<SvOvoparkUser> lastItemList = svOvoparkUserService.findPage(new Page<>(1, 1), null);
        if(lastItemList.getPageSize() > 0){
            //参数设置为最后日期前一分钟
            map.put("stime", DateUtils.formatDate(DateUtils.addMinutes(lastItemList.getList().get(0).getCreateDate(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        List<SvOvoparkUser> addlist = ListUtils.newArrayList();
        List<SvOvoparkUser> list = ApiService.getUsers(map);
        //if(ListUtils.isNotEmpty(list)){
        //    for (SvOvoparkUser svOvoparkUser : list){
        //        svOvoparkUser.setIsNewRecord(true);
        //        //svOvoparkDevice.setDeviceCode(UUID.randomUUID().toString());
        //    }
        //    svOvoparkDeviceService.insertBatch(list);
        //    return renderResult(Global.TRUE, text("同步人脸设备成功！"));
        //}
        return renderResult(Global.FALSE, text("同步人脸设备失败！"));
    }
	
}