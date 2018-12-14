/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.classify.web;

import java.util.List;
import java.util.Map;

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
import com.jeesite.modules.asset.classify.entity.AmClassify;
import com.jeesite.modules.asset.classify.service.AmClassifyService;

/**
 * 资产分类表Controller
 * @author czy
 * @version 2018-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/classify/amClassify")
public class AmClassifyController extends BaseController {

	@Autowired
	private AmClassifyService amClassifyService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmClassify get(String classifyCode, boolean isNewRecord) {
		return amClassifyService.get(classifyCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("classify:amClassify:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmClassify amClassify, Model model) {
		model.addAttribute("amClassify", amClassify);
		return "asset/classify/amClassifyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("classify:amClassify:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<AmClassify> listData(AmClassify amClassify) {
		if (StringUtils.isBlank(amClassify.getParentCode())) {
			amClassify.setParentCode(AmClassify.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(amClassify.getClassifyCode())){
			amClassify.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amClassify.getClassifyName())){
			amClassify.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amClassify.getStatus())){
			amClassify.setParentCode(null);
		}
		List<AmClassify> list = amClassifyService.findList(amClassify);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("classify:amClassify:view")
	@RequestMapping(value = "form")
	public String form(AmClassify amClassify, Model model, boolean flg) {
		if (flg) {
			// 创建并初始化下一个节点信息
			amClassify = createNextNode(amClassify);
		}
		model.addAttribute("amClassify", amClassify);
		return "asset/classify/amClassifyForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("classify:amClassify:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public AmClassify createNextNode(AmClassify amClassify) {
		if (StringUtils.isNotBlank(amClassify.getParentCode())){
			amClassify.setParent(amClassifyService.get(amClassify.getParentCode()));
		}
		if (amClassify.getIsNewRecord()) {
			AmClassify where = new AmClassify();
			where.setParentCode(amClassify.getParentCode());
			AmClassify last = amClassifyService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				amClassify.setTreeSort(last.getTreeSort() + 30);
				amClassify.setClassifyCode(IdGen.nextCode(last.getClassifyCode()));
			}else if (amClassify.getParent() != null){
				amClassify.setClassifyCode(amClassify.getParent().getClassifyCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (amClassify.getTreeSort() == null){
			amClassify.setTreeSort(AmClassify.DEFAULT_TREE_SORT);
		}
		return amClassify;
	}

	/**
	 * 保存资产分类表
	 */
	@RequiresPermissions("classify:amClassify:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmClassify amClassify) {
		amClassifyService.updateStatus(amClassify);
		amClassifyService.save(amClassify);
		return renderResult(Global.TRUE, "保存资产分类表成功！");
	}
	
	/**
	 * 删除资产分类表
	 */
	@RequiresPermissions("classify:amClassify:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmClassify amClassify) {
		amClassifyService.delete(amClassify);
		return renderResult(Global.TRUE, "删除资产分类表成功！");
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("classify:amClassify:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<AmClassify> list = amClassifyService.findList(new AmClassify());
		for (int i=0; i<list.size(); i++){
			AmClassify e = list.get(i);
			// 过滤非正常的数据
			if (!AmClassify.STATUS_NORMAL.equals(e.getStatus())){
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
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getClassifyCode(), e.getClassifyName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("classify:amClassify:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(AmClassify amClassify){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		amClassifyService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}

	/**
	 * 停用耗材分类表
	 */
	@RequiresPermissions("classify:amClassify:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AmClassify amclassify) {
		AmClassify where = new AmClassify();
		where.setStatus(AmClassify.STATUS_NORMAL);
		where.setParentCodes("," + amclassify.getId() + ",");
		long count = amClassifyService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, "该耗材分类表包含未停用的子耗材分类表！");
		}
		amclassify.setStatus(AmClassify.STATUS_DISABLE);
		amClassifyService.updateStatus(amclassify);
		return renderResult(Global.TRUE, "停用耗材分类表成功");
	}

	/**
	 * 启用耗材分类表
	 */
	@RequiresPermissions("classify:amClassify:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AmClassify amclassify) {
		amclassify.setStatus(AmClassify.STATUS_NORMAL);
		amClassifyService.updateStatus(amclassify);
		return renderResult(Global.TRUE, "启用耗材分类表成功");
	}
}