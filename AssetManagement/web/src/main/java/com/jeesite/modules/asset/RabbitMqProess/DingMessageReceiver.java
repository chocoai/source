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
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import com.jeesite.modules.sys.utils.ConfigUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 梵钻收支记录
 */
@Component
//@RabbitListener(queues = "fzMsg1")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${fzMsgC1}", durable = "true"),exchange = @Exchange(value = "${fzMsg}",type = ExchangeTypes.TOPIC)))
public class DingMessageReceiver {
    @Autowired
    RecordLogService recordLogService;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private AmUtilService amUtilService;
    private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";
//    private static final String AGENT_ID="5110142";
//    private static final String FZ_ADDRESS="http://uvh5.uvanart.com:17880/likeApp/index.html#/hall";
    //@Value("${FZ_ADDRESS}")
    private static final String MSG_TYPE="action_card";
    @RabbitHandler
    public void process(FzGoldChangeRecord fzGoldChangeRecord){

        String AGENT_ID = amUtilService.getConfigValue("fz_agentid");
        //1赞赏 2跟赞 else 后台分配
        // int sourceType=fzGoldChangeRecord.getType();
        String userId = fzGoldChangeRecord.getUserid();
        DingUser dingUser = new DingUser();
        dingUser.setUserid(userId);
        dingUser = dingUserService.get(dingUser);
        if (dingUser != null) {
            DingMessage dingMessage = new DingMessage();
            RecordLog recordLog = new RecordLog();
            recordLog.setCreateTime(new Date());
            ActionCard actionCard = new ActionCard();
            dingMessage.setMsgtype(MSG_TYPE);
            dingMessage.setAgentid(AGENT_ID);

            dingMessage.setTouser(userId);
            actionCard.setSingle_title("查看详情");
            String inout = "";
            //判断是收入还是支出
            String fzAddress = "";
            if ("0".equals(fzGoldChangeRecord.getInOrOut())) {
                inout = "收入";
                // 收入梵钻（收入可兑换梵钻）
                fzAddress = amUtilService.getConfigValue("fz_convertible");
            } else {
                inout = "支出";
                fzAddress = amUtilService.getConfigValue("fz_expenditure");
            }
            actionCard.setSingle_url(fzAddress);
            String type = amUtilService.findDictLabel(fzGoldChangeRecord.getGoldType(), "fz_gold_type");
            String message = "您在" + DateUtils.formatDate(fzGoldChangeRecord.getMsgDate(), "HH:mm:ss") + inout + "了" + fzGoldChangeRecord.getNumber() + "个" + type;
            actionCard.setTitle(message);
            actionCard.setMarkdown(message);
            dingMessage.setAction_card(actionCard);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(dingMessage);
            String accessToken = RedisHelp.redisHelp.getAcessToken();
            String url = SEND_ADDRESS + accessToken;
            String info = HttpClientUtils.ajaxPostJson(url, jsonObject.toString(), "UTF-8");
            recordLog.setTitle("梵赞消息推送");
            recordLog.setType("fanzan_changerecord_message");
            net.sf.json.JSONObject jsonObject2 = new net.sf.json.JSONObject();
            jsonObject2.put("钉钉消息请求", jsonObject);
            jsonObject2.put("钉钉消息响应", info);
            recordLog.setContent(jsonObject2.toString());
//            template.convertAndSend("recordLog1",recordLog);
            template.convertAndSend(FzTask.recordLogQueueP1, recordLog);
        }

    }
    @RabbitHandler
    public void process(Integer a){
        System.out.println("Receiver收到了消息:"+a);

    }
}
