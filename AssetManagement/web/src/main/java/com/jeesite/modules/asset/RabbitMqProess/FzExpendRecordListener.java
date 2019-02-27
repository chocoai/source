package com.jeesite.modules.asset.RabbitMqProess;

import com.jeesite.modules.fz.expendrecord.entity.FzExpenditureRecord;
import com.jeesite.modules.fz.expendrecord.service.FzExpenditureRecordService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 梵钻支出记录
 */
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${fzExpendRecordC}",durable = "true"),exchange = @Exchange(value = "${fzNeigou}",type = ExchangeTypes.TOPIC)))
public class FzExpendRecordListener {
    @Autowired
    private FzExpenditureRecordService fzExpenditureRecordService;
    @RabbitHandler
    public void process(FzExpenditureRecord fzExpenditureRecord) {
        fzExpenditureRecord.setIsNewRecord(true);
        fzExpenditureRecord.setExpendTime(new Date());
        fzExpenditureRecord.setExpendMode("点滴商城消费");
        fzExpenditureRecordService.save(fzExpenditureRecord);
    }
}
