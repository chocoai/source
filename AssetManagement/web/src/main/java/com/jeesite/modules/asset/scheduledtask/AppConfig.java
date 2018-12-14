package com.jeesite.modules.asset.scheduledtask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;
/**
 * 定时器并发类
 * **/
@Configuration
public class AppConfig {

    @Bean
    public ScheduledThreadPoolExecutor scheduledExecutorService() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(100);
        return executor;
    }
}
