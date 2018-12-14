package com.jeesite.modules.asset.util;

import com.jeesite.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "${adminPath}/redisUnits")
public class RedisUnitlController extends BaseController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisService service;
//    @RequestMapping(value = "getFlag")
//    @ResponseBody
//    public boolean getFlag(String documentCode) {
//        boolean rst=false;
//        String key = RedisKeyPrefix.GIRL + documentCode;
//        RedisData data= redisTemplate.opsForValue().get(key);
//        if (data==null){
//            rst=true;
//        }
//
//        return rst;
//    }
    @RequestMapping(value = "updataTime")
    @ResponseBody
    public void updataTime(String code) {
        String key =code;
        if (key!=null&&key.length()>0){
            String dataJson= redisTemplate.opsForValue().get(key);
//            JSONObject jsonObject=JSONObject.fromObject(dataJson);
//            RedisData data=(RedisData)JSONObject.toBean(jsonObject, RedisData.class);
//        RedisData data= redisTemplate.opsForValue().get(key);
            redisTemplate.opsForValue().set(key,dataJson , 4,TimeUnit.SECONDS);
        }
    }
}
