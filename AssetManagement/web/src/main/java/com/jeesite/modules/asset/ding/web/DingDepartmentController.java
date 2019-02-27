/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.web;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.ding.data.Node;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.TreeUtil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * js_ding_departmentController
 * @author scarlett
 * @version 2018-09-20
 */
//TODO: Restructure
@Controller
@RequestMapping(value = "${adminPath}/fz/ding/dingDepartment")
public class DingDepartmentController extends BaseController {

	@Autowired
	private DingDepartmentService dingDepartmentService;
	@Autowired
	private DingUserService dingUserService;
	@Resource
	private RedisUtil<String, List> redisList;

	@Autowired
	private DingUserDepartmentService dingUserDepartmentService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DingDepartment get(String departmentId, boolean isNewRecord) {
		return dingDepartmentService.get(departmentId, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("ding:dingDepartment:view")
	@RequestMapping(value = {"list", ""})
	public String list(DingDepartment dingDepartment, Model model) {
		model.addAttribute("dingDepartment", dingDepartment);
		return "asset/ding/dingDepartmentList";
	}
	/**
	 * 弹窗
	 */
	@RequestMapping(value = {"depetSelect", ""})
	public String listSelect(DingDepartment dingDepartment, Model model, String selectData,String checkbox) {
		model.addAttribute("selectData", selectData);
		model.addAttribute("dingUser", dingDepartment);
		model.addAttribute("checkbox", checkbox);
		return "asset/ding/departmentSelect";
	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("ding:dingDepartment:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<DingDepartment> listData(DingDepartment dingDepartment) {
		if (StringUtils.isBlank(dingDepartment.getParentCode())) {
			dingDepartment.setParentCode(DingDepartment.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(dingDepartment.getName())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getParentid())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getOrder())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getCreateDeptGroup())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getAutoAddUser())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getDeptHiding())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getDeptPermits())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getUserPermits())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getOuterDept())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getOuterPermitPepts())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getOuterPermitUsers())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getOrgDeptOwner())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getDeptManagerUseridList())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getSourceIdentifier())){
			dingDepartment.setParentCode(null);
		}
		if (StringUtils.isNotBlank(dingDepartment.getGroupContainSubdept())){
			dingDepartment.setParentCode(null);
		}
		List<DingDepartment> list = dingDepartmentService.findList(dingDepartment);
		if(list!=null &&list.size()>0){
			int j=0;
			for(DingDepartment dingDepartment1:list){
				j+=1;
				String parentid=dingDepartment1.getParentid();
				DingDepartment dingDepartment2=new DingDepartment();
				dingDepartment2.setDepartmentId(parentid);
				dingDepartment2=dingDepartmentService.get(dingDepartment2);
				if(dingDepartment2!=null){
					dingDepartment1.setPriDeptName(dingDepartment2.getName());
				}else {
					dingDepartment1.setPriDeptName("无");
				}
				String managerStr=dingDepartment1.getDeptManagerUseridList();
				if(managerStr!=null &&managerStr.length()>0){
					String managerString[]=managerStr.split("\\|");
					String managename="";
					if(managerString!=null &&managerString.length>0){
						for(String str:managerString){
							DingUser dingUser=new DingUser();
							dingUser.setUserid(str);
							dingUser=dingUserService.get(dingUser);
							if(dingUser!=null){
								//dingDepartment1.setManagerName(dingUser.getName());
								managename=managename+dingUser.getName()+",";
							}

						}
						if(managename.endsWith(",")){
							managename=managename.substring(0,managename.length()-1);
						}
						dingDepartment1.setManagerName(managename);
					}else{
						dingDepartment1.setManagerName("无");
					}
				}
			}
		}
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("ding:dingDepartment:view")
	@RequestMapping(value = "form")
	public String form(DingDepartment dingDepartment, Model model) {
		// 创建并初始化下一个节点信息
		dingDepartment = createNextNode(dingDepartment);
		model.addAttribute("dingDepartment", dingDepartment);
		return "asset/ding/dingDepartmentForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("ding:dingDepartment:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public DingDepartment createNextNode(DingDepartment dingDepartment) {
		if (StringUtils.isNotBlank(dingDepartment.getParentCode())){
			dingDepartment.setParent(dingDepartmentService.get(dingDepartment.getParentCode()));
		}
		if (dingDepartment.getIsNewRecord()) {
			DingDepartment where = new DingDepartment();
			where.setParentCode(dingDepartment.getParentCode());
			DingDepartment last = dingDepartmentService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				dingDepartment.setTreeSort(last.getTreeSort() + 30);
				dingDepartment.setDepartmentId(IdGen.nextCode(last.getDepartmentId()));
			}else if (dingDepartment.getParent() != null){
				dingDepartment.setDepartmentId(dingDepartment.getParent().getDepartmentId() + "001");
			}
		}
		// 以下设置表单默认数据
		if (dingDepartment.getTreeSort() == null){
			dingDepartment.setTreeSort(DingDepartment.DEFAULT_TREE_SORT);
		}
		return dingDepartment;
	}

	/**
	 * 保存js_ding_department,同步钉钉信息
	 */
	@RequiresPermissions("ding:dingDepartment:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DingDepartment dingDepartment) {
		dingDepartmentService.save(dingDepartment);
		return renderResult(Global.TRUE, text("保存js_ding_department成功！"));
	}
	
	/**
	 * 删除js_ding_department
	 */
	@RequiresPermissions("ding:dingDepartment:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DingDepartment dingDepartment) {
		dingDepartmentService.delete(dingDepartment);
		return renderResult(Global.TRUE, text("删除js_ding_department成功！"));
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("ding:dingDepartment:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<DingDepartment> list = dingDepartmentService.findList(new DingDepartment());
		for (int i=0; i<list.size(); i++){
			DingDepartment e = list.get(i);
			// 过滤非正常的数据
			/*if (!DingDepartment.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}*/
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
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getDepartmentId(), e.getName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("ding:dingDepartment:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(DingDepartment dingDepartment){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		dingDepartmentService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	/**
	 * 一次性插入所有的部门信息
	 */
	@PostMapping(value = "departmentList")

	@ResponseBody
	public String departmentList(@RequestBody String department) {
		dingDepartmentService.sysAllDepartment(department);
		return renderResult(Global.TRUE, "更新部门表成功");
	}
	/**
	 * 部门接口
	 */
/*	@RequestMapping(value = "getDepartments")
	@ResponseBody
	public ReturnInfo getdepartmentList() {
		DingDepartment dingDepartment = new DingDepartment();
		*//*dingDepartment.setParentCode("0");
		dingDepartment = dingDepartmentService.get(dingDepartment);*//*
		List<Map<String,Object>> list=new ArrayList<>();
		List<DingDepartment> departmentList=dingDepartmentService.findList(dingDepartment);
			if (departmentList != null && departmentList.size() > 0) {
				for (int i = 0; i < departmentList.size(); i++) {
					DingDepartment dingDepartment1= departmentList.get(i);
					Map<String,Object> map=new HashMap<>();
					map.put("departmentId",dingDepartment1.getDepartmentId());
					map.put("name",dingDepartment1.getName());
					map.put("parentCode",dingDepartment1.getParentCode());
					map.put("treeLeaf",dingDepartment1.getTreeLeaf());
					map.put("treeLevel",dingDepartment1.getTreeLevel());
					*//*List<DingUser> userCounts=dingUserService.selectUserCountByDepartment(dingDepartment1.getDepartmentId());
					int count=0;
					if(userCounts!=null &&userCounts.size()>0){
						count=userCounts.size();
					}
					map.put("userCount",count);*//*
					String deptManagerUseridList=dingDepartment1.getDeptManagerUseridList();
					if(deptManagerUseridList!=null) {
						String str[] = deptManagerUseridList.split("\\|");
						if(str!=null &&str.length>0){
							List<DingUser> userList=new ArrayList<>();
							for(String s:str){
								DingUser dingUser=new DingUser();
								dingUser.setUserid(s);
								dingUser=dingUserService.get(dingUser);
								if(dingUser!=null){
									userList.add(dingUser);
								}
							}
							map.put("deptManagerUseridList",userList);
						}
					}else {
						map.put("deptManagerUseridList","");
					}
					List<String> stringList=dingDepartmentService.getDingDepartmentByParentCode(dingDepartment1.getDepartmentId());
					if(stringList!=null &&stringList.size()>0){
						String str="";
						for(int k=0;k<stringList.size();k++){
							str=str+stringList.get(k)+",";
						}
						map.put("childCodes",str);
					}

					list.add(map);
				}
			}
		return ReturnDate.success(0, "请求成功", list);
	}*/
//	@PostMapping(value = "getDepartments")
//	@AccessLimit(limit = 1,sec = 2)
//	@IsFileter(isFile="true")
//	@ResponseBody
	//TODO: remove
	public ReturnInfo getdepartmentList(HttpServletRequest request) {
		String departmentId = request.getParameter("departmentId");
		String parentid = "";
		if (departmentId == null) {
			parentid = dingDepartmentService.getRootCode();
		} else {
			parentid = departmentId;
		}
		DingDepartment dingDepartment = new DingDepartment();
		dingDepartment.setDepartmentId(parentid);
		dingDepartment = dingDepartmentService.get(dingDepartment);
		if (dingDepartment != null) {
			Map<String, Object> parentmap = new HashMap<>();
			parentmap.put("departmentId", dingDepartment.getDepartmentId());
			parentmap.put("name", dingDepartment.getName());
			parentmap.put("parentCode", dingDepartment.getParentCode());
			parentmap.put("treeLeaf", dingDepartment.getTreeLeaf());
			parentmap.put("treeLevel", dingDepartment.getTreeLevel());
			parentmap.put("deptManagerList", getDeptManagerUseridList(dingDepartment.getDeptManagerUseridList()));
			parentmap.put("userList", dingUserService.getUserListByDepartmentId(parentid));
			List<DingUser> dingUserList=dingUserService.selectUserCountByDepartment(parentid);
			int count=0;
			if(dingUserList!=null &&dingUserList.size()>0){
				count=dingUserList.size();
			}
			parentmap.put("userCount",count );
			List<DingDepartment> childrenList = dingDepartmentService.getChiddeptsByParentCode(parentid);
			List<Map<String, Object>> list = new ArrayList<>();
			if (childrenList != null && childrenList.size() > 0) {
				for (int i = 0; i < childrenList.size(); i++) {
					DingDepartment dingDepartment1 = childrenList.get(i);
					Map<String, Object> map = new HashMap<>();
					map.put("departmentId", dingDepartment1.getDepartmentId());
					map.put("name", dingDepartment1.getName());
					map.put("parentCode", dingDepartment1.getParentCode());
					map.put("treeLeaf", dingDepartment1.getTreeLeaf());
					map.put("treeLevel", dingDepartment1.getTreeLevel());
					//map.put("deptManagerList", getDeptManagerUseridList(dingDepartment1.getDeptManagerUseridList()));
					List<DingUser> childUserList=dingUserService.selectUserCountByDepartment(dingDepartment1.getDepartmentId());
					int count1=0;
					if(childUserList!=null &&childUserList.size()>0){
						count1=childUserList.size();
					}
					//map.put("userList", dingUserService.getUserListByDepartmentId( dingDepartment1.getDepartmentId()));
					map.put("userCount", count1);
					list.add(map);
				}
			}
			parentmap.put("childdept",list );
			return ReturnDate.success(0, "请求成功",parentmap);
		}else {
			return ReturnDate.success(10001, "请求成功","该部门不存在");
		}

	}
/**
 * 更新部门人数
 */
   @PostMapping(value = "updateDepartmenUserCount")
   @ResponseBody
public ReturnInfo updateDepartmentUserCount() {
	   DingDepartment department = new DingDepartment();
//	   department.setDepartmentId("65014303");
	   List<DingDepartment> departmentList = dingDepartmentService.findList(department);
	   for (DingDepartment dingDepartment : departmentList) {
		  // List<DingUser> userList = dingUserService.getUserListByDepartmentId(dingDepartment.getDepartmentId());
		   List<DingUser> userList =dingUserService.selectUserCountByDepartment(dingDepartment.getDepartmentId());
		   Long userCount = 0L;
		   if (userList != null && userList.size() > 0) {
			  userCount = Long.valueOf(userList.size());
		   }
		   dingDepartmentService.updateDepartmentUserCount(userCount,dingDepartment.getDepartmentId());
	   }
	   return ReturnDate.success(0, "请求成功", "成功更新部门人数");
   }
	/**
	 * 获取部门管理人员
	 * @param deptManagerUseridList
	 * @return
	 */
	public List<DingUser> getDeptManagerUseridList(String deptManagerUseridList) {
		List<DingUser> managerList = new ArrayList<>();
		if (deptManagerUseridList != null) {
			String str[] = deptManagerUseridList.split("\\|");
			if (str != null && str.length > 0) {
				for (String s : str) {
					DingUser dingUser = new DingUser();
					dingUser.setUserid(s);
					dingUser = dingUserService.get(dingUser);
					if (dingUser != null) {
						managerList.add(dingUser);
					}
				}
			}
		}
		return managerList;
	}
	/**
	 * 更新是否为末节点
	 */
	@PostMapping(value = "updateTreeLeaf")
	@ResponseBody
	public ReturnInfo updateTreeLeaf(){
		List<DingDepartment> list=dingDepartmentService.findList(new DingDepartment());
		for(DingDepartment dingDepartment:list){
			List<String> strs=dingDepartmentService.getDingDepartmentByParentCode(dingDepartment.getDepartmentId());
			if(strs==null ||strs.size()<=0){
				dingDepartment.setTreeLeaf("1");
			}else{
				dingDepartment.setTreeLeaf("0");
			}
			dingDepartmentService.update(dingDepartment);
		}
		return ReturnDate.success(0, "请求成功","更新是否为末节点成功");
	}


//	@Scheduled(fixedRate = 10000 * 60)
////	@Scheduled(cron = "0 0/60 * * * ?")
//	public void toCache() {
//		String path = K3Config.BASEDIR +  "/" + FzTask.sendMsgFlag + "/testTask.txt";
//		String flag = ReadFile.ReadToString(path);
//		if (!"true".equals(flag)) {
//			return;
//		}
//		// 查出所有部门
//		List<DepartmentData> departmentList = dingDepartmentService.getDepartment();
//		redisTemplate.set("dingDepartment" + Variable.dataBase + Variable.RANDOMID, departmentList);
//		// 查出所员工(包括在职和离职的，用于后续查询自己的赞赏记录中)
//		List<DingUser> dingUserList = dingUserService.findList(new DingUser());
//		redisTemplate.set("dingUser" + Variable.dataBase + Variable.RANDOMID, dingUserList);
//		// 查出所有部门id和员工id
//		List<DingUserDepartment> dingUserDepartmentList = dingUserDepartmentService.selectByLeft();
////		List<DingUserDepartment> dingUserDepartmentList = dingUserDepartmentService.findList(new DingUserDepartment());
//		redisTemplate.set("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID, dingUserDepartmentList);
//	}

	/**
	 * 部门人员接口
	 * @param request
	 * @return
	 */
	@PostMapping(value = "getDepartments")
	//@AccessLimit(limit = 1,sec = 2)
	@IsFileter(isFile="true")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ReturnInfo getdepartment(HttpServletRequest request) {
		String departmentId = request.getParameter("departmentId");
		String parentid;
		if (departmentId == null) {
			parentid = dingDepartmentService.getRootCode();
		} else {
			parentid = departmentId;
		}
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取部门用户中间表的数据
		List<DingUserDepartment> dingUserDepartmentList1 = redisList.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取所有用户
		List<DingUser> dingUserList1 = redisList.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		// 根据部门编码获取所有下级部门
		List<DepartmentData> departList = ListUtils.newArrayList();
		List<DingUser> deptManagerList = ListUtils.newArrayList();
		DepartmentData department = new DepartmentData();
		for (DepartmentData dingDepartment : departmentList) {
			if (parentid.equals(dingDepartment.getParentId())) {
				departList.add(dingDepartment);
			}
			// 根据部门编码获取对象
			if (parentid.equals(dingDepartment.getDepartmentId())) {
				department = dingDepartment;
			}
		}
		if (department != null) {
			String managerUser = department.getManagerUser();
			if (managerUser != null && !"".equals(managerUser.trim())) {
				if (managerUser.contains("|")) {
					String[] manager = managerUser.split("\\|");
					for (int i = 0; i < manager.length; i++) {
						String managerCode = manager[i];
						getUserList(dingUserList1, managerCode, deptManagerList);
					}
				} else {
					getUserList(dingUserList1, managerUser, deptManagerList);
				}
			}
		}

//		DingDepartment dingDepartment = departmentList.stream().filter(s ->s.getParentId().equals(parentid)).findFirst().get();
		// 直接在部门下的人员
		List<DepartmentData> userList = departmentList.stream().filter(s -> s.getDepartmentId().equals(parentid)).collect(Collectors.toList());
		List<DingUserDepartment> userList1 = ListUtils.newArrayList();

		for (DepartmentData departmentData : userList) {
			userList1 = dingUserDepartmentList1.stream().filter(s -> s.getDepartmentId().equals(departmentData.getDepartmentId())).collect(Collectors.toList());
		}
		List<DingUser> userList2 = ListUtils.newArrayList();

        for (DingUserDepartment dingUserDepartment : userList1) {
			getUserList(dingUserList1, dingUserDepartment.getUserId(), userList2);
        }

		Map<String, Object> map = new HashMap<>();
		map.put("deptManagerList", deptManagerList);
		map.put("userList", userList2);
//		map.put("userList", dingUserList);
		map.put("childdept", departList);
		map.put("departmentId", parentid);
		map.put("parentCode", department.getParentCode());
		map.put("treeLeaf", department.getTreeLeaf());
		map.put("treeLevel", department.getTreeLevel());
		map.put("name", department.getName());
		map.put("userCount", department.getUserCount());
		return ReturnDate.success(map);
	}

	public void getUserList(List<DingUser> dingUserList1, String userId, List<DingUser> userList) {
		Optional<DingUser> optionalDingUser = dingUserList1.stream().filter(s -> s.getUserid().equals(userId)).findFirst();
		if (optionalDingUser.isPresent()) {
			DingUser dingUser = optionalDingUser.get();
			userList.add(dingUser);
		}
	}

	@RequestMapping(value = "getTreeData")
	@ResponseBody
	public ReturnInfo getTreeData() {
		List<Node> nodes=dingDepartmentService.getAll();
		TreeUtil.createTree(nodes);
		return ReturnDate.success(nodes) ;
	}
}