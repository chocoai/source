/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzlogin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.fz.fzlogin.dao.FzLoginRecordDao;
import com.jeesite.modules.fz.fzlogin.entity.FzLoginRecord;
import com.jeesite.modules.fz.neigou.service.FzNeigouRefundService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 梵赞登录记录Service
 * @author len
 * @version 2018-10-09
 */
@Service
@Transactional(readOnly = true)
public class FzLoginService extends CrudService<FzLoginRecordDao, FzLoginRecord> {
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private FzNeigouRefundService fzNeigouRefundService;
    @Value("${neigou.login_token_url}")
    private String login_token_url;

    /**
     * 获取内购login_token
     * @param userid
     * @param isCreateUser   是否再次创建用户,是表示获取失败了就去创建用户,一般填true
     * @return
     */
    public ReturnInfo getLoginToken(String userid,boolean isCreateUser) throws Exception{

		String login_token = "";
        String neigou_login_url = login_token_url+ "?external_user_id=" + userid;
        Map dataMap = new HashMap();
        dataMap.put("external_user_id", userid);
        Map headerMap = new HashMap();
        String access_token = fzNeigouRefundService.getAccessToken();
        headerMap.put("AUTHORIZATION", "Bearer " + access_token);
        String aa = JSON.toJSONString(dataMap);
        String result = dingUserService.doPost(neigou_login_url, dataMap, headerMap, aa, "UTF-8");
        com.alibaba.fastjson.JSONObject parse = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(result);
        if (parse == null) {
            return null;
        } else {
            if ("true".equals(parse.get("Result"))) {
                com.alibaba.fastjson.JSONObject data = (com.alibaba.fastjson.JSONObject) parse.get("Data");
                login_token = (String) data.get("login_token");
                return ReturnDate.success(login_token);
            }else{
                String errorId = (String)parse.get("ErrorId");
                //没有该用户的话,那么就去先创建用户,然后再再创建用户的时候调自己来获取login_token
                if("UNKNOWN_USER".equals(errorId)){
                    if(isCreateUser){
                        return fzNeigouRefundService.createNeigouUser(userid,true);
                    }
                    return ReturnDate.success(15012,"创建内购用户失败",null);
                }
                return null;
            }
        }
    }

}