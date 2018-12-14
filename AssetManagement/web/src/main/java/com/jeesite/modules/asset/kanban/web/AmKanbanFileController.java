/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jeesite.modules.asset.kanban.entity.AmKbAmpersonnel;
import com.jeesite.modules.asset.kanban.service.AmKbAmpersonnelService;
import com.jeesite.modules.asset.util.ParamentUntil;
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
import com.jeesite.modules.asset.kanban.entity.AmKanbanFile;
import com.jeesite.modules.asset.kanban.service.AmKanbanFileService;

/**
 * 看板档案Controller
 * @author dwh
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/kanban/amKanbanFile")
public class AmKanbanFileController extends BaseController {
	@Autowired
	private AmKbAmpersonnelService amKbAmpersonnelService;
	@Autowired
	private AmKanbanFileService amKanbanFileService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmKanbanFile get(String kanbanCode, boolean isNewRecord) {
		return amKanbanFileService.get(kanbanCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("kanban:amKanbanFile:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmKanbanFile amKanbanFile, Model model) {
		model.addAttribute("amKanbanFile", amKanbanFile);
		return "asset/kanban/amKanbanFileList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("kanban:amKanbanFile:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<AmKanbanFile> listData(AmKanbanFile amKanbanFile) {
		if (StringUtils.isBlank(amKanbanFile.getParentCode())) {
			amKanbanFile.setParentCode(AmKanbanFile.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(amKanbanFile.getKanbanName())){
			amKanbanFile.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amKanbanFile.getOfficeName())){
			amKanbanFile.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amKanbanFile.getUrl())){
			amKanbanFile.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amKanbanFile.getStatus())){
			amKanbanFile.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amKanbanFile.getRemarks())){
			amKanbanFile.setParentCode(null);
		}
		if (StringUtils.isNotBlank(amKanbanFile.getOfficeCode())){
			amKanbanFile.setParentCode(null);
		}
		List<AmKanbanFile> list = amKanbanFileService.findList(amKanbanFile);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("kanban:amKanbanFile:view")
	@RequestMapping(value = "form")
	public String form(AmKanbanFile amKanbanFile, Model model) {
		// 创建并初始化下一个节点信息
		amKanbanFile = createNextNode(amKanbanFile);
		model.addAttribute("amKanbanFile", amKanbanFile);
		return "asset/kanban/amKanbanFileForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("kanban:amKanbanFile:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public AmKanbanFile createNextNode(AmKanbanFile amKanbanFile) {
		if (StringUtils.isNotBlank(amKanbanFile.getParentCode())){
			amKanbanFile.setParent(amKanbanFileService.get(amKanbanFile.getParentCode()));
		}
		if (amKanbanFile.getIsNewRecord()) {
			AmKanbanFile where = new AmKanbanFile();
			where.setParentCode(amKanbanFile.getParentCode());
			AmKanbanFile last = amKanbanFileService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				amKanbanFile.setTreeSort(last.getTreeSort() + 30);
				amKanbanFile.setKanbanCode(IdGen.nextCode(last.getKanbanCode()));
			}else if (amKanbanFile.getParent() != null){
				amKanbanFile.setKanbanCode(amKanbanFile.getParent().getKanbanCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (amKanbanFile.getTreeSort() == null){
			amKanbanFile.setTreeSort(AmKanbanFile.DEFAULT_TREE_SORT);
		}
		return amKanbanFile;
	}

	/**
	 * 保存看板档案
	 */
	@RequiresPermissions("kanban:amKanbanFile:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmKanbanFile amKanbanFile) {
		boolean rst=amKanbanFileService.getAmKanbanFileByNameAndCode(amKanbanFile.getKanbanName(),amKanbanFile.getKanbanCode());
		if (rst){
			return renderResult(Global.FALSE, "已存在该分类名称！");
		}
		amKanbanFileService.save(amKanbanFile);
		return renderResult(Global.TRUE, "保存看板档案成功！");
	}
	
	/**
	 * 停用看板档案
	 */
	@RequiresPermissions("kanban:amKanbanFile:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AmKanbanFile amKanbanFile) {
		AmKanbanFile where = new AmKanbanFile();
		where.setStatus(AmKanbanFile.STATUS_NORMAL);
		where.setParentCodes("," + amKanbanFile.getId() + ",");
		long count = amKanbanFileService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, "该看板档案包含未停用的子看板档案！");
		}
		amKanbanFile.setStatus(AmKanbanFile.STATUS_DISABLE);
		amKanbanFileService.updateStatus(amKanbanFile);
		return renderResult(Global.TRUE, "停用看板档案成功");
	}
	
	/**
	 * 启用看板档案
	 */
	@RequiresPermissions("kanban:amKanbanFile:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AmKanbanFile amKanbanFile) {
		amKanbanFile.setStatus(AmKanbanFile.STATUS_NORMAL);
		amKanbanFileService.updateStatus(amKanbanFile);
		return renderResult(Global.TRUE, "启用看板档案成功");
	}
	
	/**
	 * 删除看板档案
	 */
	@RequiresPermissions("kanban:amKanbanFile:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmKanbanFile amKanbanFile) {
		//删除前判断是否有有效人员列表
		AmKbAmpersonnel ampersonnel=new AmKbAmpersonnel();
		ampersonnel.setKanbanCode(amKanbanFile.getKanbanCode());
		List<AmKbAmpersonnel> amKbAmpersonnels= amKbAmpersonnelService.findList(ampersonnel);
		if (ParamentUntil.isBackList(amKbAmpersonnels)){
			return renderResult(Global.FALSE, "删除失败！已有看板有效人员");
		}
		//选中要删除的节点校验完后，校验删除时删除的子数据，校验完删除，删除时判断上级不是父节点时，父节点下是否存在子节点，修改末级节点
		AmKanbanFile amKanbanFile1=new AmKanbanFile();
		amKanbanFile1.setParentCodes(amKanbanFile.getKanbanCode());
		List<AmKanbanFile> files=amKanbanFileService.findList(amKanbanFile1);
		if(ParamentUntil.isBackList(files)){
			for (int i=0;i<files.size();i++){
				//删除前判断是否有有效人员列表
				AmKbAmpersonnel ampersonne2=new AmKbAmpersonnel();
				ampersonne2.setKanbanCode(files.get(i).getKanbanCode());
				List<AmKbAmpersonnel> amKbAmpersonnels1= amKbAmpersonnelService.findList(ampersonne2);
				if (ParamentUntil.isBackList(amKbAmpersonnels1)){
					return renderResult(Global.FALSE, "删除失败！已有看板有效人员");
				}
			}
			for (int j=0;j<files.size();j++){
				amKanbanFileService.delete(files.get(j));
				updateTreeLeaf(files.get(j));
			}
		}

		amKanbanFileService.delete(amKanbanFile);
		updateTreeLeaf(amKanbanFile);
		return renderResult(Global.TRUE, "删除看板档案成功！");
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("kanban:amKanbanFile:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<AmKanbanFile> list = amKanbanFileService.findList(new AmKanbanFile());
		for (int i=0; i<list.size(); i++){
			AmKanbanFile e = list.get(i);
			// 过滤非正常的数据
			if (!AmKanbanFile.STATUS_NORMAL.equals(e.getStatus())){
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
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getKanbanCode(), e.getKanbanName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("kanban:amKanbanFile:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(AmKanbanFile amKanbanFile){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		amKanbanFileService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	//删除完更新最末级节点
	private void updateTreeLeaf(AmKanbanFile amKanbanFile){
		//根据父节点去查上一级
		if(!("0".equals(amKanbanFile.getParentCode()))){
			AmKanbanFile amKanbanFile2=new AmKanbanFile();
			amKanbanFile2.setKanbanCode(amKanbanFile.getParentCode());
			amKanbanFile2=amKanbanFileService.get(amKanbanFile2);
			if (amKanbanFile2!=null){
				//查询父节点下是还存在子节点
				AmKanbanFile amKanbanFile3=new AmKanbanFile();
				amKanbanFile3.setParentCodes(amKanbanFile2.getKanbanCode());
				List<AmKanbanFile> filesList=amKanbanFileService.findList(amKanbanFile3);
				if (filesList==null||filesList.size()<=0){
					//如果没有子节点跟新父节点的是否最末级
					amKanbanFile2.setTreeLeaf("1");
					amKanbanFileService.save(amKanbanFile2);
				}

			}
		}
	}
	
}