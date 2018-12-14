package com.jeesite.modules.asset.util;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.fgc.dao.UserDataInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisHelp {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    public static RedisHelp redisHelp;
    private static final String DING_ACCESS_TOKEN="access_toke";
    private static final String GET_DING_ACCESS_TOKEN_ADDRESS="https://oapi.dingtalk.com/gettoken?corpid=dingde55314a8e20f3f6&corpsecret=YDj118xRNB5CyG_s0uWdZvvi7DOueWS9RmUN_HFiSaTjjGb9c42jCPWO-vQ1jpht";


    @PostConstruct
    public void init() {
        redisHelp = this;
        redisHelp.redisTemplate=this.redisTemplate;
    }
    //根据token得到缓存用户信息
    public UserDataInfo getfgcUserInfo(String token){
        String openId=redisTemplate.opsForValue().get("uvanfactory_user_" + token);
        UserDataInfo userDataInfo=null;
        if (openId!=null&&openId.length()>0){
            String json =redisTemplate.opsForValue().get("uvanfactory_user_" +openId );
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
    public  String getAcessToken() {
        //  redisTemplate.opsForValue()
        String result= redisTemplate.opsForValue().get("access_toke");
        //当redis中不存在token则调用钉钉平台接口获取token
        if(result==null){
            //https://oapi.dingtalk.com/gettoken?corpid=id&corpsecret=secrect
            result=HttpClientUtils.get(GET_DING_ACCESS_TOKEN_ADDRESS);
            net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(result);
            if(jsonObject!=null){
                if(jsonObject.containsKey("errcode")){
                    int count=jsonObject.getInt("errcode");
                    if(0==count){
                        result=jsonObject.getString("access_token");
                        redisTemplate.opsForValue().set(DING_ACCESS_TOKEN,result , 7000,TimeUnit.SECONDS);
                    }
                }
            }
        }else {
            result=redisTemplate.opsForValue().get(DING_ACCESS_TOKEN);
        }
        return result;
    }

}
