package com.dl.consumer2;

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
    /**
     *  deliveryTag 消息ID
     multiple （true = 批量  / false = 不批量）
     requeue （true = 重回队列  / false = 删除该消息）
     void basicNack(long deliveryTag, boolean multiple , boolean requeue)
     channel.basicReject 一次只能拒绝一条消息，如果需要批量拒绝那么就需要用到 channel.basicNack，参数介绍如下
     //deliveryTag 消息ID
     //requeue true = （重回队列  / false = 删除该消息）
     void basicReject(long deliveryTag, boolean requeue) throws IOException;
     */

    @RabbitListener(queues = "model2")
    @RabbitHandler
    public void process2_2(Message message, Channel channel) throws InterruptedException, IOException {
      //  channel.basicQos(1);
        Thread.sleep(2000);
        log.info("收到队列process2_1的消息:{}",new String(message.getBody()) );
        //message.getMessageProperties().getDeliveryTag()消息ID
      //  channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
     //   log.info("收到队列process2_1的消息确认");
    }
}
