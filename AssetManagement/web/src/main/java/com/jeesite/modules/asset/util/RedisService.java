package com.jeesite.modules.asset.util;

import com.jeesite.modules.util.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisService {
    @Resource
    private RedisUtil<String, String> redisString;


    public RedisData getFlag(String documentCode) {
        String key =documentCode;
        String dataJson= redisString.get(key);
        JSONObject jsonObject=JSONObject.fromObject(dataJson);
        RedisData data=(RedisData)JSONObject.toBean(jsonObject, RedisData.class);
        return data;
    }
}
