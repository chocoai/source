/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.basicroleinfoconfig.entity.BasicRoleInfo;
import com.jeesite.modules.basicroleinfoconfig.entity.RoleDataPermission;
import com.jeesite.modules.basicroleinfoconfig.entity.RoleInterfaceQueryData;
import com.jeesite.modules.basicroleinfoconfig.service.BasicRoleInfoService;
import com.jeesite.modules.basicroleinfoconfig.service.RoleDataPermissionService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.userroleconfig.entity.InterfaceField;
import com.jeesite.modules.userroleconfig.entity.InterfaceInfo;
import com.jeesite.modules.userroleconfig.entity.InterfaceQuery;
import com.jeesite.modules.userroleconfig.service.InterfaceInfoService;
import com.jeesite.modules.userroleconfig.service.InterfaceQueryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 角色表Controller
 * @author dwh
 * @version 2018-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basicroleinfoconfig/basicRoleInfo")
public class BasicRoleInfoController extends BaseController {

	@Autowired
	private BasicRoleInfoService basicRoleInfoService;
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	@Autowired
	private RoleDataPermissionService roleDataPermissionService;
	@Autowired
	private InterfaceQueryService interfaceQueryService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BasicRoleInfo get(String roleCode, boolean isNewRecord) {
		return basicRoleInfoService.get(roleCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("basicroleinfoconfig:basicRoleInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasicRoleInfo basicRoleInfo, Model model) {
		model.addAttribute("basicRoleInfo", basicRoleInfo);
		return "modules/basicroleinfoconfig/basicRoleInfoList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("basicroleinfoconfig:basicRoleInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BasicRoleInfo> listData(BasicRoleInfo basicRoleInfo, HttpServletRequest request, HttpServletResponse response) {
		Page<BasicRoleInfo> page = basicRoleInfoService.findPage(new Page<BasicRoleInfo>(request, response), basicRoleInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("basicroleinfoconfig:basicRoleInfo:view")
	@RequestMapping(value = "form")
	public String form(BasicRoleInfo basicRoleInfo, Model model) {
		model.addAttribute("basicRoleInfo", basicRoleInfo);
		return "modules/basicroleinfoconfig/basicRoleInfoForm";
	}

	/**
	 * 保存角色表
	 */
	@RequiresPermissions("basicroleinfoconfig:basicRoleInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BasicRoleInfo basicRoleInfo) {
		basicRoleInfoService.save(basicRoleInfo);
		return renderResult(Global.TRUE, "保存角色表成功！");
	}

	/**
	 * 删除角色表
	 */
	@RequiresPermissions("basicroleinfoconfig:basicRoleInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BasicRoleInfo basicRoleInfo) {
		basicRoleInfoService.delete(basicRoleInfo);
		return renderResult(Global.TRUE, "删除角色表成功！");
	}
	/**
	 * 根据用户code查询接口列表数据
	 */
	@RequiresPermissions("basicroleinfoconfig:basicRoleInfo:view")
	@RequestMapping(value = "listDataByRoleCode")
	@ResponseBody
	public Page<InterfaceInfo> listDataByUserCode(InterfaceInfo interfaceInfo, HttpServletRequest request, HttpServletResponse response, String roleCode) {
		if (roleCode != null && roleCode.length() > 0) {
			interfaceInfo.setRoleCode(roleCode);
		}
		interfaceInfo.setStatus("");
		if(interfaceInfo.getRoleCode()==null||interfaceInfo.getRoleCode().length()<=0){
			Page<InterfaceInfo> page=new Page<>();
			return page;
		}
		Page<InterfaceInfo> page = interfaceInfoService.findPage(new Page<InterfaceInfo>(request, response), interfaceInfo);
		for (int i=0;i<page.getList().size();i++){
			RoleDataPermission roleDataPermission=new RoleDataPermission();
			roleDataPermission.setRoleCode(interfaceInfo.getRoleCode());
			roleDataPermission.setInterfaceCode(page.getList().get(i).getInterfaceCode());
			List<RoleDataPermission> roleDataPermissions=roleDataPermissionService.findList(roleDataPermission);
			page.getList().get(i).setStitching(roleDataPermissions.get(0).getStitching());
			page.getList().get(i).setInterfaceSql(roleDataPermissions.get(0).getInterfaceSql());
		}
		return page;
	}

	/**
	 *查询用户没有的接口信息，如果点击用户新增接口，只传用户id，返回用户没有的接口，如果编辑，传用户和接口ID返回对应接口和sql
	 */
	@RequestMapping(value = "getInfoByNotInUser")
	@ResponseBody
	public List<InterfaceInfo> getInfoByNotInUser(String roleCode,String interfaceCode) {
		List<InterfaceInfo> rst=null;
		if ((roleCode!=null&&roleCode.length()>0)&&(interfaceCode==null||interfaceCode.length()<=0)){
			rst =interfaceInfoService.getInfoByNotInRole(roleCode);
		}else if (roleCode!=null&&roleCode.length()>0&&interfaceCode!=null||interfaceCode.length()>0){
			rst =interfaceInfoService.getInfoByRoleCodeAndIntCode(roleCode,interfaceCode);
		}
		return rst;
	}

	/**
	 * 保存接口查询条件
	 */
	@PostMapping(value = "saveQuery")
	@ResponseBody
	public String saveQuery(@RequestBody RoleInterfaceQueryData interfaceQueryData) {
		if (interfaceQueryData == null) {
			return renderResult(Global.FALSE, "保存接口查询条件失败！");
		}
		RoleDataPermission roleDataPermission = interfaceQueryData.getRoleDataPermission();
		List<InterfaceQuery> interfaceQuerys = interfaceQueryData.getInterfaceQuerys();
		if (roleDataPermission != null) {
			//保存 关联表，替换sql
			System.out.println(roleDataPermission.getIsNewRecord());
			RoleDataPermission roleDataPermission2=new RoleDataPermission();
			roleDataPermission2.setRoleCode(roleDataPermission.getRoleCode());
			roleDataPermission2.setInterfaceCode(roleDataPermission.getInterfaceCode());
			List<RoleDataPermission> roleDataPermissions= roleDataPermissionService.findList(roleDataPermission2);
			if (roleDataPermissions!=null&&roleDataPermissions.size()>0){
				roleDataPermission.setIsNewRecord(false);
				roleDataPermission.setPermissionCode(roleDataPermissions.get(0).getPermissionCode());
				roleDataPermissionService.save(roleDataPermission);
			}else {
				roleDataPermissionService.save(roleDataPermission);
			}


			String stitching = roleDataPermission.getStitching();    //1=1 and 1=3
			StringBuffer interfaceSQL = new StringBuffer();
//            //查询用户接口所有的条件
			RoleDataPermission roleDataPermission1 = new RoleDataPermission();
			roleDataPermission1.setRoleCode(roleDataPermission.getRoleCode());
			roleDataPermission1.setInterfaceCode(roleDataPermission.getInterfaceCode());
			List<RoleDataPermission>userDataPermissionlist=roleDataPermissionService.findList(roleDataPermission1);
			//保存接口对应的条件
			if (interfaceQuerys != null && interfaceQuerys.size() > 0) {
				for (int i = 0; i < interfaceQuerys.size(); i++) {
					InterfaceQuery interfaceQuery = interfaceQuerys.get(i);
					String type = interfaceQuery.getType();
					if ("add".equals(type)) {
						interfaceQuery.setIsNewRecord(true);
						interfaceQueryService.save(interfaceQuery);
					} else if ("edit".equals(type)) {
						interfaceQuery.setIsNewRecord(false);
						interfaceQueryService.save(interfaceQuery);
					} else if ("del".equals(type)) {
						interfaceQueryService.delete(interfaceQuery);
					} else {
						return renderResult(Global.TRUE, "参数type不正确");
					}

				}
				InterfaceQuery interfaceQuery2=new InterfaceQuery();
				interfaceQuery2.setInterfaceCode(roleDataPermission.getInterfaceCode());
				interfaceQuery2.setRoleCode(roleDataPermission.getRoleCode());
				List<InterfaceQuery> interfaceQuerylist=interfaceQueryService.findList(interfaceQuery2);
				for (int j=0;j<interfaceQuerylist.size();j++) {
					String value = interfaceQuerylist.get(j).getFieldValue();
					if ("登陆者ID".equals(value)){
						value=UserUtils.getUser().getLoginCode();
					}
					InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerylist.get(j).getFieldCode());
					//得到参数类型
					String dataType =interfaceField.getDataType();
					//如果字段类型是string和datetime就给条件的值加上单引号
					if ("String".equals(dataType) || "DateTime".equals(dataType)) {
						value = "'" + value + "'";
					}
					//得到参数值
					String param = interfaceField.getFieldValue();
					if (stitching == null || stitching.length() <= 0) {                                   //默认用and拼接

						interfaceSQL .append( param + " " + interfaceQuerylist.get(j).getSymbol() + " " + value);
						if (interfaceQuerylist.size() > 1 && j < interfaceQuerylist.size() - 1) {  //多条件并且不是最后一条用and拼接
							interfaceSQL .append(" and ");
						}
					}
				}

			}

			if (stitching!=null&&stitching.length()>0){
				Map map=new HashMap();
				InterfaceQuery interfaceQuery=new InterfaceQuery();
				interfaceQuery.setInterfaceCode(roleDataPermission.getInterfaceCode());
				interfaceQuery.setRoleCode(roleDataPermission.getRoleCode());
				List<InterfaceQuery> interfaceQuerylist=interfaceQueryService.findList(interfaceQuery);
				for (int i=0;i<interfaceQuerylist.size();i++) {
					String value = interfaceQuerylist.get(i).getFieldValue();
					if ("登陆者ID".equals(value)){
						value=UserUtils.getUser().getLoginCode();
					}
					InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerylist.get(i).getFieldCode());
					//得到参数类型
					String dataType = interfaceField.getDataType();
					//如果字段类型是string和datetime就给条件的值加上单引号
					if ("String".equals(dataType) || "DateTime".equals(dataType)) {
						value = "'" + value + "'";
					}
					//得到参数值
					String param = interfaceField.getFieldValue();
					//查询这个序号的sql
					String sortNo = interfaceQuerylist.get(i).getSortNo();    //单个序号
					String danInterfaceSQL = param + " " + interfaceQuerylist.get(i).getSymbol() + " " + value; //单个序号对应的条件
					map.put(sortNo,danInterfaceSQL);
//					String patt = "\\b" + sortNo + "+\\b";
////                    // 用于测试的输入字符串
////                    String input = "1 and 2";
//					System.out.println("Input:" + stitching);
//
//					// 从正则表达式实例中运行方法并查看其如何运行
//					Pattern r = Pattern.compile(patt);
//					Matcher m = r.matcher(stitching);
//					stitching = m.replaceAll(danInterfaceSQL);     //循环替换正则的数字，把patt替换成sql
////                    System.out.println("ReplaceAll:" + m.replaceAll("user = 'sadsa'"));
//					interfaceSQL = stitching;
//                }
				}
				if (map!=null&&map.size()>0){
					interfaceSQL=prossesSql(stitching,map);
				}


			}else if ((interfaceQuerys == null || interfaceQuerys.size() <= 0)&&(stitching==null||stitching.length()<=0)){
				InterfaceQuery interfaceQuery2=new InterfaceQuery();
				interfaceQuery2.setInterfaceCode(roleDataPermission.getInterfaceCode());
				interfaceQuery2.setRoleCode(roleDataPermission.getRoleCode());
				List<InterfaceQuery> interfaceQuerylist=interfaceQueryService.findList(interfaceQuery2);
				for (int j=0;j<interfaceQuerylist.size();j++) {
					String value = interfaceQuerylist.get(j).getFieldValue();
					if ("登陆者ID".equals(value)){
						value=UserUtils.getUser().getLoginCode();
					}
					InterfaceField interfaceField = interfaceInfoService.getInterFaceFieldByCode(interfaceQuerylist.get(j).getFieldCode());
					//得到参数类型
					String dataType =interfaceField.getDataType();
					//如果字段类型是string和datetime就给条件的值加上单引号
					if ("String".equals(dataType) || "DateTime".equals(dataType)) {
						value = "'" + value + "'";
					}
					//得到参数值
					String param = interfaceField.getFieldValue();
					if (stitching == null || stitching.length() <= 0) {                                   //默认用and拼接

						interfaceSQL.append( param + " " + interfaceQuerylist.get(j).getSymbol() + " " + value);
						if (interfaceQuerylist.size() > 1 && j < interfaceQuerylist.size() - 1) {  //多条件并且不是最后一条用and拼接
							interfaceSQL.append( " and ");
						}
					}
				}

			}
			//更新sql
			RoleDataPermission roleDataPermission3=new RoleDataPermission();
			roleDataPermission3.setPermissionCode(roleDataPermission.getPermissionCode());
			roleDataPermission3=roleDataPermissionService.get(roleDataPermission3);
			if (roleDataPermission3!=null){
				roleDataPermission3.setIsNewRecord(false);
				roleDataPermission3.setInterfaceSql(interfaceSQL.toString());
			}
			roleDataPermissionService.save(roleDataPermission3);
			//保存进入redis
			if (interfaceSQL!=null&&interfaceSQL.length()>0){
			redisTemplate.opsForValue().set("role_"+roleDataPermission.getRoleCode()+"_"+roleDataPermission.getInterfaceCode(),interfaceSQL.toString());
			}else {
				//如果保存时没有sql，把redis中的对应的数据删除
				redisTemplate.delete( "role_"+roleDataPermission.getRoleCode()+"_"+roleDataPermission.getInterfaceCode());
			}
		}
		return renderResult(Global.TRUE, "保存接口查询条件成功！");
	}
    /**
     * 删除角色拥有的接口信息
     */
    @RequiresPermissions("basicroleinfoconfig:basicRoleInfo:edit")
    @RequestMapping(value = "delRoleInfterface")
    @ResponseBody
    public String delRoleInfterface(String roleCode, String ids) {
        if (ids != null && ids.length() > 0) {
            String[] codeList = ids.split(",");
            for (int i = 0; i < codeList.length; i++) {
                interfaceInfoService.delRoleInfterface(roleCode, codeList[i]);
                //删除redis
                redisTemplate.delete(roleCode+"_"+ codeList[i]);
            }
        }

        return renderResult(Global.TRUE, "删除接口信息成功！");
    }

	private StringBuffer prossesSql(String stitching, Map map) {
		StringBuffer rst=new StringBuffer();
		String [] lists=stitching.split(" ");
		for (int i=0;i<lists.length;i++){
			boolean isInt=isInt(lists[i]);
			if (isInt){
				lists[i]= (String) map.get(lists[i]);
				rst.append(" ");
				rst.append(lists[i]);
			}else {
				rst.append(" ");
				rst.append(lists[i]);
			}
		}
		return rst;
	}

	private boolean isInt(String list) {
		boolean rst=false;
		try {
			Integer.parseInt(list);
			rst=true;
		}catch (Exception e){
			return  rst;
		}
		return rst;
	}
}