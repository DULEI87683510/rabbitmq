package com.dl.consumer2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "5")
public class Receiver5 {
    private static final Logger log= LoggerFactory.getLogger(Receiver5.class);

    @Autowired
    private ObjectMapper objectMapper;
    //支持自动声明绑定，声明之后自动监听队列的队列，此时@RabbitListener注解的queue和bindings不能同时指定，否则报错
 /*   @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "model2",durable = "true"),
            exchange =@Exchange(value = "directExchange1",durable = "true"),key = "model2")})*/

    @RabbitHandler
    @RabbitListener(queues = "model5_1")
    public void process5_1(Message message,Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process5_1的消息:{}",new String(message.getBody()) );

    }
    @RabbitHandler
    @RabbitListener(queues = "model5_2")
    public void process5_2(Message message,Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process5_2的消息:{}",new String(message.getBody()) );

    }


}
