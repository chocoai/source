package com.jeesite.modules.asset.group.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.group.entity.*;
import com.jeesite.modules.asset.group.service.GroupPurchaseService;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.taobao.entity.TaobaoOrderRds;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 双十一小区团购接口
 */
@Controller
@RequestMapping("purchase")
public class PurchaseController {
    @Value("${POST_K3ClOUDRL}")
    private String postUrl;
    @Value("${RDSORDER}")
    private String RDSORDER;
    @Autowired
    private K3connection k3connection;
    @Autowired
    private GroupPurchaseService groupPurchaseService;
    @Autowired
    private AmSeqService amSeqService;
    private Map<String, String> skuMap = new HashMap<>();
    /**
     * 等待卖家发货,即:买家已付款
     */
    private static final String STATUS = "WAIT_SELLER_SEND_GOODS";
    /**
     * 支付时间
     */
    private static final Date PAYTIME = DateUtils.parseDate("2018-11-11");

    /**
     * 根据团长旺旺ID+phone 查询小区拼团表中是否存在该团：
     *
     * If 存在，那么返回团长和团员的旺旺id;
     *
     * If不存在，新增一个团，记录团长和团员信息；
     */
    @ResponseBody
    @RequestMapping("getGroupPurchase")
    public ReturnInfo getGroupPurchase(String wangCode, String phone) {
        if (wangCode == null || "".equals(wangCode)) {
            return ReturnDate.error(10004, "请输入旺旺ID");
        }
        if (phone.length() != 11) {
            return ReturnDate.error(10003, "请输入正确的手机号");
        }
        List<PurchaseData> purchaseDataList = groupPurchaseService.getMember(wangCode);
        for (PurchaseData purchaseData : purchaseDataList) {
            if (wangCode.equals(purchaseData.getMemberWangCode())) {
                return ReturnDate.error(10001, "该客户已参与其他小区团购");
            }
        }
        // 根据旺旺id/phone匹配团长
        List<GroupPurchase> groupPurchaseList = groupPurchaseService.getGroupPurchase(wangCode, phone);
        if (groupPurchaseList == null || groupPurchaseList.size() <= 0) {
            GroupPurchase purchase = new GroupPurchase();
            purchase.setPurchaseCode(amSeqService.getCode("TJ"));
            purchase.setWangCode(wangCode);
            purchase.setGroupPhone(phone);
            purchase.setCreateTime(new Date());
            purchase.setIsNewRecord(true);
            groupPurchaseService.save(purchase);
            purchase = groupPurchaseService.get(purchase);
            BuyerData buyerData = getGoodsNum(purchase);
            if (buyerData.isAbnormal()) {
                return ReturnDate.error(10005, "服务异常,请稍后");
            }
            return ReturnDate.success(201, "创建团购记录成功", buyerData);
        } else {
            // 根据旺旺id +phone匹配团长
            GroupPurchase purchase = groupPurchaseService.getPurchase(wangCode, phone);
            if (purchase != null) {
                purchase = groupPurchaseService.get(purchase);
                BuyerData buyerData = getGoodsNum(purchase);
                if (buyerData.isAbnormal()) {
                    return ReturnDate.error(10005, "服务异常,请稍后");
                }
                return ReturnDate.success(200, "查询团购信息成功", buyerData);
            } else {
                return ReturnDate.success(10002, "客户已参与其他团", purchase);
            }
        }
    }

