/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.order.entity.k3Info.Detail;
import com.jeesite.modules.asset.order.entity.k3Info.Order;
import com.jeesite.modules.asset.order.service.AmOrderShoppingService;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.entity.TbTianmaoItems;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.distribution.material.service.DistrMaterialService;
import com.jeesite.modules.distribution.order.entity.DistrOrderDetail;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.distribution.order.entity.DistrOrder;
import com.jeesite.modules.distribution.order.service.DistrOrderService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分销订单Controller
 * @author len
 * @version 2019-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/order/distrOrder")
public class DistrOrderController extends BaseController {

	@Autowired
	private DistrOrderService distrOrderService;
	@Value("${K3NOREALURL}")
	private String K3NOREALURL;
	@Autowired
	private TbSkuService tbSkuService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private DistrMaterialService distrMaterialService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DistrOrder get(String documentCode, boolean isNewRecord) {
		return distrOrderService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:distrOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(DistrOrder distrOrder, Model model) {
		model.addAttribute("distrOrder", distrOrder);
		return "distribution/order/distrOrderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:distrOrder:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DistrOrder> listData(DistrOrder distrOrder, HttpServletRequest request, HttpServletResponse response) {
		Page<DistrOrder> page = distrOrderService.findPage(new Page<DistrOrder>(request, response), distrOrder); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:distrOrder:view")
	@RequestMapping(value = "form")
	public String form(DistrOrder distrOrder, Model model) {
		model.addAttribute("distrOrder", distrOrder);
		return "distribution/order/distrOrderForm";
	}

	/**
	 * 保存分销订单
	 */
	@RequiresPermissions("order:distrOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DistrOrder distrOrder) {
		distrOrderService.save(distrOrder);
		return renderResult(Global.TRUE, text("保存分销订单成功！"));
	}
	
	/**
	 * 删除分销订单
	 */
	@RequiresPermissions("order:distrOrder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DistrOrder distrOrder) {
		distrOrderService.delete(distrOrder);
		return renderResult(Global.TRUE, text("删除分销订单成功！"));
	}

	@Autowired
	private TbTianmaoItemsService tbTianmaoItemsService;
	/**
	 * 创建分销订单
	 */
	@RequiresPermissions("distribution:api")
	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo createOrder(@RequestBody String requet) {
		JSONObject reqJson = JSONObject.parseObject(requet);
		// 省
		String province = reqJson.get("province").toString();
		// 市
		String city = reqJson.get("city").toString();
		// 区
		String region = reqJson.get("region").toString();
		// 客户
		String customerName = reqJson.get("customerName").toString();
		// 移动电话
		String phone = reqJson.get("phone").toString();
		// 详细地址
		String address = reqJson.get("address").toString();
		// 配送方式
		String distribution = reqJson.get("distribution").toString();
		// 支付方式
		String payType = reqJson.get("payType").toString();
		// 备注
		String remarks = null;
		if (reqJson.containsKey("remarks")) {
			remarks = reqJson.get("remarks").toString();
		}
		Double logisticsFee = 0D;
		if (reqJson.containsKey("logisticsFee")) {
			String logistics = reqJson.get("logisticsFee").toString();
			if (StringUtils.isNotEmpty(logistics)) {
				logisticsFee = Double.parseDouble(logistics);
			}
		}
		DistrOrder distrOrder = new DistrOrder();
		// 订单号
		distrOrder.setDocumentCode(amSeqService.getCode("FX", "yyyyMMdd", 6));
		distrOrder.setDocumentType("分销订单");
		distrOrder.setDocumentStatus("创建");
		distrOrder.setCustomerName(customerName);
		distrOrder.setMobilePhone(phone);
		distrOrder.setProvince(province);
		distrOrder.setCity(city);
		distrOrder.setRegion(region);
		distrOrder.setDetailedAddress(address);
		distrOrder.setDistribution(distribution);
		distrOrder.setPayType(payType);
		distrOrder.setRemarks(remarks);
		distrOrder.setLogisticsFee(logisticsFee);
		// 客户来源默认线下
		distrOrder.setCustomerSource("线下");
		JSONArray jsonArray = null;
		if (reqJson.containsKey("goods")) {
			jsonArray = reqJson.getJSONArray("goods");
		}
		// 商品总额
		Double totalPrice = 0D;
		List<DistrOrderDetail> distrOrderDetailList = ListUtils.newArrayList();
		List<String> skuIdList = ListUtils.newArrayList();
		List<String> numIidList = ListUtils.newArrayList();
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				DistrOrderDetail distrOrderDetail = new DistrOrderDetail();
				// 商品Id
				String numIid = jsonArray.getJSONObject(i).get("numIid").toString();
				// 商品名称
				String goodsName = jsonArray.getJSONObject(i).get("goodsName").toString();
				// skuId
				String skuId = jsonArray.getJSONObject(i).get("skuId").toString();
				// 数量
				String amount = jsonArray.getJSONObject(i).get("amount").toString();
				skuIdList.add(skuId);
				distrOrderDetail.setDocumentCode(distrOrder);
				distrOrderDetail.setNumIid(numIid);
				numIidList.add(numIid);
				distrOrderDetail.setGoodsName(goodsName);
				distrOrderDetail.setSkuId(skuId);
				distrOrderDetail.setQuantity(Long.valueOf(amount));
				distrOrderDetailList.add(distrOrderDetail);
			}
			// 根据商品id获取主图图片
			List<TbItemImgs> itemImgList = tbTianmaoItemsService.getLastImg(numIidList);
			// 根据订单的skuid获取sku信息
			List<TbSku> tbSkuList = tbSkuService.selectBySkuIdList(skuIdList);
			if (ListUtils.isNotEmpty(tbSkuList)) {
				for (DistrOrderDetail distrOrderDetail : distrOrderDetailList) {
					Optional<TbSku> optionalTbSku = tbSkuList.stream().filter(s ->String.valueOf(s.getSkuId()).equals(distrOrderDetail.getSkuId())).findFirst();
					if (optionalTbSku.isPresent()) {
						TbSku tbSku = optionalTbSku.get();
						// sku
						distrOrderDetail.setSku(tbSku.getOuterId());
						// 规格
						distrOrderDetail.setSpec(tbSku.getProperties());
						// 分销价
						distrOrderDetail.setPrice(tbSku.getDistributionPrice());
						// sku图片
						if (StringUtils.isNotEmpty(tbSku.getSkuUrl())) {
							distrOrderDetail.setSkuUrl(tbSku.getSkuUrl());
						} else {
							Optional<TbItemImgs> optionalItemImg = itemImgList.stream().filter(s ->String.valueOf(s.getItemId()).equals(distrOrderDetail.getNumIid())).findFirst();
							if (optionalItemImg.isPresent()) {
								distrOrderDetail.setSkuUrl(optionalItemImg.get().getUrl());
							}
						}
						// 金额
						Double skuMoney = NumberUtils.mul(tbSku.getDistributionPrice(), distrOrderDetail.getQuantity());
						distrOrderDetail.setAmount(skuMoney);
						// 商品总额
						totalPrice = NumberUtils.add(totalPrice, skuMoney);
					}
				}
			}

		}
		if (ListUtils.isNotEmpty(distrOrderDetailList)) {
			// 合计应收
			Double totalFee = NumberUtils.sub(totalPrice, distrOrder.getLogisticsFee());
			distrOrder.setTotalFee(totalFee);
			distrOrder.setTotalPrice(totalPrice);
			User user = UserUtils.getUser();
			// 创建人
			distrOrder.setCreateBy(user.getUserName());
			// 创建人用户编码
			distrOrder.setCreateCode(user.getUserCode());
			// 创建时间
			distrOrder.setCreateTime(new Date());
			// 部门编码 后续上下级查询订单可能会用到
			distrOrder.setOfficeCode(EmpUtils.getOffice().getOfficeCode());
			distrOrder.setIsNewRecord(true);
			distrOrder.setDistrOrderDetailList(distrOrderDetailList);
			distrOrderService.saveOrder(distrOrder, skuIdList, user.getUserCode());
			return ReturnDate.success();
		} else {
			return ReturnDate.error(15001, "订单创建失败");
		}
	}


