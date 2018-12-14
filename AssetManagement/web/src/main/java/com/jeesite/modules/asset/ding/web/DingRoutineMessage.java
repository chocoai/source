package com.jeesite.modules.asset.ding.web;

import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.ReadFile;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.util.RedisHelp;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.fz.appreciation.service.FzLeaderboardsService;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.List;

/**
 * 周排行月排行钉钉消息定时推送
 */
@Configuration
@EnableScheduling
public class DingRoutineMessage {
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    FzLeaderboardsService fzLeaderboardsService;
    @Autowired
    private AmUtilService amUtilService;
    private static final String SEND_ADDRESS="https://oapi.dingtalk.com/message/send?access_token=";
//    private static final String FANZAN_RANK_URL="http://uvh5.uvanart.com:17880/likeApp/index.html#/rank";
   // @Value("${FANZAN_RANK_URL}")
    private String FANZAN_RANK_URL;
//    private static final String AGENT_ID="5110142";
    private static final String MSG_TYPE="action_card";
    @Value("${file.baseDir}")
    String baseDir;
    /**
     * 排行榜，每周五16点
     */
    //@Scheduled(cron="0 0 10 ? * MON")
    //每周五下午4点
//    @Scheduled(cron="0 0 16 ? * FRI")
    //每2分钟执行一次
  /*  @Scheduled(cron=" 0 0/2 * * * ?")*/
    public void inoutGoldMessageEveryWeek(){
        // 文件
        String path = baseDir + "/fz/routineMessage.txt";
        // 获取的标识
        String flag = ReadFile.ReadToString(path);
        // false 代表不执行 true 代表执行
        if (!"true".equals(flag)) {
            return;
        }
        String titile="梵赞排行榜出炉啦~";
        String singleTile="查看详情";
        String markdown="梵赞排行榜出炉啦，请查看英雄榜单";
        DingUser dingUser=new DingUser();
        dingUser.setleft("0");
//        dingUser.setUserid("18392547561934016313");
        List<DingUser> userList=dingUserService.findList(dingUser);
        //16593529601717469825
       /* sendFixedMessage("16593529601717469825",titile,singleTile,markdown);*/
        if(userList!=null &&userList.size()>0){
            FANZAN_RANK_URL=amUtilService.getConfigValue("fanzan_rank_url");
            String AGENT_ID =amUtilService.getConfigValue("fz_agentid");
            for(DingUser dingUser1:userList){
                String touser=dingUser1.getUserid();
                sendFixedMessage(touser,titile,singleTile,markdown, FANZAN_RANK_URL, AGENT_ID);
            }
        }
    }
    /**
     * 月排行，每月1号10:15分
     */
  /*  @Scheduled(cron="0 1 10 30 * ?")
    public void inoutGoldMessageEveryMonth(){
        String titile="请查阅梵赞月排行榜";
        String singleTile="";
        String markdown="";
        DingUser dingUser=new DingUser();
        dingUser.setleft("0");
        List<DingUser> userList=dingUserService.findList(dingUser);
        if(userList!=null &&userList.size()>0){
            for(DingUser dingUser1:userList){
                String touser=dingUser1.getUserid();
                sendFixedMessage(touser,titile,singleTile,markdown);
            }
        }
    }*/
    public void sendFixedMessage(String touser,String title,String singleTitle,String markdown, String FANZAN_RANK_URL, String AGENT_ID){

        JSONObject jsonObject=new JSONObject();
        RecordLog recordLog=new RecordLog();
        recordLog.setCreateTime(new Date());
        String accessToken= RedisHelp.redisHelp.getAcessToken();
        jsonObject.put("touser",touser);
        jsonObject.put("agentid",AGENT_ID);
        jsonObject.put("msgtype",MSG_TYPE);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("title",title);
        jsonObject1.put("markdown",markdown);
        jsonObject1.put("single_title",singleTitle);
        jsonObject1.put("single_url",FANZAN_RANK_URL);
        jsonObject.put("action_card",jsonObject1);
        String url=SEND_ADDRESS+accessToken;
        String responseinfo= HttpClientUtils.ajaxPostJson(url,jsonObject.toString(),"UTF-8");
        recordLog.setWriteTime(new Date());
        JSONObject jsonObject3=JSONObject.fromObject(responseinfo);
        if(0==jsonObject3.getInt("errcode")){
            recordLog.setLevel("info");
        }else{
            recordLog.setLevel("warn");
        }

        recordLog.setTitle("梵赞消息推送");
        recordLog.setType("fanzan_rank_message");
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("钉钉消息请求",jsonObject);
        jsonObject2.put("钉钉消息响应",responseinfo);
        recordLog.setContent(jsonObject2.toString());
//        template.convertAndSend("recordLog1",recordLog);
        template.convertAndSend(FzTask.recordLogQueueP1,recordLog);
    }
}
