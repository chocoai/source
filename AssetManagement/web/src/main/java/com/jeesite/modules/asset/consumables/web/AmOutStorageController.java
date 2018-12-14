/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.amlocation.service.AmLocationService;
import com.jeesite.modules.asset.consumables.entity.AmConLocationStock;
import com.jeesite.modules.asset.consumables.entity.AmConStock;
import com.jeesite.modules.asset.consumables.entity.AmOutStorage;
import com.jeesite.modules.asset.consumables.entity.AmOutStorageDetails;
import com.jeesite.modules.asset.consumables.service.*;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.service.AmWarehouseService;
import com.jeesite.modules.userroleconfig.UserDataPermissionUnit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 耗材出库表Controller
 * @author czy
 * @version 2018-05-07
 */
@Controller
@RequestMapping(value = "${adminPath}/consumables/amOutStorage")
public class AmOutStorageController extends BaseController {

	private final String PER_FIX = "HCCK";  //流水号前缀
	@Autowired
	private AmOutStorageService amOutStorageService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmConStockService amConStockService;
	@Autowired
	private AmConLocationStockService amConLocationStockService;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AmLocationService locationService;
	@Autowired
	private AmWarehouseService amWarehouseService;

	@Autowired
	private UserDataPermissionUnit userDataPermissionUnit;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmOutStorage get(String outstorageCode, boolean isNewRecord) {
		return amOutStorageService.get(outstorageCode, isNewRecord);
	}
	/**
	 * 查询列表
	 */
	@RequiresPermissions("consumables:amOutStorage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmOutStorage amOutStorage, Model model) {
		model.addAttribute("amOutStorage", amOutStorage);
		return "asset/consumables/amOutStorageList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("consumables:amOutStorage:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmOutStorage> listData(AmOutStorage amOutStorage, HttpServletRequest request, HttpServletResponse response) {
		String param=userDataPermissionUnit.getParam(request);
		amOutStorage.setParam(param);
		Page<AmOutStorage> page = amOutStorageService.findPage(new Page<AmOutStorage>(request, response), amOutStorage);
		return page;
	}
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("consumables:amOutStorage:view")
	@RequestMapping(value = "form")
	public String form(AmOutStorage amOutStorage, Model model, boolean isNewRecord) {
		String statusLabel = "";
		if (isNewRecord) {
			Date date = new Date();
			amOutStorage.setOutDate(date);
			amOutStorage.setOutstorageCode(amSeqService.getSeq(PER_FIX));
			// 单据状态
			statusLabel = amUtilService.findDictLabel("0", "am_document_status");
			amOutStorage.setBillStatus("0");
			amOutStorage.setStatusLabel(statusLabel);
		}
		List<AmWarehouse> amWarehouse = amUtilService.getWarehouseListByLeaf("1");
		model.addAttribute("amWarehouse", amWarehouse);
		model.addAttribute("amOutStorage", amOutStorage);
		if (!isNewRecord) {
			AmOutStorage outStorage = new AmOutStorage();
			outStorage.setOutstorageCode(amOutStorage.getOutstorageCode());
			outStorage = amOutStorageService.get(outStorage);
			// 单据状态
			statusLabel = amUtilService.findDictLabel(outStorage.getBillStatus(), "am_document_status");
			amOutStorage.setStatusLabel(statusLabel);
			if ("1".equals(outStorage.getBillStatus())) {
				// 单据类型
				String typeLabel = amUtilService.findDictLabel(amOutStorage.getBillType(), "am_out_lib");
				amOutStorage.setTypeLabel(typeLabel);
				return "asset/consumables/amOutStorageExamine";
			}
		}
		return "asset/consumables/amOutStorageForm";
	}

	/**
	 * 保存耗材出库表
	 */
	@RequiresPermissions("consumables:amOutStorage:edit")
	@PostMapping(value = "save")
	@ResponseBody
	@Transactional(readOnly=false)
	public String save(AmOutStorage amOutStorage, String examine, boolean isNewRecord) {
		// 仓库编码
		String warehouseCode = amOutStorage.getWarehouseCode();
		// 仓库名称
		String warehouseName = amOutStorageService.findWarehouseName(warehouseCode);
		if (examine != null) {
			if (amOutStorage.getAmOutStorageDetailsList().size() <= 0 || amOutStorage.getAmOutStorageDetailsList() == null ) {
				return renderResult(Global.FALSE, "审核失败，明细为空");
			}
			String message = amUtilService.getSection();   // // 单据合法性检查
			if (!"".equals(message)) {
				return renderResult(Global.FALSE, message);
			}
		}
		if ("1".equals(examine) || examine == null) {
			//保存的时候判断仓位与仓库是否对应，验证仓库和仓位
			if (amOutStorage.getAmOutStorageDetailsList() != null && amOutStorage.getAmOutStorageDetailsList().size() > 0) {
				for (int i = 0; i < amOutStorage.getAmOutStorageDetailsList().size(); i++) {
					String localtionCode = amOutStorage.getAmOutStorageDetailsList().get(i).getLocationCode();
					AmLocation amLocation = new AmLocation();
					amLocation.setLocationCode(localtionCode);
					amLocation = locationService.get(amLocation);
					if (amLocation != null && !(amLocation.getWarehouseCode().equals(amOutStorage.getWarehouseCode()))) {
						return renderResult(Global.FALSE, "保存失败，仓库中找不到该仓位");
					}
					if (amLocation != null && !"0".equals(amLocation.getStatus())) {
						return renderResult(Global.FALSE, "保存失败，仓位已停用");
					}
				}
			}
			//判断仓库是否停用，如果停用，提示
			AmWarehouse amWarehouse = amWarehouseService.get(warehouseCode);
			if (amWarehouse != null && "2".equals(amWarehouse.getStatus())) {
				return renderResult(Global.FALSE, "保存失败，仓库已被停用");
			}
			String message = amOutStorageService.getoutStock(amOutStorage);
			if (!"".equals(message)) {
				return renderResult(Global.FALSE, message);
			}
			if (isNewRecord) {
				amOutStorage.setOutstorageCode(amSeqService.getCode(PER_FIX));
			}
			amOutStorageService.save(amOutStorage);
		}

		if (examine != null) {
			for (AmOutStorageDetails amOutStorageDetails : amOutStorage.getAmOutStorageDetailsList()) {
				AmConStock amConStock = new AmConStock();     // // 耗材库存表
				AmConLocationStock amConLocationStock = new AmConLocationStock();   // 库存库位表
				// 耗材编码
				String consumablesCode = amOutStorageDetails.getConsumablesCode();
				// 耗材名称
				String consumablesName = amOutStorageDetails.getConsumablesName();
				// 出库数量
				long outstorageCount = amOutStorageDetails.getOutstorageCount();
				amConStock.setWarehouseCode(warehouseCode);
				amConStock.setConsumablesCode(consumablesCode);
				amConStock = amConStockService.get(amConStock);
				// 耗材库存表
				if (amConStock != null) {
					if ("1".equals(examine) && (amConStock.getStock() - outstorageCount) < 0) {      // 审核
						return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减");
					} else if ("2".equals(examine) && (amConStock.getStock() + outstorageCount) < 0) {   //反审核
						return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减");
					}
				} else {
					if (!"2".equals(examine)) {
						return renderResult(Global.FALSE, "仓库" + warehouseName + "找不到耗材" + consumablesCode + consumablesName);
					}
				}
				amConLocationStock.setWarehouseCode(warehouseCode);
				// 库存库位表
				amConLocationStock.setConsumablesCode(consumablesCode);
				// 库位
				String locationCode = amOutStorageDetails.getLocationCode();
				amConLocationStock.setLocationCode(locationCode);
				amConLocationStock = amConLocationStockService.get(amConLocationStock);
				if (amConLocationStock != null) {
					if ("1".equals(examine) && (amConLocationStock.getStock() - outstorageCount) < 0) {
						return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减");
					} else if ("2".equals(examine) && (amConLocationStock.getStock() + outstorageCount) < 0) {
						return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减");
					}
				} else {
					if (!"2".equals(examine)) {
						String locationName = amConLocationStockService.findLocationName(locationCode);
						return renderResult(Global.FALSE, "仓库" + warehouseName + locationName + "库位找不到耗材" + consumablesCode + consumablesName);
					}
				}
			}
			try {
				amOutStorageService.saveData(amOutStorage, warehouseCode, examine, isNewRecord);
			}catch (Exception e) {
				return renderResult(Global.FALSE, "保存耗材出库表失败！");
			}
		}
		return renderResult(Global.TRUE, "保存耗材出库表成功！");
	}

//	/**
//	 * 停用耗材出库表
//	 */
//	@RequiresPermissions("consumables:amOutStorage:edit")
//	@RequestMapping(value = "disable")
//	@ResponseBody
//	public String disable(AmOutStorage amOutStorage) {
//		amOutStorage.setStatus(AmOutStorage.STATUS_DISABLE);
//		amOutStorageService.updateStatus(amOutStorage);
//		return renderResult(Global.TRUE, "停用耗材出库表成功");
//	}
//
//	/**
//	 * 启用耗材出库表
//	 */
//	@RequiresPermissions("consumables:amOutStorage:edit")
//	@RequestMapping(value = "enable")
//	@ResponseBody
//	public String enable(AmOutStorage amOutStorage) {
//		amOutStorage.setStatus(AmOutStorage.STATUS_NORMAL);
//		amOutStorageService.updateStatus(amOutStorage);
//		return renderResult(Global.TRUE, "启用耗材出库表成功");
//	}
	
	/**
	 * 删除耗材出库表
	 */
	@RequiresPermissions("consumables:amOutStorage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmOutStorage amOutStorage, String outStorageCode) {
		String message = amOutStorageService.deleteDB(amOutStorage, outStorageCode);
		if (!"".equals(message)) {
			return renderResult(Global.TRUE, message);
		}
		return renderResult(Global.TRUE, "删除耗材出库表成功！");

	}
}