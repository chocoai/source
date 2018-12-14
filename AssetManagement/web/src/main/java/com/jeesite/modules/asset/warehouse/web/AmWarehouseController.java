/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehouse.web;

import java.util.List;
import java.util.Map;

import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.amlocation.service.AmLocationService;
import com.jeesite.modules.asset.consumables.entity.AmInstorage;
import com.jeesite.modules.asset.consumables.entity.AmOutStorage;
import com.jeesite.modules.asset.consumables.service.AmInstorageService;
import com.jeesite.modules.asset.consumables.service.AmOutStorageService;
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
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.service.AmWarehouseService;

/**
 * 仓库Controller
 * @author dwh
 * @version 2018-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/warehouse/amWarehouse")
public class AmWarehouseController extends BaseController {

	@Autowired
	private AmWarehouseService amWarehouseService;
	@Autowired
	private AmLocationService amLocationService;
	@Autowired
	private AmInstorageService amInstorageService;
	@Autowired
	private AmOutStorageService amOutStorageService;


	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmWarehouse get(String warehouseCode, boolean isNewRecord) {
		return amWarehouseService.get(warehouseCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("warehouse:amWarehouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmWarehouse amWarehouse, Model model) {
		model.addAttribute("amWarehouse", amWarehouse);
		return "asset/warehouse/amWarehouseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("warehouse:amWarehouse:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<AmWarehouse> listData(AmWarehouse amWarehouse) {
		if (StringUtils.isBlank(amWarehouse.getParentCode())) {
			amWarehouse.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amWarehouse.getWarehouseCode())){
			amWarehouse.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amWarehouse.getWarehouseName())){
			amWarehouse.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amWarehouse.getStatus())){
			amWarehouse.setParentCode(null);
		}
		List<AmWarehouse> list = amWarehouseService.findList(amWarehouse);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("warehouse:amWarehouse:view")
	@RequestMapping(value = "form")
	public String form(AmWarehouse amWarehouse, Model model,boolean isNew) {
		if (!isNew){
		// 创建并初始化下一个节点信息
		amWarehouse = createNextNode(amWarehouse);
		}
		model.addAttribute("amWarehouse", amWarehouse);
		return "asset/warehouse/amWarehouseForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("warehouse:amWarehouse:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public AmWarehouse createNextNode(AmWarehouse amWarehouse) {
		if (StringUtils.isNotBlank(amWarehouse.getParentCode())){
			amWarehouse.setParent(amWarehouseService.get(amWarehouse.getParentCode()));
		}
		if (amWarehouse.getIsNewRecord()) {
			AmWarehouse where = new AmWarehouse();
			where.setParentCode(amWarehouse.getParentCode());
			AmWarehouse last = amWarehouseService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				amWarehouse.setTreeSort(last.getTreeSort() + 30);
				amWarehouse.setWarehouseCode(IdGen.nextCode(last.getWarehouseCode()));
			}else if (amWarehouse.getParent() != null){
				amWarehouse.setWarehouseCode(amWarehouse.getParent().getWarehouseCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (amWarehouse.getTreeSort() == null){
			amWarehouse.setTreeSort(AmWarehouse.DEFAULT_TREE_SORT);
		}
		return amWarehouse;
	}

	/**
	 * 保存仓库
	 */
	@RequiresPermissions("warehouse:amWarehouse:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmWarehouse amWarehouse) {
		amWarehouseService.save(amWarehouse);
		return renderResult(Global.TRUE, "保存仓库成功！");
	}
	
	/**
	 * 停用仓库
	 */
	@RequiresPermissions("warehouse:amWarehouse:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AmWarehouse amWarehouse) {
		AmWarehouse where = new AmWarehouse();
		where.setStatus(AmWarehouse.STATUS_NORMAL);
		where.setParentCodes("," + amWarehouse.getId() + ",");
		long count = amWarehouseService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, "该仓库包含未停用的子仓库！");
		}
		amWarehouse.setStatus(AmWarehouse.STATUS_DISABLE);
		amWarehouseService.updateStatus(amWarehouse);
		return renderResult(Global.TRUE, "停用仓库成功");
	}
	
	/**
	 * 启用仓库
	 */
	@RequiresPermissions("warehouse:amWarehouse:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AmWarehouse amWarehouse) {
		amWarehouse.setStatus(AmWarehouse.STATUS_NORMAL);
		amWarehouseService.updateStatus(amWarehouse);
		return renderResult(Global.TRUE, "启用仓库成功");
	}
	
	/**
	 * 删除仓库
	 */
	@RequiresPermissions("warehouse:amWarehouse:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmWarehouse amWarehouse) {
		AmWarehouse amWarehouse1=new AmWarehouse();
		amWarehouse1.setIsQueryChildren(true);
		amWarehouse1.setWarehouseCode(amWarehouse.getWarehouseCode());
		//删除父节点和当前节点都需判断
		AmLocation amLocation=new AmLocation();
		amLocation.setAmWarehouse(amWarehouse1);
		List<AmLocation>locationList=amLocationService.findList(amLocation);
		if (locationList!=null&&locationList.size()>0){
			return renderResult(Global.FALSE, "删除仓库失败！已设置仓位");
		}
		AmInstorage amInstorage=new AmInstorage();
		amInstorage.setAmWarehouse(amWarehouse1);
		List<AmInstorage> amInstorageList=amInstorageService.findList(amInstorage);
		if (amInstorageList!=null&&amInstorageList.size()>0){
			return renderResult(Global.FALSE, "删除仓库失败！入库单已产生业务");
		}
		AmOutStorage amOutStorage=new AmOutStorage();
		amOutStorage.setAmWarehouse(amWarehouse1);
		List<AmOutStorage> amOutStorageList=amOutStorageService.findList(amOutStorage);
		if (amOutStorageList!=null&&amOutStorageList.size()>0){
			return renderResult(Global.FALSE, "删除仓库失败！出库单已产生业务");
		}

		amWarehouseService.delete(amWarehouse);
		return renderResult(Global.TRUE, "删除仓库成功！");
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("warehouse:amWarehouse:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<AmWarehouse> list = amWarehouseService.findList(new AmWarehouse());
		for (int i=0; i<list.size(); i++){
			AmWarehouse e = list.get(i);
			// 过滤非正常的数据
			if (!AmWarehouse.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getWarehouseCode(), e.getWarehouseName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("warehouse:amWarehouse:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(AmWarehouse amWarehouse){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		amWarehouseService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	
}