package com.jeesite.modules.asset.order.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.asset.order.entity.AmOrderDetail;
import com.jeesite.modules.asset.order.service.AmOrderService;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "trade")
public class TradeFullinfoController {

    @Autowired
    private AmOrderService amOrderService;

    @RequestMapping(value = "synTrade")
    @ResponseBody
    public Map synTrade(@RequestBody String req) {
        JSON json = JSONObject.parseObject(req);
        Trade trade = JSONObject.toJavaObject(json, Trade.class);
        AmOrder amOrder = new AmOrder();
        String documentode = trade.getTid().toString();
        amOrder.setDocumentCode(documentode); // 订单编号
        AmOrder amOrder1 = new AmOrder();
        amOrder1 = amOrderService.get(amOrder);
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        if (amOrder1 == null) {
            amOrder.setDocumentType("平台原始订单");            // 单据类型
            amOrder.setDocumentStatus("创建");
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
//            amOrder.setCreateDate(trade.getCreated());          // 创建时间
            amOrder.setCreateTime(format.format(trade.getCreated()));
            amOrder.setCreateBy(trade.getO2oGuideName());       // 导购员姓名
            amOrder.setTotalPrice(new Double(trade.getTotalFee()));    // 商品总额
            amOrder.setPreferential(new Double(trade.getDiscountFee()));// 优惠金额
            amOrder.setLogisticsFee(new Double(trade.getPostFee()));    // 物流费
            amOrder.setTotalFee(new Double(trade.getPayment()));    // 合计应收
            amOrder.setStageStatus(trade.getStepTradeStatus());   // 分阶段状态
            amOrder.setIsNewRecord(true);
            List<Order> detailList = trade.getOrders();
            List<AmOrderDetail> amOrderDetails = new ArrayList<>();
            for (Order order : detailList) {
                AmOrderDetail amOrderDetail = new AmOrderDetail();
                amOrderDetail.setDocumentCode(amOrder);
                amOrderDetail.setGoodsName(order.getTitle());   // 商品名称
                amOrderDetail.setSkuId(order.getSkuId());      // SKUid
                amOrderDetail.setSku(order.getOuterSkuId());  // sku
                amOrderDetail.setSpec(order.getSkuPropertiesName());  // 规格
                amOrderDetail.setQuantity(order.getNum());      // 数量
                amOrderDetail.setStandPrice(new Double(order.getPrice()));// 标准售价
                amOrderDetail.setIsNewRecord(true);
                amOrderDetails.add(amOrderDetail);
            }
            map = amOrderService.saveData(amOrder, amOrderDetails, date);
            return map;
        } else {
            map.put("flag", "failure");
            map.put("code", "500");
            map.put("msg", "存在相同订单");
            map.put("time", date);
            return map;
        }
    }
}
