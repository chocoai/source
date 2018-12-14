package com.jeesite.modules.fz.utils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.fz.fzlogin.entity.DingTalkData;

import javax.servlet.http.HttpServletRequest;

/**
 * AccessToken和jsticket的获取封装
 */
public class AuthHelper {

    // 调整到1小时30分钟
    public static final long cacheTime = 1000 * 60 * 45 * 2;
    private  static  String CorpId = "dingde55314a8e20f3f6";
    /**
     * 获取企业的jsticket
     */
    private static final String JSTICKETURL = "https://oapi.dingtalk.com/get_jsapi_ticket?access_token=";

    /**
     * 请求jsapi参数类型 固定值
     */
    private static final String TYPE = "&type=jsapi";


    /**
     * 获取JSTicket, 用于js的签名计算
     * 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
     */
    public static String getJsapiTicket(String accessToken, String baseDir) throws Exception {
        JSONObject jsTicketValue = (JSONObject) FileUtils.getValue(baseDir + "/jsticket", CorpId);
        long curTime = System.currentTimeMillis();
        String jsTicket = "";

        if (jsTicketValue == null || curTime -
                jsTicketValue.getLong("begin_time") >= cacheTime) {
//            ServiceFactory serviceFactory;
            try {
//                serviceFactory = ServiceFactory.getInstance();
//                JsapiService jsapiService = serviceFactory.getOpenService(JsapiService.class);
//
//                JsapiTicket JsapiTicket = jsapiService.getJsapiTicket(accessToken, "jsapi");
//                jsTicket = JsapiTicket.getTicket();

                String ticketUrl = JSTICKETURL + accessToken + TYPE;
                String info = HttpClientUtils.ajaxGet(ticketUrl);
                JSONObject json = JSONObject.parseObject(info);
                jsTicket = json.get("ticket").toString();
                JSONObject jsonTicket = new JSONObject();
                JSONObject jsontemp = new JSONObject();
                jsontemp.clear();
                jsontemp.put("ticket", jsTicket);
                jsontemp.put("begin_time", curTime);
                jsonTicket.put(CorpId, jsontemp);
                FileUtils.write2File(jsonTicket, baseDir + "/jsticket", baseDir);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsTicket;
        } else {
            return jsTicketValue.getString("ticket");
        }
    }


    public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
        try {
            return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
        } catch (Exception ex) {
            //throw new OApiException(0, ex.getMessage());
        }
        return  null;
    }


    /**
     * 计算当前请求的jsapi的签名数据<br/>
     * <p>
     * 如果签名数据是通过ajax异步请求的话，签名计算中的url必须是给用户展示页面的url
     *
     * @param request
     * @return
     */
    public static String getConfig(HttpServletRequest request,String baseDir) {
        baseDir = baseDir + "/fzSigin";
        // 前端传的url地址
        String url = request.getParameter("url");
        String nonceStr = "abcdefg";
        long timeStamp = System.currentTimeMillis() / 1000;
        String signedUrl = url;
        String accessToken = null;
        String ticket = null;
        String signature = null;
        String agentid = null;

        try {
//            RedisHelp rh = new RedisHelp();

            accessToken = RedisHelp.redisHelp.getAcessToken();

            ticket = AuthHelper.getJsapiTicket(accessToken, baseDir);
            signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
            agentid = "194893302";

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String configValue = "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
//                + timeStamp + "',corpId:'" + CorpId + "',agentid:'" + agentid + "'}";
        // 返回前端签名信息
        DingTalkData dingTalkData = new DingTalkData();
        dingTalkData.setJsticket(ticket);
        dingTalkData.setNonceStr(nonceStr);
        dingTalkData.setSignature(signature);
        dingTalkData.setTimeStamp(timeStamp);
        dingTalkData.setCorpId(CorpId);
        dingTalkData.setAgentId(agentid);
        System.out.println(JsonMapper.toJson(dingTalkData));
        return JsonMapper.toJson(dingTalkData);
    }



}
