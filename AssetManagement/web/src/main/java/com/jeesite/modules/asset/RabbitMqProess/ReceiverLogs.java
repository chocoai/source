package com.jeesite.modules.asset.RabbitMqProess;



import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.record.entity.RecordLog;
import com.jeesite.modules.asset.record.service.RecordLogService;
//import net.sf.json.JSONObject;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * Austin
 * RabbitListener表示监听方法，和RabbitHandler搭配使用时，在监听到这个消息队列后，根据不同的参数找到对应得处理方法
 * 搭配使用可接受多种参数
 * */
@Component
//@RabbitListener(queues = "recordLog1")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${recordLogC1}", durable = "true"),exchange = @Exchange(value = "${fzMsgs}",type = ExchangeTypes.TOPIC)))
public class ReceiverLogs {
    @Autowired
    RecordLogService recordLogService;
    @RabbitHandler
    public void process(RecordLog recordLog){
        /*  RecordLog recordLog= (RecordLog) JSONObject.toBean(JSONObject.fromObject(msg));*/
        recordLog.setIsNewRecord(true);
        recordLogService.save(recordLog);
       // System.out.println("Receiver收到了消息:");

    }
    @RabbitHandler
    public void process(String msg){
        JSONObject jsonObject=JSONObject.parseObject(msg);
        RecordLog recordLog=new RecordLog();
        if(jsonObject.containsKey("title")) {
            recordLog.setTitle(jsonObject.getString("title"));
        }
        if(jsonObject.containsKey("type")) {
            recordLog.setType(jsonObject.getString("type"));
        }
        if(jsonObject.containsKey("content")) {
            recordLog.setContent(jsonObject.getString("content"));
        }
        if(jsonObject.containsKey("createTime")) {
            recordLog.setCreateTime(DateUtils.parseDate(jsonObject.getString("createTime")));
        }
        recordLog.setWriteTime(new Date());
        if(jsonObject.containsKey("level")) {
            recordLog.setLevel(jsonObject.getString("level"));
        }
        if(jsonObject.containsKey("path")) {
            recordLog.setPath(jsonObject.getString("path"));
        }
        //recordLog.set
        recordLog.setIsNewRecord(true);
        recordLogService.save(recordLog);
       // System.out.println("Receiver收到了消息:"+msg);

    }
    @RabbitHandler
    public void process(Integer a){
       // System.out.println("Receiver收到了消息:"+a);

    }
}
