package com.jeesite.modules.asset.RabbitMqProess;

import com.jeesite.modules.fz.order.dao.FzNeigouFzgoldLogDao;
import com.jeesite.modules.fz.order.dao.FzNeigouOrderDao;
import com.jeesite.modules.fz.order.entity.FzNeigouFzgoldLog;
import com.jeesite.modules.fz.order.entity.FzNeigouOrder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author easter
 * @data 2018/11/26 11:57
 */
@Component
//@RabbitListener(queues = "fzNeigouOrderLogP")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${fzNeigouOrderLogC}", durable = "true"),exchange = @Exchange(value = "${fzNeigou}",type = ExchangeTypes.TOPIC)))
public class FzNeigouOrderLogListener {

    @Autowired
    private FzNeigouFzgoldLogDao dao;
    @RabbitHandler
    public void process(FzNeigouFzgoldLog fzNeigouFzgoldLog){
        if(fzNeigouFzgoldLog != null){
            fzNeigouFzgoldLog.setIsNewRecord(true);
            dao.insert(fzNeigouFzgoldLog);
        }
    }
}
