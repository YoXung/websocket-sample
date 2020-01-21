package com.seabed.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Description:
 * @Author: YoXung
 * @Date: 2020/01/15 23:58
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WebSocketEndpointConfigure myEndpointConfigure() {
        return new WebSocketEndpointConfigure();
    }

}
