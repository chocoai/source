/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.fgcqualitycheck.common.Convert;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.scheduledtask.K3Config;
import com.jeesite.modules.asset.tianmao.entity.GoodsTag;
import com.jeesite.modules.asset.tianmao.entity.PriceTag;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
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
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品-SKU管理Controller
 * @author jace
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbSku")
public class TbSkuController extends BaseController {

	@Autowired
	private TbSkuService tbSkuService;

	@Value("${POST_K3ClOUDRL}")
	private String POST_K3ClOUDRL;

	private static String URL = null;

	@Autowired
	private K3connection k3connection;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbSku get(String skuId, boolean isNewRecord) {
		return tbSkuService.get(skuId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tianmao:tbSku:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbSku tbSku, Model model) {
		model.addAttribute("tbSku", tbSku);
		return "asset/tianmao/tbSkuList";
	}

	/**
	 * 查询列表
	 */
//	@RequiresPermissions("tianmao:tbSku:view")
	@RequestMapping(value = {"listSelect", ""})
	public String listSelect(TbSku tbSku, Model model, String selectData) {
		model.addAttribute("selectData", selectData);
		model.addAttribute("tbSku", tbSku);
		return "asset/tianmao/tbSkuSelect";
	}

	@RequestMapping(value = {"skuSelect", ""})
	public String skuSelect(TbSku tbSku, Model model, String selectData, String checkbox) {
		model.addAttribute("checkbox", checkbox);
		model.addAttribute("selectData", selectData);
		model.addAttribute("tbSku", tbSku);
		return "asset/tianmao/skuSelect";
	}
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("tianmao:tbSku:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbSku> listData(TbSku tbSku, HttpServletRequest request, HttpServletResponse response) {
		Page<TbSku> page = tbSkuService.findPage(new Page<TbSku>(request, response), tbSku);
//		for (int i =0; i<page.getList().size(); i++) {
//			String properties = page.getList().get(i).getProperties();
//			String[] propertiy = properties.split(";");
//			String propertiesName = page.getList().get(i).getPropertiesName();
//			for (int j = 0; j < propertiy.length; j++) {
//				propertiesName = propertiesName.replace(propertiy[j] + ":", "");
//			}
//			page.getList().get(i).setPropertiesName(propertiesName);
//		}
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tianmao:tbSku:view")
	@RequestMapping(value = "form")
	public String form(TbSku tbSku, Model model) {
		model.addAttribute("tbSku", tbSku);
		return "asset/tianmao/tbSkuForm";
	}

	/**
	 * 保存商品-SKU管理
	 */
	@RequiresPermissions("tianmao:tbSku:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbSku tbSku) {
		tbSkuService.save(tbSku);
		return renderResult(Global.TRUE, "保存商品-SKU管理成功！");
	}
	
	/**
	 * 删除商品-SKU管理
	 */
	@RequiresPermissions("tianmao:tbSku:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbSku tbSku) {
		tbSkuService.delete(tbSku);
		return renderResult(Global.TRUE, "删除商品-SKU管理成功！");
	}

	/**
	 * 获取店铺（卖家昵称），SKU，真实售价
	 * @return
	 */
	@RequestMapping("getSkuPrice")
	@ResponseBody
	public ReturnInfo getSkuPrice () {
		List<TbSku> tbSkuList = tbSkuService.getSkuAndPrice();
		return ReturnDate.success(tbSkuList);
	}


    @RequiresPermissions("tianmao:tbSku:batchPrint")
	@RequestMapping(value = "print",method = RequestMethod.POST)
	public String print(@RequestBody String request, Model model) {
		System.out.println(request);
		String []req = request.split("\r\n");
		String outerIds = req[1].replace("arrzzz=","");
		outerIds = "[" + outerIds + "]";
		JSONArray skuArr = JSONArray.parseArray(outerIds);
		List<TbSku> tbSkuList = skuArr.toJavaList(TbSku.class);
		String type = req[0].replace("type=", "");
		String materialFormId = "BD_MATERIAL";
		String priceFormId = "BD_SAL_PriceList";
		List<GoodsTag> goodsTagList = ListUtils.newArrayList();
//		JSONObject jsonObject = JSONObject.parseObject(selectData);
		List<String> skuList = ListUtils.newArrayList();
		boolean isLogin = k3connection.getConnection();
		if (!isLogin) {
		 	return renderResult(Global.FALSE, "服务异常，请稍后");
		}
		if ("http://uvan.ik3cloud.com/K3cloud/".equals(K3Config.K3ClOUDRL)) {
			URL = "https://uvan.ik3cloud.com/K3cloud/";
		} else {
			URL = K3Config.K3ClOUDRL;
		}
		for (TbSku tbSku : tbSkuList) {
			String result = null;
			String resultPrice = null;
			String outerId = tbSku.getOuterId();
			String quoteDate = DateUtils.getDateTime();

				String content = "{\"FormId\":\"BD_MATERIAL\",\"FieldKeys\":\"FNUMBER,F_YF_UnderLineName,F_YF_EnglishName,F_YF_PlaceOfOrigin,FLENGTH,FWIDTH,FHEIGHT,F_YF_UnderLineProductColor,F_YF_Specifications.FNAME,FImageFileServer1,F_YF_BaseProperty,F_YF_PROPVALUE\",\"FilterString\":\"FNUMBER='"+outerId+"'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
//				String content1 = "{\"FormId\":\"BD_SAL_PriceList\",\"FieldKeys\":\"FPrice\",\"FilterString\":\"FMaterialId.FNumber='"+ outerId +"' and F_YF_Shop.FNAME='优梵艺术旗舰店' AND F_YF_EntryExpiryDateLong>'"+ quoteDate +"' AND F_YF_EntryEffectiveDateLong<'"+ quoteDate +"'AND FRowAuditStatus='A' AND FEntryForbidStatus='A'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";

				try {
					result = InvokeHelper.ExecuteBillQuery(materialFormId, content, POST_K3ClOUDRL);
					String[] c = result.split(",");
					if (c.length == 1) {
						skuList.add(outerId);
						continue;
					}
					GoodsTag goodsTag = assignment(result);
					goodsTag.setMoney(tbSku.getRealPrice());
					goodsTagList.add(goodsTag);
				} catch (Exception e) {
					return renderResult(Global.FALSE, "服务异常，请稍后");
				}
		}
		PriceTag priceTag = new PriceTag();
		if (goodsTagList != null && goodsTagList.size() >0) {
			priceTag.setGoodsTagList(goodsTagList);
			priceTag.setType(type);
			priceTag.setToken(InvokeHelper.UserToken);
			if (skuList != null && skuList.size() >0) {
				priceTag.setSkuList(skuList);
			} else {
				priceTag.setSkuList(null);
			}
			model.addAttribute("priceTag", priceTag);
			return "asset/tianmao/cmdyPriceTagPrint";
		} else {
            if (skuList != null && skuList.size() >0) {
				priceTag.setType("-1");
                priceTag.setSkuList(skuList);
            }
            model.addAttribute("priceTag", priceTag);
            return "asset/tianmao/cmdyPriceTagPrint";
		}
	}


	public GoodsTag assignment (String result) {
//		result = result.replace("[[", "[");
//		result = result.replace("]]", "]");
		JSONArray arrList = (JSONArray) JSONArray.parse(String.valueOf(result));
		JSONArray jsonList = arrList.getJSONArray(0);
		GoodsTag goodsTag = new GoodsTag();
		// 物料编码
		goodsTag.setMaterielCode(jsonList.get(0).toString());
		// 物料名
		goodsTag.setMaterielName(Convert.getStr(jsonList,1));
		// 英文名
		goodsTag.setEmpName(Convert.getStr(jsonList,2));
		// 产品产地
		goodsTag.setProductOrigin(Convert.getStr(jsonList,3));
		String materielLength = Convert.getStr(jsonList,4);
		if (materielLength != null) {
			materielLength = String.valueOf(Double.parseDouble(materielLength));
		}
		// 长
		goodsTag.setMaterielLength(materielLength);
		String materielWidth = Convert.getStr(jsonList,5);
		if (materielWidth != null) {
			materielWidth = String.valueOf(Double.parseDouble(materielWidth));
		}
		// 宽
		goodsTag.setMaterielWidth(materielWidth);
		String materielHeight = Convert.getStr(jsonList,6);
		if (materielHeight != null) {
			materielHeight = String.valueOf(Double.parseDouble(materielHeight));
		}
		// 高
		goodsTag.setMaterielHeight(materielHeight);
		//颜色
		goodsTag.setColor(Convert.getStr(jsonList,7));
		// 规格名称
		goodsTag.setSpecName(Convert.getStr(jsonList,8));
		// 商品链接(二维码)
		String linkAddress = Convert.getStr(jsonList, 9);
		if (linkAddress != null) {
			linkAddress = URL + "FileUpLoadServices/download.aspx?fileId=" + linkAddress + "&dbId="+ K3Config.DBID +"&token=";
			goodsTag.setLinkAddress(linkAddress);
		}
		for (int i = 0 ;i <arrList.size(); i++) {
			if ("材质".equals(arrList.getJSONArray(i).get(10).toString())) {
				// 材质
				goodsTag.setTexture(Convert.getStr(arrList.getJSONArray(i), 11));
				break;
			} else {
				goodsTag.setTexture("");
			}
		}

		return goodsTag;
	}

	public static String removeTrim(String str){
		if(str.indexOf(".") > 0){
			str = str.replaceAll("0+?$", "");//去掉多余的0
			str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return str;
	}


	/**
	 * 导入用户数据
	 */
	@ResponseBody
	@RequiresPermissions("tianmao:tbSku:import")
	@PostMapping(value = "importData")
	public String importData(MultipartFile file, String updateSupport) {
		try {
			boolean isUpdateSupport = Global.YES.equals(updateSupport);
			String message = tbSkuService.importData(file, isUpdateSupport);
			return renderResult(Global.TRUE, "posfull:"+message);
		} catch (Exception ex) {
			return renderResult(Global.FALSE, "posfull:"+ex.getMessage());
		}
	}
}