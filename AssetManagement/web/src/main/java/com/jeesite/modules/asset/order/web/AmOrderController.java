/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.collect.SetUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.order.entity.AmOrderDetail;
import com.jeesite.modules.asset.order.entity.AmOrderDiscount;
import com.jeesite.modules.asset.order.entity.AmOrderImg;
import com.jeesite.modules.asset.order.entity.k3Info.*;
import com.jeesite.modules.asset.order.entity.print.ItemDetail;
import com.jeesite.modules.asset.order.entity.print.PrintData;
import com.jeesite.modules.asset.order.service.AmOrderImgService;
import com.jeesite.modules.asset.order.service.AmOrderLogService;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.tianmao.entity.InventoryStockQuery;
import com.jeesite.modules.asset.tianmao.entity.InventoryStockQueryModel;
import com.jeesite.modules.asset.tianmao.web.TbProductController;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.taobao.api.domain.Trade;
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
import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.asset.order.service.AmOrderService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单管理Controller
 * @author czy
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/order/amOrder")
public class AmOrderController extends BaseController {

	@Autowired
	private AmOrderService amOrderService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private K3connection k3connection;
	@Autowired
	private AmOrderLogService amOrderLogService;
	@Autowired
	private AmOrderImgService amOrderImgService;
	@Autowired
	private AmUtilService amUtilService;
	// 用于是否享受
	private static String EnjoyVal = null;
	// 新增单事默认值
	private static String DEFAULT = "1";

	@Value("${POST_K3ClOUDRL}")
	private String POST_K3ClOUDRL;
	/**
     *
	 * 订单前缀
	 */
	private final String PERFIX = "MD";
	/**
	 * 订单状态
	 */
	private final String CREATE = "创建";
	private final String SUBMIT = "提交";
	private final String CONFIRM = "确认";
	/**
	 * 判断是否首次登录
	 */
	public boolean LOGIN = true;   // 是否是第一次登录
	/**
	 * token定义全局变量
	 */
	public String TOKEN = null;    // 定义token
	/**
	 * 登录地址
	 */
	@Value("${LOGINURL}")
	private String LOGINURL ;
	@Value("${ORDERURL}")
	private String ORDERURL ;
	@Value("${K3URL}")
	private String K3URL;
	@Value("${K3NOREALURL}")
	private String K3NOREALURL;
	@Value("${TOKENURL}")
	private String TOKENURL;

