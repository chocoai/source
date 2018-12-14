package com.jeesite.modules.asset.RabbitMqProess;

import com.jeesite.modules.fz.fzlogin.dao.FzNeigouLoginLogDao;
import com.jeesite.modules.fz.fzlogin.entity.FzNeigouLoginLog;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author easter
 * @data 2018/11/26 10:43
 */
@Component
//@RabbitListener(queues = "fzNeigouLoginLogP")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${fzNeigouLoginLogC}",durable = "true"),exchange = @Exchange(value = "${fzNeigou}",type = ExchangeTypes.TOPIC)))
public class FzNeigouLoginLogListener {
    @Autowired
    private FzNeigouLoginLogDao dao;
    @RabbitHandler
    public void process(FzNeigouLoginLog fzNeigouLoginLog){
        if(fzNeigouLoginLog != null){
            fzNeigouLoginLog.setIsNewRecord(true);
            dao.insert(fzNeigouLoginLog);
        }
    }
}
