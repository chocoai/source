package com.jeesite.modules.storevideo.config;

import com.jeesite.modules.asset.ding.FzTask;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SVRabbitConfig {

    //@Value("${spring.rabbitmq.host}")
    //String host;
    //@Value("${spring.rabbitmq.port}")
    //String port;
    //@Value("${spring.rabbitmq.username}")
    //String username;
    //@Value("${spring.rabbitmq.password}")
    //String password;

    public final static String EXCHANGE = "AM_SV";
    public final static String QUEUE_PUSH_VIDEO = "SV_PUSH_VIDEO";

    @Bean
    public Queue pushVideoQueue() {
        return new Queue(SVRabbitConfig.QUEUE_PUSH_VIDEO);
    }

    ////创建mq连接
    //@Bean(name = "connectionFactory")
    //public ConnectionFactory connectionFactory() {
    //    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    //    connectionFactory.setUsername(username);
    //    connectionFactory.setPassword(password);
    //    connectionFactory.setVirtualHost("/");
    //    connectionFactory.setPublisherConfirms(true);
    //    /**
    //     * 设置通道数量
    //     */
    //    connectionFactory.setChannelCacheSize(40);
    //
    //    //该方法配置多个host，在当前连接host down掉的时候会自动去重连后面的host
    //    connectionFactory.setAddresses(host);
    //    return connectionFactory;
    //}
    //
    //@Bean
    //public String[] mqMsgQueues() throws AmqpException, IOException {
    //    int queueSize = 1;
    //    String[] queueNames = new String[queueSize];
    //    String hostName = OsUtil.getHostNameForLiunx();//获取hostName
    //    for (int i = 1; i <= queueSize; i++) {
    //        String queueName = String.format("%s.queue%d", hostName, i);
    //        connectionFactory().createConnection().createChannel(false).queueDeclare(queueName, true, false, false, null);
    //        connectionFactory().createConnection().createChannel(false).queueBind(queueName, EXCHANGE, queueName);
    //        queueNames[i - 1] = queueName;
    //    }
    //    return queueNames;
    //}
}
