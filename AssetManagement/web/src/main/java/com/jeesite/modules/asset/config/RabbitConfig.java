package com.jeesite.modules.asset.config;


import com.jeesite.modules.asset.ding.FzTask;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue logsQueue() {
        return new Queue(FzTask.recordLogQueueP);
    }
    @Bean
    public Queue newLogQueue() {
        return new Queue(FzTask.recordLogQueueP1);
    }
    @Bean
    public Queue fzMsgQueue() {
        return new Queue(FzTask.fzMsgQueueP1);
    }
    @Bean
    public Queue fzAppreciationRecordQueue() {
        return new Queue(FzTask.fzAppreciationQueueP);
    }
    @Bean
    public Queue fzLoginRecord() {
        return new Queue(FzTask.loginQueuesP);
    }
    @Bean
    public Queue fzNeigouLoginLog(){
        return new Queue(FzTask.fzNeigouLoginLogsP);
    }
    @Bean
    public Queue fzNeigouOrderLog(){
        return new Queue(FzTask.fzNeigouOrderLogsP);
    }
}