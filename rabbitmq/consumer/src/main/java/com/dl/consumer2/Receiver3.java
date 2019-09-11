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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "3")
public class Receiver3 {
    private static final Logger log= LoggerFactory.getLogger(Receiver3.class);

    @Autowired
    private ObjectMapper objectMapper;
    //支持自动声明绑定，声明之后自动监听队列的队列，此时@RabbitListener注解的queue和bindings不能同时指定，否则报错
 /*   @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "model2",durable = "true"),
            exchange =@Exchange(value = "directExchange1",durable = "true"),key = "model2")})*/

    @RabbitHandler
    @RabbitListener(queues = "model3_1")
    public void process3_1(Message message,Channel channel) throws InterruptedException, IOException {
/*        //手动回执，消费成功，并删除
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        //消费失败，
        //deliveryTag 消息ID
        //multiple （true = 批量  / false = 不批量）
        //requeue （true = 重回队列  / false = 删除该消息），可以批量操作
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        //消息拒绝，requeue true = （重回队列  / false = 删除该消息），该方法一次只能拒绝一条
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);*/
        log.info("收到队列process3_1的消息:{}",new String(message.getBody()) );

    }



}
