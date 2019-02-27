/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.screen.entity.*;
import com.jeesite.modules.asset.screen.service.ScreenConfigService;
import com.jeesite.modules.asset.screen.service.ScreenProductService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.screen.service.ScreenEnterpriseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 零售家企业Controller
 * @author len
 * @version 2019-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/screen/screenEnterprise")
public class ScreenEnterpriseController extends BaseController {

	@Autowired
	private ScreenEnterpriseService screenEnterpriseService;
	@Autowired
	private AmSeqService amSeqService;

	@Autowired
	private ScreenConfigService screenConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ScreenEnterprise get(String enterpriseCode, boolean isNewRecord) {
		return screenEnterpriseService.get(enterpriseCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("screen:screenEnterprise:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScreenEnterprise screenEnterprise, Model model) {
		model.addAttribute("screenEnterprise", screenEnterprise);
		return "asset/screen/screenEnterpriseList";
	}

	@RequiresPermissions("screen:screenEnterprise:view")
	@RequestMapping(value = {"enterpriseSelect", ""})
	public String enterpriseSelect(ScreenEnterprise screenEnterprise, Model model, String selectData) {
		model.addAttribute("selectData", selectData);
		model.addAttribute("screenEnterprise", screenEnterprise);
		return "asset/screen/enterpriseSelect";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("screen:screenEnterprise:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ScreenEnterprise> listData(ScreenEnterprise screenEnterprise, HttpServletRequest request, HttpServletResponse response) {
		Page<ScreenEnterprise> page = screenEnterpriseService.findPage(new Page<ScreenEnterprise>(request, response), screenEnterprise); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("screen:screenEnterprise:view")
	@RequestMapping(value = "form")
	public String form(ScreenEnterprise screenEnterprise, Model model) {
		if (screenEnterprise.getIsNewRecord()) {
			screenEnterprise.setEnterpriseCode(amSeqService.getCode("QY", "yyyyMMdd", 4));
		}
		model.addAttribute("screenEnterprise", screenEnterprise);
		return "asset/screen/screenEnterpriseForm";
	}

	/**
	 * 保存零售家企业
	 */
	@RequiresPermissions("screen:screenEnterprise:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ScreenEnterprise screenEnterprise) {
		screenEnterpriseService.save(screenEnterprise);
		return renderResult(Global.TRUE, text("保存零售家企业成功！"));
	}
	
	/**
	 * 删除零售家企业
	 */
	@RequiresPermissions("screen:screenEnterprise:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ScreenEnterprise screenEnterprise) {
		screenEnterpriseService.delete(screenEnterprise);
		return renderResult(Global.TRUE, text("删除零售家企业成功！"));
	}

	@Autowired
	private AmFileUploadService amFileUploadService;
	@Autowired
	private ScreenProductService screenProductService;

	/**
	 * 封面首页
	 */
	@RequestMapping(value = "cover", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo cover() {
		// 查询符合条件的零售家企业
		List<CoverData> enterpriseDetailList = screenConfigService.getConfigEnterprise();
		if (ListUtils.isNotEmpty(enterpriseDetailList)) {
			List<String> enterpriseList = ListUtils.extractToList(enterpriseDetailList, "enterpriseCode");

			// 根据企业编号获取企业下的产品产品图片等
			List<ProductData> productDataList = screenProductService.getProduct(enterpriseList);
			if (ListUtils.isNotEmpty(productDataList)) {
				screenProductService.productImg(productDataList);
			}
			// 根据企业编码查询企业图片
			List<AmFileUpload> amFileUploadList = amFileUploadService.getImgs(enterpriseList, "screenEnterprise_image");
			for (CoverData coverData : enterpriseDetailList) {
				Optional<AmFileUpload> optionalCoverData = amFileUploadList.stream().filter(s ->s.getBizKey().equals(coverData.getEnterpriseCode())).findFirst();
				if (optionalCoverData.isPresent()) {
					coverData.setEnterpriseImg(optionalCoverData.get().getFileUrl());
				}
				List<ProductData> productDataList1 = productDataList.stream().filter(s ->s.getEnterpriseCode().equals(coverData.getEnterpriseCode())).collect(Collectors.toList());
				// 封面显示3个产品
				if (productDataList1.size() > 3) {
					productDataList1 = productDataList1.subList(0, 3);
				}
				coverData.setProductDataList(productDataList1);
			}
		}
		return ReturnDate.success(enterpriseDetailList);
	}

	@RequestMapping(value = "enterpriseProduct", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo enterpriseProduct (String enterpriseCode, int pageNo, int pageSize) {
		Page<ProductData> page = screenProductService.getProductList(enterpriseCode, pageNo, pageSize);
		return ReturnDate.success(page);
	}
}