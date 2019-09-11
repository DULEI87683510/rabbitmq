package com.dl.consumer2;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *@ClassName Receiver6
 *@Description TODO
 *@Author DL
 *@Date 2019/9/11 11:20    
 *@Version 1.0
 */
@Component
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "6")
public class Receiver6 {
    private static final Logger log= LoggerFactory.getLogger(Receiver6.class);
    @RabbitHandler
    @RabbitListener(queues = "testQueue")
    public void process6(Message message, Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process6的消息:{}",new String(message.getBody()) );

    }
}
