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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "4")
public class Receiver4 {
    private static final Logger log= LoggerFactory.getLogger(Receiver4.class);

    @Autowired
    private ObjectMapper objectMapper;
    //支持自动声明绑定，声明之后自动监听队列的队列，此时@RabbitListener注解的queue和bindings不能同时指定，否则报错
 /*   @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "model2",durable = "true"),
            exchange =@Exchange(value = "directExchange1",durable = "true"),key = "model2")})*/

    @RabbitHandler
    @RabbitListener(queues = "model4_1")
    public void process4_1(Message message,Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process4_1的消息:{}",new String(message.getBody()) );

    }
    @RabbitHandler
    @RabbitListener(queues = "model4_2")
    public void process4_2(Message message,Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process4_2的消息:{}",new String(message.getBody()) );

    }


}
