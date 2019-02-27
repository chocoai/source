package com.jeesite.modules.dingding.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DingDingService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public final String DING_ACCESS_TOKEN="access_toke";
    public final String GET_DING_ACCESS_TOKEN_ADDRESS = "https://oapi.dingtalk.com/gettoken?corpid=dingde55314a8e20f3f6&corpsecret=YDj118xRNB5CyG_s0uWdZvvi7DOueWS9RmUN_HFiSaTjjGb9c42jCPWO-vQ1jpht";
    public final String REDIS_KEY_DING_ACCESSTOKEN = "ding_access_token";

    /**
     * 获取钉钉AccessToken
     * @return
     */
    public String getAccessToken(){
        String accessToken = null;
        String jsonAccessTokenResult = HttpClientUtils.get(GET_DING_ACCESS_TOKEN_ADDRESS);
        JSONObject resultJsonObject = JSONObject.parseObject(jsonAccessTokenResult);
        if (resultJsonObject != null && resultJsonObject.containsKey("errcode") && resultJsonObject.getInteger("errcode").equals(0)) {
            accessToken = resultJsonObject.getString("access_token");
            //redisUtil.set(REDIS_KEY_DING_ACCESSTOKEN, accessToken, 70200000L);   //没有检验是否过期接口，不写缓存//7200秒过期，官方7200秒
        }
        return accessToken;
    }

    /**
     * 获取钉钉userId
     * @param accessToken
     * @param ddCode
     * @return
     */
    public ReturnInfo getDingUserIdInfo(String accessToken, String ddCode){
        ReturnInfo returnInfo = new ReturnInfo();
        StringBuilder sbUrl = new StringBuilder("https://oapi.dingtalk.com/user/getuserinfo?access_token=")
                .append(accessToken).append("&code=").append(ddCode);
        String jsonDingUserResult = HttpClientUtils.ajaxGet(sbUrl.toString());
        JSONObject dingUserJObject = JSONObject.parseObject(jsonDingUserResult);

        if(dingUserJObject != null && dingUserJObject.containsKey("errcode")){
            if(dingUserJObject.getInteger("errcode").equals(0)){
                if (dingUserJObject.containsKey("userid")) {
                    String userid = dingUserJObject.getString("userid");
                    returnInfo.setCode(200);
                    returnInfo.setData(userid);
                    //dingUserService.getDingUser(userid)
                } else {
                    returnInfo.setCode(400);
                    returnInfo.setMsg("接口缺少必要信息");
                    logger.error(jsonDingUserResult);
                }
            }
            else{
                returnInfo.setCode(400);
                returnInfo.setMsg(dingUserJObject.getString("errmsg"));
                logger.error(jsonDingUserResult);
            }
        }
        return returnInfo;
    }
}
