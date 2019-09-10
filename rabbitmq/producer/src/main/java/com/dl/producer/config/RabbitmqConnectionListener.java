package com.dl.producer.config;

import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;


public class RabbitmqConnectionListener implements ConnectionListener {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConnectionListener.class);
    @Override
    public void onCreate(Connection connection) {

     log.info("rabbitmq连接已经建立", connection.getLocalPort());
    }

    @Override
    public void onClose(Connection connection) {

        log.info("rabbitmq连接已经关闭", connection.getLocalPort());
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {

        log.info("rabbitmq连接已经中断");
    }
}
