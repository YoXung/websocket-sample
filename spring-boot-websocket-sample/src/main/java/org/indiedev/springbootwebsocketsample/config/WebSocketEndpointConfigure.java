package org.indiedev.springbootwebsocketsample.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import jakarta.websocket.server.ServerEndpointConfig;

/**
 * @Description
 * @Author YoXung
 * @Date 2023/7/2 20:05
 */
public class WebSocketEndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return super.getEndpointInstance(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSocketEndpointConfigure.context = applicationContext;
    }

}
