/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fgc.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wechat.service.WxchatLoginService;
import com.jeesite.modules.asset.wechat.util.FgcLogUtil;
import com.jeesite.modules.fgc.dao.UserDataInfo;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.service.FgcUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 梵工厂用户表Controller
 * @author dwh
 * @version 2018-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/fgc/fgcUser")
public class FgcUserController extends BaseController {

	@Autowired
	private FgcUserService fgcUserService;
	@Resource
	private RedisUtil<String, String> redisString;
	@Autowired
	private WxchatLoginService wxchatLoginService;

	@Value("${FGC_EXPRIED_TIME}")
	Long FGC_EXPRIED_TIME;  //token过期时间
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FgcUser get(String documentCode, boolean isNewRecord) {
		return fgcUserService.get(documentCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("fgc:fgcUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(FgcUser fgcUser, Model model) {
		model.addAttribute("fgcUser", fgcUser);
		return "modules/fgc/fgcUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("fgc:fgcUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FgcUser> listData(FgcUser fgcUser, HttpServletRequest request, HttpServletResponse response) {
		Page<FgcUser> page = fgcUserService.findPage(new Page<FgcUser>(request, response), fgcUser); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("fgc:fgcUser:view")
	@RequestMapping(value = "form")
	public String form(FgcUser fgcUser, Model model) {
		model.addAttribute("fgcUser", fgcUser);
		return "modules/fgc/fgcUserForm";
	}

	/**
	 * 保存梵工厂用户表
	 */
	@RequiresPermissions("fgc:fgcUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FgcUser fgcUser) {
		fgcUserService.save(fgcUser);
		return renderResult(Global.TRUE, "保存梵工厂用户表成功！");
	}
	@PostMapping(value = "updataStatus")
	@ResponseBody
	public String updataStatus( String documentCode) {
		FgcUser fgcUser=new FgcUser();
		fgcUser.setDocumentCode(documentCode);
		fgcUser=fgcUserService.get(fgcUser);
		String status="";
		String msg="";
		if ("0".equals(fgcUser.getStatus())){
			status="1";
			 msg="停用成功";
		}
		if ("1".equals(fgcUser.getStatus())){
			status="0";
			 msg="启用成功";

		}
		fgcUserService.updataStatus(documentCode,status);
		//更新缓存
		fgcUser=new FgcUser();
		fgcUser.setDocumentCode(documentCode);
		fgcUser=fgcUserService.get(fgcUser);
		UserDataInfo userDataInfo= fgcUserService.getData(fgcUser,"");
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userDataInfo);
		redisString.set("uvanfactory_user_" + fgcUser.getOpenId(), jsonObject.toString(),FGC_EXPRIED_TIME,TimeUnit.SECONDS);
		return renderResult(Global.TRUE, msg);
	}
	/**
	 * 删除梵工厂用户表
	 */
	@RequiresPermissions("fgc:fgcUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FgcUser fgcUser) {
		fgcUserService.delete(fgcUser);
		return renderResult(Global.TRUE, "删除梵工厂用户表成功！");
	}
	@RequestMapping(value = "fgcLogin")
	@ResponseBody
	public ReturnInfo fgcLogin(String openId) {
		FgcUser fgcUser= fgcUserService.getFgcUserByOpenId(openId);
		if (fgcUser!=null&&"0".equals(fgcUser.getStatus())){
			return ReturnDate.success(0,"登陆成功","");
		}else if (fgcUser!=null&&"1".equals(fgcUser.getStatus())){
			return ReturnDate.error(10003,"当前绑定的用户被禁用，请联系系统管理员");
		}else {
			return ReturnDate.error(-300,"进入用户绑定页面");
		}
	}
	@RequestMapping(value = "fgc_bindUser")
	@ResponseBody
	public ReturnInfo fgc_bindUser(String openId, String userName, String verificationCode) {
		FgcUser fgcUser= fgcUserService.getFgcUserByNameAndCode(userName,verificationCode);
		if (fgcUser!=null&&"0".equals(fgcUser.getStatus())){
			fgcUser.setOpenId(openId);
			fgcUserService.save(fgcUser);
			return ReturnDate.success(0,"用户名："+userName+"已绑定微信成功！","");
		} else {
			return ReturnDate.error(-300,"用户名不存在或已被绑定，请联系系统管理员。");
		}
	}
	/**
	 * 微信登陆接口
	 */
	@RequestMapping(value = "/wechatLogin",method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo wechatLogin(String wxCode , HttpServletResponse response) {
		if (StringUtils.isBlank(wxCode)) {
			return ReturnDate.error(10002, "参数wxCode为空！");
		}
		try{
			return fgcUserService.login(wxCode,response);
		}catch (Exception e){
			this.logger.error(e.getMessage(), e);
			FgcLogUtil.insertLog(wxCode,"","","/fgc/wechatLogin","微信登录","微信登录失败");
			return ReturnDate.error(-300, "系统用户登录失败:"+e.toString());
		}
	}


	@RequestMapping(value = "/sysLoginFgc",method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo sysLoginFgc(@RequestBody String body , HttpServletResponse response) throws Exception {
		JSONObject json = JSONObject.parseObject(body);
		String wxcode=(String)json.get("code");
		String userName=(String)json.get("userName");
		String passWord=(String)json.get("passWord");
//            wechatK3User.setOpenId(session.getOpenid());
		if (!ParamentUntil.isBackString(wxcode)){
			return ReturnDate.error(10002,"参数code为空");
		}
		if (!ParamentUntil.isBackString(userName)){
			return ReturnDate.error(10002,"参数userName为空");
		}
		if (!ParamentUntil.isBackString(passWord)){
			return ReturnDate.error(10002,"参数password为空");
		}
		//登录微信得到openID
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9c6eb8c57de10e0c&secret=fa48b7a3be32d55bb25a041bded54337&js_code=" + wxcode + "&grant_type=authorization_code";
		String info = HttpClientUtils.ajaxGet(url);
		JSONObject infoJson = JSONObject.parseObject(info);
		String openid = (String) infoJson.get("openid");
		FgcUser user=fgcUserService.getUserByloginName(userName);
		if (user!=null&&!(openid.equals(user.getOpenId()))){
			return ReturnDate.error(10004,userName+"已绑定微信号");
		}
		//新增微信用户
		FgcUser user2=fgcUserService.getFgcUserByOpenId(openid);
		if (user2==null){
			return ReturnDate.error(-1, "系统异常,请重新登录", "");
		}
		user2.setCity((String) json.get("city"));
		user2.setCountry((String) json.get("country"));
		user2.setSex(json.get("sex").toString());
		user2.setNickname((String) json.get("nickname"));
		user2.setHeadimgurl((String) json.get("headimgurl"));
		return fgcUserService.sysLoginFgc(openid,userName,passWord,response,user2);

	}


//	@RequestMapping(value = "logOut")
//	@ResponseBody
//	public ReturnInfo logOut(String token) throws Exception {
//		if(!ParamentUntil.isBackString(token)){
//			return ReturnDate.error(10002, "参数错误，token为空");
//		}
//		String openId=redisString.get("uvanfactory_user_"+token);
//		if (!ParamentUntil.isBackString(openId)){
//			return ReturnDate.error(10002, "缓存找不到openId");
//		}
//		FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId); //是否微信用户信息
//		if (fgcUser==null){
//			return ReturnDate.error(10001, "系统内部错误，微信用户不存在");
//		}
//		fgcUser.setSysLoginCode("");
//		fgcUser.setSysLoginPas("");
//
////		String secretKey = Global.getConfig("shiro.loginSubmit.secretKey");
//		//解密
////		String sysuser = DesUtils.decode(fgcUser.getSysLoginCode(),secretKey);
//		fgcUserService.save(fgcUser);
//		//删除缓存
//		String dataJson = redisString.get("uvanfactory_user_" + openId);
//		if (dataJson != null && dataJson.length() > 0) {
//			JSONObject json1 = JSONObject.parseObject(dataJson);
//			redisString.delete("uvanfactory_user_" + json1.get("token"));
//		}
//		redisString.delete("uvanfactory_user_" + openId);
//
//		FgcLogUtil.insertLog(openId,fgcUser.getSysLoginCode(),"","/fgc/wechatLogin","微信注销","注销成功");
//		return ReturnDate.success( "注销成功");
//	}

}