package com.dl.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "2")
public class Receiver2 {
    private static final Logger log= LoggerFactory.getLogger(Receiver2.class);
    @Autowired
    private ObjectMapper objectMapper;
    //支持自动声明绑定，声明之后自动监听队列的队列，此时@RabbitListener注解的queue和bindings不能同时指定，否则报错
/*    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "model2",durable = "true"),
            exchange =@Exchange(value = "directExchange1",durable = "true"),key = "model2")})
    @RabbitListener(queues = "model2")
    @RabbitHandler
    public void process2_1(String message) throws InterruptedException {
        log.info("收到队列process2_1的消息:{}",message);
    }*/

    @RabbitListener(queues = "model2")
    @RabbitHandler
    public void process2_2(Message message, Channel channel) throws InterruptedException, IOException {
        Thread.sleep(2000);
        log.info("收到队列process2_1的消息:{}",new String(message.getBody()) );
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        log.info("收到队列process2_1的消息确认");
    }
}
