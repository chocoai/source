package com.jeesite.modules.asset.ding.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.entity.ActionCard;
import com.jeesite.modules.asset.ding.entity.DingMessage;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "${adminPath}/ding/message")
public class DingMessageController extends BaseController {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private AmUtilService amUtilService;
    private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";
//    private static final String AGENT_ID="5110142";
    //文本类型
   @RequestMapping(value = "message")
    @ResponseBody
    public String sendMessage(){

        String result= RedisHelp.redisHelp.getAcessToken();
       // String url="https://oapi.dingtalk.com/message/send_to_conversation?access_token="+result;
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("touser","18392547561934016313");
        jsonObject.put("agentid","5110142");
       // jsonObject.put("sender","16593529601717469825");
        jsonObject.put("msgtype","text");
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("content","测试啦啦啦");
        jsonObject.put("text",jsonObject1);
        String url=SEND_ADDRESS+result;
       String info= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
        System.out.println(info);
        //5110142平台id
        //1856293363663750138  scarlett用户id
    return result;
    }
    //actioncard
    @RequestMapping(value = "messageToOneUser")
    @ResponseBody
    public ReturnInfo sendMessage(HttpServletRequest request){
        DingMessage dingMessage=new DingMessage();
        ActionCard actionCard=new ActionCard();
        dingMessage.setMsgtype("action_card");
        String AGENT_ID = amUtilService.getConfigValue("fz_agentid");
        dingMessage.setAgentid(AGENT_ID);
        String configName=amUtilService.getConfigValue("fanzan_rank_url");
        if(configName!=null){
            dingMessage.setTouser("1856293363663750138");
            //标题
            actionCard.setTitle("测试haaa");
            actionCard.setMarkdown("ttttest");
            actionCard.setSingle_title("查看详情");
            actionCard.setSingle_url(configName);
            dingMessage.setAction_card(actionCard);
            JSONObject jsonObject=JSONObject.fromObject(dingMessage);
            String accessToken= RedisHelp.redisHelp.getAcessToken();
            String url=SEND_ADDRESS+accessToken;
            String info= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
            RecordLog recordLog=new RecordLog();
            recordLog.setWriteTime(new Date());
            recordLog.setPath(request.getServletPath());
            recordLog.setTitle("梵钻消息推送");
            recordLog.setType("fanzan_ding_message");
            JSONObject jsonObject2=new JSONObject();
            jsonObject2.put("钉钉消息请求",jsonObject);
            jsonObject2.put("钉钉消息响应",info);
            recordLog.setContent(jsonObject2.toString());
//            template.convertAndSend("recordLog1",recordLog);
            template.convertAndSend(FzTask.recordLogQueueP1,recordLog);
            return ReturnDate.success(info);
        }else {
            return ReturnDate.error(40004,"请先设置系统参数梵赞排行榜地址");
        }


    }
}