    /**
     * 新增团员：
     *
     * 前台传团长ID和团员旺旺ID；
     *
     * 后台查询团员旺旺ID是否存在小区拼团表中，
     *
     * If 存在，那么返回前台“该客户已参团”
     *
     * If 不存在，那么在该团长下新增团员旺旺ID ;
     * @param wangCode
     * @param memberWangCode
     */
    @ResponseBody
    @RequestMapping("addMembers")
    public ReturnInfo addMember (String wangCode, String memberWangCode) {
        if (wangCode == null || "".equals(wangCode) || memberWangCode == null || "".equals(memberWangCode)) {
            return ReturnDate.error(10002, "请输入旺旺ID");
        }
        // 不存在， 在该团长下新增团员旺旺id
        List<GroupPurchase> groupPurchaseList = groupPurchaseService.getGroupPurchase(wangCode,null);
        if (groupPurchaseList == null || groupPurchaseList.size() <= 0) {
            return ReturnDate.error(10003, "团长旺旺ID不存在");
        }
        // 根据团员旺旺号查询是否存在小区拼团表中
        List<PurchaseData> purchaseDataList = groupPurchaseService.getMember(memberWangCode);
        if (purchaseDataList != null && purchaseDataList.size() >0) {
            return ReturnDate.error(10001, "该客户已参团");
        } else {
//            // 不存在， 在该团长下新增团员旺旺id
//            GroupPurchase groupPurchase = groupPurchaseService.getGroupPurchase(wangCode,null);
            GroupPurchase groupPurchase = groupPurchaseService.get(groupPurchaseList.get(0));
            List<GroupDetail> groupDetailList = ListUtils.newArrayList();
            GroupDetail groupDetail = new GroupDetail();
            groupDetail.setPurchaseCode(groupPurchase);
            groupDetail.setMemberWangCode(memberWangCode);
            groupDetail.setCreateTime(new Date());
            // 在团购表中增加团员
            groupDetail.setIsNewRecord(true);
            groupDetailList.add(groupDetail);
            groupPurchase.setGroupDetailList(groupDetailList);
            groupPurchaseService.save(groupPurchase);
            groupPurchase = groupPurchaseService.get(groupPurchase);
            BuyerData buyerData = getGoodsNum(groupPurchase);
            if (buyerData.isAbnormal()) {
                return ReturnDate.error(10005, "服务异常,请稍后");
            }
            return ReturnDate.success(buyerData);
        }
    }
    /**
     * 查询团内购买商品件数及可享折扣；
     * @param
     * @param
     */
    public BuyerData getGoodsNum (GroupPurchase purchase) {
        // 购买总件数
        Double goodsNum = new Double(0);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(purchase.getWangCode());
        for (GroupDetail groupDetail : purchase.getGroupDetailList()) {
            jsonArray.add(groupDetail.getMemberWangCode());
        }
        String param1 = JSONObject.toJSON(jsonArray).toString();
//        String url = RDSORDER + "?json='" + param1+"'";
        // 请求获取的参数
        String url = RDSORDER + "?json=" + param1;
        // 把双引号转义成单引号
        url = url.replace('\"','\'');
        String orderRes = HttpClientUtils.ajaxGet(url);
        JSONObject json = JSONObject.parseObject(orderRes);
        if ("true".equals(json.get("IsError").toString())) {
            BuyerData buyer = new BuyerData();
            buyer.setAbnormal(true);
            return buyer;
        }
        String value = json.get("Value").toString();
        // 查询根据买家昵称获取订单
        List<TaobaoOrderRds> rdsList = JSONArray.parseArray(value, TaobaoOrderRds.class);
        List<MembersData> membersList = ListUtils.newArrayList();
        Map<String, Double> map = new HashMap();
        boolean isLogin = k3connection.getConnection();
        if (!isLogin){
            BuyerData buyer = new BuyerData();
            buyer.setAbnormal(true);
            return buyer;
        }

        for (TaobaoOrderRds taobaoOrderRds : rdsList) {
            // 单人购买件数
            Double num = new Double(0);
            TradeGetResponse tradeGetResponse = new TradeGetResponse();
            try {
                tradeGetResponse = TaobaoUtils.parseResponse(taobaoOrderRds.getJdpResponse(), TradeGetResponse.class);
                Trade trade = tradeGetResponse.getTrade();
                // 订单状态=买家已付款
                if (STATUS.equals(trade.getStatus())) {
                    // 订单支付时间在2018/11/11
                    if (PAYTIME.compareTo(DateUtils.parseDate(DateUtils.formatDate(trade.getPayTime(), "yyyy-MM-dd"))) == 0) {
                        List<Order> orderList = trade.getOrders();
                        for (Order order : orderList) {
                            String formId = "BD_MATERIAL";
                            Long buyNum = order.getNum();
                            String result = null;
                            if (skuMap.containsKey(order.getOuterSkuId())) {
                                result = skuMap.get(order.getOuterSkuId());
                            } else {
                                String param = "{\"FormId\":\""+ formId +"\",\"FieldKeys\":\"F_YF_OperationNum\",\"FilterString\":\"FNUMBER='"+ order.getOuterSkuId() +"'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
                                try {
                                    result = InvokeHelper.ExecuteBillQuery(formId, param, postUrl);
                                    skuMap.put(order.getOuterSkuId(), result);
                                } catch (Exception e) {
                                    BuyerData buyer = new BuyerData();
                                    buyer.setAbnormal(true);
                                    return buyer;
                                }
                            }
                            if (!"[]".equals(result) && !"[[[]]]".equals(result)) {
                                result = result.replace("[[", "");
                                result = result.replace("]]", "");
                                num = NumberUtils.add(Double.parseDouble(result) * buyNum, num);

                            }
                        }
                        goodsNum = NumberUtils.add(goodsNum, num);
                    }
                }
                if (map.containsKey(taobaoOrderRds.getBuyerNick())) {
                    Double buyerNum = map.get(taobaoOrderRds.getBuyerNick());
                    map.put(taobaoOrderRds.getBuyerNick(), NumberUtils.add(buyerNum, num));
                } else {
                    map.put(taobaoOrderRds.getBuyerNick(), num);
                }
            } catch (ApiException e) {

            }
        }
//        list.remove(length - 1);
        for (int i = 0; i < jsonArray.size(); i++) {
            String code = jsonArray.get(i).toString();
            MembersData membersData = new MembersData();
            membersData.setMemberCode(code);
            if (map.containsKey(code)) {
                membersData.setNum(map.get(code));
            } else {
                membersData.setNum(new Double(0));
            }
            membersList.add(membersData);
        }
        BuyerData buyer = new BuyerData();
        // 购买总数量
        buyer.setGoodsNum(goodsNum);
        // 团员购买信息
        buyer.setMembersList(membersList);
        // 购买的总件数符合的折扣
        buyer.setRebate(getRebate(goodsNum));
        return buyer;
    }


    public String getRebate(Double goodsNum) {
        String rebate = "无可享折扣";
        if (goodsNum >= 5 && goodsNum < 8) {
            rebate = "97折";
        } else if (goodsNum >= 8 && goodsNum < 10) {
            rebate = "96折";
        } else if (goodsNum >= 10 && goodsNum < 15) {
            rebate = "95折";
        } else if (goodsNum >= 15 && goodsNum < 20) {
            rebate = "93折";
        } else if (goodsNum >= 20 && goodsNum < 35) {
            rebate = "9折";
        } else if (goodsNum >= 35) {
            rebate = "88折";
        }
        return rebate;
    }
}
