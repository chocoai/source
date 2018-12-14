/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.taobao.entity.TaobaoEvaluate;
import com.jeesite.modules.taobao.service.TaobaoEvaluateService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片评价表Controller
 * @author len
 * @version 2018-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/taobao/taobaoEvaluate")
public class TaobaoEvaluateController extends BaseController {
	@Resource
	private RedisTemplate<String, TaobaoEvaluate> redisTemplate;


	@Autowired
	private TaobaoEvaluateService taobaoEvaluateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaobaoEvaluate get(String pkey, boolean isNewRecord) {
		return taobaoEvaluateService.get(pkey, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("taobao:taobaoEvaluate:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaobaoEvaluate taobaoEvaluate, Model model) {
		model.addAttribute("taobaoEvaluate", taobaoEvaluate);
		return "modules/taobao/taobaoEvaluateList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("taobao:taobaoEvaluate:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaobaoEvaluate> listData(TaobaoEvaluate taobaoEvaluate, HttpServletRequest request, HttpServletResponse response) {
		Page<TaobaoEvaluate> page = taobaoEvaluateService.findPage(new Page<TaobaoEvaluate>(request, response), taobaoEvaluate); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("taobao:taobaoEvaluate:view")
	@RequestMapping(value = "form")
	public String form(TaobaoEvaluate taobaoEvaluate, Model model) {
		taobaoEvaluate = taobaoEvaluateService.getEvaluate(taobaoEvaluate.getProductId(), taobaoEvaluate.getPublishDate(), taobaoEvaluate.getAuthorName());
		model.addAttribute("taobaoEvaluate", taobaoEvaluate);
		return "modules/taobao/taobaoEvaluateForm";
	}

	/**
	 * 保存图片评价表
	 */
	@RequiresPermissions("taobao:taobaoEvaluate:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaobaoEvaluate taobaoEvaluate) {
		taobaoEvaluateService.save(taobaoEvaluate);
		return renderResult(Global.TRUE, text("保存图片评价表成功！"));
	}
	
	/**
	 * 删除图片评价表
	 */
	@RequiresPermissions("taobao:taobaoEvaluate:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaobaoEvaluate taobaoEvaluate) {
		taobaoEvaluateService.delete(taobaoEvaluate);
		return renderResult(Global.TRUE, text("删除图片评价表成功！"));
	}

	@ResponseBody
	@RequestMapping(value = "saveData")
	public ReturnInfo saveData(@RequestBody String request) {
		try {
			JSONObject json = JSONObject.parseObject(request);
			// 商品id
			String productId = getStr(json, "product_id");
			// 时间戳
			String publishDate = getStr(json, "publish_time");
			Date publishTime = DateUtils.parseDate(DateUtils.formatDate(Long.valueOf(publishDate), "yyyy-MM-dd HH:mm:ss"));
			// 评价人
			String authorName =  getStr(json, "author_name");
			// 查询表中是否存在相同数据
			TaobaoEvaluate taobaoEvaluate = taobaoEvaluateService.getEvaluate(productId, Long.valueOf(publishDate), authorName);
			if (taobaoEvaluate == null) {
				// 如果没有对应的对象
				taobaoEvaluate = new TaobaoEvaluate();
				// 插入新数据
				taobaoEvaluate.setIsNewRecord(true);
				taobaoEvaluate.setProductId(productId);
				taobaoEvaluate.setPublishTime(publishTime);
				taobaoEvaluate.setPublishDate(Long.valueOf(publishDate));
			} else {
				// 如果有，更新数据
				taobaoEvaluate.setIsNewRecord(false);
			}
			// 内容
			String content =  getStr(json, "content");
			// 评价级别
			String authorLevel =  getStr(json, "author_level");
			// 商品规格
			String productSku =  getStr(json, "product_sku");
			// 卖家评价内容
			String sellerReplyContent =  getStr(json, "seller_reply_content");
			// 评价发布时间
			String appendPublishTime = getStr(json, "append_publish_time");
			if (appendPublishTime != null && !"".equals(appendPublishTime)) {
				Date appendPublishDate = DateUtils.parseDate(DateUtils.formatDate(Long.valueOf(appendPublishTime), "yyyy-MM-dd HH:mm:ss"));
				taobaoEvaluate.setAppendPublishTime(appendPublishDate);
			}
			// 评价间隔
			String appendInterval = getStr(json, "append_interval");
			// 评价内容
			String appendContent = getStr(json, "append_content");

			saveImgUrl(json, taobaoEvaluate);
			saveAppendImages(json, taobaoEvaluate);

			taobaoEvaluate.setProductId(productId);
			taobaoEvaluate.setContent(content);
			taobaoEvaluate.setAuthorName(authorName);
			taobaoEvaluate.setAuthorLevel(authorLevel);
			taobaoEvaluate.setProductSku(productSku);
			taobaoEvaluate.setSellerReplyContent(sellerReplyContent);
			taobaoEvaluate.setAppendInterval(appendInterval);
			taobaoEvaluate.setAppendContent(appendContent);
			taobaoEvaluate.setUpdateTime(new Date());
			taobaoEvaluateService.save(taobaoEvaluate);
			// 保存成功后把数据存入缓存
			return ReturnDate.success(200, "同步数据成功", "");
		}catch (Exception e){
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return ReturnDate.error(200, sw.toString(), "");
		}

	}

	public static String getStr (JSONObject json, String key) {
		String value = null;
		if (json.containsKey(key)) {
			value = json.getString(key);
		}
		return value;
	}

	public void saveImgUrl(JSONObject json, TaobaoEvaluate taobaoEvaluate) {
		if (json.containsKey("images")) {
			JSONArray imagesArray = json.getJSONArray("images");
			for (int i = 0; i< imagesArray.size(); i++) {
				if (i == 0) {
					taobaoEvaluate.setImageUrl1(imagesArray.getJSONObject(i).get("image_url").toString());
				}
				if (i == 1) {
					taobaoEvaluate.setImageUrl2(imagesArray.getJSONObject(i).get("image_url").toString());
				}
				if (i == 2) {
					taobaoEvaluate.setImageUrl3(imagesArray.getJSONObject(i).get("image_url").toString());
				}
				if (i == 3) {
					taobaoEvaluate.setImageUrl4(imagesArray.getJSONObject(i).get("image_url").toString());
				}
				if (i == 4) {
					taobaoEvaluate.setImageUrl5(imagesArray.getJSONObject(i).get("image_url").toString());
				}
			}
		}
	}

	public void saveAppendImages(JSONObject json, TaobaoEvaluate taobaoEvaluate) {
		if (json.containsKey("append_images")) {
			JSONArray imagesArray = json.getJSONArray("append_images");
			for (int i = 0; i< imagesArray.size(); i++) {
				if (i == 0) {
					taobaoEvaluate.setAppendImages1(imagesArray.getJSONObject(i).get("append_image_url").toString());
				}
				if (i == 1) {
					taobaoEvaluate.setAppendImages2(imagesArray.getJSONObject(i).get("append_image_url").toString());
				}
				if (i == 2) {
					taobaoEvaluate.setAppendImages3(imagesArray.getJSONObject(i).get("append_image_url").toString());
				}
				if (i == 3) {
					taobaoEvaluate.setAppendImages4(imagesArray.getJSONObject(i).get("append_image_url").toString());
				}
				if (i == 4) {
					taobaoEvaluate.setAppendImages5(imagesArray.getJSONObject(i).get("append_image_url").toString());
				}
			}
		}
	}
}