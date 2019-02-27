package com.jeesite.modules.storevideo.msgserver;

import com.jeesite.modules.storevideo.camera.service.SvTvService;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientLogService;
import com.jeesite.modules.storevideo.tvclient.service.SvTvClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    ////TODO tomcat要删除
    ///**
    // * tomcat要删除
    // */
    //@Bean
    //public ServerEndpointExporter serverEndpointExporter() {
    //    return new ServerEndpointExporter();
    //}

    @Autowired
    public void setMessageService(SvTvClientService svTvClientService, SvTvService svTvService, SvTvClientLogService svTvClientLogService) {
        WebSocketServer.svTvClientService = svTvClientService;
        WebSocketServer.svTvService = svTvService;
        WebSocketServer.svTvClientLogService = svTvClientLogService;
    }
}