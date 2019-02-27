package com.jeesite.modules.storevideo.config;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.fz.fzlogin.entity.FzLoginRecord;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;
import com.jeesite.modules.storevideo.util.DetectEngine;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author easter
 * @data 2018/10/18 13:19
 */

/**
 * 梵赞登陆使用mq插入日志
 */
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = SVRabbitConfig.QUEUE_PUSH_VIDEO, durable = "true"),exchange = @Exchange(value = SVRabbitConfig.EXCHANGE,type = ExchangeTypes.TOPIC)))
public class PushVideoListener {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private DetectEngine detectEngine;
    @RabbitHandler
    public void process(Map param){
        try{
            //发送视频到电视机
            detectEngine.pushVideo(param);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
