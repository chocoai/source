package com.jeesite.modules.storevideo.faceplus;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FaceplusApi {

    @Value("${faceplus.api_key}")
    private String apiKey;
    @Value("${faceplus.api_secret}")
    private String apiSecret;
    @Value("${faceplus.outer_id}")
    private String outerId;

    public SearchResult getResultByUrl(String imageUrl){
        String facePlusApiUrl = "https://api-cn.faceplusplus.com/facepp/v3/search";
        Map<String, String> map = MapUtils.newHashMap();
        map.put("api_key",apiKey);
        map.put("api_secret",apiSecret);
        map.put("outer_id",outerId);
        map.put("image_url",imageUrl);
        String result = HttpClientUtils.ajaxPost(facePlusApiUrl, map);
        SearchResult searchResult = JSONObject.parseObject(result, SearchResult.class);

        return searchResult;
    }
}
