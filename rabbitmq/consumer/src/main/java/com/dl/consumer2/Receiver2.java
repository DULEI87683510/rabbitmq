package com.dl.consumer2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "2")
public class Receiver2  {
    private static final Logger log= LoggerFactory.getLogger(Receiver2.class);

    @Autowired
    private ObjectMapper objectMapper;
    //支持自动声明绑定，声明之后自动监听队列的队列，此时@RabbitListener注解的queue和bindings不能同时指定，否则报错
 /*   @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "model2",durable = "true"),
            exchange =@Exchange(value = "directExchange1",durable = "true"),key = "model2")})*/

    @RabbitHandler
    @RabbitListener(queues = "model2")
    public void process2_1( Message message, Channel channel) throws InterruptedException, IOException {
        //channel.basicQos(1);
        Thread.sleep(1000);
        log.info("收到队列process2_1的消息:{}",new String(message.getBody()) );
      //  channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
       // log.info("收到队列process2_1的消息确认");

    }


    /*@Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        log.info("收到队列process2_1的消息:{}",message);
        //进行消费确认
        log.info("收到队列process2_1的消息确认");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        //进行消费否认
      //  channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    this.channel=channel;
    }*/
}
