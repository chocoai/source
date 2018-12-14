/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dingding.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.UserData;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.config.AccessLimit;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.util.DingDingAuth;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 钉钉Controller
 * @author Philip
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/dingding")
public class DingDingController extends BaseController {

	@Value("${FZ_EXPRIED_TIME}")
	Long FZ_EXPRIED_TIME;  //token过期时间
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private DingUserService dingUserService;
	@Autowired
	private DingDepartmentService dingDepartmentService;
	@Autowired
	private FzLoginRecordService fzLoginRecordService;

	/**
	 * 登录接口
	 * @param ddCode
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo Login(String ddCode, HttpServletResponse response) throws Exception {
		String GET_DING_ACCESS_TOKEN_ADDRESS = "https://oapi.dingtalk.com/gettoken?corpid=dingde55314a8e20f3f6&corpsecret=YDj118xRNB5CyG_s0uWdZvvi7DOueWS9RmUN_HFiSaTjjGb9c42jCPWO-vQ1jpht";
		String result = HttpClientUtils.get(GET_DING_ACCESS_TOKEN_ADDRESS);
		net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(result);
		if (jsonObject1 != null) {
			if (jsonObject1.containsKey("errcode")) {
				int count = jsonObject1.getInt("errcode");
				if (0 == count) {
					result = jsonObject1.getString("access_token");
				}
			}
		}
		String url = "https://oapi.dingtalk.com/user/getuserinfo?access_token=" + result + "&code=" + ddCode;
		String info = HttpClientUtils.ajaxGet(url);
		JSONObject jsonObject = JSONObject.parseObject(info);
		DingUser dingUser = null;
		if (jsonObject.containsKey("userid")) {
			String userid = jsonObject.get("userid").toString();
			if (ParamentUntil.isBackString(userid)) {
				dingUser = dingUserService.getUserById(userid);
				if (dingUser != null) {
					// 保存登录记录表，生成token并保存到redis
					saveToken(dingUser, response);
				}
			}
		} else {
			logger.error(info);

			return ReturnDate.error(400, "钉钉登录失败");
		}
		return ReturnDate.success(dingUser);
	}

	public void saveToken (DingUser dingUser, HttpServletResponse response) {
		// 保存登录记录表
		fzLoginRecordService.saveData(dingUser);
		List<DingDepartment> dingDepartments = dingDepartmentService.getDingDepartmentByUser(dingUser.getUserid());
		dingUser.setDingDepartmentList(dingDepartments);
		//根据uuid生产随机token,把"-"去掉,不然在跟前端交互的时候会出现问题
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		dingUser.setUvan_token(token);
		String userData = prossesOne(dingUser, token);
		redisTemplate.opsForValue().set("dingding_user_" + token, dingUser.getUserid(), FZ_EXPRIED_TIME, TimeUnit.SECONDS);
		redisTemplate.opsForValue().set("dingding_user_" + dingUser.getUserid(), userData, FZ_EXPRIED_TIME, TimeUnit.SECONDS);
		response.setHeader("Access-Control-Expose-Headers", "*");
		response.setHeader("uvan_token", token);
	}


	private String prossesOne(DingUser dingUser, String token) {
		UserData userData =new UserData();
		userData.setActive(dingUser.getActive());
		userData.setAvatar(dingUser.getAvatar());
		userData.setChineseName(dingUser.getChineseName());
		userData.setConvertibleGold(dingUser.getConvertibleGold());
		userData.setDepartmentId(dingUser.getDepartmentId());
		userData.setDepartmentNames(dingUser.getDepartmentNames());
		userData.setEmail(dingUser.getEmail());
		userData.setExtattr(dingUser.getExtattr());
		userData.setInDepartmentGold(dingUser.getInDepartmentGold());
		userData.setIsAdmin(dingUser.getIsAdmin());
		userData.setJobnumber(dingUser.getJobnumber());
		userData.setLeft(dingUser.getleft());
		userData.setMobile(dingUser.getMobile());
		userData.setName(dingUser.getName());
		userData.setOpenid(dingUser.getOpenid());
		userData.setOrderinDepts(dingUser.getOrderinDepts());
		userData.setOrgEmail(dingUser.getOrgEmail());
		userData.setOutDepartmentGold(dingUser.getOutDepartmentGold());
		userData.setRoleNames(dingUser.getRoleNames());
		userData.setSex(dingUser.getSex());
		userData.setStateCode(dingUser.getStateCode());
		userData.setTel(dingUser.getTel());
		userData.setUnionid(dingUser.getUnionid());
		userData.setUserid(dingUser.getUserid());
		userData.setWorkPlace(dingUser.getWorkPlace());
		userData.setUvan_token(token);
		JSONObject userJson = (JSONObject) JSONObject.toJSON(userData);
		return userJson.toJSONString();
	}
}