/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.modules.asset.guideApp.service.GuideService;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.order.dao.*;
import com.jeesite.modules.asset.order.entity.AmOrderImg;
import com.jeesite.modules.asset.order.entity.k3Info.Discount;
import com.jeesite.modules.asset.order.entity.k3Info.K3DiscountData;
import com.jeesite.modules.asset.order.entity.k3Info.VirtualQuotation;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.asset.order.entity.AmOrderDiscount;
import com.jeesite.modules.asset.order.entity.AmOrderDetail;

/**
 * 订单管理Service
 * @author czy
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly=true)
public class AmOrderService extends CrudService<AmOrderDao, AmOrder> {

	@Autowired
	private AmOrderDiscountDao amOrderDiscountDao;

	@Autowired
	private AmOrderDetailDao amOrderDetailDao;
	@Autowired
	private AmOrderDao amOrderDao;

	@Autowired
	private K3connection k3connection;
	@Autowired
	private GuideService guideService;
	@Autowired
	private AmOrderShoppingDao amOrderShoppingDao;
	@Autowired
	private AmOrderImgDao amOrderImgDao;

	@Value("${POST_K3ClOUDRL}")
	private String POST_K3ClOUDRL;

	/**
	 * 总部部门编码
	 */
	private final String ZBCODE = "A1000000";
	/**
	 * 部门最根级父节点
	 */
	private final String ZERO = "0";
	@Autowired
	private TbSkuService tbSkuService;

	/**
	 * 获取单条数据
	 * @param amOrder
	 * @return
	 */
	@Override
	public AmOrder get(AmOrder amOrder) {
		AmOrder entity = super.get(amOrder);
		if (entity != null){
			AmOrderDiscount amOrderDiscount = new AmOrderDiscount(entity);
			amOrderDiscount.setStatus(AmOrderDiscount.STATUS_NORMAL);
			entity.setAmOrderDiscountList(amOrderDiscountDao.findList(amOrderDiscount));
			AmOrderDetail amOrderDetail = new AmOrderDetail(entity);
			amOrderDetail.setStatus(AmOrderDetail.STATUS_NORMAL);
			entity.setAmOrderDetailList(amOrderDetailDao.findList(amOrderDetail));
			AmOrderImg amOrderImg = new AmOrderImg();
			amOrderImg.setDocumentCode(entity.getDocumentCode());
			amOrderImg.setImgStatus(AmOrderImg.STATUS_NORMAL);
			entity.setImageList(amOrderImgDao.findList(amOrderImg));
		}
		return entity;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amOrder
	 * @return
	 */
	@Override
	public Page<AmOrder> findPage(Page<AmOrder> page, AmOrder amOrder) {
		String parentCode = EmpUtils.getOffice().getParentCode();
		if (!ZERO.equals(parentCode) && "employee".equals(UserUtils.getUser().getUserType())) {
//			if (!ZBCODE.equals(parentCode) && !ZERO.equals(parentCode)) {
			amOrder.setOfficeCodes(EmpUtils.getOffice().getOfficeCode());
			amOrder.setUserName(UserUtils.getUser().getUserName());
//			if ("employee".equals(UserUtils.getUser().getUserType())) {
//				amOrder.setUserName(UserUtils.getUser().getUserName());
//			}
		}
		return super.findPage(page, amOrder);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param amOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmOrder amOrder) {
		super.save(amOrder);
		// 保存 AmOrder子表
		for (AmOrderDiscount amOrderDiscount : amOrder.getAmOrderDiscountList()){
			if (!AmOrderDiscount.STATUS_DELETE.equals(amOrderDiscount.getStatus())){
				amOrderDiscount.setDocumentCode(amOrder);
				if (amOrderDiscount.getIsNewRecord()){
					amOrderDiscount.preInsert();
					amOrderDiscountDao.insert(amOrderDiscount);
				}else{
					amOrderDiscount.preUpdate();
					amOrderDiscountDao.update(amOrderDiscount);
				}
			}else{
				amOrderDiscountDao.delete(amOrderDiscount);
			}
		}
		// 保存 AmOrder子表
		for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()){
			if (!AmOrderDetail.STATUS_DELETE.equals(amOrderDetail.getStatus()) && amOrderDetail.getStatus() != null){
				amOrderDetail.setDocumentCode(amOrder);
				if (amOrderDetail.getIsNewRecord()){
					amOrderDetail.preInsert();
					amOrderDetailDao.insert(amOrderDetail);
				}else{
					amOrderDetail.preUpdate();
					amOrderDetailDao.update(amOrderDetail);
				}
			}else{
				amOrderDetailDao.delete(amOrderDetail);
			}
		}
	}

	/**
	 * 更新状态
	 * @param amOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmOrder amOrder) {
		super.updateStatus(amOrder);
	}

	/**
	 * 删除数据
	 * @param amOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmOrder amOrder) {
		super.delete(amOrder);
		AmOrderDiscount amOrderDiscount = new AmOrderDiscount();
		amOrderDiscount.setDocumentCode(amOrder);
		amOrderDiscountDao.delete(amOrderDiscount);
		AmOrderDetail amOrderDetail = new AmOrderDetail();
		amOrderDetail.setDocumentCode(amOrder);
		amOrderDetailDao.delete(amOrderDetail);
	}

	/**
	 * 保存主表
	 * @param amOrder
	 */
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public Map saveData (AmOrder amOrder, List<AmOrderDetail> amOrderDetail, String date) {
		Map<String, Object> map = new HashMap<>();
		try {
			super.save(amOrder);
			amOrderDetailDao.insertBatch(amOrderDetail);
		}catch (Exception e) {
			map.put("flag", "failure");
			map.put("code", "500");
			map.put("msg", "订单创建失败");
			map.put("time", date);
			return map;
		}
		map.put("flag", "success");
		map.put("code", "200");
		map.put("msg", "订单创建成功");
		map.put("time", date);
		return map;
	}

	/**
	 * 保存子表
	 * @param amOrderDetail
	 */
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void saveDetail (AmOrderDetail amOrderDetail) {
		// 保存 AmOrder子表
		if (amOrderDetail.getIsNewRecord()) {
			amOrderDetail.preInsert();
			amOrderDetailDao.insert(amOrderDetail);
		} else {
			amOrderDetail.preUpdate();
			amOrderDetailDao.update(amOrderDetail);
		}
	}
	/**
	 * 判断是否有相同库位耗材的明细
	 * @param amOrder
	 * @return
	 */
	public String getSku (AmOrder amOrder) {
		String message = "";
		List<String> detiList = new ArrayList();
		for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()) {
			if (!amOrderDetail.STATUS_DELETE.equals(amOrderDetail.getStatus())) {
				String key = amOrderDetail.getSkuId();
				if ("".equals(key) || key == null) {
					continue;
				}
				if (detiList.contains(key)) {
					message = "明细中存在相同SKU";
					return message;
				}
				detiList.add(key);
			}
		}
		return message;
	}

	/**
	 * 只保存主表
	 * @param amOrder
	 */
	@Transactional(readOnly = false)
	public void saveData (AmOrder amOrder) {
		super.save(amOrder);
	}



	/**
	 *	插入信息到主表明细表 优惠信息表 添加成功后更新购物车商品为无效
	 * @param amOrder
	 */
	@Transactional(readOnly = false)
	public Map saveOrder (AmOrder amOrder, List<String> skuIdList, String userCode) {
		Map<String, Object> map = new HashMap<>();
		try {
			super.save(amOrder);
			if (amOrder.getAmOrderDetailList() != null && amOrder.getAmOrderDetailList().size() >0) {
				amOrderDetailDao.insertBatch(amOrder.getAmOrderDetailList());
			}
			if (amOrder.getAmOrderDiscountList() != null && amOrder.getAmOrderDiscountList().size() >0) {
				amOrderDiscountDao.insertBatch(amOrder.getAmOrderDiscountList());
			}
			if (ListUtils.isNotEmpty(skuIdList)) {
				amOrderShoppingDao.updateBySkuIdList(skuIdList, userCode);
			}
		}catch (Exception e) {
			map.put("code", "500");
			map.put("msg", "订单创建失败");
			map.put("data", "");
			return map;
		}
		map.put("code", "200");
		map.put("msg", "订单创建成功");
		map.put("data", "订单号:"+ amOrder.getDocumentCode());
		return map;
	}

	/**
	 *
	 * @param trade
	 * @param orderList
	 * @param pay
	 * @param
	 * @param postFee
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map savaUplode(Trade trade, List<Order> orderList, Double pay, Double postFee) {
		AmOrder amOrder = new AmOrder();
		String documentode = trade.getTid().toString();
		amOrder.setDocumentCode(documentode); // 订单编号
		amOrder.setDocumentType("平台原始订单");            // 单据类型
		amOrder.setDocumentStatus("创建");
		amOrder.setQuoteTime(new Date());					// 报价时间
		amOrder.setCustomerName(trade.getReceiverName());  // 客户姓名
		amOrder.setCustomerNick(trade.getBuyerNick());     // 客户呢称
		amOrder.setMobilePhone(trade.getReceiverMobile()); // 移动电话
		amOrder.setFixedPhone(trade.getReceiverPhone());   // 固定电话
		amOrder.setProvince(trade.getReceiverState());     // 省
		amOrder.setCity(trade.getReceiverCity());          // 市
		amOrder.setRegion(trade.getReceiverDistrict());    // 区
		amOrder.setDetailedAddress(trade.getReceiverAddress());    // 详细地址
		amOrder.setDistribution("送货上门并安装");         // 配送方式
		amOrder.setPayType("支付宝");                      // 支付方式
        String O2oGuideName = trade.getO2oGuideName();      // 导购员姓名
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (O2oGuideName == null || "".equals(O2oGuideName)) {
            amOrder.setCreateBy(UserUtils.getUser().getUserName());
        } else {
            amOrder.setCreateBy(O2oGuideName);		// 导购员姓名-创建人
        }
        amOrder.setOfficeCode(EmpUtils.getOffice().getOfficeCode());
        if (trade.getCreated() != null) {
			String date = format.format(trade.getCreated());
			String []datetime = date.split("-");
			int a = Integer.parseInt(datetime[1]);
			if (a < 10) {
				datetime[1] = datetime[1].replace("0", "");
			}
			String time = datetime[0] + "-"+ datetime[1] + "-" + datetime[2];
			amOrder.setCreateTime(time);          		// 创建时间
		} else {
        	amOrder.setCreateTime(null);
		}
		amOrder.setCreateDate(trade.getCreated());
//		amOrder.setTotalPrice(new Double(trade.getTotalFee()));    // 商品总额
		if (pay != null || postFee != null) {
//			amOrder.setPreferential(discountFee);				// 优惠金额
			amOrder.setLogisticsFee(postFee);					// 物流费
			amOrder.setTotalFee(pay);    						// 合计应收
		} else {
//			amOrder.setPreferential(new Double(trade.getPayment()));
			amOrder.setLogisticsFee(new Double(trade.getPostFee()));
			amOrder.setTotalFee(new Double(trade.getPayment()));
		}
		amOrder.setStageStatus(trade.getStepTradeStatus());   // 分阶段状态
		amOrder.setIsNewRecord(true);
		List<Order> detailList = trade.getOrders();
		List<AmOrderDetail> amOrderDetailList = new ArrayList<>();
		Double totalAtm = new Double(0);
		Map<String, Object> map = new HashMap<>();
		if (orderList != null && orderList.size() >0) {
			for (Order order : orderList) {
				if (order.getSkuId() == null || "".equals(order.getSkuId())) {
					continue;
				}
				AmOrderDetail amOrderDetail = new AmOrderDetail();
				amOrderDetail.setDocumentCode(amOrder);
				amOrderDetail.setGoodsName(order.getTitle());   // 商品名称
				amOrderDetail.setSkuId(order.getSkuId());      // SKUid
				TbSku tbSku = new TbSku();
				tbSku.setSkuId(Long.valueOf(order.getSkuId()));
				tbSku = tbSkuService.get(tbSku);
				Double realPrice = new Double(0);
				if (tbSku != null) {
					realPrice = Double.parseDouble(tbSku.getRealPrice());    // 真实售价
					amOrderDetail.setPrice(realPrice);
				}
				amOrderDetail.setSku(order.getOuterSkuId());  // sku
				amOrderDetail.setSpec(order.getSkuPropertiesName());  // 规格
				Long num = order.getNum();
				amOrderDetail.setQuantity(num);      // 数量
				if (num == null) {
					num = Long.valueOf(0);
				}
				Double amount = realPrice * num;
				amOrderDetail.setAmount(amount);
				amOrderDetail.setStandPrice(new Double(order.getPrice()));// 标准售价
				totalAtm = NumberUtils.add(totalAtm, amount);
				amOrderDetail.setShop("0");
				amOrderDetail.setIsNewRecord(true);
				amOrderDetailList.add(amOrderDetail);
			}
		}
		if (detailList != null && detailList.size() > 0) {
			for (Order order : detailList) {
				if (order.getSkuId() == null || "".equals(order.getSkuId())) {
					continue;
				}
				AmOrderDetail amOrderDetail = new AmOrderDetail();
				amOrderDetail.setDocumentCode(amOrder);
				amOrderDetail.setGoodsName(order.getTitle());   // 商品名称
				amOrderDetail.setSkuId(order.getSkuId());      // SKUid
				TbSku tbSku = new TbSku();
				tbSku.setSkuId(Long.valueOf(order.getSkuId()));
				tbSku = tbSkuService.get(tbSku);
				Double realPrice = new Double(0);
				if (tbSku != null) {
					realPrice = Double.parseDouble(tbSku.getRealPrice());    // 真实售价
					amOrderDetail.setPrice(realPrice);
				}
				amOrderDetail.setSku(order.getOuterSkuId());  // sku
				amOrderDetail.setSpec(order.getSkuPropertiesName());  // 规格
				Long num = order.getNum();
				amOrderDetail.setQuantity(num);      // 数量
				if (num == null) {
					num = Long.valueOf(0);
				}
				Double amount = num * realPrice;
				amOrderDetail.setAmount(amount);
				amOrderDetail.setStandPrice(new Double(order.getPrice()));// 标准售价
				totalAtm = NumberUtils.add(totalAtm, amount);
				amOrderDetail.setShop("0");
				amOrderDetail.setIsNewRecord(true);
				amOrderDetailList.add(amOrderDetail);
			}
		}
		amOrder.setTotalPrice(totalAtm);
		String date = format.format(new Date());
		map = saveData(amOrder, amOrderDetailList, date);
		return map;
	}

    /**
     * @param tradeList
     * @param trade
     * @return
     */
    @Transactional(readOnly = false)
	public Map getOrderList (List<Trade> tradeList, Trade trade) {
        Long tid = trade.getTid();
        Double payment = new Double(0);
//        Double discountFee = new Double(0);
        Double postFee = new Double(0);
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < tradeList.size(); i++) {
            if (tradeList.get(i).getPayment() == null) {
                payment = NumberUtils.add(new Double(0), payment);                // 合并在此订单中（头信息：Payment，DiscountFee，PostFee要合并
            } else {
                payment = NumberUtils.add(Double.parseDouble(tradeList.get(i).getPayment()), payment);                // 合并在此订单中（头信息：Payment，DiscountFee，PostFee要合并
            }
//            if (tradeList.get(i).getDiscountFee() == null) {
//                discountFee = NumberUtils.add(new Double(0), discountFee);                // 合并在此订单中（头信息：Payment，DiscountFee，PostFee要合并
//            } else {
//                discountFee = NumberUtils.add(Double.parseDouble(tradeList.get(i).getDiscountFee()), discountFee);
//            }
            if (tradeList.get(i).getPostFee() == null) {
                postFee = NumberUtils.add(new Double(0), postFee);                // 合并在此订单中（头信息：Payment，DiscountFee，PostFee要合并
            } else {
                postFee = NumberUtils.add(Double.parseDouble(tradeList.get(i).getPostFee()), postFee);
            }
            if (tid != tradeList.get(i).getTid()) {
                for (int j = 0; j < tradeList.get(i).getOrders().size(); j++) {
                    Order order = tradeList.get(i).getOrders().get(j);
                    orderList.add(order);
                }
            }
        }
        return savaUplode(trade, orderList, payment, postFee);
    }

    @Transactional(readOnly = false)
    public Map saveMigrateData(AmOrder amOrder, String date) {
		Map<String, Object> map = new HashMap<>();
		try {
			super.save(amOrder);
			amOrderDetailDao.insertBatch(amOrder.getAmOrderDetailList());
		}catch (Exception e) {
			map.put("flag", "failure");
			map.put("code", "500");
			map.put("msg", "数据推送失败");
			map.put("time", date);
			return map;
		}
		map.put("flag", "success");
		map.put("code", "200");
		map.put("msg", "数据推送成功");
		map.put("time", date);
		return map;
	}

	@Transactional(readOnly = false)
	public int deleteDb (String documentCode) {
    	return amOrderDetailDao.deleteDb(documentCode);
	}

	@Transactional(readOnly = true)
	public List<User> getUser(String userName) {
    	return amOrderDao.getUser(userName);
	}

	/**
	 * 根据帐号获取部门编码
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getEmp(String loginCode) {
		return amOrderDao.getOffice(loginCode);
	}
	@Transactional(readOnly = false)
	public void saveDiscount (AmOrder amOrder, List<AmOrderDiscount> discountList){
		amOrderDiscountDao.deleteDiscount(amOrder.getDocumentCode());
		super.save(amOrder);
		for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()){
			if (!AmOrderDetail.STATUS_DELETE.equals(amOrderDetail.getStatus())){
				amOrderDetail.setDocumentCode(amOrder);
				if (amOrderDetail.getIsNewRecord()){
					amOrderDetail.preInsert();
					amOrderDetailDao.insert(amOrderDetail);
				}else{
					amOrderDetail.preUpdate();
					amOrderDetailDao.update(amOrderDetail);
				}
			}else{
				amOrderDetailDao.delete(amOrderDetail);
			}
		}
//		amOrderDetailDao.insertBatch(amOrder.getAmOrderDetailList());
//		amOrderDiscountDao.insertBatch(amOrder.getAmOrderDiscountList());
		if (discountList != null) {
			amOrderDiscountDao.insertBatch(discountList);
		}
	}
	@Transactional
	public void deleteDiscount(String documentCode) {
		amOrderDiscountDao.deleteDiscount(documentCode);
	}

	/**
	 * 删除订单，订单商品明细，订单优惠活动
	 * @auther: len
	 * @date: 2018/7/27 16:00
	 */
	@Transactional(readOnly = false)
	public void deleteOrder(String documentCode) {
		amOrderDao.deleteOrder(documentCode);
		amOrderDetailDao.deleteDb(documentCode);
		amOrderDiscountDao.deleteDiscount(documentCode);
    }

	/**
	 * 根据用户名获取订单
	 */
	public List<AmOrder> getGuideOrder (String userName){
		return amOrderDao.getGuideOrder(userName);
	}

	/**
	 * 根据订单号获取明细信息
	 * @param documentCode
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AmOrderDetail> getDetail(String documentCode) {
	    return amOrderDetailDao.getDetail(documentCode);
    }

	/**
	 *
	 * @param amOrder
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map saveHeader(AmOrder amOrder) {
		Map<String, Object> map = new HashMap<>();
		try {
			super.save(amOrder);
		}catch (Exception e) {
			map.put("code", "900");
			map.put("msg", "更新失败");
			return map;
		}
		map.put("code", "200");
		map.put("msg", "更新成功");
		return map;
	}

	/**
	 * 动态报价，计算运费，计算优惠
	 * @param amOrder
	 * @return
	 */
	@Transactional(readOnly = false)
	public String quotation(AmOrder amOrder) throws Exception{
		if (amOrder.getQuoteTime() == null) {
			return "请输入报价时间";
		}
		String province = getArea(amOrder.getProvince());       // 省
		if ("".equals(province) || province == null) {
			return "请输入省";
		}
		String city = getArea(amOrder.getCity());               // 市
		if ("".equals(city) || city == null) {
			return "请输入市";
		}
		String region = getArea(amOrder.getRegion());            // 区
		if ("".equals(region) || region == null) {
			return "请输入区";
		}
		String distribution = amOrder.getDistribution();        // 区
		if ("".equals(distribution) || distribution == null) {
			return "配送方式不能为空";
		}
		// 获取报价时间
		Date quoteTime = amOrder.getQuoteTime();
		if (quoteTime == null) {
			return "报价时间为空，不能进行动态报价";
		}
		if (amOrder.getAmOrderDiscountList() != null && amOrder.getAmOrderDiscountList().size() > 0) {
			List<AmOrderDiscount> discountList = amOrder.getAmOrderDiscountList().stream().filter(s -> s.getDiscount().equals("其它优惠")).collect(Collectors.toList());
			if (discountList.size() > 1) {
				return "已存在其它优惠，不能重复新增!";
			}
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String quoteDate = dateFormat.format(quoteTime);
		// 登录K3
		boolean isLogin = k3connection.getConnection();
		if (isLogin) {
			Discount discount = new Discount();
			List<VirtualQuotation> quotationList = new ArrayList<>();
			Map<String, AmOrderDetail> map = new HashMap<>();
			if (amOrder.getAmOrderDetailList() != null && amOrder.getAmOrderDetailList().size() > 0) {
				for (AmOrderDetail detail : amOrder.getAmOrderDetailList()) {            // 判断明细行选择了同样的sku 并合并数量
					Long quantity = 0L;
					if (map.containsKey(detail.getSku())) {
						quantity = detail.getQuantity() + map.get(detail.getSku()).getVirtualQuantity();
						detail.setVirtualQuantity(quantity);
						map.put(detail.getSku(), detail);
					} else {
						detail.setVirtualQuantity(detail.getQuantity());
						map.put(detail.getSku(), detail);
					}

				}
			}
			Iterator<Map.Entry<String, AmOrderDetail>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				VirtualQuotation virtualQuotation = new VirtualQuotation();
				Map.Entry<String, AmOrderDetail> entry = it.next();
				AmOrderDetail amOrderDetail = entry.getValue();
				// sku
				virtualQuotation.setMaterialNumber(amOrderDetail.getSku());
				// 数量
				virtualQuotation.setMaterialQty(amOrderDetail.getVirtualQuantity());
				if ("0".equals(amOrderDetail.getShop())) {
					// 明细店铺
					virtualQuotation.setOtherShop("优梵艺术旗舰店");
				} else if ("1".equals(amOrderDetail.getShop())) {
					virtualQuotation.setOtherShop("优梵艺术家居旗舰店");
				}

				quotationList.add(virtualQuotation);
			}
			// 根据当前登录用户获取门店
			String treeNames = guideService.selectShop(EmpUtils.getEmployee().getEmpCode());
			String store = null;
			if (treeNames != null) {
				if (treeNames.contains("/")) {
					String[] officeNmaes = treeNames.split("/");
					store = officeNmaes[1];
				}
			}
			if (store != null) {
				discount.setStore(store);
			}
			Double wholeReducedSum = new Double(0);
			for (AmOrderDiscount amOrderDiscount : amOrder.getAmOrderDiscountList()) {
				if ("其它优惠".equals(amOrderDiscount.getDiscount())) {
					wholeReducedSum = amOrderDiscount.getDiscountPrice();
				}
			}
			if ("".equals(wholeReducedSum) || wholeReducedSum == null) {
				wholeReducedSum = new Double(0);;
			}
			// 其它优惠的金额
			discount.setWholeReducedSum(NumberUtils.formatDouble(wholeReducedSum));
			// 是否享受油补
			discount.setIsPostageDisscount(amOrder.getIsEnjoy());
			discount.setShop("优梵艺术旗舰店");
			discount.setActivityDate(quoteDate);
			discount.setMaterialList(quotationList);
			String favourable = JSONObject.toJSON(discount).toString();
			// 总优惠
			Double discountAtm = new Double(0);
			Double totlePrice = new Double(0);
			// 油费补贴
			Double oilSubsidy = new Double(0);


			StringBuffer buffer1 = InvokeHelper.concession("virtualQuotation", favourable, POST_K3ClOUDRL);     // 计算优惠
			JSONObject json1 = JSON.parseObject(buffer1.toString());
			System.out.println(json1);
			String isError1 = json1.get("IsError").toString();
			List<AmOrderDiscount> discountList = new ArrayList<>();
			List<AmOrderDetail> detailList = ListUtils.newArrayList();
			if ("true".equals(isError1)) {
				String msg = json1.get("Message").toString();
				if (msg.contains("K3不存在")) {
					String msg1 = msg.substring(5, msg.length());
					if (msg1.startsWith("】")) {
						msg1 = msg1.replace("】", "");
						return msg1;
					} else {
						return msg1;
					}
				} else {
					return msg;
				}

			} else if ("false".equals(isError1)) {                                        // 如果没有错误返回false
				Map<String, String> skuMap = new HashMap();
				if (json1.getJSONObject("Value").get("VirtualQuotationOriginalGoodList") != null) {
					JSONArray priceInfo = JSON.parseArray(json1.getJSONObject("Value").get("VirtualQuotationOriginalGoodList").toString());
					if (priceInfo != null && priceInfo.size() > 0) {
						for (int i = 0; i < priceInfo.size(); i++) {
							String materialNumber = priceInfo.getJSONObject(i).get("materialNumber").toString();
							String price = priceInfo.getJSONObject(i).get("paymentGoodsPrice").toString();
							skuMap.put(materialNumber, price);
						}
					}
				} else {
					return "商品取价失败，请联系管理员";
				}
				if (skuMap != null) {
					for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()) {
						if (skuMap.containsKey(amOrderDetail.getSku())) {
							Double price = Double.parseDouble(skuMap.get(amOrderDetail.getSku()));
							amOrderDetail.setPrice(price);
							// sku总额
							Double amount = price * amOrderDetail.getQuantity();
							amOrderDetail.setAmount(amount);
							detailList.add(amOrderDetail);
							// 商品总额
							totlePrice = NumberUtils.add(amount, totlePrice);
						}
					}
				}
				// 判断是否存在门店优惠
				if (json1.getJSONObject("Value").get("StoreVirtualQuotationResponseList") != null) {

					// 门店特有优惠
					JSONArray storeInfo = JSON.parseArray(json1.getJSONObject("Value").get("StoreVirtualQuotationResponseList").toString());
					if (storeInfo != null && storeInfo.size() > 0) {
						for (int i = 0; i < storeInfo.size(); i++) {
							// 油费
							String postageDisscount = storeInfo.getJSONObject(i).get("PostageDisscount").toString();
							if (Double.parseDouble(postageDisscount) == 0) {
								AmOrderDiscount amOrderDiscount = new AmOrderDiscount();
								String offerName = storeInfo.getJSONObject(i).get("offerName").toString();        // 获取名称
								String offerAmount = storeInfo.getJSONObject(i).get("offerAmount").toString();    // 获取优惠金额
								amOrderDiscount.setDocumentCode(amOrder);
								amOrderDiscount.setDiscount(offerName);
								// 立减金额
								String subtractionAmmount = storeInfo.getJSONObject(i).get("SubtractionAmmount").toString();

								String viceStoreVouchers = "0";
								String discountAmount = "0";
								if (storeInfo.getJSONObject(i).containsKey("viceStoreVouchers")) {
									// 副店券金额
									viceStoreVouchers = storeInfo.getJSONObject(i).get("viceStoreVouchers").toString();
								}
								if (storeInfo.getJSONObject(i).containsKey("discountAmount")) {
									// 副店优惠金额
									discountAmount = storeInfo.getJSONObject(i).get("discountAmount").toString();
								}
								amOrderDiscount.setDocumentCode(amOrder);
								amOrderDiscount.setDiscount(offerName);
								// 优惠金额 = 优惠金额+立减金额
								Double discountPrice = NumberUtils.add(Double.parseDouble(offerAmount), Double.parseDouble(subtractionAmmount));
								Double discountMoney = NumberUtils.add(NumberUtils.add(discountPrice, Double.parseDouble(viceStoreVouchers)), Double.parseDouble(discountAmount));
//								Double discountPrice = Double.parseDouble(offerAmount);
								amOrderDiscount.setDiscountPrice(discountMoney);
								discountAtm = NumberUtils.add(discountAtm, discountMoney);
								discountList.add(amOrderDiscount);
								// 油费
							} else {
								oilSubsidy = NumberUtils.add(oilSubsidy, Double.parseDouble(postageDisscount));
							}
						}
					}
				}
			}

			Double threeCharges = amOrder.getThreeCharges();
			if (threeCharges == null) {
				threeCharges = new Double(0);
			}
			if (discountList != null && discountList.size() > 0) {
				// 优惠页签
				amOrder.setAmOrderDiscountList(discountList);
				// 优惠金额
				amOrder.setPreferential(discountAtm);
				// 明细信息
				amOrder.setAmOrderDetailList(detailList);
				// 商品总额
				amOrder.setTotalPrice(totlePrice);
				String message = calculateShippingCosts(amOrder, province, city, region, isLogin, "0");            // 计算运费
				if (!"".equals(message)) {
					return message;
				}
				if ("0".equals(amOrder.getIsEnjoy())) {
					// 合计应收
					amOrder.setTotalFee(NumberUtils.sub(NumberUtils.add(NumberUtils.add(totlePrice, amOrder.getLogisticsFee()), threeCharges), discountAtm));
				} else {
					// 油费
					amOrder.setOilSubsidy(oilSubsidy);
					Double totalFee = NumberUtils.sub(NumberUtils.add(NumberUtils.add(totlePrice, amOrder.getLogisticsFee()), threeCharges), discountAtm);
					// 合计应收=商品总额 +物流费+三包费–优惠金额–油费补贴
					amOrder.setTotalFee(NumberUtils.sub(totalFee, oilSubsidy));
				}

				amOrder.setProvince(province);
				amOrder.setCity(city);
				amOrder.setRegion(region);
				this.saveDiscount(amOrder, discountList);
			} else {
				// 明细信息
				amOrder.setAmOrderDetailList(detailList);
				// 商品总额
				amOrder.setTotalPrice(totlePrice);
				// 优惠金额
				amOrder.setPreferential(discountAtm);
				String message = calculateShippingCosts(amOrder, province, city, region, isLogin, "0");            // 计算运费
				if (!"".equals(message)) {
					return message;
				}
				if ("0".equals(amOrder.getIsEnjoy())) {
//						// 合计应收
//						amOrder.setTotalFee(NumberUtils.sub(NumberUtils.add(NumberUtils.add(totlePrice, amOrder.getLogisticsFee()), threeCharges), discountAtm));
					// 合计应收
					amOrder.setTotalFee(NumberUtils.sub(NumberUtils.add(NumberUtils.add(totlePrice, amOrder.getLogisticsFee()), threeCharges), discountAtm));
				} else {
					Double totalFee = NumberUtils.sub(NumberUtils.add(NumberUtils.add(totlePrice, amOrder.getLogisticsFee()), threeCharges), discountAtm);
					// 合计应收=商品总额 +物流费+三包费–优惠金额–油费补贴
					amOrder.setTotalFee(NumberUtils.sub(totalFee, oilSubsidy));
					// 油费
					amOrder.setOilSubsidy(oilSubsidy);
				}

				amOrder.setProvince(province);
				amOrder.setCity(city);
				amOrder.setRegion(region);
				this.saveDiscount(amOrder, null);
				return "没有匹配到优惠信息!";
			}

		} else {
			return "服务异常，请稍后";
		}
		return "";
	}

	/**
	 * 计算运费
	 * @param amOrder
	 * @param province
	 * @param city
	 * @param region
	 * @param isLogin
	 * @return
	 */
	public String calculateShippingCosts(AmOrder amOrder, String province, String city, String region, boolean isLogin, String flag) throws Exception{
		if (isLogin) {
//			Double preferential = amOrder.getPreferential();        // 优惠金额
//			if (preferential == null) {
//				preferential = new Double(0);
//			}
			// 明细行店铺为优梵艺术旗舰店的商品总额
			Double goodsPrice = new Double(0);
			for (AmOrderDetail amOrderDetail : amOrder.getAmOrderDetailList()) {
				if ("0".equals(amOrderDetail.getShop())) {
					goodsPrice = NumberUtils.add(amOrderDetail.getAmount(), goodsPrice);
				}
			}
			String distribution = amOrder.getDistribution();
			K3DiscountData concession = new K3DiscountData();
			concession.setProvince(province);
			concession.setCity(city);
			concession.setDistrict(region);
			// 商品总额改为取明细行店铺为优梵艺术旗舰店的商品总额
//			concession.setMoney(getTwoDecimal(totalPrice));
			concession.setMoney(getTwoDecimal(goodsPrice));
			concession.setDeliveryMethod(distribution);
			String info = JSONObject.toJSONString(concession);
			StringBuffer buffer = InvokeHelper.concession("calculateShippingCosts", info, POST_K3ClOUDRL);     // 计算运费
			JSONObject json = JSON.parseObject(buffer.toString());
			String isError = json.get("IsError").toString();
			if ("true".equals(isError)) {
				return json.get("Message").toString();
			} else if ("false".equals(isError)) {
				String money = "0";
				try {
					money = json.getJSONObject("Value").get("money").toString();
				} catch (NullPointerException e) {

				}
				Double freight = Double.parseDouble(money);
				amOrder.setLogisticsFee(freight);
				if ("1".equals(flag)) {
					// 三包费
					Double threeCharges = amOrder.getThreeCharges();
					if ("".equals(threeCharges) || threeCharges == null) {
						threeCharges = new Double(0);
					}
					// 商品总额 + 运费 + 三包费
					Double total = NumberUtils.add(NumberUtils.add(amOrder.getTotalPrice(), freight), threeCharges);
					Double preferential1 = amOrder.getPreferential();
					if ("".equals(preferential1) || preferential1 == null) {
						preferential1 = new Double(0);
					}
					Double totalFee = NumberUtils.sub(total, preferential1);
					amOrder.setTotalFee(totalFee);
				}
			}

		} else {
			return "服务异常,请稍后";
		}
		return "";
	}

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
			return null;
		}
	}

	/**
	 * 根据特权定金获取订单详情
	 * @param set
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<AmOrder> selectByPrivilege(Set<String> set) {
		return amOrderDao.selectByPrivilege(set);
	}

	/**
	 * 更新核销状态
	 * @param amOrder
	 * @throws Exception
	 */
	public void updateWriteoff(AmOrder amOrder) throws Exception{
		boolean isLogin = k3connection.getConnection();
		if (isLogin) {
			String content = "{\"FormId\":\"YF_SAL_LineDownRegister\",\"FieldKeys\":\"FID\",\"FilterString\":\"F_YF_PromoCode='"+ amOrder.getCouponCode() +"'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
			String fid = InvokeHelper.ExecuteBillQuery("YF_SAL_LineDownRegister", content, POST_K3ClOUDRL);
			if (!"[[[]]]".equals(fid) || !"[]".equals(fid)) {
				fid = fid.replace("[[", "");
				fid = fid.replace("]]", "");
				String date = DateUtils.formatDateTime(new Date());
				date = date.replace("T", " ");
				String shop = guideService.selectShop(UserUtils.getUser().getUserCode());
				String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"ValidateFlag\":\"True\",\"NumberSearch\":\"True\",\"InterationFlags\":\"\",\"IsAutoSubmitAndAudit\":\"false\",\"Model\":{\"FID\":\""+ fid +"\",\"F_YF_WriteoffStatus\":\"1\",\"F_YF_WriteoffTime\":\""+ date +"\",\"F_YF_WriteoffStore\":\""+ shop +"\",\"F_YF_StoreGuide\":\""+ UserUtils.getUser().getUserName() +"\"}";
				InvokeHelper.Save("YF_SAL_LineDownRegister", saveParam, POST_K3ClOUDRL);
			}
		}
	}
}