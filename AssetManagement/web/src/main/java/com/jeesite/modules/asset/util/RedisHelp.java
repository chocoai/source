package com.jeesite.modules.asset.util;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.dingding.service.DingDingService;
import com.jeesite.modules.fgc.dao.UserDataInfo;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class RedisHelp {
    @Resource
    private RedisUtil<String,String> redisString;
    public static RedisHelp redisHelp;

    @Autowired
    private DingDingService dingDingService;


    @PostConstruct
    public void init() {
        redisHelp = this;
        redisHelp.redisString =this.redisString;
    }
    //根据token得到缓存用户信息
    public UserDataInfo getfgcUserInfo(String token){
        String openId= redisString.get("uvanfactory_user_" + token);
        UserDataInfo userDataInfo=null;
        if (openId!=null&&openId.length()>0){
            String json = redisString.get("uvanfactory_user_" +openId );
            if (json!=null&&json.length()>0){
            JSONObject json1 = JSONObject.parseObject(json);
            userDataInfo=JSONObject.toJavaObject(json1,UserDataInfo.class);
            }
        }
        return userDataInfo;
    }

    /**
     * 获取钉钉access_token
     * @return
     */
    public  String getDingDingAcessToken() {
        return dingDingService.getAccessToken();
    }

}