	private final String STATUS = "WAIT_BUYER_PAY";
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmOrder get(String documentCode, boolean isNewRecord) {
		return amOrderService.get(documentCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:amOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmOrder amOrder, Model model) {
		model.addAttribute("amOrder", amOrder);
		return "asset/order/amOrderList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:amOrder:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmOrder> listData(AmOrder amOrder, HttpServletRequest request, HttpServletResponse response) {
		Page<AmOrder> page = amOrderService.findPage(new Page<AmOrder>(request, response), amOrder);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:amOrder:view")
	@RequestMapping(value = "form")
	public String form(AmOrder amOrder, Model model) {
		if (amOrder.getIsNewRecord()) {
			if ("1".equals(EmpUtils.getEmployee().getTreasure())) {
				amOrder.setDocumentType("零售订单");
			} else if ("0".equals(EmpUtils.getEmployee().getTreasure())) {
				amOrder.setDocumentType("新零售合作订单");
			}
			amOrder.setDocumentStatus(CREATE);
			amOrder.setDocumentCode(amSeqService.getOrderCode(PERFIX));		// 订单编号
//			amOrder.setDocumentCode(amSeqService.getSeqCode(PERFIX));
			amOrder.setQuoteTime(new Date());
			amOrder.setDetailedAddress(DEFAULT);
			amOrder.setDistribution("送货上门并安装");
			amOrder.setCustomerName(DEFAULT);
			amOrder.setMobilePhone(DEFAULT);
		}
		if (!CREATE.equals(amOrder.getDocumentStatus())) {	// 非创建状态全部不可编辑
			if (amOrder.getIsEnjoy() != null) {
				EnjoyVal = amUtilService.findDictLabel(amOrder.getIsEnjoy(),"written");
				amOrder.setEnjoyVal(EnjoyVal);
			} else {
				amOrder.setEnjoyVal("否");
			}
			amOrder.setConfirm(true);
			// 导购宝付款
			String treasure = EmpUtils.getEmployee().getTreasure();
			amOrder.setTreasure(treasure);
		} else {
			amOrder.setConfirm(false);
		}
		for (AmOrderDiscount amOrderDiscount : amOrder.getAmOrderDiscountList()) {
			if ("其它优惠".equals(amOrderDiscount.getDiscount())) {
				amOrder.setOtherDiscount(amOrderDiscount.getDetailCode());
			}
		}
		model.addAttribute("amOrder", amOrder);
		return "asset/order/amOrderForm";
	}

	/**
	 * 保存订单管理
	 */
	@RequiresPermissions("order:amOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(AmOrder amOrder, String flag, HttpServletRequest request) {
		try {

			String province = getArea(amOrder.getProvince());
			String city = getArea(amOrder.getCity());            // 省市区获取
			String region = getArea(amOrder.getRegion());
			amOrder.setProvince(province);
			amOrder.setCity(city);
			amOrder.setRegion(region);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());                    // 当前时间
			if ("0".equals(flag)) {
				if (amOrder.getAmOrderDetailList() == null || amOrder.getAmOrderDetailList().size() <= 0) {
					return renderResult(Global.FALSE, "明细为空");
				}
			}
			if (amOrder.getAmOrderDiscountList() != null && amOrder.getAmOrderDiscountList().size() > 0) {
				List<AmOrderDiscount> discountList = amOrder.getAmOrderDiscountList().stream().filter(s -> s.getDiscount().equals("其它优惠")).collect(Collectors.toList());
				if (discountList.size() > 1) {
					return renderResult(Global.FALSE, "已存在其它优惠，不能重复新增!");
				}
			}
			if (amOrder.getIsNewRecord()) {
				amOrder.setDocumentStatus(CREATE);                                // 订单状态
//			amOrder.setDocumentCode(amSeqService.getOrderCode(PERFIX));		// 订单编号
				amOrder.setCreateBy(UserUtils.getUser().getUserName());
//            amOrder.setCreateBy(EmpUtils.getOffice().getOfficeName());  	// 创建人设置为当前用户所属部门
				amOrder.setOfficeCode(EmpUtils.getOffice().getOfficeCode());
				String[] datetime = time.split("-");
				int a = Integer.parseInt(datetime[1]);
				if (a < 10) {
					datetime[1] = datetime[1].replace("0", "");
				}
				time = datetime[0] + "-" + datetime[1] + "-" + datetime[2];
				amOrder.setCreateTime(time);                // 创建时间
			} else {
				if (amOrder.getOfficeCode() == null || "".equals(amOrder.getOfficeCode())) {
					List<User> userList = amOrderService.getUser(amOrder.getCreateBy());
					if (userList != null && userList.size() > 0) {
						for (User user : userList) {
							String userCode = user.getUserCode();
							if ("employee".equals(user.getUserType())) {
								String officeCode = amOrderService.getEmp(userCode);
								amOrder.setOfficeCode(officeCode);
							}
						}
					}
				}
			}
			if ("0".equals(flag)) {
				if ("".equals(amOrder.getCustomerSource()) || amOrder.getCustomerSource() == null) {
					return renderResult(Global.FALSE, "请选择客户来源!");
				}
				if ("".equals(province) || province == null) {
					return renderResult(Global.FALSE, "请选择省");
				}
				if ("".equals(city) || city == null) {
					return renderResult(Global.FALSE, "请选择市");
				}
				if ("".equals(amOrder.getDistribution()) || amOrder.getDistribution() == null) {
					return renderResult(Global.FALSE, "请选择配送方式");
				}
				if ("".equals(amOrder.getQuoteTime()) || amOrder.getQuoteTime() == null) {
					return renderResult(Global.FALSE, "请输入取价时间");
				}
				if ("其它区".equals(region)) {
					return renderResult(Global.FALSE, "不能选择其它区");
				}
				// 导购宝付款
				String treasure = EmpUtils.getEmployee().getTreasure();
				Set<String> set = SetUtils.newHashSet();
				// 如果导购宝付款= 是
				if ("1".equals(treasure)) {
					// 特权定金1
					String privilege1 = amOrder.getPrivilege1();
					// 特权定金2
					String privilege2 = amOrder.getPrivilege2();
					// 特权定金3
					String privilege3 = amOrder.getPrivilege3();

					if (StringUtils.isNotEmpty(privilege1)) {
						set.add(privilege1);
					}
					if (StringUtils.isNotEmpty(privilege2)) {
						set.add(privilege2);
					}
					if (StringUtils.isNotEmpty(privilege3)) {
						set.add(privilege3);
					}
					if (set == null || set.size() <= 0) {
						return renderResult(Global.FALSE, "请输入特权定金单号");
					}
					// 根据特权定金查询订单
					List<AmOrder> amOrderList = amOrderService.selectByPrivilege(set);
					if (ListUtils.isNotEmpty(amOrderList)) {
						Double totalFee = new Double(0);
						for (AmOrder amOrder1 : amOrderList) {
							if (amOrder1.getTotalFee() != null) {
								totalFee = NumberUtils.add(totalFee, amOrder1.getTotalFee());
							}
						}
						if (new BigDecimal(0).compareTo(new BigDecimal(totalFee)) == 0) {
							amOrder.setPrivilegesTotal("");
						} else {
							amOrder.setPrivilegesTotal(totalFee.toString());
						}
					}
				}
				amOrder.setDocumentStatus(CONFIRM);
				amOrder.setConfirmBy(UserUtils.getUser().getUserName());    // 确认人当前登录用户
				amOrder.setConfirmDate(time);                                // 确认时间当前时间
				boolean isLogin = k3connection.getConnection();
				amOrderService.calculateShippingCosts(amOrder, province, city, region, isLogin, "1");
//			if (!"".equals(message)) {
//				return renderResult(Global.FALSE, "运费重新计算失败！");
//			}
			} else if ("1".equals(flag)) {
				amOrder.setDocumentStatus(CREATE);
				amOrder.setConfirmBy("");                                    // 取消确认时清空确认人和确认时间
				amOrder.setConfirmDate("");
				amOrder.setPrivilegesTotal("");
			}
			if ("否".equals(amOrder.getIsEnjoy())) {
				amOrder.setIsEnjoy("0");
			} else if ("是".equals(amOrder.getIsEnjoy())) {
				amOrder.setIsEnjoy("1");
			}
			amOrderService.save(amOrder);
			return renderResult(Global.TRUE, "保存订单管理成功！");
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return renderResult(Global.FALSE, "服务异常");
		}
	}

	/**
	 * 将数据保留两位小数
	 */
	private String getTwoDecimal(double num) {
		DecimalFormat dFormat = new DecimalFormat("#.00");
		String atm = dFormat.format(num);
		if (".00".equals(atm)) {
			atm = "0.00";
		}
		return atm;
	}


	/**
	 * 获取省市县
	 * @param area
	 * @return
	 */
	public String getArea(String area) {
		if (area.endsWith(",")) {
			return "";
		}
		String[] pro =  area.split(",");
		if (pro.length == 2) {
			return pro[1];
		} else if (pro.length == 1){
			return pro[0];
		} else {
			return "";
		}
	}
	/**
	 * 删除订单管理
	 */
	@RequiresPermissions("order:amOrder:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmOrder amOrder) {
		if (!"创建".equals(amOrder.getDocumentStatus()) || "平台原始订单".equals(amOrder.getDocumentType())) {
			return renderResult(Global.TRUE, "非创建状态订单或者平台原始订单不能被删除！");
		}
		amOrderService.deleteOrder(amOrder.getDocumentCode());
		return renderResult(Global.TRUE, "订单删除成功！");
	}

	/**
	 *
	 * @return
	 */
	@RequiresPermissions("order:amOrder:view")
	@RequestMapping(value = "print")
	public String print (String documentCode, Model model) {
		AmOrder amOrder = new AmOrder();
		amOrder.setDocumentCode(documentCode);
		amOrder = amOrderService.get(amOrder);

		if (CREATE.equals(amOrder.getDocumentStatus())) {
			return renderResult(Global.FALSE, "订单是创建状态，不能打印!");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dateFormat.format(new Date());
		amOrder.setPrintingBy(UserUtils.getUser().getUserName());
		amOrder.setPrintingDate(time);
		amOrderService.saveData(amOrder);                        // 更新打印人，打印时间，截取省市区

		PrintData printData = new PrintData();
		printData.setCustomerName(amOrder.getCustomerName());    // 客户ID
		String createTime = amOrder.getCreateTime();
		if (createTime != null && !"".equals(createTime)) {
			String[] createDate = createTime.split(" ");
			printData.setCreateDate(createDate[0]);    // 下单日期
		} else {
			printData.setCreateDate(null);
		}
		printData.setCustomerSource(amOrder.getCustomerSource());            // 客户来源
		Double totalPrice = amOrder.getTotalPrice();
		printData.setTotalPrice(getTwoDecimal(totalPrice));                    // 商品总额
		Double preferential = amOrder.getPreferential();
		if (preferential == null) {
			preferential = new Double(0);
		}
		printData.setPreferential(getTwoDecimal(preferential));                // 优惠金额
		Double logisticsFee = amOrder.getLogisticsFee();
		Double threeCharges = amOrder.getThreeCharges();
		if (logisticsFee == null) {
			logisticsFee = new Double(0);
		}
		if (threeCharges == null) {
			threeCharges = new Double(0);
		}
		printData.setInstallFee(getTwoDecimal(NumberUtils.add(logisticsFee, threeCharges)));    // 物流费+三包费
		printData.setRemarks(amOrder.getRemarks());                            // 备注
		printData.setCollectBy(amOrder.getCustomerName());                    // 收货人
		printData.setMobile(amOrder.getMobilePhone());                        // 联系电话
		String province = amOrder.getProvince();                                // 省
		String city = amOrder.getCity();                                        // 市
		String region = amOrder.getRegion();                                    // 区
		String address = amOrder.getDetailedAddress();                            // 详细地址
		String detailedAddress = province + city + region + address;
		printData.setReceiveAddress(detailedAddress);                            // 收货地址
        printData.setTotalFee(getTwoDecimal(amOrder.getTotalFee()));            // 订单合计
		List<ItemDetail> detailList = new ArrayList<>();
		int size = 0;
		for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()) {
			ItemDetail itemDetail = new ItemDetail();
			itemDetail.setSku(amOrderDetail.getSku());                        // 物料编码
			itemDetail.setGoodsName(amOrderDetail.getGoodsName());            // 商品名称
			itemDetail.setSpec(amOrderDetail.getSpec());                    // 规格
			Double price = amOrderDetail.getPrice();
			itemDetail.setPrice(getTwoDecimal(price));                    // 单价
			itemDetail.setCount(amOrderDetail.getQuantity());                // 数量
			itemDetail.setAmount(getTwoDecimal(amOrderDetail.getAmount()));
			Double atm = NumberUtils.sub(1, NumberUtils.div(preferential, totalPrice));
			itemDetail.setDiscountAmt(getTwoDecimal(NumberUtils.mul(price, atm)));            // 折扣后单价
			size++;
			detailList.add(itemDetail);
		}
		printData.setSize(size);
		printData.setItem(detailList);
		model.addAttribute("orderPrint", printData);
		return "asset/order/amOrderPrint";
	}

	/**
	 *
	 * @param uploadId
	 */
	@RequiresPermissions("order:amOrder:down")
	@RequestMapping(value = "uploadFile")
	@ResponseBody
	public String uploadFile(String uploadId, HttpServletRequest request) {
		try {

			if (uploadId == null || "".equals(uploadId)) {
				return renderResult(Global.FALSE, "请输入客户呢称或淘宝单号!");
			}
			if (TOKEN != null) {
				String isTimOut = HttpClientUtils.ajaxGet(TOKENURL + TOKEN);
				if ("false".equals(isTimOut)) {
					LOGIN = true;
				}
			}
			if (LOGIN) {
				String info = HttpClientUtils.ajaxGet(LOGINURL);
				JSONObject json = JSONObject.parseObject(info);
				try {
					TOKEN = json.get("message").toString();
				} catch (NullPointerException e) {
					return renderResult(Global.FALSE, "获取token失败!");
				}
				LOGIN = false;
			}
			String orderUrl = ORDERURL + uploadId + "&sessionKey=" + TOKEN + "";
			System.out.println(orderUrl);
			String returnInfo = null;
			try {
				returnInfo = HttpClientUtils.ajaxGet(orderUrl);                            // 根据输入值获取信息
			} catch (Exception e) {
				return renderResult(Global.FALSE, "请检查客户昵称或单号");
			}
			if ("null".equals(returnInfo)) {
				return renderResult(Global.FALSE, "获取不到订单信息!");
			} else {
				JSONObject returnJson = JSONObject.parseObject(returnInfo);
				String isError = returnJson.get("IsError").toString();
				if ("false".equals(isError)) {
					String value = returnJson.get("Value").toString();                    // 如果没有错误取订单信息
					JSONArray jsonArray = JSONArray.parseArray(value);
					List<Trade> tradeList = new ArrayList<>();
					boolean flag = true;
					boolean isUpload = false;
					for (int i = 0; i < jsonArray.size(); i++) {
						JSON json1 = JSONObject.parseObject(jsonArray.get(i).toString());
						Trade trade = JSONObject.toJavaObject(json1, Trade.class);
						if (STATUS.equals(trade.getStatus())) {            // 判断订单状态是否是等待买家付款
							flag = false;
							AmOrder amOrder = new AmOrder();
							amOrder.setDocumentCode(trade.getTid().toString());
							amOrder = amOrderService.get(amOrder);                    // 如果是等待买家付款状态，查询是否已存在相同订单
							if (amOrder != null) {
								isUpload = true;
								continue;
							}
							tradeList.add(trade);                                    // 是等待买家付款状态并且未下载的订单
						}
					}
					if (flag) {
						return renderResult(Global.FALSE, "当前客户没有今天的『等待买家付款』订单!");
					}
					Map<String, String> mapInfo = new HashMap<>();
					if (tradeList.size() > 1) {
						Map<Long, Trade> maps = new HashMap<>();
						Long[] millisecond = new Long[tradeList.size()];
						for (int i = 0; i < tradeList.size(); i++) {
							Date date = tradeList.get(i).getCreated();
							long time = date.getTime();
							millisecond[i] = time;
							maps.put(time, tradeList.get(i));
						}
						Arrays.sort(millisecond);
						Trade trade = maps.get(millisecond[millisecond.length - 1]);
						mapInfo = amOrderService.getOrderList(tradeList, trade);
					} else if (tradeList.size() == 1) {
						mapInfo = amOrderService.savaUplode(tradeList.get(0), null, null, null);
					}
					if (isUpload) {
						return renderResult(Global.FALSE, "存在有相同的订单,不能重复下载!");
					}
					if ("false".equals(mapInfo.get("flag"))) {
						return renderResult(Global.FALSE, mapInfo.get("msg"));
					} else {
						return renderResult(Global.TRUE, mapInfo.get("msg"));
					}
				}
				return renderResult(Global.FALSE, "订单下载失败!");
			}
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return renderResult(Global.FALSE, "服务异常");
		}
	}

	/**
	 * 提交K3
	 * @param amOrder
	 * @return
	 */
	@RequiresPermissions("order:amOrder:deliver")
	@RequestMapping(value = "submitToK3")
	@ResponseBody
	public String submitToK3( AmOrder amOrder) throws Exception{
		if (amOrder.getMobilePhone().length() != 11) {
			return renderResult(Global.FALSE, "请输入正确的移动电话");
		}
		Date quoteTime = amOrder.getQuoteTime();
		// 取价时间
		if ("".equals(quoteTime) || quoteTime == null) {
			return renderResult(Global.FALSE, "请输入取价时间");
		}
		if (!"新零售合作订单".equals(amOrder.getDocumentType())) {
			if ("".equals(amOrder.getPrivilege1()) || amOrder.getPrivilege1() == null) {
				return renderResult(Global.FALSE, "请输入特权订单号");
			}
		}
		Order orders = new Order();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 取价时间
		orders.setF_PriceofTime(dateFormat.format(quoteTime));
		orders.setF_BillNo(amOrder.getDocumentCode());
		orders.setF_ReceiverName(amOrder.getCustomerName());
		orders.setF_Tel(amOrder.getMobilePhone());
		// 省
		String province = getArea(amOrder.getProvince());
		orders.setF_Province(province);
		// 市
		String city = getArea(amOrder.getCity());
		orders.setF_City(city);
		// 区
		String region = getArea(amOrder.getRegion());
		orders.setF_District(region);
		orders.setF_Address(amOrder.getDetailedAddress());
		orders.setF_Remark(amOrder.getRemarks());
		// 优惠金额
		orders.setF_FavorableTotal(amOrder.getPreferential());
		Double logisticsFee = new Double(0);
		// 物流费
		if (amOrder.getLogisticsFee() != null && !"".equals(amOrder.getLogisticsFee())) {
			logisticsFee = amOrder.getLogisticsFee();
		}
		// 三包费
		Double threeCharges = new Double(0);
		if (amOrder.getThreeCharges() != null && !"".equals(amOrder.getThreeCharges())) {
			threeCharges = amOrder.getThreeCharges();
		}
		// 邮费
		Double postageAmount = NumberUtils.add(logisticsFee, threeCharges);
		orders.setF_YF_PostageAmount(postageAmount);
		if ("新零售合作订单".equals(amOrder.getDocumentType())){
			// 提交人
			orders.setF_SubmitUserName(UserUtils.getUser().getUserName());
			// 创建时间
			orders.setF_CreatorTime(amOrder.getCreateTime());
			// 合计应收
			orders.setF_AllTotal(amOrder.getTotalFee());
//			// 优惠金额
//			orders.setF_FavorableTotal(amOrder.getPreferential());
			// 商品金额
			orders.setF_GoodsTotal(amOrder.getTotalPrice());
			// 物流费
			orders.setF_LogisticsFee(logisticsFee);
			// 三包费
			orders.setF_SbFee(threeCharges);
		}
		// 客户来源
		String cusFrom = amOrder.getCustomerSource();
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
		orders.setF_RealNo1(amOrder.getPrivilege1());
		orders.setF_RealNo2(amOrder.getPrivilege2());
		orders.setF_RealNo3(amOrder.getPrivilege3());
		List<AmOrderDetail> detailList = amOrder.getAmOrderDetailList();
		List<Detail> detailList1 = new ArrayList<>();
		for (AmOrderDetail amOrderDetail : detailList) {
			Detail detail = new Detail();
			detail.setF_MaterialNo(amOrderDetail.getSku());
			detail.setF_Qty(amOrderDetail.getQuantity());
			detail.setF_UnitPrice(amOrderDetail.getPrice());
			detail.setF_StandPrice(amOrderDetail.getStandPrice());
			detail.setF_Amount(amOrderDetail.getAmount());
			// 店铺
			String shop = null;
			if ("".equals(amOrderDetail.getShop()) || amOrderDetail.getShop() == null) {
				shop = "优梵艺术旗舰店";
				amOrderDetail.setShop("0");
			} else {
				if ("0".equals(amOrderDetail.getShop())) {
					shop = "优梵艺术旗舰店";
				} else if ("1".equals(amOrderDetail.getShop())) {
					shop = "优梵艺术家具旗舰店";
				}
			}
			detail.setF_GoodsStore(shop);
			detailList1.add(detail);
		}
		String order = JSONObject.toJSONString(orders);
		String details = JSONObject.toJSONString(detailList1);
		Map<String, String> map = new HashMap<>();
		map.put("order", order);
		map.put("details", details);
		String info = null;
		if ("新零售合作订单".equals(amOrder.getDocumentType())) {
			info = HttpClientUtils.ajaxPost(K3NOREALURL, map);
		} else {
			info = HttpClientUtils.ajaxPost(K3URL, map);
		}

		JSONObject returnJson = JSONObject.parseObject(info);
		String message = returnJson.get("Message").toString();
		String flag = returnJson.get("IsError").toString();
		if ("false".equals(flag)) {
			amOrder.setSubmitDate(DateUtils.getDateTime());
			amOrder.setSubmitBy(UserUtils.getUser().getUserName());
			amOrder.setDocumentStatus(SUBMIT);
			amOrder.setProvince(province);
			amOrder.setCity(city);
			amOrder.setRegion(region);
			amOrderService.updateWriteoff(amOrder);
			amOrderService.saveData(amOrder);
			return renderResult(Global.TRUE, "提交优梵成功");
		} else {
			if (message.contains("ErrorCode")) {
				JSONObject jsonObject = JSONObject.parseObject(message);
				String msg = null;
				try{
					msg = jsonObject.getJSONObject("Errors").get("Message").toString();
				}catch (Exception e) {
					msg = null;
				}
				return renderResult(Global.FALSE, "不允许先收款后报价，请在K3报价单里删除该特权订金1：" + msg);
			}
			return renderResult(Global.FALSE, message);
		}
	}

	/**
	 *  动态报价
	 * @time 2018/9/12
	 * @param amOrder
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("order:amOrder:quotation")
	@RequestMapping("quotation")
	public String quotation (@Validated AmOrder amOrder, HttpServletRequest request) {
		String message = "";
		try{
			message = amOrderService.quotation(amOrder);
		}catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return renderResult(Global.FALSE, "服务异常");
		}

		if (!"".equals(message)) {
			return renderResult(Global.FALSE, message);
		} else {
			return renderResult(Global.TRUE, "操作成功");
		}
	}

	/**
	 * 查货期
	 * @param amOrder
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("order:amOrder:inventoryStock")
	@RequestMapping("inventoryStock")
	public String inventoryStock (AmOrder amOrder, HttpServletRequest request) {
		try {
			// 判断订单明细行是否为空
			if (ListUtils.isEmpty(amOrder.getAmOrderDetailList())) {
				return renderResult(Global.FALSE, "明细行不能为空");
			} else {
				boolean flag = k3connection.getConnection();
				if (!flag) {
					return renderResult(Global.FALSE, "服务异常，请稍后再试");
				}
				// 用于仓库库存查询
				List<String> list = ListUtils.newArrayList();
				// 这个list用于在Bom列表中是没有子项物料编码的
				List<String> materialList = ListUtils.newArrayList();
				// 这个map用于有子项物料编码的
				Map<String, String> map = new HashMap<>();
				for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()) {
					// 请求组装bom列表接口
					StringBuffer stringBuffer = InvokeHelper.concession("materialGetBomSonMaterial", amOrderDetail.getSku(), K3Config.K3ClOUDRL);
					JSONObject bomJson = JSONObject.parseObject(stringBuffer.toString());
					String isErr = bomJson.get("IsError").toString();
					if ("true".equals(isErr)) {
						list.add(amOrderDetail.getSku());
						materialList.add(amOrderDetail.getSku());
					} else {
						// 如果返回false说明在组合bom表可以查询到
						String value = bomJson.get("Value").toString();
						JSONArray jsonArray = JSONArray.parseArray(value);
						String fHaveSon = jsonArray.getJSONObject(0).get("FHaveSon").toString();
						// N代表没子项物料
						if ("N".equals(fHaveSon)) {
							list.add(amOrderDetail.getSku());
							materialList.add(amOrderDetail.getSku());
						} else {
							// Y代表有子项
							for (int i = 0; i < jsonArray.size(); i++) {
								String materialNo = jsonArray.getJSONObject(i).get("FMaterialNo").toString();
								list.add(materialNo);
								if (map.containsKey(amOrderDetail.getSku())) {
									// 有子项的用逗号隔开
									map.put(amOrderDetail.getSku(), map.get(amOrderDetail.getSku()) + "," + materialNo);
								}
								map.put(amOrderDetail.getSku(), materialNo);
							}
						}
					}
				}
				InventoryStockQuery inventory = new InventoryStockQuery();
				inventory.set_F_YF_Materials(list);
				List<String> fieldList = ListUtils.newArrayList();
				// K3查询用到的字段 用到几个set几个
				fieldList.add("F_YF_MaterialNumber ");
				fieldList.add("F_YF_ExpectedDate");
				fieldList.add("F_YF_PurchaseCycle");
				inventory.setLstField(fieldList);
				String inventoryStockQuery = JSONObject.toJSON(inventory).toString();
				StringBuffer buffer = InvokeHelper.concession("getInventoryStockQueryReport2", inventoryStockQuery, K3Config.K3ClOUDRL);
				// K3返回的数据
				JSONObject jsonObj = JSONObject.parseObject(buffer.toString());
				String isError = jsonObj.get("IsError").toString();
				// 若查不到停止 用下一个物料查询
				if ("true".equals(isError)) {
					return jsonObj.get("Message").toString();
				}
				JSONArray jsonArray = JSONArray.parseArray(jsonObj.get("Value").toString());
				if (jsonArray == null || jsonArray.size() <= 0) {
					saveDate(amOrder);
					return renderResult(Global.FALSE, "查询失败，请稍后再试");
				}
				JSONArray jsonArr = JSONArray.parseArray(jsonObj.get("Value").toString());
				// 把查询的数据转化为实体list
				List<InventoryStockQueryModel> inventoryList = JSONArray.parseArray(jsonArr.toString(), InventoryStockQueryModel.class);
				List<AmOrderDetail> detailList = amOrder.getAmOrderDetailList();
				// 如果没有子项的list不为空时 直接查询K3返回的list获取数据
				if (ListUtils.isNotEmpty(materialList)) {
					for (String materialNo : materialList) {
						InventoryStockQueryModel inventoryStockQueryModel = null;
						try {
							inventoryStockQueryModel = inventoryList.stream().filter(s ->s.getF_YF_MaterialNumber().equals(materialNo)).findFirst().get();
						} catch (NoSuchElementException e) {

						}
						if (inventoryStockQueryModel == null) {
							continue;
						}
						List<AmOrderDetail> amOrderDetailList = detailList.stream().filter(s ->s.getSku().equals(materialNo)).collect(Collectors.toList());
						for (AmOrderDetail amOrderDetail : amOrderDetailList) {
							// 预计交期
							amOrderDetail.setExpectedDelivery(inventoryStockQueryModel.getF_YF_ExpectedDate());
							// 采购周期
							amOrderDetail.setPurchaseCycle(inventoryStockQueryModel.getF_YF_PurchaseCycle());
						}
					}
				}
				// 如果订单中有商品有子项物料编码 map不为空
				if (MapUtils.isNotEmpty(map)) {
					Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
					while (it.hasNext()) {
						List<InventoryStockQueryModel> inventoryStockQueryModelList = ListUtils.newArrayList();
						Map.Entry<String, String> entry = it.next();
						String sku = entry.getKey();
						String sonSku = entry.getValue();
						String [] sonSkus = sonSku.split(",");
						for (String str : sonSkus) {
							try {
								List<InventoryStockQueryModel> modelList = inventoryList.stream().filter(s ->s.getF_YF_MaterialNumber().equals(str)).collect(Collectors.toList());
								// 根据其中的子项物料编码获取到数据放在一个list中
								inventoryStockQueryModelList.addAll(modelList);
							} catch (NoSuchElementException e) {

							}

						}
						if (ListUtils.isNotEmpty(inventoryStockQueryModelList)) {
							// 把同一个父物料编码的子物料的所有预计交期放一起 后边计算最大的
							List<String> expectedDateList = ListUtils.newArrayList();
							// 把同一个父物料编码的子物料的所有采购周期放一起 后边计算最大的
							List<String> purchaseCycleList = ListUtils.newArrayList();
							for (InventoryStockQueryModel inventoryStockQueryModel : inventoryStockQueryModelList) {
								// 预计交期
								expectedDateList.add(inventoryStockQueryModel.getF_YF_ExpectedDate());
								// 采购周期
								purchaseCycleList.add(inventoryStockQueryModel.getF_YF_PurchaseCycle());
							}
							// 根据sku从明细数据中获取对象

							List<AmOrderDetail> amOrderDetailList = detailList.stream().filter(s ->s.getSku().equals(sku)).collect(Collectors.toList());
							for (AmOrderDetail amOrderDetail : amOrderDetailList) {
								calculated(amOrderDetail, expectedDateList, purchaseCycleList);
							}
						}
					}
				}
				saveDate(amOrder);
				return renderResult(Global.TRUE, "查询货期成功，请参考");
			}
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return renderResult(Global.FALSE, "服务异常");
		}
	}

	/**
	 * 更新查货人查货时间
	 */
	public void saveDate(AmOrder amOrder) {
		// 查货人
		amOrder.setCheckBy(UserUtils.getUser().getUserName());
		// 查货时间
		amOrder.setCheckDate(new Date());
		amOrder.setProvince(getArea(amOrder.getProvince()));
		amOrder.setCity(getArea(amOrder.getCity()));
		amOrder.setRegion(getArea(amOrder.getRegion()));
		amOrderService.save(amOrder);
	}
	/**
	 * 计算预计交期和采购周期
	 * @param amOrderDetail
	 * @param expectedDateList
	 * @param purchaseCycleList
	 */
	public void calculated(AmOrderDetail amOrderDetail, List<String> expectedDateList, List<String> purchaseCycleList) {
		String expectedDate = "";
		if (ListUtils.isNotEmpty(expectedDateList)) {
			expectedDateList = TbProductController.removeList(expectedDateList);
			if (ListUtils.isNotEmpty(expectedDateList)) {
				Collections.sort(expectedDateList);
				expectedDate = expectedDateList.get(expectedDateList.size() - 1);
				if (StringUtils.isNotEmpty(expectedDate.trim())) {
					if (!expectedDate.contains("预计交期")) {
						Date expected = DateUtils.parseDate(expectedDate);
						if (expected != null) {
							if (DateUtils.parseDate(expectedDate).compareTo(new Date()) <= 0) {
								expectedDate = "现货";
							}
						}
					}
				}
			} else {
				expectedDate = "现货";
			}
			amOrderDetail.setExpectedDelivery(expectedDate);
		}
		String purchaseCycle = "";
		if (ListUtils.isNotEmpty(purchaseCycleList)) {
			Collections.sort(purchaseCycleList);
			purchaseCycle = purchaseCycleList.get(purchaseCycleList.size() - 1);
			amOrderDetail.setPurchaseCycle(purchaseCycle);
		}
	}

	/**
	 * 根据图片Id删除图片
	 * @param imgCode
	 * @return
	 */
	@RequiresPermissions("order:amOrder:edit")
	@RequestMapping("deleteImg")
	@ResponseBody
	public String deleteImg(String imgCode, String documentCode) {
		// 更新图片信息为删除
		amOrderImgService.updateImgStatus(imgCode);
		AmOrderImg amOrderImg = new AmOrderImg();
		amOrderImg.setDocumentCode(documentCode);
		amOrderImg.setImgStatus("0");
		// 根据订单号获取
		List<AmOrderImg> imgList = amOrderImgService.findList(amOrderImg);
		return renderResult(Global.TRUE, "图片删除成功", imgList);
	}
}