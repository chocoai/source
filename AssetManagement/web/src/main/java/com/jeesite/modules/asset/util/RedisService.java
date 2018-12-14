package com.jeesite.modules.asset.util;

import net.sf.json.JSONObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    public RedisData getFlag(String documentCode) {
        String key =documentCode;
        String dataJson= redisTemplate.opsForValue().get(key);
        JSONObject jsonObject=JSONObject.fromObject(dataJson);
        RedisData data=(RedisData)JSONObject.toBean(jsonObject, RedisData.class);
        return data;
    }
}
