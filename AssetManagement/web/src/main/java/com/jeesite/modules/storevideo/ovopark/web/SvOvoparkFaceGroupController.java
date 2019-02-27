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
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkFaceGroup;
import com.jeesite.modules.storevideo.ovopark.service.SvOvoparkFaceGroupService;

import java.util.List;
import java.util.Map;

/**
 * 万店掌人脸分组Controller
 * @author Philip Guan
 * @version 2019-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/ovopark")
public class SvOvoparkFaceGroupController extends BaseController {

	@Autowired
	private SvOvoparkFaceGroupService svOvoparkFaceGroupService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvOvoparkFaceGroup get(String id, boolean isNewRecord) {
		return svOvoparkFaceGroupService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:ovopark:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvOvoparkFaceGroup svOvoparkFaceGroup, Model model) {
		model.addAttribute("svOvoparkFaceGroup", svOvoparkFaceGroup);
		return "storevideo/ovopark/svOvoparkFaceGroupList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:ovopark:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvOvoparkFaceGroup> listData(SvOvoparkFaceGroup svOvoparkFaceGroup, HttpServletRequest request, HttpServletResponse response) {
		Page<SvOvoparkFaceGroup> page = svOvoparkFaceGroupService.findPage(new Page<SvOvoparkFaceGroup>(request, response), svOvoparkFaceGroup); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:ovopark:view")
	@RequestMapping(value = "form")
	public String form(SvOvoparkFaceGroup svOvoparkFaceGroup, Model model) {
		model.addAttribute("svOvoparkFaceGroup", svOvoparkFaceGroup);
		return "storevideo/ovopark/svOvoparkFaceGroupForm";
	}

	/**
	 * 保存万店掌人脸分组
	 */
	@RequiresPermissions("sv:ovopark:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvOvoparkFaceGroup svOvoparkFaceGroup) {
	    Map<String, String> map = MapUtils.newHashMap();
	    map.put("groupname", svOvoparkFaceGroup.getGroupName());
	    SvOvoparkFaceGroup result = ApiService.addGroup(map);
	    if(result != null){
	        svOvoparkFaceGroup.setId(result.getId());
            svOvoparkFaceGroup.setIsNewRecord(true);
            svOvoparkFaceGroup.setOrgid(ApiService.orgidLong);
            svOvoparkFaceGroupService.save(svOvoparkFaceGroup);
            return renderResult(Global.TRUE, text("保存万店掌人脸分组成功！"));
        }
        return renderResult(Global.FALSE, text("保存万店掌人脸分组失败！"));
	}

    @RequiresPermissions("sv:ovopark:edit")
	@GetMapping("syncData")
    @ResponseBody
	public String syncData(){
		List<SvOvoparkFaceGroup> list = ApiService.getGroups();
		if(ListUtils.isNotEmpty(list)){
			svOvoparkFaceGroupService.deleteAll();
			for (SvOvoparkFaceGroup svOvoparkFaceGroup : list){
			    svOvoparkFaceGroup.setIsNewRecord(true);
			    svOvoparkFaceGroupService.insert(svOvoparkFaceGroup);
            }
            return renderResult(Global.TRUE, text("同步万店掌人脸分组成功！"));
		}
        return renderResult(Global.FALSE, text("同步万店掌人脸分组失败！"));
	}
	
}