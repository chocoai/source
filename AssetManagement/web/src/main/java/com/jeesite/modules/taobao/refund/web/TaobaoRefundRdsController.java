/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.refund.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Refund;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.RefundGetResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.taobao.refund.entity.TaobaoRefundRds;
import com.jeesite.modules.taobao.refund.service.TaobaoRefundRdsService;

import java.util.List;

/**
 * taobao_refund_rdsController
 * @author scarlett
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/refund/taobaoRefundRds")
public class TaobaoRefundRdsController extends BaseController {

	@Autowired
	private TaobaoRefundRdsService taobaoRefundRdsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaobaoRefundRds get(String pkey, boolean isNewRecord) {
		return taobaoRefundRdsService.get(pkey, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("refund:taobaoRefundRds:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaobaoRefundRds taobaoRefundRds, Model model) {
		model.addAttribute("taobaoRefundRds", taobaoRefundRds);
		return "taobao/refund/taobaoRefundRdsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("refund:taobaoRefundRds:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaobaoRefundRds> listData(TaobaoRefundRds taobaoRefundRds, HttpServletRequest request, HttpServletResponse response) {
		Page<TaobaoRefundRds> page = taobaoRefundRdsService.findPage(new Page<TaobaoRefundRds>(request, response), taobaoRefundRds);
		List<TaobaoRefundRds> taobaoRefundRdsList=page.getList();
		for(TaobaoRefundRds taobaoRefundRds1:taobaoRefundRdsList){
			String str=taobaoRefundRds1.getJdpResponse();
			Refund refund= null;
			try {
				refund = TaobaoUtils.parseResponse(str,RefundGetResponse.class).getRefund();
				taobaoRefundRds1.setRefund(refund);
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("refund:taobaoRefundRds:view")
	@RequestMapping(value = "form")
	public String form(TaobaoRefundRds taobaoRefundRds, Model model) {
		try {
			String str=taobaoRefundRds.getJdpResponse();
			Refund refund=TaobaoUtils.parseResponse(str,RefundGetResponse.class).getRefund();
			taobaoRefundRds.setRefund(refund);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		model.addAttribute("taobaoRefundRds", taobaoRefundRds);
		return "taobao/refund/taobaoRefundRdsForm";

	}

	/**
	 * 保存taobao_refund_rds
	 */
	@RequiresPermissions("refund:taobaoRefundRds:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaobaoRefundRds taobaoRefundRds) {
		taobaoRefundRdsService.save(taobaoRefundRds);
		return renderResult(Global.TRUE, text("保存taobao_refund_rds成功！"));
	}
	@RequestMapping(value = "sysRefund")
	@ResponseBody
	public String sysRefund(@RequestBody String str) {
		try {
			JSONObject jsonObject=JSONObject.parseObject(str);
			TaobaoRefundRds taobaoRefundRds=jsonObject.toJavaObject(TaobaoRefundRds.class);
			taobaoRefundRds.setRefundStatus(taobaoRefundRds.getStatus());
			TaobaoRefundRds taobaoRefundRds1=new TaobaoRefundRds();
			String tid=String.valueOf(taobaoRefundRds.getRefundId());
			System.out.println(tid);
			taobaoRefundRds1.setPkey(tid);
			taobaoRefundRds1=taobaoRefundRdsService.get(taobaoRefundRds1);
			taobaoRefundRds.setPkey(tid);
			if(taobaoRefundRds1==null){
				taobaoRefundRds.setIsNewRecord(true);
				taobaoRefundRdsService.insert(taobaoRefundRds);
			}else{
				taobaoRefundRds.setIsNewRecord(false);
				taobaoRefundRdsService.update(taobaoRefundRds);
			}

			return renderResult(Global.TRUE, text("同步淘宝RDS退款单成功！"));
		}catch (Exception e){
			return renderResult(Global.FALSE, text("同步淘宝RDS退款单失败，"+e.getMessage()));
		}

	}
	
}