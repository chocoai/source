/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;


import com.alibaba.fastjson.*;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.order.service.AmOrderLogService;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.tianmao.dao.DaoGouDAO;
import com.jeesite.modules.asset.tianmao.dao.TbSkuDao;
import com.jeesite.modules.asset.tianmao.dao.TbSkuK3NameDao;
import com.jeesite.modules.asset.tianmao.entity.*;
import com.jeesite.modules.asset.tianmao.service.TbLogService;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 同步淘宝商品列表Controller
 * @author Jace
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbProduct")
public class TbProductController extends BaseController {

	@Autowired
	private TbProductService tbProductService;
	@Autowired
	private TbTianmaoItemsService tbTianmaoItemsService;
	@Autowired
	private TbSkuService tbSkuService;
	@Autowired
    private DaoGouDAO daoGouDAO;

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TbProductController.class);
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProduct get(String numIid, boolean isNewRecord) {
		return tbProductService.get(numIid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:tbProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbProduct tbProduct, Model model) {
		model.addAttribute("tbProduct", tbProduct);
		return "asset/tianmao/tbProductList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tianmao:tbProduct:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProduct> listData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
		Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:tbProduct:view")
	@RequestMapping(value = "form")
	public String form(TbProduct tbProduct, Model model) {
		String approveStatus = tbProduct.getApproveStatus();
		//TbProduct tbProduct1=tbProductService.get(tbProduct.getNumIid());
		if (approveStatus != null && !"".equals(approveStatus)) {
			if (approveStatus.equals("instock")) {
				approveStatus = "库存";
			} else {
				approveStatus = "在售";
			}
		}
		tbProduct.setApproveStatus(approveStatus);
		TbTianmaoItems tbTianmaoItems = tbTianmaoItemsService.get(tbProduct.getNumIid());
		List<TbSku> tbSkuList = tbSkuService.getSku(Long.valueOf(tbProduct.getNumIid()));
		tbProduct.setTbSkuList(tbSkuList);
		//tbProduct.setProcategoryCode(tbProduct1.getProcategoryCode());
		//tbProduct.setProseriesCode(tbProduct1.getProseriesCode());
        model.addAttribute("tbProduct", tbProduct);
        if(tbTianmaoItems==null){
            return "asset/tianmao/tbProductForm";
        }
        Item item= JSONObject.parseObject(tbTianmaoItems.getBody(),Item.class);
        model.addAttribute("item", item);
		return "asset/tianmao/tbProductForm";
	}

	/**
	 * 保存同步淘宝商品列表
	 */
	@RequiresPermissions("tianmao:tbProduct:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProduct tbProduct) {
		String str[]=tbProduct.getNumIid().split(",");
		tbProductService.updateProductCategory(tbProduct.getProcategoryCode(),tbProduct.getProseriesCode(),str[0]);
		return renderResult(Global.TRUE, "保存同步淘宝商品列表成功！");
	}
/*	public String save(@Validated TbProduct tbProduct) {
        if("在售".equals(tbProduct.getApproveStatus())){
            tbProduct.setApproveStatus("onsale");
        }else {
            tbProduct.setApproveStatus("instock");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
		User user = UserUtils.getUser();
		System.out.println("获取用户名："+ user.getUserName());
		List<TbSku> tbSkuList = tbProduct.getTbSkuList();
		//循环遍历skuList并对比sku的字段值
		for(TbSku tbSku: tbSkuList){
			String outerId = tbSku.getOuterId();
			//K3物料名称，是否更新
            TbSkuK3Name tbSkuK3Name = tbSkuK3NameDao.selectByOuterId(outerId);
			if( tbSkuK3Name!=null && !tbSkuK3Name.getSkuName().equals(tbSku.getSkuName())){
                LOGGER.info("K3物料名称原值【"+tbSkuK3Name.getSkuName()+"】,新值【"+tbSku.getSkuName()+"】");
                String describe = "K3物料名称原值【"+tbSkuK3Name.getSkuName()+"】,新值【"+tbSku.getSkuName()+"】";
                TbLog tbLog = new TbLog();
                tbLog.setDescribe(describe);
                tbLog.setSku(tbSku.getOuterId());
                tbLog.setSkuId(tbSku.getSkuId().toString());
                tbLog.setUser(user.getUserName());
                tbLog.setType("门店下单");
                tbLog.setTime(time);
                tbLogService.save(tbLog);
//                tbSkuK3NameDao.updateK3Name(outerId,tbSku.getSkuName()); //保存
            }
            //SKU是否更新
            TbSku tbSku2 = tbSkuService.get(tbSku);
            if(tbSku2!=null && !tbSku2.getOuterId().equals(tbSku.getOuterId())){
                LOGGER.info("SKU原值【"+tbSku2.getOuterId()+"】,新值【"+tbSku.getOuterId()+"】");
                String describe = "SKU原值【"+tbSku2.getOuterId()+"】,新值【"+tbSku.getOuterId()+"】";
                TbLog tbLog = new TbLog();
                tbLog.setDescribe(describe);
                tbLog.setSku(tbSku.getOuterId());
                tbLog.setSkuId(tbSku.getSkuId().toString());
                tbLog.setUser(user.getUserName());
                tbLog.setType("门店下单");
                tbLog.setTime(time);
                tbLogService.save(tbLog);
            }
            //标准售价是否更新
            assert tbSku2 != null;
            if(!tbSku2.getPrice().equals(tbSku.getPrice())){
                LOGGER.info("标准售价原值【"+tbSku2.getPrice()+"】,新值【"+tbSku.getPrice()+"】");
                String describe = "标准售价原值【"+tbSku2.getPrice()+"】,新值【"+tbSku.getPrice()+"】";
                TbLog tbLog = new TbLog();
                tbLog.setDescribe(describe);
                tbLog.setSku(tbSku.getOuterId());
                tbLog.setSkuId(tbSku.getSkuId().toString());
                tbLog.setUser(user.getUserName());
                tbLog.setType("门店下单");
                tbLog.setTime(time);
                tbLogService.save(tbLog);
            }
            //真实售价是否更新
            if(!tbSku2.getRealPrice().equals(tbSku.getRealPrice())){
                LOGGER.info("真实售价原值【"+tbSku2.getRealPrice()+"】,新值【"+tbSku.getRealPrice()+"】");
                String describe = "真实售价原值【"+tbSku2.getRealPrice()+"】,新值【"+tbSku.getRealPrice()+"】";
                TbLog tbLog = new TbLog();
                tbLog.setDescribe(describe);
                tbLog.setSku(tbSku.getOuterId());
                tbLog.setSkuId(tbSku.getSkuId().toString());
                tbLog.setUser(user.getUserName());
                tbLog.setType("门店下单");
                tbLog.setTime(time);
                tbLogService.save(tbLog);
            }
            //商品规格是否更新
            if (!tbSku2.getPropertiesName().equals(tbSku.getPropertiesName())){
                LOGGER.info("商品规格原值【"+tbSku2.getPropertiesName()+"】,新值【"+tbSku.getPropertiesName()+"】");
                String describe = "商品规格原值【"+tbSku2.getPropertiesName()+"】,新值【"+tbSku.getPropertiesName()+"】";
                TbLog tbLog = new TbLog();
                tbLog.setDescribe(describe);
                tbLog.setSku(tbSku.getOuterId());
                tbLog.setSkuId(tbSku.getSkuId().toString());
                tbLog.setUser(user.getUserName());
                tbLog.setType("门店下单");
                tbLog.setTime(time);
                tbLogService.save(tbLog);
            }
		}

		tbProductService.save(tbProduct);
		return renderResult(Global.TRUE, "保存同步淘宝商品列表成功！");
	}*/
	
	/**
	 * 停用同步淘宝商品列表
	 */
	@RequiresPermissions("tianmao:tbProduct:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbProduct tbProduct) {
		tbProduct.setStatus(TbProduct.STATUS_DISABLE);
		tbProductService.updateStatus(tbProduct);
		return renderResult(Global.TRUE, "停用同步淘宝商品列表成功");
	}
	
	/**
	 * 启用同步淘宝商品列表
	 */
	@RequiresPermissions("tianmao:tbProduct:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbProduct tbProduct) {
		tbProduct.setStatus(TbProduct.STATUS_NORMAL);
		tbProductService.updateStatus(tbProduct);
		return renderResult(Global.TRUE, "启用同步淘宝商品列表成功");
	}



	//=======================================================================================
    /**
     * 查看编辑表单
     */
    @RequiresPermissions("tianmao:tbProduct:view")
    @RequestMapping(value = "formDaoGou")
    public Map formDaoGou(TbProduct tbProduct) {
        String approveStatus = tbProduct.getApproveStatus();
        if (approveStatus != null && !"".equals(approveStatus)) {
            if ("instock".equals(approveStatus)) {
                approveStatus = "库存";
            } else {
                approveStatus = "在售";
            }
        }
        tbProduct.setApproveStatus(approveStatus);
        TbTianmaoItems tbTianmaoItems = tbTianmaoItemsService.get(tbProduct.getNumIid());
         List<TbSku> tbSkuList = daoGouDAO.getSkuList(tbProduct.getNumIid());
        for (TbSku tbSku : tbSkuList) {
        	if (tbSku.getPreSale() == null) {
        		tbSku.setPreSale("");
			}
		}
        tbProduct.setTbSkuList(tbSkuList);
//        String text = JSONObject.toJSONString(tbProduct);
//        System.out.println(text);
//        JSONObject jsonTbProduct= JSONObject.parseObject(text);
        String text = JsonMapper.toJson(tbProduct);
        JSONObject jsonTbProduct= JSONObject.parseObject(text);
        Item item= JSONObject.parseObject(tbTianmaoItems.getBody(),Item.class);
        String descModules = item.getDescModules();
		List<ItemImg> itemImgs = item.getItemImgs();
		jsonTbProduct.put("descModules",descModules);
		jsonTbProduct.put("itemImgs",itemImgs);

        Map<String,Object> dataMap = new HashMap<>(20);
        dataMap.put("item",jsonTbProduct);
//        dataMap.put("item",item);

        Map<String,Object> map1 = new HashMap<>(20);
        map1.put("code",200);
        map1.put("msg","请求成功");
        map1.put("data",dataMap);

        return map1;
    }

    /**
     * 查询导购列表数据
     */
    @RequiresPermissions("tianmao:tbProduct:view")
    @RequestMapping(value = "listDaoGou")
    @ResponseBody
    public Map listDataDaoGou(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
//        Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct);
//        List<TbProduct> list = page.getList();
//        //获取sku中最低实际售价
//        for(int k=0;k<list.size();k++){
//        	//只返回在售商品（instock-库存，onsale-在售）
//        	if("instock".equals(list.get(k).getApproveStatus())){
//				list.remove(k);
//				continue;
//			}
//			//返回sku中的最低实际售价
//            TbSku tbSku = tbSkuDao.getSkuPrice(Long.valueOf(list.get(k).getNumIid()));
//            if(tbSku!=null){
//                list.get(k).setPrice(tbSku.getRealPrice());
//            }
//            //商品的所有sku
//			List<TbSku> tbSkuList = tbSkuService.getSku(Long.valueOf(list.get(k).getNumIid()));
//            for(int i=0;i<tbSkuList.size();i++){
//				if(tbSkuList.get(i).getQuantity()==0){
//					tbSkuList.remove(i);
//				}
//			}
//            list.get(k).setTbSkuList(tbSkuList);
//        }
        Page<TbProduct> page = new Page<>();
        String page1 = request.getParameter("pageNo");
        int pageNo = Integer.parseInt(page1);
        int count = daoGouDAO.getCountByOnsale("onsale");
        List<TbProduct> tbProductList = daoGouDAO.getTbProductList((pageNo-1)*50,50);
        for (TbProduct aTbProductList : tbProductList) {
            List<TbSku> tbSkuList = daoGouDAO.getSkuList(aTbProductList.getNumIid());
            aTbProductList.setTbSkuList(tbSkuList);
        }

        page.setList(tbProductList);
        page.setCount(count);
        page.setPageNo(pageNo);

        Map<String,Object> map = new HashMap<>(20);
        map.put("code",200);
        map.put("msg","请求成功");
        map.put("data",page);
        return map;
    }

    @Autowired
	private K3connection k3connection;

    private static Boolean flag = true;
    @Autowired
    private AmOrderLogService amOrderLogService;

	/**
	 * 根据sku（物料编码）获取k3仓库库存
	 */
	@ResponseBody
//	@RequiresPermissions("tianmao:tbProduct:view")
	@RequestMapping("inventoryStockQueryReport")
	public ReturnInfo getInventoryStockQueryReport (String outerId, HttpServletRequest request){
		try {
			// 登录K3
			boolean isLogin = k3connection.getConnection();
			if (!isLogin) {
				return ReturnDate.error(10002, "服务异常");
			}
			// 请求组装Bom报表查询是够是组合商品
			StringBuffer stringBuffer = InvokeHelper.concession("materialGetBomSonMaterial", outerId, K3Config.K3ClOUDRL);
			JSONObject bomJson = JSONObject.parseObject(stringBuffer.toString());
			String isErr = bomJson.get("IsError").toString();
			// 如果返回错误说明再组合Bom表中查不到这个物料 那么直接用这个物料去仓库库存报表查询
			if ("true".equals(isErr)) {
				return noSubitem(outerId);
			} else {
				// 如果返回false说明在组合bom表可以查询到
				String value = bomJson.get("Value").toString();
				JSONArray jsonArray = JSONArray.parseArray(value);
				String fHaveSon = jsonArray.getJSONObject(0).get("FHaveSon").toString();
				// N代表没子项物料
				if ("N".equals(fHaveSon)) {
					return noSubitem(outerId);
				} else {
					// Y代表有子项
					// 父项物料名称
					String materialName = jsonArray.getJSONObject(0).get("FParentMaterialName").toString();
					return haveSubitem(jsonArray, materialName, outerId);
				}
			}
		} catch (Exception e) {
			amOrderLogService.insertLog(e, request);
			return ReturnDate.error(10004, "服务异常");
		}
	}

	/**
	 * 有子项物料时的操作
	 * @param MaterialNoArray
	 * @param materialName
	 * @param materialCode
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo haveSubitem(JSONArray MaterialNoArray, String materialName, String materialCode) throws Exception{
		List<String> expectedDateList = ListUtils.newArrayList();
		List<String> purchaseCycleList = ListUtils.newArrayList();
		List<String> list = ListUtils.newArrayList();
		for (int i = 0; i < MaterialNoArray.size(); i++) {
			list.add(MaterialNoArray.getJSONObject(i).get("FMaterialNo").toString());
		}
		InventoryStockQuery inventory = new InventoryStockQuery();
		inventory.set_F_YF_Materials(list);
		List<String> fieldList = ListUtils.newArrayList();
		// K3查询用到的字段 用到几个set几个
		fieldList.add("F_YF_MaterialNumber ");
		fieldList.add("F_YF_ExpectedDate");
		fieldList.add("F_YF_PurchaseCycle");
		fieldList.add("F_YF_CanSaleQty");
		inventory.setLstField(fieldList);
		String inventoryStockQuery = JSONObject.toJSON(inventory).toString();
		StringBuffer buffer = InvokeHelper.concession("getInventoryStockQueryReport2", inventoryStockQuery, K3Config.K3ClOUDRL);
		// K3返回的数据
		JSONObject jsonObj = JSONObject.parseObject(buffer.toString());
		String isError = jsonObj.get("IsError").toString();
		// 若查不到停止 用下一个物料查询
		if ("true".equals(isError)) {
			return ReturnDate.error(10003, jsonObj.get("Message").toString());
		}
		JSONArray jsonArray = JSONArray.parseArray(jsonObj.get("Value").toString());
		if (jsonArray == null || jsonArray.size() <= 0) {
			return ReturnDate.error(10001, "查询不到对应的货期");
		}
		JSONArray jsonArr = JSONArray.parseArray(jsonObj.get("Value").toString());
		// 把查询的数据转化为实体list
		List<InventoryStockQueryModel> inventoryList = JSONArray.parseArray(jsonArr.toString(), InventoryStockQueryModel.class);
		for (InventoryStockQueryModel inventoryStockQueryModel : inventoryList) {
			expectedDateList.add(inventoryStockQueryModel.getF_YF_ExpectedDate());
			purchaseCycleList.add(inventoryStockQueryModel.getF_YF_PurchaseCycle());
		}
		String expectedDate = "";
		if (ListUtils.isNotEmpty(expectedDateList)) {
			expectedDateList = removeList(expectedDateList);
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
		}
		String purchaseCycle = "";
		if (ListUtils.isNotEmpty(purchaseCycleList)) {
			Collections.sort(purchaseCycleList);
			purchaseCycle = purchaseCycleList.get(purchaseCycleList.size() - 1);
		}
		// 如果预计交期和采购周期都没有 说明在仓库库存中查询不到
		if ("".equals(expectedDate) || "".equals(purchaseCycle)) {
			return ReturnDate.error(10001, "查询不到对应的货期");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("outerId", materialCode);
		jsonObject.put("materialName", materialName);
		jsonObject.put("canSaleQty", 0);
		jsonObject.put("expectedDate", expectedDate);
		jsonObject.put("purchaseCycle", purchaseCycle);
		return ReturnDate.success(jsonObject);
	}

	/**
	 * 没有子项物料编码时的操作
	 * @param outerId
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo noSubitem (String outerId) throws Exception{
		// 请求K3参数实体
		InventoryStockQuery inventory = new InventoryStockQuery();
		inventory.setF_YF_Material(outerId);
		String inventoryStockQuery = JSONObject.toJSON(inventory).toString();
		// 请求K3仓库库存查询接口
		StringBuffer buffer = InvokeHelper.concession("getInventoryStockQueryReport", inventoryStockQuery, K3Config.K3ClOUDRL);
		// K3返回的数据
		JSONObject jsonObj = JSONObject.parseObject(buffer.toString());
		String isError = jsonObj.get("IsError").toString();
		if ("true".equals(isError)) {
			return ReturnDate.error(10003, jsonObj.get("IsError").toString());
		}
		JSONArray jsonArray = JSONArray.parseArray(jsonObj.get("Value").toString());
		if (jsonArray == null || jsonArray.size() <= 0) {
			return ReturnDate.error(10001, "查询不到对应的货期");
		}
		// 可销售数
		Integer canSaleQty = 0;
		// 预计交期
		String expectedDate = "";
		// 采购周期
		String purchaseCycle = "";
		// 物料名称
		String materialName = "";
		// 物料编码
		String materialCode = "";
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				// 可销售数
				Double saleQty = Double.parseDouble(jsonArray.getJSONObject(i).get("F_YF_CanSaleQty").toString());
				canSaleQty = saleQty.intValue() + canSaleQty;
			}
			// 预计交期
			expectedDate = jsonArray.getJSONObject(0).get("F_YF_ExpectedDate").toString();
			// 采购周期
			purchaseCycle = jsonArray.getJSONObject(0).get("F_YF_PurchaseCycle").toString();
			// 物料名称
			materialName = jsonArray.getJSONObject(0).get("F_YF_MaterialName").toString();
			// 物料编码
			materialCode = jsonArray.getJSONObject(0).get("F_YF_MaterialNumber").toString();
		}
		if (canSaleQty <= 10) {
			canSaleQty = 0;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("outerId", materialCode);
		jsonObject.put("materialName", materialName);
		jsonObject.put("canSaleQty", canSaleQty);
		jsonObject.put("expectedDate", expectedDate);
		jsonObject.put("purchaseCycle", purchaseCycle);
		return ReturnDate.success(jsonObject);
	}


	/**
	 * 去除包含现货的字符串
	 * @param list
	 * @return
	 */
	public static List<String> removeList (List<String> list) {
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			String x = it.next();
			if(x.equals("现货")){
				it.remove();
			}
		}
		return list;
	}
}