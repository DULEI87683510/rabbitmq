package com.dl.consumer2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 *@ClassName Receiver1 第一种消息模型
 *@Description TODO
 *@Author DL
 *@Date 2019/9/6 17:20    
 *@Version 1.0
 */
@Component
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "1")
public class Receiver1 {
    private static final Logger log= LoggerFactory.getLogger(Receiver1.class);
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = {"model1"})
    @RabbitHandler
    public void process1(String message) throws InterruptedException {
        log.info("收到队列hello的消息:{}",message);
    }

}