	@RequiresPermissions("distribution:api")
	@RequestMapping(value = "conform", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo conform(DistrOrder distrOrder) {
		if (ListUtils.isNotEmpty(distrOrder.getDistrOrderDetailList())) {
			// 更新订单状态
			distrOrder.setDocumentStatus("确认");
			User user = UserUtils.getUser();
			distrOrder.setConfirmBy(user.getUserName());
			distrOrder.setConfirmCode(user.getUserCode());
			distrOrder.setConfirmDate(new Date());
			distrOrderService.saveData(distrOrder);
			return ReturnDate.success();
		} else {
			return ReturnDate.error(15001, "订单操作失败");
		}
	}

	/**
	 * 订单取消确认
	 * @param distrOrder
	 * @return
	 */
	@RequiresPermissions("distribution:api")
	@RequestMapping(value = "cancleConform", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo cancleConform(DistrOrder distrOrder) {
		if (!"确认".equals(distrOrder.getDocumentStatus())) {
			return ReturnDate.error(15001, "订单不是确认状态");
		}
		distrOrderService.cancleConform(distrOrder.getDocumentCode());
		return ReturnDate.success();
	}

	/**
	 * 分销订单提交K3
	 * @param distrOrder
	 * @return
	 */
	@RequiresPermissions("distribution:api")
	@ResponseBody
	@RequestMapping(value = "submit", method = RequestMethod.GET)
	public ReturnInfo submit (DistrOrder distrOrder) {
		Order orders = new Order();
		orders.setF_BillNo(distrOrder.getDocumentCode());
		orders.setF_ReceiverName(distrOrder.getCustomerName());
		orders.setF_Tel(distrOrder.getMobilePhone());
		// 省
		String province = distrOrder.getProvince();
		orders.setF_Province(province);
		// 市
		String city = distrOrder.getCity();
		orders.setF_City(city);
		// 区
		String region = distrOrder.getRegion();
		orders.setF_District(region);
		orders.setF_Address(distrOrder.getDetailedAddress());
		orders.setF_Remark(distrOrder.getRemarks());

		Double logisticsFee = new Double(0);
		// 物流费
		if (distrOrder.getLogisticsFee() != null && !"".equals(distrOrder.getLogisticsFee())) {
			logisticsFee = distrOrder.getLogisticsFee();
		}
		// 客户来源
		String cusFrom = distrOrder.getCustomerSource();
		if ("线上".equals(cusFrom)) {
			cusFrom = "1";
		} else if ("线下".equals(cusFrom)) {
			cusFrom = "2";
		} else if ("地推".equals(cusFrom)) {
			cusFrom = "3";
		} else if ("样品".equals(cusFrom)) {
			cusFrom = "4";
		} else if ("老客户".equals(cusFrom)) {
			cusFrom = "5";
		}
		orders.setF_CusFromType(cusFrom);
		// 提交人
		orders.setF_SubmitUserName(UserUtils.getUser().getUserName());
		// 创建时间
		orders.setF_CreatorTime(DateUtils.formatDate(distrOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		// 合计应收
		orders.setF_AllTotal(distrOrder.getTotalFee());

		// 商品金额
		orders.setF_GoodsTotal(distrOrder.getTotalPrice());
		// 物流费
		orders.setF_LogisticsFee(logisticsFee);
		List<DistrOrderDetail> distrOrderDetailList = distrOrder.getDistrOrderDetailList();
		List<Detail> detailList = ListUtils.newArrayList();
		for (DistrOrderDetail distrOrderDetail : distrOrderDetailList) {
			Detail detail = new Detail();
			detail.setF_MaterialNo(distrOrderDetail.getSku());
			detail.setF_Qty(distrOrderDetail.getQuantity());
			detail.setF_UnitPrice(distrOrderDetail.getPrice());
			detail.setF_Amount(distrOrderDetail.getAmount());
			detailList.add(detail);
		}
		String order = JSONObject.toJSONString(orders);
		String details = JSONObject.toJSONString(detailList);
		Map<String, String> maps = new HashMap<>();
		maps.put("order", order);
		maps.put("details", details);
		String info = HttpClientUtils.ajaxPost(K3NOREALURL, maps);

		JSONObject returnJson = JSONObject.parseObject(info);
		String message = returnJson.get("Message").toString();
		String flag = returnJson.get("IsError").toString();
		if ("false".equals(flag)) {
			// 提交时间
			distrOrder.setSubmitDate(new Date());
			// 提交人
			distrOrder.setSubmitBy(UserUtils.getUser().getUserName());
			// 提交人用户编码
			distrOrder.setSubmitCode(UserUtils.getUser().getUserCode());
			// 状态
			distrOrder.setDocumentStatus("提交");
			distrOrderService.saveData(distrOrder);
			return ReturnDate.success();
		} else {
			if (message.contains("ErrorCode")) {
				JSONObject jsonObject = JSONObject.parseObject(message);
				String msg = null;
				try{
					msg = jsonObject.getJSONObject("Errors").get("Message").toString();
				}catch (Exception e) {
					msg = null;
				}
				message = "不允许先收款后报价，请在K3报价单里删除该特权订金1：" + msg;
			}
			return ReturnDate.error(15001, message);
		}
	}

	/**
	 * 订单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("distribution:api")
	@ResponseBody
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public ReturnInfo queryOrder(HttpServletRequest request, HttpServletResponse response) {
		String userType = UserUtils.getUser().getUserType();
		DistrOrder distrOrder = new DistrOrder();
		if (User.USER_TYPE_EMPLOYEE.equals(userType)) {
			// 当前用户部门编码
			String officeCode = EmpUtils.getOffice().getOfficeCode();
			distrOrder.setOfficeCodes(officeCode);
		}
		// 查询当前用户以及下属订单
		Page<DistrOrder> page = distrOrderService.findPage(new Page<DistrOrder>(request, response), distrOrder);
		List<String> orderList = ListUtils.newArrayList();
		for (DistrOrder order : page.getList()) {
			orderList.add(order.getDocumentCode());
		}
		// 查询明细信息
		List<DistrOrderDetail> distrOrderDetailList = distrOrderService.selectById(orderList);
		for (DistrOrder order : page.getList()) {
			// 根据订单号获取明细信息放入主表实体中
			List<DistrOrderDetail> detailList = distrOrderDetailList.stream().filter(s ->s.getBillNo().equals(order.getDocumentCode())).collect(Collectors.toList());
			order.setDistrOrderDetailList(detailList);
		}
		return ReturnDate.success(page);
	}

	/**
	 * 订单详情
	 * @param distrOrder
	 * @return
	 */
	@RequiresPermissions("distribution:api")
	@ResponseBody
	@RequestMapping(value = "formOrder", method = RequestMethod.GET)
	public ReturnInfo formOrder(DistrOrder distrOrder) {
		return ReturnDate.success(distrOrder);
	}
}