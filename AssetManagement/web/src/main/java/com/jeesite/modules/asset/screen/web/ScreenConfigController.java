/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.SetUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.screen.entity.FirstPageData;
import com.jeesite.modules.asset.screen.entity.ScreenConfigDetail;
import com.jeesite.modules.asset.tianmao.dao.DaoGouDAO;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.entity.TbTianmaoItems;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.util.StringUtils;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.screen.entity.ScreenConfig;
import com.jeesite.modules.asset.screen.service.ScreenConfigService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 屏幕配置Controller
 * @author len
 * @version 2018-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/screen/screenConfig")
public class ScreenConfigController extends BaseController {

	@Autowired
	private ScreenConfigService screenConfigService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private TbTianmaoItemsService tbTianmaoItemsService;
	@Autowired
	private DaoGouDAO daoGouDAO;
	// 梵店评价接口
	@Value("${evaluate}")
	private String evaluateUrl;
	private static final String ALLCITY = "全部城市";
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ScreenConfig get(String configureCode, boolean isNewRecord) {
		return screenConfigService.get(configureCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("screen:screenConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScreenConfig screenConfig, Model model) {
		model.addAttribute("screenConfig", screenConfig);
		return "asset/screen/screenConfigList";
	}

	@RequiresPermissions("screen:screenConfig:view")
	@RequestMapping(value = {"goodsList", ""})
	public String goodsList(TbProduct tbProduct, Model model, String selectData) {
		model.addAttribute("tbProduct", tbProduct);
		model.addAttribute("selectData", selectData);
		return "asset/screen/goodsSelect";
	}
	@Autowired
	private TbProductService tbProductService;
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("screen:screenConfig:view")
	@RequestMapping(value = "goodsListData")
	@ResponseBody
	public Page<TbProduct> goodsListData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
		tbProduct.setApproveStatus("onsale");
		tbProduct.setNick("优梵艺术旗舰店");
		Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct);
		return page;
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("screen:screenConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ScreenConfig> listData(ScreenConfig screenConfig, HttpServletRequest request, HttpServletResponse response) {
		Page<ScreenConfig> page = screenConfigService.findPage(new Page<ScreenConfig>(request, response), screenConfig); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("screen:screenConfig:view")
	@RequestMapping(value = "form")
	public String form(ScreenConfig screenConfig, Model model) {
		if (screenConfig.getIsNewRecord()) {
			screenConfig.setConfigureCode(amSeqService.getCode("PZ"));
		}
		model.addAttribute("screenConfig", screenConfig);
		return "asset/screen/screenConfigForm";
	}

	/**
	 * 保存屏幕配置
	 */
	@RequiresPermissions("screen:screenConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ScreenConfig screenConfig, String flag) {
	    if (StringUtils.isNotEmpty(flag)) {
            screenConfig.setConfigureStatus(flag);
        }
        if ("1".equals(screenConfig.getConfigurePage())) {
			screenConfig.setPageLocation("3");
		}
		// 如果添加有重复的商品 做提示
	    Set<String> set = SetUtils.newLinkedHashSet();
		List<String> list = ListUtils.newArrayList();
		if(ListUtils.isNotEmpty(screenConfig.getScreenConfigDetailList())) {
			for (ScreenConfigDetail screenConfigDetail : screenConfig.getScreenConfigDetailList()) {
				list.add(screenConfigDetail.getNumIid());
				set.add(screenConfigDetail.getNumIid());
			}
		}
		List<String> list2 = ListUtils.newArrayList();
		for (String str : set) {
			List<String> list1 = list.stream().filter(s ->s.equals(str)).collect(Collectors.toList());
			if (list1.size() > 1) {
				String msg = "第" + (list.indexOf(str)+1) +"行" + str;
				list2.add(msg);
			}
		}
		if (ListUtils.isNotEmpty(list2)) {
			return renderResult(Global.FALSE, text("不能添加重复的商品！<br> " + StringUtils.join(list2, "<br>")));
		}
		screenConfigService.save(screenConfig);
		return renderResult(Global.TRUE, text("保存屏幕配置成功！"));
	}

	/**
	 * 删除屏幕配置
	 */
	@RequiresPermissions("screen:screenConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ScreenConfig screenConfig) {
		screenConfigService.delete(screenConfig);
		return renderResult(Global.TRUE, text("删除屏幕配置成功！"));
	}


	/**
	 * 获取一级页面商品
	 * @param flag
	 * @return
	 */

//	@RequiresPermissions("screen:screenConfig:edit")
	@RequestMapping(value = "firstPage")
	@ResponseBody
	public ReturnInfo firstPage(String flag) {
		// 表头【配置店铺】=杭州六空店 【配置页面】=一楼单品区 【状态】=生效
		List<FirstPageData> list = screenConfigService.getFirstPage(flag);
		// 根据页面位置分四个组
		List<FirstPageData> list1 = list.stream().filter(s ->s.getPageLocation().equals("0")).collect(Collectors.toList());
		List<FirstPageData> list2 = list.stream().filter(s ->s.getPageLocation().equals("1")).collect(Collectors.toList());
		List<FirstPageData> list3 = list.stream().filter(s ->s.getPageLocation().equals("2")).collect(Collectors.toList());
		List<FirstPageData> list4 = list.stream().filter(s ->s.getPageLocation().equals("3")).collect(Collectors.toList());
		JSONObject json = new JSONObject();
		json.put("0", list1);
		json.put("1", list2);
		json.put("2", list3);
		json.put("3", list4);
		return ReturnDate.success(json);
	}

	/**
	 *
	 * 商品详情
	 * @param
	 * @return
	 */
	@RequestMapping(value = "formGoods")
	@ResponseBody
	public ReturnInfo formGoods(String numIid) {
		// 根据商品id获取信息
		TbProduct tbProduct = tbProductService.get(numIid);
		TbTianmaoItems tbTianmaoItems = tbTianmaoItemsService.get(tbProduct.getNumIid());
		List<TbSku> tbSkuList = daoGouDAO.getSkuList(tbProduct.getNumIid());
		tbProduct.setTbSkuList(tbSkuList);
		String text = JsonMapper.toJson(tbProduct);
		JSONObject jsonTbProduct= JSONObject.parseObject(text);
		Item item= JSONObject.parseObject(tbTianmaoItems.getBody(),Item.class);
		String descModules = item.getDescModules();
		List<ItemImg> itemImgs = item.getItemImgs();
		jsonTbProduct.put("descModules",descModules);
		jsonTbProduct.put("itemImgs",itemImgs);
		return ReturnDate.success(jsonTbProduct);
	}

	/**
	 * 获取商品参数
	 * @param numIid
	 * @return
	 */
	@RequestMapping(value = "getItem")
	@ResponseBody
	public ReturnInfo getItem(String numIid) {
		TbTianmaoItems tbTianmaoItems = tbTianmaoItemsService.get(numIid);
		Item item= JSONObject.parseObject(tbTianmaoItems.getBody(),Item.class);
		String[] str = item.getPropsName().split(";");
		List<String> list = Arrays.asList(str);
		JSONObject json = new JSONObject();
		json.put("名称", item.getTitle());
		acquisition(json, list, "品牌");
		acquisition(json, list, "风格");
		acquisition(json, list, "木质材质");
		acquisition(json, list, "材质");
		multi(json, list, "颜色分类");
		acquisition(json, list, "产地");
		acquisition(json, list, "是否可定制");
		acquisition(json, list, "包装体积");
		acquisition(json, list, "包含组件");
		acquisition(json, list, "是否带书架");
		acquisition(json, list, "是否可预售");
		acquisition(json, list, "型号");
		multi(json, list, "可送货/安装");
		acquisition(json, list, "是否可组装");
		return ReturnDate.success(json);
	}


	/**
	 * 多参数拼接
	 * @param json
	 * @param list
	 * @param str
	 */
	public void multi(JSONObject json, List<String> list, String str) {
		List<String> colorList = list.stream().filter(s ->s.contains(str)).collect(Collectors.toList());
		if (ListUtils.isNotEmpty(colorList)) {
			for (String color : colorList) {
				if (json.containsKey(str)) {
					json.put(str, json.get(str) + " " + param(color, str));
				} else {
					json.put(str, param(color, str));
				}
			}
		} else {
			json.put(str, "");
		}
		if (json.get(str).toString().contains(ALLCITY)) {
			json.put(str, ALLCITY);
		}
	}

	/**
	 * 单参数获取
	 * @param json
	 * @param list
	 * @param str
	 */
	public void acquisition(JSONObject json, List<String> list, String str) {
		Optional<String> optionalS = list.stream().filter(s ->s.contains(str)).findFirst();
		if (optionalS.isPresent()) {
			String woodiness = optionalS.get();
			json.put(str, param(woodiness, str));
		} else {
			json.put(str, "");
		}
	}


	/**
	 * 去除数据前的数字和冒号
	 * @param str
	 * @param st
	 * @return
	 */
	public String param(String str, String st) {
		int num = str.indexOf(":");
		int num1 = str.indexOf(":", num + 1);
		str = str.substring(num1+1);
		str = str.replace(st + ":", "");
		return str;
	}

	/**
	 * 获取商品评价
	 * @param numIid
	 * @param page
	 * @param limit
	 */
	@RequestMapping(value = "evaluate")
	@ResponseBody
	public ReturnInfo evaluate(String numIid, int page, int limit) {
		String url = evaluateUrl + numIid + "&page=" + page + "&limit=" + limit;
		String result = HttpClientUtils.get(url);
		return ReturnDate.success(JSONObject.parseObject(result));
	}

	/**
	 * 根据系列名获取该系列下的商品
	 * @param seriesName
	 * @return
	 */
	@RequestMapping(value = "series")
	@ResponseBody
	public ReturnInfo series(String seriesName, int pageNo, int pageSize) {
		seriesName = seriesName.toLowerCase();
		int count = screenConfigService.getGoodsNum(seriesName);
		List<TbProduct> tbProductList = screenConfigService.getGoods(seriesName, pageNo, pageSize);
		List<String> list = ListUtils.extractToList(tbProductList, "numIid");
		List<TbItemImgs> tbItemImgsList = tbTianmaoItemsService.getLastImg(list);
		for (TbProduct tbProduct : tbProductList) {
			Optional<TbItemImgs> optionalItemImgs = tbItemImgsList.stream().filter(s ->s.getItemId().toString().equals(tbProduct.getNumIid())).findFirst();
			if (optionalItemImgs.isPresent()) {
				tbProduct.setPicUrl(optionalItemImgs.get().getUrl());
			}
		}
		JSONObject json = new JSONObject();
		json.put("count", count);
		json.put("goodsList", tbProductList);
		return ReturnDate.success(json);
	}
}