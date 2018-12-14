/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.category.web;

import java.util.List;
import java.util.Map;

import com.jeesite.modules.asset.consumables.entity.AmInstorage;
import com.jeesite.modules.asset.consumables.entity.AmInstorageDetails;
import com.jeesite.modules.asset.consumables.entity.AmOutStorageDetails;
import com.jeesite.modules.asset.consumables.entity.Consumables;
import com.jeesite.modules.asset.consumables.service.AmInstorageService;
import com.jeesite.modules.asset.consumables.service.AmOutStorageService;
import com.jeesite.modules.asset.consumables.service.ConsumablesService;
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
import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.category.service.CategoryService;

/**
 * 耗材分类表Controller
 * @author czy
 * @version 2018-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/category/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ConsumablesService consumablesService;

	@Autowired
	private AmInstorageService amInstorageService;

	@Autowired
	private AmOutStorageService amOutStorageService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Category get(String categoryCode, boolean isNewRecord) {
		return categoryService.get(categoryCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("category:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Category category, Model model) {
		model.addAttribute("category", category);
		return "asset/category/categoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("category:category:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<Category> listData(Category category) {
		if (StringUtils.isBlank(category.getParentCode())) {
			category.setParentCode(Category.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(category.getCategoryCode())){
			category.setParentCode(null);
		}
		if (StringUtils.isNotBlank(category.getCategoryName())){
			category.setParentCode(null);
		}
		if (StringUtils.isNotBlank(category.getStatus())){
			category.setParentCode(null);
		}
		List<Category> list = categoryService.findList(category);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("category:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model, boolean flg) {
		// 创建并初始化下一个节点信息
		if (flg) {
			category = createNextNode(category);
		}
		model.addAttribute("category", category);
		return "asset/category/categoryForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("category:category:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public Category createNextNode(Category category) {
		if (StringUtils.isNotBlank(category.getParentCode())){
			category.setParent(categoryService.get(category.getParentCode()));
		}
		if (category.getIsNewRecord()) {
			Category where = new Category();
			where.setParentCode(category.getParentCode());
			Category last = categoryService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				category.setTreeSort(last.getTreeSort() + 30);
				category.setCategoryCode(IdGen.nextCode(last.getCategoryCode()));
			}else if (category.getParent() != null){
				category.setCategoryCode(category.getParent().getCategoryCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (category.getTreeSort() == null){
			category.setTreeSort(Category.DEFAULT_TREE_SORT);
		}
		return category;
	}

	/**
	 * 保存耗材分类表
	 */
	@RequiresPermissions("category:category:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Category category) {
//		categoryService.updateStatus(category);
		boolean rst=categoryService.getCategoryByName(category.getCategoryName(),category.getCategoryCode());
		if (rst){
			return renderResult(Global.FALSE, "已存在该分类名称！");
		}
		categoryService.save(category);
		return renderResult(Global.TRUE, "保存耗材分类表成功！");
	}
	/**
	 * 停用耗材分类表
	 */
	@RequiresPermissions("category:category:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Category category) {
		Category where = new Category();
		where.setStatus(Category.STATUS_NORMAL);
		where.setParentCodes("," + category.getId() + ",");
		long count = categoryService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, "该耗材分类表包含未停用的子耗材分类表！");
		}
		category.setStatus(Category.STATUS_DISABLE);
		categoryService.updateStatus(category);
		return renderResult(Global.TRUE, "停用耗材分类表成功");
	}
	
	/**
	 * 启用耗材分类表
	 */
	@RequiresPermissions("category:category:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Category category) {
		category.setStatus(Category.STATUS_NORMAL);
		categoryService.updateStatus(category);
		return renderResult(Global.TRUE, "启用耗材分类表成功");
	}
	
	/**
	 * 删除耗材分类表
	 */
	@RequiresPermissions("category:category:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Category category) {
		//删除前判断有没有对应的耗材
		Category category1=new Category();
		category1.setIsQueryChildren(true);
		category1.setCategoryCode(category.getCategoryCode());
		//验证耗材分类是否有对应的耗材
		Consumables consumables=new Consumables();
		consumables.setCategory(category1);
		List<Consumables> consumablesList=consumablesService.findList(consumables);
		if (consumablesList!=null&&consumablesList.size()>0){
			return renderResult(Global.FALSE, "删除耗材分类表失败！已有对应耗材");
		}
		//验证入库单是否应用耗材分类
		List<AmInstorageDetails> amInstorageDetailsList=amInstorageService.getDetailsByCategoryCode(category.getCategoryCode());
		if (amInstorageDetailsList!=null&&amInstorageDetailsList.size()>0){
			return renderResult(Global.FALSE, "删除耗材分类表失败！入库单已发生业务");
		}

		//验证出库单是否应用耗材分类
		List<AmOutStorageDetails> amOutStorageDetailsList=amOutStorageService.getDetailsByCategoryCode(category.getCategoryCode());
		if (amOutStorageDetailsList!=null&&amOutStorageDetailsList.size()>0){
			return renderResult(Global.FALSE, "删除耗材分类表失败！出库单已发生业务");
		}


		categoryService.delete(category);
		return renderResult(Global.TRUE, "删除耗材分类表成功！");
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("category:category:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<Category> list = categoryService.findList(new Category());
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			// 过滤非正常的数据
			if (!Category.STATUS_NORMAL.equals(e.getStatus())){
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
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getCategoryCode(), e.getCategoryName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("category:category:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(Category category){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		categoryService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}

}