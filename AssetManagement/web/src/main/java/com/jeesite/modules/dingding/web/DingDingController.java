/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dingding.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.dingding.service.DingDingService;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;
import com.jeesite.modules.util.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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


	@Autowired
	private DingUserService dingUserService;
	@Autowired
	private DingDepartmentService dingDepartmentService;
	@Autowired
	private FzLoginRecordService fzLoginRecordService;

	@Autowired
    private DingDingService dingDingService;

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
        ReturnInfo returnInfo = null;
        String accessToken = dingDingService.getAccessToken();
        if(!StringUtils.isEmpty(accessToken)){
            ReturnInfo dingUserReturnInfo = dingDingService.getDingUserIdInfo(accessToken, ddCode);
            if(dingUserReturnInfo.getCode().equals(200)){
                DingUser dingUser = dingUserService.getDingUserFromCache(dingUserReturnInfo.getData().toString());
				// 保存登录记录表
				fzLoginRecordService.saveData(dingUser);
                setResponse(response, dingUser);
                returnInfo = ReturnDate.success(dingUser);
            }
            else {
                return dingUserReturnInfo;
            }
        }
        else returnInfo = ReturnDate.error(400, "钉钉登录失败");
		return returnInfo;
	}

	private void setResponse(HttpServletResponse response, DingUser dingUser){
		response.setHeader("Access-Control-Expose-Headers", "*");
		response.setHeader("uvan_token", dingUser.getUvan_token());
	}
}