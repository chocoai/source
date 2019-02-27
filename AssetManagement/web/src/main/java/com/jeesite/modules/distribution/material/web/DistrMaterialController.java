/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.material.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.distribution.material.entity.DistrMaterialDetail;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.StringUtils;
import com.taobao.api.domain.ItemImg;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jeesite.modules.distribution.material.entity.DistrMaterial;
import com.jeesite.modules.distribution.material.service.DistrMaterialService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分销素材Controller
 * @author len
 * @version 2019-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/material/distrMaterial")
public class DistrMaterialController extends BaseController {

	@Autowired
	private DistrMaterialService distrMaterialService;
	@Autowired
	private TbProductService tbProductService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DistrMaterial get(String materialCode, boolean isNewRecord) {
		return distrMaterialService.get(materialCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("material:distrMaterial:view")
	@RequestMapping(value = {"list", ""})
	public String list(DistrMaterial distrMaterial, Model model) {
		model.addAttribute("distrMaterial", distrMaterial);
		return "distribution/material/distrMaterialList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("material:distrMaterial:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DistrMaterial> listData(DistrMaterial distrMaterial, HttpServletRequest request, HttpServletResponse response) {
		Page<DistrMaterial> page = distrMaterialService.findPage(new Page<DistrMaterial>(request, response), distrMaterial); 
		return page;
	}

	@Autowired
	private AmSeqService amSeqService;
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("material:distrMaterial:view")
	@RequestMapping(value = "form")
	public String form(DistrMaterial distrMaterial, Model model) {
		if (distrMaterial.getIsNewRecord()) {
			distrMaterial.setMaterialCode(amSeqService.getCode("SC", "yyyyMMdd", 6));
		}
		model.addAttribute("distrMaterial", distrMaterial);
		return "distribution/material/distrMaterialForm";
	}

	/**
	 * 保存分销素材
	 */
	@RequiresPermissions("material:distrMaterial:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DistrMaterial distrMaterial) {
		if (distrMaterial.getIsNewRecord()) {
			distrMaterial.setCreateBy(UserUtils.getUser().getUserName());
			distrMaterial.setCreateTime(new Date());
		}
		distrMaterialService.save(distrMaterial);
		return renderResult(Global.TRUE, text("保存分销素材成功！"));
	}
	
	/**
	 * 删除分销素材
	 */
	@RequiresPermissions("material:distrMaterial:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DistrMaterial distrMaterial) {
		distrMaterialService.delete(distrMaterial);
		return renderResult(Global.TRUE, text("删除分销素材成功！"));
	}


	@RequiresPermissions("material:distrMaterial:view")
	@RequestMapping(value = {"goodsList", ""})
	public String goodsList(TbProduct tbProduct, Model model, String selectData) {
		model.addAttribute("tbProduct", tbProduct);
		model.addAttribute("selectData", selectData);
		return "distribution/material/goodsSelect";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("material:distrMaterial:view")
	@RequestMapping(value = "goodsListData")
	@ResponseBody
	public Page<TbProduct> goodsListData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
		tbProduct.setApproveStatus("onsale");
		tbProduct.getSqlMap().getWhere().and("nick", QueryType.NE, "saladliang");
		Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct);
		return page;
	}


	/**
	 * 素材列表接口
	 */
	@RequiresPermissions("distribution:api")
	@RequestMapping(value = "materialListData")
	@ResponseBody
	public ReturnInfo materialListData(DistrMaterial distrMaterial, HttpServletRequest request, HttpServletResponse response) {
		distrMaterial = new DistrMaterial();
		// 设置成什么都可以 只是为了判断apiFlag不为空
		distrMaterial.setApiFlag("0");
		// 查询有效的素材
		distrMaterial.setMaterialStatus("0");
		Page<DistrMaterial> page = distrMaterialService.findPage(new Page<DistrMaterial>(request, response), distrMaterial);
		return ReturnDate.success(page);
	}

	@Autowired
	private TbTianmaoItemsService tbTianmaoItemsService;
	/**
	 * 素材详情
	 * @param
	 * @return
	 */
	@RequiresPermissions("distribution:api")
	@RequestMapping(value = "materialFormData")
	@ResponseBody
	public ReturnInfo materialFormData(DistrMaterial distrMaterial) {
		DistrMaterial material = distrMaterialService.selectByMaterial(distrMaterial.getMaterialCode());
		// 素材详情图
		List<String> imgList = ListUtils.newArrayList();
		if (StringUtils.isNotEmpty(material.getImg())) {
			String[] imgs = material.getImg().split(",");
			for (String img : imgs) {
				imgList.add(img);
			}
		}
		distrMaterial.setImgList(imgList);
		// 根据商品Id查询商品信息
		List<String> numIidList = ListUtils.newArrayList();
		for (DistrMaterialDetail distrMaterialDetail : distrMaterial.getDistrMaterialDetailList()) {
			numIidList.add(distrMaterialDetail.getNumIid());
		}
		List<TbProduct> tbProductList = ListUtils.newArrayList();
		tbProductList = distrMaterialService.selectByNumIid(numIidList);
		// 根据商品id获取主图图片
		List<TbItemImgs> itemImgList = tbTianmaoItemsService.getLastImg(numIidList);
		List<TbSku> tbSkuList = distrMaterialService.selectSkuByNumIid(numIidList);
		Map<String, String> map = new HashMap<>();
		for (TbProduct tbProduct : tbProductList) {
			Optional<TbItemImgs> optionalItemImg = itemImgList.stream().filter(s ->String.valueOf(s.getItemId()).equals(tbProduct.getNumIid())).findFirst();
			if (optionalItemImg.isPresent()) {
				// 取最后一张图片 sql中排过序
				String distrPicUrl = optionalItemImg.get().getUrl();
				tbProduct.setDistrPicUrl(distrPicUrl);
				// 主图
				map.put(tbProduct.getNumIid(), distrPicUrl);
			} else {
				map.put(tbProduct.getNumIid(), "");
			}
			tbProduct.setTbSkuList(tbSkuList.stream().filter(s ->String.valueOf(s.getNumIid()).equals(tbProduct.getNumIid())).collect(Collectors.toList()));

		}
		for (TbSku tbSku : tbSkuList) {
			if (StringUtils.isEmpty(tbSku.getSkuUrl())) {
				tbSku.setSkuUrl(map.get(String.valueOf(tbSku.getNumIid())));
			}
		}
		distrMaterial.setDistrMaterialDetailList(null);
		distrMaterial.setTbProductList(tbProductList);
		return ReturnDate.success(distrMaterial);
	}
}