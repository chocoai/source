package com.jeesite.modules.asset.ding.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.util.RedisHelp;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "${adminPath}/test")
public class DingRedisController extends BaseController {
    @RequestMapping(value = "test")
    @ResponseBody
    public String test(){

        String result= RedisHelp.redisHelp.getAcessToken();
       // String url="https://oapi.dingtalk.com/message/send_to_conversation?access_token="+result;
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("touser","1856293363663750138");
        jsonObject.put("agentid","5110142");
       // jsonObject.put("sender","16593529601717469825");
        jsonObject.put("msgtype","text");
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("content","测试啦啦啦");
        jsonObject.put("text",jsonObject1);
        String url="https://oapi.dingtalk.com/message/send?access_token="+result;
       String info= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
        System.out.println(info);
        //5110142平台id
        //1856293363663750138  scarlett用户id
    return result;
    }
}
