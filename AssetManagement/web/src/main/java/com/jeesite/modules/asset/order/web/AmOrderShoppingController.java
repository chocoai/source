/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.order.entity.Shopping;
import com.jeesite.modules.asset.order.service.AmOrderLogService;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.order.entity.AmOrderShopping;
import com.jeesite.modules.asset.order.service.AmOrderShoppingService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 导购购物车Controller
 * @author len
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/order/amOrderShopping")
public class AmOrderShoppingController extends BaseController {
	@Autowired
	private AmOrderLogService amOrderLogService;
	@Autowired
	private AmOrderShoppingService amOrderShoppingService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmOrderShopping get(String seq, boolean isNewRecord) {
		return amOrderShoppingService.get(seq, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:amOrderShopping:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmOrderShopping amOrderShopping, Model model) {
		model.addAttribute("amOrderShopping", amOrderShopping);
		return "asset/order/amOrderShoppingList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:amOrderShopping:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmOrderShopping> listData(AmOrderShopping amOrderShopping, HttpServletRequest request, HttpServletResponse response) {
		Page<AmOrderShopping> page = amOrderShoppingService.findPage(new Page<AmOrderShopping>(request, response), amOrderShopping); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:amOrderShopping:view")
	@RequestMapping(value = "form")
	public String form(AmOrderShopping amOrderShopping, Model model) {
		model.addAttribute("amOrderShopping", amOrderShopping);
		return "asset/order/amOrderShoppingForm";
	}

	/**
	 * 保存导购购物车
	 */
	@RequiresPermissions("order:amOrderShopping:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmOrderShopping amOrderShopping) {
		amOrderShoppingService.save(amOrderShopping);
		return renderResult(Global.TRUE, text("保存导购购物车成功！"));
	}
	
	/**
	 * 删除导购购物车
	 */
	@RequiresPermissions("order:amOrderShopping:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmOrderShopping amOrderShopping) {
		amOrderShoppingService.delete(amOrderShopping);
		return renderResult(Global.TRUE, text("删除导购购物车成功！"));
	}

	/**
	 * 添加到购物车
	 * @param numIid
	 * @param skuId
	 * @param count
	 * @return
	 */
	@RequiresPermissions("order:amOrder:edit")
	@ResponseBody
	@RequestMapping(value = "addShopping", method = RequestMethod.GET)
	public ReturnInfo addShopping (String numIid, String skuId, int count,String flag, HttpServletRequest request) {
		try {
			// 当前登录用户编码
			String userCode = UserUtils.getUser().getUserCode();
			// 当前登录用户名
			String guideName = UserUtils.getUser().getUserName();
			AmOrderShopping orderShopping = new AmOrderShopping();
			orderShopping.setGuideCode(userCode);
			orderShopping.setGoodsStatus("0");
			orderShopping.setSkuId(skuId);
			// 根据当前登录账号 skuid查询购物车是否存在此商品
			List<AmOrderShopping> shoppingList = amOrderShoppingService.findList(orderShopping);
			if (shoppingList != null && shoppingList.size() > 0) {
				orderShopping = shoppingList.get(0);
				// 0代表加入购物车 累加件数
				if ("0".equals(flag)) {
					orderShopping.setNum(count + orderShopping.getNum());
				} else if ("1".equals(flag)){
					// 1代表减件数
					Integer num = orderShopping.getNum() - count;
					if (num < 1) {
						return ReturnDate.error(11002, "数量不能小于1");
					}
					orderShopping.setNum(num);
				} else if ("2".equals(flag)) {
				    // 如果是2代表输入 那么直接更新件数
                    orderShopping.setNum(count);
                }
				// 若存在 更新件数
				orderShopping.setUpdateTime(new Date());
				orderShopping.setIsNewRecord(false);
				amOrderShoppingService.save(orderShopping);
				return ReturnDate.success();
			} else {
				// 若不存在 插入数据到购物车表
				Shopping shopping = amOrderShoppingService.getSkuInfo(skuId);
				if (shopping == null) {
					return ReturnDate.error(11001, "商品已下架");
				} else {
					AmOrderShopping amOrderShopping = new AmOrderShopping();
					// 导购账号
					amOrderShopping.setGuideCode(userCode);
					// 导购昵称
					amOrderShopping.setGuideName(guideName);
					// 商品id
					amOrderShopping.setNumIid(numIid);
					// 商品名称
					amOrderShopping.setTitle(shopping.getName());
					// 商品规格
					amOrderShopping.setProperties(shopping.getProperty());
					// 商品sku
					amOrderShopping.setOuterId(shopping.getOuterCode());
					// 商品skuid
					amOrderShopping.setSkuId(skuId);
					// 商品价格
					amOrderShopping.setPrice(shopping.getPrice());
					// 件数
					amOrderShopping.setNum(count);
					// 状态
					amOrderShopping.setGoodsStatus("0");
					// 商品主图
					amOrderShopping.setPicUrl(shopping.getPic());
					// 库存数
					amOrderShopping.setQuantity(shopping.getQuantity());
					// 店铺
					amOrderShopping.setStoreName(shopping.getStorename());
					// 创建时间
					amOrderShopping.setCreateTime(new Date());
					amOrderShopping.setIsNewRecord(true);
					amOrderShoppingService.save(amOrderShopping);
					return ReturnDate.success();
				}
			}
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return ReturnDate.error(11010, "服务异常!");
		}
	}

	/**
	 * 移除购物车
	 */
	@RequiresPermissions("order:amOrder:edit")
	@ResponseBody
	@RequestMapping(value = "removeShopping", method = RequestMethod.POST)
	public ReturnInfo removeShopping (@RequestBody String skuIds, HttpServletRequest request) {
		try {
			if (skuIds == null || "".equals(skuIds) || "[]".equals(skuIds)) {
				return ReturnDate.error(11002, "请先选择商品再删除！");
			}
			List<String> skuIdList = ListUtils.newArrayList();
			JSONArray jsonArray = JSONArray.parseArray(skuIds);
			for (int i = 0; i< jsonArray.size(); i++) {
				skuIdList.add(jsonArray.getJSONObject(i).get("skuid").toString());
			}
			String userCode = UserUtils.getUser().getUserCode();
			amOrderShoppingService.updateBySkuIdList(skuIdList, userCode);
			return ReturnDate.success();
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return ReturnDate.error(11010, "服务异常!");
		}
	}

	/**
	 * 查询导购购物车符合条件的商品
	 */
	@ResponseBody
	@RequiresPermissions("order:amOrder:view")
	@RequestMapping(value = "queryShopping", method = RequestMethod.GET)
	public ReturnInfo queryShopping(HttpServletRequest request) {
		try {
			// 当前登录用户编码
			String userCode = UserUtils.getUser().getUserCode();
			AmOrderShopping amOrderShopping = new AmOrderShopping();
			amOrderShopping.setGuideCode(userCode);
			amOrderShopping.setGoodsStatus("0");
			// 根据导购账号获取购物车中状态为有效的商品
			List<AmOrderShopping> orderShoppingList = amOrderShoppingService.findList(amOrderShopping);
			List<String> list = ListUtils.newArrayList();
			if (orderShoppingList != null && orderShoppingList.size() > 0) {
				for (AmOrderShopping shopping : orderShoppingList) {
					list.add(shopping.getSkuId());
				}
			} else {
				return ReturnDate.error(11001, "购物车为空");
			}
			// 根据购物车表中有效的商品skuId查询商品资料中的数据
			List<TbSku> tbSkuList = amOrderShoppingService.selectBySkuId(list);
			List<Shopping> shoppingList = ListUtils.newArrayList();
			List<String> skuIdList = ListUtils.newArrayList();
			for (AmOrderShopping orderShopping : orderShoppingList) {
				TbSku tbSku = null;
				try {
					// 根据购物车中skuId获取商品资料中查出的商品sku
					tbSku = tbSkuList.stream().filter(s -> String.valueOf(s.getSkuId()).equals(orderShopping.getSkuId())).findFirst().get();
				} catch (NoSuchElementException e) {

				}
				// 如果能获取到说明skuId没有变 取库存数和真实售价
				if (tbSku != null) {
					getShoppingList(shoppingList, orderShopping, tbSku, "0");
				} else {

					// 如果获取不到说明购物车中的skuId变化了 那么库存数更新为0 把商品状态改为无效
					skuIdList.add(orderShopping.getSkuId());
//					getShoppingList(shoppingList, orderShopping, tbSku, "1");
				}
			}
			if (ListUtils.isNotEmpty(skuIdList)) {
				amOrderShoppingService.updateBySkuIdList(skuIdList, userCode);
			}
			return ReturnDate.success(shoppingList);
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return ReturnDate.error(11010, "服务异常!");
		}
	}

	/**
	 * 获取数据传到前端
	 * @param shoppingList
	 * @param orderShopping
	 * @param tbSku
	 * @param flag
	 */
	public void getShoppingList(List<Shopping> shoppingList, AmOrderShopping orderShopping, TbSku tbSku, String flag) {
		Shopping shopping = new Shopping();
		// 商品id
		shopping.setId(orderShopping.getNumIid());
		// 商品名称
		shopping.setName(orderShopping.getTitle());
		// 商品sku
		shopping.setOuterCode(orderShopping.getOuterId());
		// 商品主图
		shopping.setPic(orderShopping.getPicUrl());
		// 商品规格
		shopping.setProperty(orderShopping.getProperties());
		// 商品店铺
		shopping.setStorename(orderShopping.getStoreName());
		// 数量
		shopping.setCount(orderShopping.getNum());
		// 商品skuId
		shopping.setSkuid(orderShopping.getSkuId());
		// 如果是0代表商品资料存在此SkuId 那么取商品资料中的真实售价库存数
		if ("0".equals(flag)) {
			shopping.setPrice(tbSku.getRealPrice());
			shopping.setQuantity(tbSku.getQuantity().intValue());
		}
//		else {
//			// 如果是1代表商品资料不存在此SkuId 那么库存数更新为0 用于前端判断是否显示
//			shopping.setPrice(orderShopping.getPrice());
//			shopping.setQuantity(0);
//		}
		shoppingList.add(shopping);
	}
}