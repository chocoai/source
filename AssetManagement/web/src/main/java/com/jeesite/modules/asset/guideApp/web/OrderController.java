package com.jeesite.modules.asset.guideApp.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.guideApp.entity.*;
import com.jeesite.modules.asset.guideApp.entity.DiscountAndPrice;
import com.jeesite.modules.asset.guideApp.service.GuideService;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.asset.order.entity.AmOrderDetail;
import com.jeesite.modules.asset.order.entity.AmOrderDiscount;
import com.jeesite.modules.asset.order.entity.AmOrderImg;
import com.jeesite.modules.asset.order.entity.k3Info.*;
import com.jeesite.modules.asset.order.service.AmOrderImgService;
import com.jeesite.modules.asset.order.service.AmOrderLogService;
import com.jeesite.modules.asset.order.service.AmOrderService;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @param: OrderController
 * @return:
 * @auther: len
 * @date: 2018/7/26 16:22
 */
@RestController
@RequestMapping(value="order")
public class OrderController {

    @Value("${POST_K3ClOUDRL}")
    private String POST_K3ClOUDRL;
    @Value("${K3URL}")
    private String K3URL;
    @Value("${K3NOREALURL}")
    private String K3NOREALURL;
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private AmOrderService amOrderService;
    @Autowired
    private K3connection k3connection;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TbProductService tbProductService;
    @Autowired
    private TbSkuService tbSkuService;
    @Autowired
    private GuideService guideService;
    @Autowired
    private AmOrderImgService amOrderImgService;
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
    @Autowired
    private AmOrderLogService amOrderLogService;
    /**
     *
     * @auther: len
     * @date: 2018/7/26 16:36
     */
    @RequiresPermissions("order:amOrder:edit")
    @RequestMapping(value = "createOrder")
    @ResponseBody
    public Map createOrder(@RequestBody String req, HttpServletRequest request) {
        JSONObject reqJson = JSONObject.parseObject(req);

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
        String distribution = getInfo(reqJson, "distribution");

        // 支付方式
        String payType = getInfo(reqJson, "payType");

        // 备注
        String remarks = getInfo(reqJson, "remarks");

        // 优惠金额
        String discount = getInfo(reqJson, "discount");
        if ("".equals(discount)) {
            discount = "0";
        }

        Double preferential = Double.parseDouble(discount);

        // 运费
        String logisticsFee = getInfo(reqJson, "logisticsFee");
        if ("".equals(logisticsFee)) {
            logisticsFee = "0";
        }
        Double logistics = Double.parseDouble(logisticsFee);

        // 报价时间
        String quoteDate = reqJson.get("quoteTime").toString();
        String isEnjoy = null;
        if (reqJson.containsKey("isEnjoy")) {
            // 是否享受油补
            isEnjoy = reqJson.get("isEnjoy").toString();
        } else {
            isEnjoy = "0";
        }
        String oilSubsidy = null;
        if (reqJson.containsKey("oilSubsidy")) {
            // 油费补贴
            oilSubsidy = reqJson.get("oilSubsidy").toString();
        } else {
            oilSubsidy = "0";
        }

        // 登录帐号
        String userCode = reqJson.get("userCode").toString();
        AmOrder amOrder = new AmOrder();
        // 优惠码
        if (reqJson.containsKey("couponCode")) {
            String couponCode = reqJson.get("couponCode").toString();
            amOrder.setCouponCode(couponCode);
        }
        amOrder.setIsEnjoy(isEnjoy);
        amOrder.setOilSubsidy(Double.parseDouble(oilSubsidy));

        amOrder.setQuoteTime(DateUtils.parseDate(quoteDate));
        amOrder.setCustomerName(customerName);
        amOrder.setMobilePhone(phone);
        amOrder.setProvince(province);
        amOrder.setCity(city);
        amOrder.setRegion(region);
        amOrder.setDetailedAddress(address);
        amOrder.setDistribution(distribution);
        amOrder.setPayType(payType);
        amOrder.setPreferential(preferential);
        amOrder.setLogisticsFee(logistics);
        amOrder.setRemarks(remarks);
        Employee employee = new Employee();
        employee.setEmpCode(userCode);
        employee = employeeService.get(employee);
        if (employee != null) {
            amOrder.setOfficeCode(employee.getOffice().getOfficeCode());
            if ("1".equals(employee.getTreasure())) {
                amOrder.setDocumentType("零售订单");
            } else if ("0".equals(employee.getTreasure())) {
                amOrder.setDocumentType("新零售合作订单");
            }
        }
        amOrder.setDocumentStatus(CREATE);
        // 创建时间
        String time = DateUtils.getDateTime();
        amOrder.setCreateTime(time);
        String[] datetime = DateUtils.getDateTime().split("-");
        int a = Integer.parseInt(datetime[1]);
        if (a < 10) {
            datetime[1] = datetime[1].replace("0", "");
        }
        time = datetime[0] + "-" + datetime[1] + "-" + datetime[2];
        amOrder.setCreateTime(time);                // 创建时间
        amOrder.setDocumentCode(amSeqService.getOrderCode(PERFIX));
        // 创建人
        amOrder.setCreateBy(UserUtils.get(userCode).getUserName());

        amOrder.setIsNewRecord(true);

        Double totalPrice = new Double(0);
        // 明细
        List<AmOrderDetail> detailList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = JSON.parseArray(reqJson.get("goods").toString());
        } catch (NullPointerException e) {

        }
        Map<String, String> map = new HashMap<>();
        List<String> skuIdList = ListUtils.newArrayList();
        if (jsonArray != null && jsonArray.size() > 0) {
            boolean isLogin = k3connection.getConnection();
            if (!isLogin) {
                map.put("code", "900");
                map.put("msg", "服务异常，请稍后");
                map.put("data", "");
                return map;
            }

            try {

                for (int i = 0; i < jsonArray.size(); i++) {
                    AmOrderDetail amOrderDetail = new AmOrderDetail();

                    // 商品Id
                    String numId = jsonArray.getJSONObject(i).get("numId").toString();
                    TbProduct tbProduct = new TbProduct();
                    tbProduct.setNumIid(numId);
                    tbProduct = tbProductService.get(tbProduct);

                    // skuid
                    String skuId = jsonArray.getJSONObject(i).get("skuId").toString();
                    // 数量
                    String amount = null;
                    if ("".equals(jsonArray.getJSONObject(i).get("amount").toString()) || jsonArray.getJSONObject(i).get("amount").toString() == null) {
                        amount = "0";
                    } else {
                        amount = jsonArray.getJSONObject(i).get("amount").toString();
                    }
                    Long quantity = Long.valueOf(amount);

                    amOrderDetail.setSkuId(skuId);
                    skuIdList.add(skuId);
                    TbSku tbSku = new TbSku();
                    tbSku.setSkuId(Long.valueOf(skuId));
                    tbSku = tbSkuService.get(tbSku);
                    amOrderDetail.setStandPrice(Double.parseDouble(tbSku.getPrice()));  // 标准售价
                    amOrderDetail.setGoodsName(tbProduct.getTitle());                   // 商品名称
                    amOrderDetail.setSku(tbSku.getOuterId());
                    String result = null;
                    // K3表单名
                    String sFormId = "BD_SAL_PriceList";
                    // 2018/9/13
                    String content = "{\"FormId\":\"BD_SAL_PriceList\",\"FieldKeys\":\"FPrice\",\"FilterString\":\"FMaterialId.FNumber='" + tbSku.getOuterId() + "' and F_YF_Shop.FNAME='优梵艺术旗舰店' AND F_YF_EntryExpiryDateLong>'" + quoteDate + "' AND F_YF_EntryEffectiveDateLong<'" + quoteDate + "'AND FRowAuditStatus='A' AND FEntryForbidStatus='A'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";

                    result = InvokeHelper.ExecuteBillQuery(sFormId, content, POST_K3ClOUDRL);
                    JSONArray priceArray = JSONObject.parseArray(result);
                    String realPrice = priceArray.getJSONArray(0).get(0).toString();
                    Double price = new Double(0);
                    // 如果价格不为空
                    if (realPrice != null && !"[]".equals(realPrice)) {
                        price = Double.parseDouble(realPrice);
                        amOrderDetail.setPrice(price);
                    } else {
                        // 真实售价
                        price = Double.parseDouble(tbSku.getRealPrice());
                        amOrderDetail.setPrice(price);
                    }

                    // 店铺
                    String shop = jsonArray.getJSONObject(i).get("shop").toString();
                    if ("优梵艺术旗舰店".equals(shop)) {
                        shop = "0";
                    } else if ("优梵艺术家居旗舰店".equals(shop)) {
                        shop = "1";
                    }
                    amOrderDetail.setShop(shop);
                    // 金额
                    Double amountPrice = price * quantity;
                    amOrderDetail.setAmount(amountPrice);
                    // 规格
                    amOrderDetail.setSpec(tbSku.getProperties());

                    amOrderDetail.setQuantity(quantity);
                    amOrderDetail.setDocumentCode(amOrder);

                    totalPrice = NumberUtils.add(totalPrice, amountPrice);
                    detailList.add(amOrderDetail);
                }
            } catch (Exception e) {
                map.put("code", "900");
                map.put("msg", "服务异常，请稍后");
                map.put("data", "");
                amOrderLogService.insertLog(e, request);
                return map;
            }
        }
        // 商品总额
        amOrder.setTotalPrice(totalPrice);
        // 商品总额 + 物流费 - 油补
        Double atm = NumberUtils.sub(NumberUtils.add(totalPrice, logistics), Double.parseDouble(oilSubsidy));
        // 合计应收
        amOrder.setTotalFee(NumberUtils.sub(atm, preferential));
        if (detailList != null && detailList.size() > 0) {
            amOrder.setAmOrderDetailList(detailList);
        }
        List<AmOrderDiscount> discountList = ListUtils.newArrayList();
        if (reqJson.containsKey("discountList")) {
            JSONArray disArray = JSON.parseArray(reqJson.get("discountList").toString());
            for (int i = 0; i < disArray.size(); i++) {
                AmOrderDiscount amOrderDiscount = new AmOrderDiscount();
                // 获取优惠名称
                String offerName = disArray.getJSONObject(i).get("offerName").toString();
                // 获取优惠金额
                String offerAmount = disArray.getJSONObject(i).get("offerAmount").toString();
                amOrderDiscount.setDocumentCode(amOrder);
                amOrderDiscount.setDiscount(offerName);
                amOrderDiscount.setDiscountPrice(Double.parseDouble(offerAmount));
                amOrderDiscount.setIsNewRecord(true);
                discountList.add(amOrderDiscount);
            }
        }
        if (discountList != null && discountList.size() > 0) {
            amOrder.setAmOrderDiscountList(discountList);
        }
        return amOrderService.saveOrder(amOrder, skuIdList, userCode);
    }

    /**
     * 计算运费
     * @auther: len
     * @date: 2018/7/26 18:51
     */
    @RequiresPermissions("order:amOrder:edit")
    @RequestMapping(value = "calculate")
    @ResponseBody
    public Map calculateShippingCosts(@RequestBody String req, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject reqJson = JSONObject.parseObject(req);
            // 省
            String province = reqJson.get("province").toString();

            // 市
            String city = reqJson.get("city").toString();

            // 区
            String region = reqJson.get("region").toString();
            // 金额
            String price = getInfo(reqJson, "price");
            if ("".equals(price)) {
                price = "0";
            }
            // 配送方式
            String distribution = reqJson.get("distribution").toString();
            K3DiscountData concession = new K3DiscountData();
            concession.setProvince(province);
            concession.setCity(city);
            concession.setDistrict(region);
            concession.setDeliveryMethod(distribution);
            concession.setMoney(price);
            String info = JSONObject.toJSONString(concession);
            boolean isLogin = k3connection.getConnection();
            String money = "0.0";
            if (isLogin) {
                StringBuffer buffer = InvokeHelper.concession("calculateShippingCosts", info, POST_K3ClOUDRL);     // 计算运费
                JSONObject json = JSON.parseObject(buffer.toString());
                String isError = json.get("IsError").toString();
                if ("true".equals(isError)) {
                    map.put("code", "900");
                    map.put("msg", json.get("Message").toString());
                    map.put("data", "");
                    return map;
                } else if ("false".equals(isError)) {
                    try {
                        money = json.getJSONObject("Value").get("money").toString();
                    } catch (NullPointerException e) {
                        money = "0.0";
                    }
                }

            } else {
                map.put("code", "500");
                map.put("msg", "服务异常,请稍后");
                map.put("data", "");
                return map;
            }
            map.put("code", "200");
            map.put("msg", "运费计算成功");
            map.put("data", money);
            return map;
        } catch (Exception e) {
            map.put("code", "500");
            map.put("msg", "服务异常,请稍后");
            map.put("data", "");
            amOrderLogService.insertLog(e, request);
            return map;
        }
    }

    /**
     * 计算优惠
     * @auther: len
     * @date: 2018/7/26 19:15
     */
    @RequiresPermissions("order:amOrder:edit")
    @RequestMapping(value = "discount")
    @ResponseBody
    public Map discount (@RequestBody String req, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject reqJson = JSONObject.parseObject(req);
            // 获取报价时间
            String datetime = reqJson.get("quoteTime").toString();
            // 获取用户账号
            String userCode = reqJson.get("userCode").toString();
            // 其它优惠金额
            String wholeReducedSum = null;
            if (reqJson.containsKey("wholeReducedSum")) {
                wholeReducedSum = reqJson.get("wholeReducedSum").toString();
            }
            String isPostageDisscount = null;
            // 是否享受优惠
            if (reqJson.containsKey("isPostageDisscount")) {
                isPostageDisscount = reqJson.get("isPostageDisscount").toString();
            } else {
                isPostageDisscount = "0";
            }

            // 根据用户账号获取当前用户的treeNames
            String store = guideService.selectShop(userCode);
            JSONArray jsonArray = JSON.parseArray(reqJson.get("materialList").toString());
            List<VirtualQuotation> virtualQuotationList = new ArrayList<>();
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    VirtualQuotation virtualQuotation = new VirtualQuotation();
                    String materialNumber = jsonArray.getJSONObject(i).get("materialNumber").toString();        // 物料名称
                    String materialQty = jsonArray.getJSONObject(i).get("materialQty").toString();                // 数量
                    String shop = null;
                    if (jsonArray.getJSONObject(i).containsKey("shop")) {
                        shop = jsonArray.getJSONObject(i).get("shop").toString();                // 数量
                        virtualQuotation.setOtherShop(shop);
                    }
                    virtualQuotation.setMaterialNumber(materialNumber);
                    virtualQuotation.setMaterialQty(Long.valueOf(materialQty));
                    virtualQuotationList.add(virtualQuotation);
                }
            }
            Discount discount = new Discount();
            if (store != null) {
                discount.setStore(store);
            }
            Double wholeReduced = new Double(0);
            if (!"".equals(wholeReducedSum) && wholeReducedSum != null) {
                wholeReduced = Double.parseDouble(wholeReducedSum);
            }
            discount.setWholeReducedSum(NumberUtils.formatDouble(wholeReduced));
            discount.setShop("优梵艺术旗舰店");
            discount.setIsPostageDisscount(isPostageDisscount);
            discount.setActivityDate(datetime);
            discount.setMaterialList(virtualQuotationList);
            String favourable = JSONObject.toJSON(discount).toString();
            boolean isLogin = k3connection.getConnection();
            List<K3Discount> discountList = new ArrayList<>();
            List<SkuPrice> skuList = ListUtils.newArrayList();
            DiscountAndPrice discountAndPrice = new DiscountAndPrice();
            // 油费补贴
            Double oilSubsidy = new Double(0);
            if (isLogin) {

                StringBuffer buffer = InvokeHelper.concession("virtualQuotation", favourable, POST_K3ClOUDRL);     // 计算优惠
                JSONObject json1 = JSON.parseObject(buffer.toString());
                System.out.println(json1);
                String isError = json1.get("IsError").toString();
                if ("true".equals(isError)) {
                    map.put("code", "900");
                    map.put("msg", json1.get("Message").toString());
                    map.put("data", "");
                    return map;
                } else {
                    if (json1.getJSONObject("Value").get("StoreVirtualQuotationResponseList") != null) {
                        // 门店优惠
                        JSONArray storeInfo = JSON.parseArray(json1.getJSONObject("Value").get("StoreVirtualQuotationResponseList").toString());    // 获取json数组
                        if (storeInfo != null && storeInfo.size() > 0) {
                            for (int i = 0; i < storeInfo.size(); i++) {
                                // 油费补贴
                                String postageDisscount = storeInfo.getJSONObject(i).get("PostageDisscount").toString();
                                if (Double.parseDouble(postageDisscount) == 0) {
                                    K3Discount k3Discount = new K3Discount();
                                    String offerName = storeInfo.getJSONObject(i).get("offerName").toString();        // 获取名称
                                    String offerAmount = storeInfo.getJSONObject(i).get("offerAmount").toString();    // 获取优惠金额

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

                                    // 优惠金额=优惠金额+立减金额+副店券金额+副店优惠金额
                                    Double discounAmount = NumberUtils.add(Double.parseDouble(offerAmount), Double.parseDouble(subtractionAmmount));
                                    Double discountMoney = NumberUtils.add(NumberUtils.add(discounAmount, Double.parseDouble(viceStoreVouchers)), Double.parseDouble(discountAmount));
                                    // 优惠金额
                                    k3Discount.setOfferAmount(String.valueOf(discountMoney));
                                    k3Discount.setOfferName(offerName);
//                            k3Discount.setOfferAmount(offerAmount);
                                    discountList.add(k3Discount);

                                } else {
                                    // 油费补贴
                                    oilSubsidy = NumberUtils.add(oilSubsidy, Double.parseDouble(postageDisscount));
                                }

                            }
                        }
                    }
                    if (json1.getJSONObject("Value").get("VirtualQuotationOriginalGoodList") != null) {
                        JSONArray priceInfo = JSON.parseArray(json1.getJSONObject("Value").get("VirtualQuotationOriginalGoodList").toString());
                        if (priceInfo != null && priceInfo.size() > 0) {
                            for (int i = 0; i < priceInfo.size(); i++) {
                                SkuPrice skuPrice = new SkuPrice();
                                String materialNumber = priceInfo.getJSONObject(i).get("materialNumber").toString();
                                String price = priceInfo.getJSONObject(i).get("paymentGoodsPrice").toString();
                                skuPrice.setMaterialNumber(materialNumber);
                                skuPrice.setPrice(Double.parseDouble(price));
                                skuList.add(skuPrice);
                            }
                        }
                    }
                    discountAndPrice.setDiscountList(discountList);
                    discountAndPrice.setSkuPriceList(skuList);
                    discountAndPrice.setOilSubsidy(oilSubsidy);
                }
            } else {
                map.put("code", "500");
                map.put("msg", "服务异常,请稍后");
                map.put("data", "");
                return map;
            }
            map.put("code", "200");
            map.put("msg", "请求成功");
            map.put("data", discountAndPrice);
            return map;
        } catch (Exception e) {
            amOrderLogService.insertLog(e, request);
            map.put("code", "500");
            map.put("msg", "服务异常,请稍后");
            map.put("data", "");
            return map;
        }
    }

    @Autowired
    private TbTianmaoItemsService tbTianmaoItemsService;
    /**
     * 查询订单
     * @param
     */
    @RequiresPermissions("order:amOrder:view")
    @RequestMapping(value = "query")
    @ResponseBody
    public Map queryOrder(HttpServletRequest request, HttpServletResponse response, GuideOrder guide, String userCode) {
        Map<String, Object> map = new HashMap<>();
        if (UserUtils.get(userCode) == null) {
            map.put("code", "901");
            map.put("msg", "请检查用户帐号");
            return map;
        }
        guide.setUserName(userCode);
        Page<GuideOrder> page = guideService.findPage(new Page<GuideOrder>(request, response), guide);
        // 订单号
        List<String> orderList = ListUtils.newArrayList();
        for (int i = 0; i < page.getList().size(); i++) {
            orderList.add(page.getList().get(i).getDocumentCode());
        }
        List<GuideGoods> goodsList = guideService.getDetail(orderList);

        // 根据商品id获取主图图片
        List<TbItemImgs> itemImgList = tbTianmaoItemsService.getLastImg(ListUtils.extractToList(goodsList, "numId"));
        for (GuideOrder guideOrder : page.getList()) {
            // 根据订单号查出该订单的商品
            List<GuideGoods> guideGoodsList = goodsList.stream().filter(s ->s.getDocumentCode().equals(guideOrder.getDocumentCode())).collect(Collectors.toList());
            // 如果该订单有商品
            if (ListUtils.isNotEmpty(guideGoodsList)) {
                for (GuideGoods guideGoods : goodsList) {
                    // 取该商品选购的SKU的图片，若该SKU不存在图片，那么取商品主图的主图最后一张；
                    if (StringUtils.isNotEmpty(guideGoods.getSkuUrl())) {
                        guideGoods.setPicUrl(guideGoods.getSkuUrl());
                    } else {
                        Optional<TbItemImgs> optionalItemImg = itemImgList.stream().filter(s ->String.valueOf(s.getItemId()).equals(guideGoods.getNumId())).findFirst();
                        if (optionalItemImg.isPresent()) {
                            guideGoods.setSkuUrl(optionalItemImg.get().getUrl());
                        }
                    }
                }
                // 订单的商品
                guideOrder.setGoods(guideGoodsList);
            }
        }
        if (page.getList() != null && page.getList().size() >0) {
            map.put("code", "200");
            map.put("msg", "");
            map.put("data", page);
            return map;
        } else {
            if (guide.getNameOrPhone() != null) {
                map.put("code", "903");
                map.put("msg", "没有查询到符合条件的订单");
                map.put("data", "");
                return map;
            }
            map.put("code", "900");
            map.put("msg", "该导购没有订单");
            map.put("data", "");
            return map;
        }
    }

    /**
     * app订单确认
     * @auther: len
     * @date: 2018/8/1 17:26
     */
    @RequiresPermissions("order:amOrder:edit")
    @ResponseBody
    @RequestMapping(value = "confirm")
    public Map confirm (@RequestBody String req) {
        Map<String, Object> map = new HashMap<>();
        JSONObject reqJson = JSONObject.parseObject(req);
        // 用户编码
        String userCode = reqJson.get("userCode").toString();
        // 订单号
        String documentCode = reqJson.get("documentCode").toString();
        AmOrder amOrder = new AmOrder();
        amOrder.setDocumentCode(documentCode);
        amOrder = amOrderService.get(amOrder);
        if (amOrder == null) {
            map.put("code", "900");
            map.put("msg", "订单不存在");
            return map;
        }
        // 客户来源
        String cusFrom = getInfo(reqJson, "cusFrom");
        // 备注
        String remarks = getInfo(reqJson, "remarks");
        // 特权定金1
        String privilege1 = getInfo(reqJson, "privilege1");
        // 特权定金2
        String privilege2 = getInfo(reqJson, "privilege2");
        // 特权定金3
        String privilege3 = getInfo(reqJson, "privilege3");

        if (!"新零售合作订单".equals(amOrder.getDocumentType())) {
            if (!ParamentUntil.isBackString(privilege1)) {
                map.put("code", "901");
                map.put("msg", "请输入特权定金单号1");
                return map;
            }
            amOrder.setPrivilege1(privilege1);
            amOrder.setPrivilege2(privilege2);
            amOrder.setPrivilege3(privilege3);
        }
        // 客户来源
        amOrder.setCustomerSource(cusFrom);
        amOrder.setRemarks(remarks);
        if (CONFIRM.equals(amOrder.getDocumentStatus())){
            map.put("code", "902");
            map.put("msg", "订单是确认状态，不能重复确认");
            return map;
        }
        // 订单状态
        amOrder.setDocumentStatus(CONFIRM);
        // 确认时间
        amOrder.setConfirmDate(DateUtils.getDateTime());
        // 确认人
        amOrder.setConfirmBy(UserUtils.get(userCode).getUserName());
        amOrderService.saveData(amOrder);
        map.put("code", "200");
        map.put("msg", "订单确认成功");
        return map;
    }


    /**
     * app提交接口
     * @param documentCode
     * @param userCode
     */
    @RequiresPermissions("order:amOrder:deliver")
    @ResponseBody
    @RequestMapping(value = "submit")
    public Map submit (String documentCode, String userCode, HttpServletRequest request) throws Exception{
        Map<String, Object> map = new HashMap<>();
        AmOrder amOrder = new AmOrder();
        amOrder.setDocumentCode(documentCode);
        amOrder = amOrderService.get(amOrder);
        // 取价时间
        Date quoteTime = amOrder.getQuoteTime();
        // 取价时间
        if ("".equals(quoteTime) || quoteTime == null) {
            map.put("code", "900");
            map.put("msg", "请输入报价时间");
            return map;
        }
        if (!"新零售合作订单".equals(amOrder.getDocumentType())) {
            if ("".equals(amOrder.getPrivilege1()) || amOrder.getPrivilege1() == null) {
                map.put("code", "900");
                map.put("msg", "请输入特权定金单号");
                return map;
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
        String province = amOrder.getProvince();
        orders.setF_Province(province);
        // 市
        String city = amOrder.getCity();
        orders.setF_City(city);
        // 区
        String region = amOrder.getRegion();
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
            orders.setF_SubmitUserName(UserUtils.get(userCode).getUserName());
            // 创建时间
            orders.setF_CreatorTime(amOrder.getCreateTime());
            // 合计应收
            orders.setF_AllTotal(amOrder.getTotalFee());
//            // 优惠金额
//            orders.setF_FavorableTotal(amOrder.getPreferential());
            // 商品金额
            orders.setF_GoodsTotal(amOrder.getTotalPrice());
            // 物流费
            orders.setF_LogisticsFee(amOrder.getLogisticsFee());
            // 三包费
            orders.setF_SbFee(amOrder.getThreeCharges());
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
                amOrderDetail.setShop("0");
                shop = "优梵艺术旗舰店";
            } else {
                if ("0".equals(amOrderDetail.getShop())) {
                    shop = "优梵艺术旗舰店";
                } else if ("1".equals(amOrderDetail.getShop())) {
                    shop = "优梵艺术家具旗舰店";
                }
            }
            // 店铺
            detail.setF_GoodsStore(shop);
            detailList1.add(detail);
        }
        String order = JSONObject.toJSONString(orders);
        String details = JSONObject.toJSONString(detailList1);
        Map<String, String> maps = new HashMap<>();
        maps.put("order", order);
        maps.put("details", details);
        String info = null;
        if ("新零售合作订单".equals(amOrder.getDocumentType())) {
            info = HttpClientUtils.ajaxPost(K3NOREALURL, maps);
        } else {
            info = HttpClientUtils.ajaxPost(K3URL, maps);
        }

        JSONObject returnJson = JSONObject.parseObject(info);
        String message = returnJson.get("Message").toString();
        String flag = returnJson.get("IsError").toString();
        if ("false".equals(flag)) {
            amOrder.setSubmitDate(DateUtils.getDateTime());
            amOrder.setSubmitBy(UserUtils.get(userCode).getUserName());
            amOrder.setDocumentStatus(SUBMIT);

            if (StringUtils.isNotEmpty(amOrder.getCouponCode())) {
                amOrderService.updateWriteoff(amOrder);
            }
            amOrderService.saveData(amOrder);
            map.put("code", "200");
            map.put("msg", "提交优梵成功");
            return map;
        } else {
            if (message.contains("ErrorCode")) {
                JSONObject jsonObject = JSONObject.parseObject(message);
                String msg = null;
                try{
                    msg = jsonObject.getJSONObject("Errors").get("Message").toString();
                }catch (Exception e) {
                    msg = null;
                }
                map.put("msg", "不允许先收款后报价，请在K3报价单里删除该特权订金1：" + msg);
            } else {
                map.put("msg", message);
            }
            map.put("code", "900");
            return map;
        }
    }

    /**
     * 取消确认
     * @auther: len
     * @date: 2018/8/7 8:56
     */
    @RequiresPermissions("order:amOrder:edit")
    @ResponseBody
    @RequestMapping(value = "cancelConfirm")
    public Map cancelConfirm(String documentCode) {
        Map<String, Object> map = new HashMap<>();
        AmOrder amOrder = amOrderService.get(documentCode);
        if (amOrder == null) {
            map.put("code", "900");
            map.put("msg", "订单不存在");
            return map;
        }
        if (!CONFIRM.equals(amOrder.getDocumentStatus())) {
            map.put("code", "901");
            map.put("msg", "订单不是确认状态，不能取消确认!");
            return map;
        }
        // 取消确认时状态变为创建
        amOrder.setDocumentStatus(CREATE);
        // 清空创建时间创建人
        amOrder.setConfirmDate("");
        amOrder.setConfirmBy("");
        return amOrderService.saveHeader(amOrder);
    }
    /**
     * 订单详情接口
     * @param
     * @return
     */
    @ResponseBody
    @RequiresPermissions("order:amOrder:view")
    @RequestMapping(value = "form")
    public Map form (String documentCode) {
        Map<String, Object> map = new HashMap<>();
        AmOrder amOrder = amOrderService.get(documentCode);
        Double logisticsFee = amOrder.getLogisticsFee();
        if (logisticsFee == null || "".equals(logisticsFee)) {
            logisticsFee = new Double(0);
        }
        Double threeCharges = amOrder.getThreeCharges();
        if (threeCharges == null || "".equals(threeCharges)) {
            threeCharges = new Double(0);
        }
        amOrder.setFreight(NumberUtils.add(logisticsFee, threeCharges));    // 运费
        List<AmOrderDetail> detailList = amOrderService.getDetail(documentCode);
        for (AmOrderDetail amOrderDetail : detailList) {
            if (StringUtils.isNotEmpty(amOrderDetail.getSkuUrl())) {
                amOrderDetail.setPicUrl(amOrderDetail.getSkuUrl());
            } else {
                // 如果为空取商品详情图最后一张作为主图
                String img = tbTianmaoItemsService.getLastImg(amOrderDetail.getNumId());
                amOrderDetail.setPicUrl(img);
            }
        }
        amOrder.setAmOrderDetailList(detailList);
        if (amOrder != null) {
            map.put("code", "200");
            map.put("msg", "");
            map.put("data", amOrder);
        } else {
            map.put("code", "900");
            map.put("msg", "请求失败");
        }
        return map;
    }
    /**
     * json数据根据key获取value 不存在key设置为空字符串
     * @param json
     * @param key
     * @return
     */
    public String getInfo (JSONObject json, String key) {
        String value = "";
        try {
            value = json.get(key).toString();
        }catch (NullPointerException e) {

        }
        return value;
    }

    /**
     * 订单上传凭证图片
     */
    @RequiresPermissions("order:amOrder:edit")
    @ResponseBody
    @RequestMapping("uploadCredentials")
    public ReturnInfo uploadCredentials (@RequestBody String req, HttpServletRequest request) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(req);
            // 订单编号
            String documentCode = jsonObject.get("documentCode").toString();
            AmOrderImg orderImg = new AmOrderImg();
            orderImg.setImgStatus("0");
            orderImg.setDocumentCode(documentCode);
            List<AmOrderImg> orderImgList = amOrderImgService.findList(orderImg);
            // 此订单图片的数量
            int initialSize = orderImgList.size();
            // 图片列表
            JSONArray jsonArray = jsonObject.getJSONArray("imgList");
            List<AmOrderImg> imgNewList = ListUtils.newArrayList();
            List<String> imgList = ListUtils.newArrayList();
            for (int i = 0; i < jsonArray.size(); i++) {
                // 图片状态(0表示正常1标识删除)
                String imgStatus = jsonArray.getJSONObject(i).get("imgStatus").toString();
                String imgUrl = jsonArray.getJSONObject(i).get("imgUrl").toString();
                if (AmOrderImg.STATUS_NORMAL.equals(imgStatus)) {
                    AmOrderImg amOrderImg = new AmOrderImg();
                    // 图片路径
                    amOrderImg.setImgUrl(imgUrl);
                    // 图片code
                    amOrderImg.setImgCode(amSeqService.getCode("IMG"));
                    // 订单编号
                    amOrderImg.setDocumentCode(documentCode);
                    // 状态
                    amOrderImg.setImgStatus(AmOrderImg.STATUS_NORMAL);
                    // 创建时间
                    amOrderImg.setCreateDate(new Date());
                    amOrderImg.setIsNewRecord(true);
                    imgNewList.add(amOrderImg);
                } else if (AmOrderImg.STATUS_DELETE.equals(imgStatus)) {
                    String imgCode = jsonArray.getJSONObject(i).get("imgCode").toString();
                    imgList.add(imgCode);
                }
            }
            // 新增的图片数量
            int newImgSize = imgNewList.size();
            // 删除的图片数量
            int delImgSize = imgList.size();
            if (initialSize - delImgSize + newImgSize > 6) {
                return ReturnDate.error(901, "最多只能上传6张图片");
            }
            if (ListUtils.isNotEmpty(imgNewList)) {
                // 如果有状态为0的表示新增
                amOrderImgService.insertBatch(imgNewList);
            }
            if (ListUtils.isNotEmpty(imgList)) {
                // 如果有状态为1的表示删除
                amOrderImgService.updateBatch(imgList);
            }
            return ReturnDate.success();
        }catch (Exception e) {
            amOrderLogService.insertLog(e, request);
            return ReturnDate.error(500, "服务异常,请稍后");
        }
    }

    /**
     * 获取凭证图片
     * @param documentCode
     * @return
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("getCredentials")
    public ReturnInfo getCredentials(String documentCode) {
        AmOrderImg amOrderImg = new AmOrderImg();
        amOrderImg.setDocumentCode(documentCode);
        amOrderImg.setImgStatus("0");
        // 根据订单编号查询状态为0的图片信息
        List<AmOrderImg> imgList = amOrderImgService.findList(amOrderImg);
        return ReturnDate.success(imgList);
    }

    /**
     * 获取店铺
     * @return
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("getShop")
    public ReturnInfo getShop() {
        String userCode = UserUtils.getUser().getUserCode();
        // 根据用户账号获取当前用户的treeNames
        String shop = guideService.selectShop(userCode);
        JSONObject json = new JSONObject();
        json.put("shop", shop);
        json.put("userName", UserUtils.getUser().getUserName());
        return ReturnDate.success(JSONObject.parseObject(json.toString()));
    }


    /**
     * 引流到店客户
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("drainageCustomer")
    public ReturnInfo drainageCustomer (String shop, String flag, Integer pageNo, Integer pageSize) throws Exception{
        boolean isLogin = k3connection.getConnection();
        if (!isLogin) {
            return ReturnDate.error(500, "服务异常,请稍后");
        }
        // 表单名
        String formId = "BD_Customer";
        // k3客户资料标准api
        String content = "{\"FormId\":\""+ formId +"\",\"FieldKeys\":\"FNumber,FName,F_YF_ShoppingGuide\",\"FilterString\":\"F_YF_ShoppingGuide LIKE '%"+ shop +"%'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String result = InvokeHelper.ExecuteBillQuery(formId, content, POST_K3ClOUDRL);
        JSONArray jsonArray = JSONArray.parseArray(result);
        int num = 0;
        List<LineDownRegister> downRegisterList = ListUtils.newArrayList();
        List<SpeedProgress> speedProgressList = ListUtils.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray array = JSONArray.parseArray(jsonArray.get(i).toString());
            // 导购宝账号/供销账号
            String guideCode = array.get(2).toString();
            // k3店铺编码
            String shopCode =  array.get(0).toString();
            //
            String [] shops = guideCode.trim().split("\r\n");
            boolean inFlag = StringUtils.inString(shop, shops);
            if (inFlag) {
                String obj = null;
                if ("0".equals(flag)) {
                    obj = "{\"FormId\":\"YF_SAL_LineDownRegister\",\"FieldKeys\":\"FBillNo,F_YF_TrackUserId\",\"FilterString\":\"FCreateDate>'2019-01-01' and FDocumentStatus = 'C' and F_YF_ReservationShop.FNUMBER='"+ shopCode +"'\",\"OrderString\":\"FCreateDate\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
                } else {
                    obj = "{\"FormId\":\"YF_SAL_LineDownRegister\",\"FieldKeys\":\"FBillNo,F_YF_TrackUserId,FCreateDate,FCustId.FNAME,F_YF_Phone,F_YF_TrackDatetime,F_YF_Schedule,F_YF_TrackUserId.FNAME,FID\",\"FilterString\":\"FCreateDate>'2019-01-01' and FDocumentStatus = 'C' and F_YF_ReservationShop.FNUMBER='"+ shopCode +"'\",\"OrderString\":\"FCreateDate DESC\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
                }

                String resultShop = InvokeHelper.ExecuteBillQuery("YF_SAL_LineDownRegister", obj, POST_K3ClOUDRL);
                // 如果返回[[[]]]或者[]说明不存在符合条件的数据
                if ("[[[]]]".equals(resultShop) || "[]".equals(resultShop)) {
                    if ("0".equals(flag)) {
                        return ReturnDate.success(num);
                    } else {
                        return ReturnDate.success(ListUtils.newArrayList());
                    }

                }
                JSONArray shopArray = JSONArray.parseArray(resultShop);
                num = 0;

                for (int j = 0; j < shopArray.size(); j++) {
                    JSONArray json = JSONArray.parseArray(shopArray.get(j).toString());
                    String trackUserId = null;
                    try {
                        trackUserId = json.get(1).toString();
                    } catch (NullPointerException e) {
                    }
                    // 0代表点我的时候的操作 只计算数量
                    if (StringUtils.isEmpty(trackUserId)) {

                        if ("0".equals(flag)) {
                            num++;
                        } else {
                            getLineDownRegister(downRegisterList, json);
                        }
                    }
                    if (StringUtils.isNotEmpty(trackUserId) && "1".equals(flag)){
                        getLineDownRegister(downRegisterList, json);
                        // 跟踪信息
                        SpeedProgress speedProgress = new SpeedProgress();
                        // 单号
                        speedProgress.setFBillNo(json.get(0).toString());
                        // 跟进时间
                        String trackDatetime = json.get(5).toString().replace("T", " ");
                        if (trackDatetime.contains(".")) {
                            trackDatetime = trackDatetime.substring(0, trackDatetime.indexOf("."));
                        }
                        speedProgress.setFTrackDatetime(trackDatetime);
                        // 进度
                        speedProgress.setFSchedule(json.get(6).toString());
                        // 跟进人
                        speedProgress.setFTrackUserName(json.get(7).toString());
                        // 跟进人id
                        speedProgress.setFTrackUserId(json.get(1).toString());
                        speedProgressList.add(speedProgress);
                    }
                }

            }
        }
        // 点我的的时候只返回不存在跟踪记录的客户个数
        if ("0".equals(flag)) {
            return ReturnDate.success(num);
        } else {
            int size = downRegisterList.size();
            // 点查看的时候返回客户列表和进度
            if (ListUtils.isNotEmpty(downRegisterList)) {
                // 如果页数*页码大于总条数 查到最后一条
                if (pageSize * pageNo > size) {
                    downRegisterList = downRegisterList.subList((pageNo - 1) * pageSize, size);
                } else {
                    downRegisterList = downRegisterList.subList((pageNo - 1) * pageSize, pageSize * pageNo);
                }
                for (LineDownRegister lineDownRegister : downRegisterList) {
                    lineDownRegister.setSpeedProgressList(speedProgressList.stream().filter(s ->s.getFBillNo().equals(lineDownRegister.getFBillNo())).collect(Collectors.toList()));
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count", size);
            jsonObject.put("downRegisterList", downRegisterList);
            return ReturnDate.success(jsonObject);
        }
    }

    public void getLineDownRegister(List<LineDownRegister> downRegisterList, JSONArray json) {

        LineDownRegister lineDownRegister = new LineDownRegister();
        // 单号
        String billNo = json.get(0).toString();
        List list = downRegisterList.stream().filter(s ->s.getFBillNo().equals(billNo)).collect(Collectors.toList());
        if (ListUtils.isEmpty(list)) {
            lineDownRegister.setFBillNo(json.get(0).toString());
            // fid
            lineDownRegister.setFid(json.get(8).toString());
            // 创建时间
            String createDate = json.get(2).toString().replace("T", " ");
            if (createDate.contains(".")) {
                createDate = createDate.substring(0, createDate.indexOf("."));
            }

            lineDownRegister.setFCreateDate(createDate);
            // 客户名称
            lineDownRegister.setFCustName(json.get(3).toString());
            // 移动电话
            lineDownRegister.setFPhone(json.get(4).toString());

            downRegisterList.add(lineDownRegister);

        }

    }

    /**
     * 查询/核销优惠码接口
     * @param couponCode 优惠码
     * 根据优惠码获取赠送状态
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("checkStatus")
    public ReturnInfo checkStatus(String couponCode) throws Exception{
        boolean isLogin = k3connection.getConnection();
        if (!isLogin) {
            return ReturnDate.error(500, "服务异常,请稍后");
        }
        String content = "{\"FormId\":\"YF_SAL_LineDownRegister\",\"FieldKeys\":\"FCustId.FNAME,F_YF_GiftStatus,F_YF_StoreGuide,FID\",\"FilterString\":\"F_YF_PromoCode='"+ couponCode +"'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String resultStatus = InvokeHelper.ExecuteBillQuery("YF_SAL_LineDownRegister", content, POST_K3ClOUDRL);

        if ("[[[]]]".equals(resultStatus) || "[]".equals(resultStatus)) {
            return ReturnDate.success(10012, "不存在优惠码", null);
        }
        JSONArray jsonArray = JSONArray.parseArray(resultStatus);

        JSONArray json = JSONArray.parseArray(jsonArray.get(0).toString());
        // 赠送状态
        String giftStatus = json.get(1).toString();
        JSONObject object = new JSONObject();
        // 客户名
        String custName = json.get(0).toString();
        object.put("custName", custName);
        // 门店导购员
        String storeGuide = json.get(2).toString();
        // 未赠送
        String fid = json.get(3).toString();
        if (!"B".equals(giftStatus)) {
            object.put("fid", fid);
            return ReturnDate.success(10010, "", object);
        } else {
            object.put("storeGuide", storeGuide);
            object.put("couponCode", couponCode);
            return ReturnDate.success(10011, "", object);
        }
    }


    /**
     * 领取赠品后更新赠品状态和赠品领取时间
     * @param fid
     * @return
     * @throws Exception
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("addSchedule")
    public ReturnInfo addSchedule(String fid, String schedule) throws Exception{
        boolean isLogin = k3connection.getConnection();
        if (!isLogin) {
            return ReturnDate.error(500, "服务异常,请稍后");
        }
        // 门店
        String shop = guideService.selectShop(UserUtils.getUser().getUserCode());
        String date = DateUtils.formatDateTime(new Date());
        date = date.replace(" ","T");
        String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"false\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"ValidateFlag\":\"True\",\"NumberSearch\":\"True\",\"InterationFlags\":\"\",\"IsAutoSubmitAndAudit\":\"false\",\"Model\":{\"FID\":\""+ fid +"\",\"F_YF_ScheduleEntity\":[{\"F_YF_Schedule\":\""+ schedule +"\",\"F_YF_TrackDatetime\":\""+ date +"\",\"F_YF_TrackStore\":\""+ shop +"\",\"F_YF_Guide\":\""+ UserUtils.getUser().getUserName()+"\"}]}";
        StringBuffer buffer1 = InvokeHelper.Save("YF_SAL_LineDownRegister", saveParam, POST_K3ClOUDRL);
        boolean isSuccess = isSuccess(buffer1);
        if (isSuccess) {
            return ReturnDate.success();
        } else {
            return ReturnDate.error(500, "服务异常");
        }
    }

    /**
     * 领取赠品后更新赠品状态和赠品领取时间
     * @param fid
     * @return
     * @throws Exception
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("updateStatus")
    public ReturnInfo update(String fid) throws Exception{
        boolean isLogin = k3connection.getConnection();
        if (!isLogin) {
            return ReturnDate.error(500, "服务异常,请稍后");
        }
        String date = DateUtils.formatDateTime(new Date());
        date = date.replace(" ","T");
        String saveParam = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"True\",\"ValidateFlag\":\"True\",\"NumberSearch\":\"True\",\"InterationFlags\":\"\",\"IsAutoSubmitAndAudit\":\"false\",\"Model\":{\"FID\":\""+ fid +"\",\"F_YF_GIFTSTATUS\":\"B\",\"F_YF_GetGiftTime\":\""+ date +"\"}";
        StringBuffer buffer1 = InvokeHelper.Save("YF_SAL_LineDownRegister", saveParam, POST_K3ClOUDRL);
        boolean isSuccess = isSuccess(buffer1);
        if (isSuccess) {
            return ReturnDate.success();
        } else {
            return ReturnDate.error(500, "服务异常");
        }
    }

    /**
     * 查询优惠码是否核销
     * @param couponCode
     */
    @RequiresPermissions("order:amOrder:view")
    @ResponseBody
    @RequestMapping("checkWriteoff")
    public ReturnInfo checkWriteoff(String couponCode) throws Exception {
        for (int i = 0; i < couponCode.length(); i++) {
            if (Character.isLowerCase(couponCode.charAt(i))) {
                return ReturnDate.error(10013, "字母不能输入小写");
            }
        }
        boolean isLogin = k3connection.getConnection();
        if (!isLogin) {
            return ReturnDate.error(500, "服务异常,请稍后");
        }
        String content = "{\"FormId\":\"YF_SAL_LineDownRegister\",\"FieldKeys\":\"FCustId.FNAME,F_YF_WRITEOFFSTATUS,FID\",\"FilterString\":\"F_YF_PromoCode='" + couponCode + "'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String resultStatus = InvokeHelper.ExecuteBillQuery("YF_SAL_LineDownRegister", content, POST_K3ClOUDRL);

        if ("[[[]]]".equals(resultStatus) || "[]".equals(resultStatus)) {
            return ReturnDate.success(10012, "不存在优惠码", null);
        }
        JSONArray jsonArray = JSONArray.parseArray(resultStatus);
        JSONArray json = JSONArray.parseArray(jsonArray.get(0).toString());
        // 核销状态
        String writeoffStatus = json.get(1).toString();
        // 如果已核销
        if ("1".equals(writeoffStatus)) {
            return ReturnDate.success(10010, "该优惠码已使用", null);
        } else {
            AmOrder amOrder = new AmOrder();
            amOrder.setCouponCode(couponCode);
            List<AmOrder> amOrderList = amOrderService.findList(amOrder);
            if (ListUtils.isNotEmpty(amOrderList)) {
                return ReturnDate.success(10011, "请核对优惠码并且删除已使用该优惠码的历史订单", null);
            } else {
                return ReturnDate.success();
            }
        }
    }

    /**
     * 获取保存信息
     * @param buffer
     * @return
     */
    public boolean isSuccess (StringBuffer buffer) {
        JSONObject json = JSON.parseObject(buffer.toString());
        String responseStatus = json.getJSONObject("Result").get("ResponseStatus").toString();
        JSONObject json1 = JSON.parseObject(responseStatus);
        boolean isSuccess = Boolean.parseBoolean(json1.get("IsSuccess").toString());
        return isSuccess;
    }


}
