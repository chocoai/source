/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.kanban.entity.AmKanbanFile;
import com.jeesite.modules.asset.kanban.service.AmKanbanFileService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.userroleconfig.UserDataPermissionUnit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.jeesite.modules.asset.kanban.entity.AmKbAmpersonnel;
import com.jeesite.modules.asset.kanban.service.AmKbAmpersonnelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 看板有效人员维护Controller
 * @author dwh
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/kanban/amKbAmpersonnel")
public class AmKbAmpersonnelController extends BaseController {

	@Autowired
	private AmKbAmpersonnelService amKbAmpersonnelService;

	@Autowired
	private UserDataPermissionUnit userDataPermissionUnit;

	@Autowired
	private AmKanbanFileService amKanbanFileService;
	@Value("${SMSNOTIFICATION}")
	String SMSNOTIFICATION;  //短信验证
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AmKbAmpersonnel get(String kbAmpersonnelCode, String phone, boolean isNewRecord) {
		return amKbAmpersonnelService.get(new Class<?>[]{String.class, String.class},
				new Object[]{kbAmpersonnelCode, phone}, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("kanban:amKbAmpersonnel:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmKbAmpersonnel amKbAmpersonnel, Model model) {
		model.addAttribute("amKbAmpersonnel", amKbAmpersonnel);
		return "asset/kanban/amKbAmpersonnelList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("kanban:amKbAmpersonnel:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AmKbAmpersonnel> listData(AmKbAmpersonnel amKbAmpersonnel, HttpServletRequest request, HttpServletResponse response) {
		String param=userDataPermissionUnit.getParam(request);
//		System.out.println("员工所属角色为"+UserUtils.getUser().getRoleList().get(0));
		amKbAmpersonnel.getAmKanbanFile().setIsQueryChildren(true);
		amKbAmpersonnel.setParam(param);
		Page<AmKbAmpersonnel> page = amKbAmpersonnelService.findPage(new Page<AmKbAmpersonnel>(request, response), amKbAmpersonnel);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("kanban:amKbAmpersonnel:view")
	@RequestMapping(value = "form")
	public String form(AmKbAmpersonnel amKbAmpersonnel, Model model) {
		model.addAttribute("amKbAmpersonnel", amKbAmpersonnel);
		return "asset/kanban/amKbAmpersonnelForm";
	}

	/**
	 * 保存看板有效人员维护
	 */
	@RequiresPermissions("kanban:amKbAmpersonnel:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AmKbAmpersonnel amKbAmpersonnel) {
		String phone=amKbAmpersonnel.getPhone();
		String kanbanCode=amKbAmpersonnel.getKanbanCode();
		AmKbAmpersonnel ampersonnel= amKbAmpersonnelService.getAmKbAmpersonnelByPhone(phone,kanbanCode);
		if (ampersonnel!=null&&!(ampersonnel.getKbAmpersonnelCode().equals(amKbAmpersonnel.getKbAmpersonnelCode()))){
			return renderResult(Global.FALSE, "该看板下联系电话已存在");
		}

		amKbAmpersonnelService.save(amKbAmpersonnel);
		return renderResult(Global.TRUE, "保存看板有效人员维护成功！");
	}
	
	/**
	 * 删除看板有效人员维护
	 */
	@RequiresPermissions("kanban:amKbAmpersonnel:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AmKbAmpersonnel amKbAmpersonnel) {
		amKbAmpersonnelService.delete(amKbAmpersonnel);
		return renderResult(Global.TRUE, "删除看板有效人员维护成功！");
	}
	@RequiresPermissions("kanban:amKbAmpersonnel:view")
	@RequestMapping(value = "index")
	public String index(AmKbAmpersonnel amKbAmpersonnel, Model model) {
		return "asset/kanban/amKbAmpersonnelIndex";
	}

	/**
	 * 物理删除入库表
	 */
	@RequiresPermissions("kanban:amKbAmpersonnel:edit")
	@RequestMapping(value = "deleteDb")
	@ResponseBody
	public String deleteDb(AmKbAmpersonnel amKbAmpersonnel, String ids) {
		if (ids != null && ids.length() > 0) {
			String[] codeList = ids.split(",");
			for (int i = 0; i < codeList.length; i++) {
				amKbAmpersonnelService.deleteDb(codeList[i]);
			}
		}else {
			return renderResult(Global.FALSE, "删除失败！");
		}
			return renderResult(Global.TRUE, "删除成功！");
	}
	//
	/**
	 * 获取手机验证码
	 */
	@RequestMapping(value = "getVerificationCode")
	@ResponseBody
	public ReturnInfo getVerificationCode(String adaptationUrl, String tel, String typeName){
		AmKanbanFile amKanbanFile=new AmKanbanFile();
		amKanbanFile.setAdaptationUrl(adaptationUrl);
		List<AmKanbanFile> amKanbanFiles= amKanbanFileService.findList(amKanbanFile);
		if (amKanbanFiles==null||amKanbanFiles.size()<=0){
			return  ReturnDate.error(400, "网址不存在");
		}
		if (!("0".equals( amKanbanFiles.get(0).getStatus()))){
			return  ReturnDate.error( 400,"看板状态不正常");
		}
		List<AmKbAmpersonnel> amKbAmpersonnels=amKbAmpersonnelService.getInfoByTel(tel,amKanbanFiles.get(0).getKanbanCode());
		if (amKbAmpersonnels==null||amKbAmpersonnels.size()<=0){
			return  ReturnDate.error( 400,"操作失败，手机号码不存在");
		}
		Map <String,String> map=new HashMap<>();
		map.put("userName","uvanapi");
		map.put("password","123456");
		String longin=HttpClientUtils.ajaxGet(SMSNOTIFICATION.trim()+"/api/account/login?userName=uvanapi&password=123456");
		JSONObject jsonObject=JSONObject.parseObject(longin);
		int randomNo=(int)((Math.random()*9+1)*100000);
		HttpClientUtils.ajaxGet(SMSNOTIFICATION.trim()+"/api/alibabaapi/SendSmsByVerifyCode?tel="+tel+"&code="+randomNo+"&name="+typeName+"&sessionKey="+jsonObject.get("message"));
		String rst=Md5Utils.md5(String.valueOf(randomNo));
		return  ReturnDate.success( rst);
	}
	/**
	 * 校验手机号码是否存在
	 */
	@RequestMapping(value = "isHaveTel")
	@ResponseBody
	public ReturnInfo isHaveTel(String tel,String adaptationUrl){
		if(!ParamentUntil.isBackString(tel)){
			return  ReturnDate.error( 400,"参数为空");
		}
        AmKanbanFile amKanbanFile=new AmKanbanFile();
        amKanbanFile.setAdaptationUrl(adaptationUrl);
        List<AmKanbanFile> amKanbanFiles= amKanbanFileService.findList(amKanbanFile);
        if (!ParamentUntil.isBackList(amKanbanFiles)){
            return  ReturnDate.error( 400,"适配地址没有对应的看板");
        }
		AmKbAmpersonnel ampersonnel=new AmKbAmpersonnel();
		ampersonnel.setPhone(tel);
		ampersonnel.setKanbanCode(amKanbanFiles.get(0).getKanbanCode());
		List<AmKbAmpersonnel> amKbAmpersonnels=amKbAmpersonnelService.getInfoByTel(tel,amKanbanFiles.get(0).getKanbanCode());
		if (amKbAmpersonnels==null||amKbAmpersonnels.size()<=0){
			return  ReturnDate.success( "0");
//			return renderResult(Global.FALSE, "0");
		}else {
			return  ReturnDate.success( "1");
	}
	}
}