package com.jeesite.modules.asset.RabbitMqProess;

import com.jeesite.modules.fz.fzlogin.entity.FzLoginRecord;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author easter
 * @data 2018/10/18 13:19
 */

/**
 * 梵赞登陆使用mq插入日志
 */
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${loginQueueC}", durable = "true"),exchange = @Exchange(value = "${fzMsg}",type = ExchangeTypes.TOPIC)))
public class FzLoginRecordListener {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private FzLoginRecordService fzLoginRecordService;
    @RabbitHandler
    public void process(FzLoginRecord fzLoginRecord){
        if(fzLoginRecord != null){
            fzLoginRecord.setIsNewRecord(true);
            fzLoginRecordService.save(fzLoginRecord);
        }
    }
}
