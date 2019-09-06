package com.dl.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *@ClassName Receiver1 第一种消息模型
 *@Description TODO
 *@Author DL
 *@Date 2019/9/6 17:20    
 *@Version 1.0
 */
@Component
public class Receiver1 {
    private static final Logger log= LoggerFactory.getLogger(Receiver1.class);
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = {"hello"})
    @RabbitHandler
    public void process(String message) throws InterruptedException {
        log.info("收到队列hello的消息:{}",message);
    }
}
