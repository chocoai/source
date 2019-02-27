/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClient;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientService;

/**
 * 电视在线客户端Controller
 * @author Philip Guan
 * @version 2019-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/tvclient")
public class SvTvClientController extends BaseController {

	@Autowired
	private SvTvClientService svTvClientService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvTvClient get(String clientCode, boolean isNewRecord) {
		return svTvClientService.get(clientCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:tvclient:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvTvClient svTvClient, Model model) {
		model.addAttribute("svTvClient", svTvClient);
		return "storevideo/tvclient/svTvClientList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:tvclient:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvTvClient> listData(SvTvClient svTvClient, HttpServletRequest request, HttpServletResponse response) {
		Page<SvTvClient> page = svTvClientService.findPage(new Page<SvTvClient>(request, response), svTvClient); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:tvclient:view")
	@RequestMapping(value = "form")
	public String form(SvTvClient svTvClient, Model model) {
		model.addAttribute("svTvClient", svTvClient);
		return "modules/storevideo/tvclient/svTvClientForm";
	}
	
}