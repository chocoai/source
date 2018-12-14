/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeGetResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.taobao.entity.TaobaoOrderRds;
import com.jeesite.modules.taobao.service.TaobaoOrderRdsService;

import java.util.Date;
import java.util.List;

/**
 * taobao_order_rdsController
 * @author scarlett
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/taobao/taobaoOrderRds")
public class TaobaoOrderRdsController extends BaseController {

	@Autowired
	private TaobaoOrderRdsService taobaoOrderRdsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaobaoOrderRds get(String pkey, boolean isNewRecord) {
		return taobaoOrderRdsService.get(pkey, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("taobao:taobaoOrderRds:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaobaoOrderRds taobaoOrderRds, Model model) {
		model.addAttribute("taobaoOrderRds", taobaoOrderRds);
		return "taobao/taobaoOrderRdsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("taobao:taobaoOrderRds:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaobaoOrderRds> listData(TaobaoOrderRds taobaoOrderRds, HttpServletRequest request, HttpServletResponse response) {
		Page<TaobaoOrderRds> page = taobaoOrderRdsService.findPage(new Page<TaobaoOrderRds>(request, response), taobaoOrderRds);
		List<TaobaoOrderRds> taobaoOrderRdsList=page.getList();
		//	1417350367000
		for(TaobaoOrderRds taobaoOrderRds1:taobaoOrderRdsList){
			String str=taobaoOrderRds1.getJdpResponse();
			TradeGetResponse tradeGetResponse= null;
			try {
				tradeGetResponse = TaobaoUtils.parseResponse(str,TradeGetResponse.class);
				Trade trade= tradeGetResponse.getTrade();
				taobaoOrderRds1.setTrade(trade);
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("taobao:taobaoOrderRds:view")
	@RequestMapping(value = "form")
	public String form(TaobaoOrderRds taobaoOrderRds, Model model) {
		String str=taobaoOrderRds.getJdpResponse();
		try {
			TradeGetResponse tradeGetResponse=TaobaoUtils.parseResponse(str,TradeGetResponse.class);
			Trade trade= tradeGetResponse.getTrade();
			taobaoOrderRds.setTrade(trade);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		model.addAttribute("taobaoOrderRds", taobaoOrderRds);
		return "taobao/taobaoOrderRdsForm";
	}

	/**
	 * 保存taobao_order_rds
	 */
	@RequiresPermissions("taobao:taobaoOrderRds:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaobaoOrderRds taobaoOrderRds) {
		taobaoOrderRdsService.save(taobaoOrderRds);
		return renderResult(Global.TRUE, text("保存taobao_order_rds成功！"));
	}
	
	/**
	 * 删除taobao_order_rds
	 */
	@RequiresPermissions("taobao:taobaoOrderRds:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaobaoOrderRds taobaoOrderRds) {
		taobaoOrderRdsService.delete(taobaoOrderRds);
		return renderResult(Global.TRUE, text("删除taobao_order_rds成功！"));
	}
	@RequestMapping(value = "sysOrder")
	@ResponseBody
	public String sysOrder(@RequestBody String str) {
		JSONObject jsonObject=JSONObject.parseObject(str);
		TaobaoOrderRds taobaoOrderRds=jsonObject.toJavaObject(TaobaoOrderRds.class);
		try {
			Long tid=taobaoOrderRds.getTid();
			if(tid==null){
				return renderResult(Global.FALSE, text("tid为空，同步失败！"));
			}
			taobaoOrderRds.setRdsStatus(taobaoOrderRds.getStatus());
			TaobaoOrderRds taobaoOrderRds1=new TaobaoOrderRds();
			taobaoOrderRds1.setPkey(String.valueOf(taobaoOrderRds.getTid()));
			taobaoOrderRds1=taobaoOrderRdsService.get(taobaoOrderRds1);
			taobaoOrderRds.setPkey(String.valueOf(taobaoOrderRds.getTid()));
			if(taobaoOrderRds1==null){
				taobaoOrderRds.setIsNewRecord(true);
				taobaoOrderRdsService.insert(taobaoOrderRds);

			}else{
				taobaoOrderRds.setIsNewRecord(false);
				taobaoOrderRdsService.update(taobaoOrderRds);
			}

			return renderResult(Global.TRUE, text("同步淘宝RDS订单成功！"));
		}catch (Exception e){
			return renderResult(Global.FALSE, text("同步淘宝RDS订单失败，"+e.getMessage()));
		}

	}

	
}