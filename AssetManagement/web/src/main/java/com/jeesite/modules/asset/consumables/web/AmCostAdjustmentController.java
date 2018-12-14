/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.consumables.entity.AmTransfer;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.service.AmWarehouseService;
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
import com.jeesite.modules.asset.consumables.entity.AmCostAdjustment;
import com.jeesite.modules.asset.consumables.service.AmCostAdjustmentService;

import java.util.List;

/**
 * 耗材成本调整单Controller
 * @author dwh
 * @version 2018-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/consumables/amCostAdjustment")
public class AmCostAdjustmentController extends BaseController {

	@Autowired
	private AmCostAdjustmentService amCostAdjustmentService;
	@Autowired
	private AmWarehouseService amWarehouseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmCostAdjustment get(String documentCode, boolean isNewRecord) {
		return amCostAdjustmentService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("consumables:amCostAdjustment:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmCostAdjustment amCostAdjustment, Model model) {
		List<AmWarehouse> warehouseList = amWarehouseService.getWarehouseListByLeaf("1");
		model.addAttribute("warehouseList", warehouseList);
		model.addAttribute("amCostAdjustment", amCostAdjustment);
		return "asset/consumables/amCostAdjustmentList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("consumables:amCostAdjustment:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmCostAdjustment> listData(AmCostAdjustment amCostAdjustment, HttpServletRequest request, HttpServletResponse response) {
		Page<AmCostAdjustment> page = amCostAdjustmentService.findPage(new Page<AmCostAdjustment>(request, response), amCostAdjustment); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("consumables:amCostAdjustment:view")
	@RequestMapping(value = "form")
	public String form(AmCostAdjustment amCostAdjustment, Model model) {
		model.addAttribute("amCostAdjustment", amCostAdjustment);
		return "asset/consumables/amCostAdjustmentForm";
	}

	/**
	 * 保存耗材成本调整单
	 */
	@RequiresPermissions("consumables:amCostAdjustment:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save( AmCostAdjustment amCostAdjustment) {
		amCostAdjustmentService.save(amCostAdjustment);
		return renderResult(Global.TRUE, "保存耗材成本调整单成功！");
	}
	
	/**
	 * 删除耗材成本调整单
	 */
	@RequiresPermissions("consumables:amCostAdjustment:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmCostAdjustment amCostAdjustment) {
		amCostAdjustmentService.delete(amCostAdjustment);
		return renderResult(Global.TRUE, "删除耗材成本调整单成功！");
	}
	/**
	 * 物理删除入库表
	 */
	@RequiresPermissions("consumables:amTransfer:edit")
	@RequestMapping(value = "deleteDb")
	@ResponseBody
	public String deleteDb(AmCostAdjustment amCostAdjustment, String ids) {
		boolean isShStutrs=  amCostAdjustmentService.deleteDbs(amCostAdjustment,ids);
		if (isShStutrs == true) {
			return renderResult(Global.TRUE, "删除成功！已审核单据未被删除");
		} else {
			return renderResult(Global.TRUE, "删除成本调整单成功！");
		}

	}
}