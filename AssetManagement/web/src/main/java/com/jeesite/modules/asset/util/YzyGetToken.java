package com.jeesite.modules.asset.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jeesite.common.lang.StringUtils;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.util.http.DefaultHttpClient;
import com.youzan.open.sdk.util.http.HttpClient;
import com.youzan.open.sdk.util.json.JsonUtils;
import org.apache.http.entity.ContentType;
import com.youzan.open.sdk.client.*;
public class YzyGetToken {
//    public static  OAuthToken getToken() {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpClient.Params params = HttpClient.Params.custom()
//                .add("client_id", "a5446429905eaa1fbf")
//                .add("client_secret", "17f6ba6a61aa075d79d3295f6a986389")
//                .add("grant_type", "silent")
//                .add("kdt_id","17280895")
//                .setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
//        String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
//        System.out.println(resp);
//        if (StringUtils.isBlank(resp) || !resp.contains("access_token")) {
//            throw new RuntimeException(resp);
//        }
//        return JsonUtils.toBean(resp, new TypeReference<OAuthToken>() {
//        });
//    }
    public static  String getToken() {
        String token=null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpClient.Params params = HttpClient.Params.custom()
                .add("client_id", "a5446429905eaa1fbf")
                .add("client_secret", "17f6ba6a61aa075d79d3295f6a986389")
                .add("grant_type", "silent")
                .add("kdt_id","17280895")
                .setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
        String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
        System.out.println(resp);
        if (StringUtils.isBlank(resp) || !resp.contains("access_token")) {
            throw new RuntimeException(resp);
        }

        token= (String) JSONObject.parseObject(resp).get("access_token");
        return token;
    }
}
