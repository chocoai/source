package com.jeesite.modules.asset.RabbitMqProess;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.entity.ActionCard;
import com.jeesite.modules.asset.ding.entity.DingMessage;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.record.service.RecordLogService;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationRecord;
import com.jeesite.modules.sys.utils.ConfigUtils;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 梵赞赞赏记录
 */
//FzAppreciationRecord
@Component
//@RabbitListener(queues = "fzAppreciationRecordMsg1")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${fzAppreciationC}", durable = "true"),exchange = @Exchange(value = "${fzMsg}",type = ExchangeTypes.TOPIC)))
public class
DingFavorMessage {
    @Autowired
    RecordLogService recordLogService;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private AmUtilService amUtilService;
    private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";
//    private static final String FZ_ADDRESS="http://uvh5.uvanart.com:17880/likeApp/index.html#/hall";
  //  @Value("${FZ_ADDRESS}")
    private static final String MSG_TYPE="action_card";
    @RabbitHandler
    public void process(FzAppreciationRecord fzAppreciationRecord) {

        String AGENT_ID = amUtilService.getConfigValue("fz_agentid");
        //获赠人
        DingUser dingUser = new DingUser();
        dingUser.setUserid(fzAppreciationRecord.getPraiserId());
        dingUser = dingUserService.get(dingUser);
        DingUser dingUser1 = new DingUser();
        //赠送人
        dingUser1.setUserid(fzAppreciationRecord.getPresenterId());
        dingUser1 = dingUserService.get(dingUser1);
        if (dingUser != null && dingUser1 != null) {
            //发送给获赠者
            RecordLog recordLog = new RecordLog();
            recordLog.setCreateTime(new Date());
            DingMessage dingMessage = new DingMessage();
            ActionCard actionCard = new ActionCard();
            dingMessage.setMsgtype(MSG_TYPE);
            dingMessage.setAgentid(AGENT_ID);
            // 获赞
            String fzAddress = amUtilService.getConfigValue("fz_income");
            actionCard.setSingle_url(fzAddress);
            dingMessage.setTouser(fzAppreciationRecord.getPraiserId());
            //标题
            actionCard.setTitle("您在" + DateUtils.formatDate(new Date(), "HH:mm:ss") + "获得了" + dingUser1.getName() + "的赞赏");
            actionCard.setMarkdown("您获得了" + dingUser1.getName() + "的赞赏，得到了" + fzAppreciationRecord.getCoinNumber() + "个赞币");
            actionCard.setSingle_title("查看详情");
            dingMessage.setAction_card(actionCard);
            JSONObject jsonObject = JSONObject.fromObject(dingMessage);
            String accessToken = RedisHelp.redisHelp.getAcessToken();
            String url = SEND_ADDRESS + accessToken;
            String info = HttpClientUtils.ajaxPostJson(url, jsonObject.toString(), "UTF-8");
            recordLog.setTitle("梵赞消息推送");
            recordLog.setType("fanzan_appreciation_message");
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("钉钉消息请求", jsonObject);
            jsonObject2.put("钉钉消息响应", info);
            recordLog.setContent(jsonObject2.toString());
//            template.convertAndSend("recordLog1",recordLog);
            template.convertAndSend(FzTask.recordLogQueueP1, recordLog);
            recordLog.setCreateTime(new Date());
            //发送给赠送者
            dingMessage.setTouser(fzAppreciationRecord.getPresenterId());
            String expenditureAddress = ConfigUtils.getConfig("fz_expenditure").getConfigValue();
            actionCard.setSingle_url(expenditureAddress);
            actionCard.setTitle("您在" + DateUtils.formatDate(new Date(), "HH:mm:ss") + "成功赞赏了" + dingUser.getName());
            actionCard.setMarkdown("您赞赏了" + dingUser.getName() + " " + fzAppreciationRecord.getCoinNumber() + "个赞币");
            dingMessage.setAction_card(actionCard);
            JSONObject senderInfo = JSONObject.fromObject(dingMessage);
            url = SEND_ADDRESS + accessToken;
            info = HttpClientUtils.ajaxPostJson(url, senderInfo.toString(), "UTF-8");
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("钉钉消息请求", senderInfo);
            jsonObject3.put("钉钉消息响应", info);
            recordLog.setWriteTime(new Date());
            recordLog.setContent(jsonObject3.toString());
//            template.convertAndSend("recordLog1",recordLog);
            template.convertAndSend(FzTask.recordLogQueueP1, recordLog);
        }
        //赠送人姓名

        //获赠人


        /*JSONObject jsonObject=new JSONObject();
        jsonObject.put("agentid",AGENT_ID);
        jsonObject.put("msgtype","action_card");
        jsonObject.put("touser","");
        String accessToken= RedisHelp.redisHelp.getAcessToken();
        RecordLog recordLog=new RecordLog();
        recordLog.setCreateTime(new Date());
        String url=SEND_ADDRESS+accessToken;
        String info= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
        JSONObject jsonObject1=JSONObject.fromObject(info);
        if(0==jsonObject1.getInt("errcode")){
            recordLog.setLevel("info");
        }else{
            recordLog.setLevel("warn");
        }
        System.out.println(info);
        recordLog.setWriteTime(new Date());
       // recordLog.setPath(request.getServletPath());
        recordLog.setTitle("梵钻消息推送");
        recordLog.setType("fanzan_ding_message");
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("钉钉消息请求",jsonObject);
        jsonObject2.put("钉钉消息响应",info);
        recordLog.setContent(jsonObject2.toString());
        template.convertAndSend("recordLog1",recordLog);*/
       // return ReturnDate.success();

    }

}
