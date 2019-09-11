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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "7")
public class Receiver7 {
    private static final Logger log= LoggerFactory.getLogger(Receiver7.class);
    @RabbitHandler
    @RabbitListener(queues = "deal7Queue")
    public void process7(Message message, Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process7的消息并拒绝这个消息，让消息重回到队列:{}",new String(message.getBody()) );
        /**
         * false,将消息从该队列中删除，并发送到由该死信队列绑定的真正消费队列里面去
         * true，回到当前发送的队列里面去，重新发送
         */
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);

    }
    @RabbitHandler
    @RabbitListener(queues = "rejectQueue")
    public void process7_2(Message message, Channel channel) throws InterruptedException, IOException {
        log.info("收到队列process7_2由死信队列得到消息:{}",new String(message.getBody()) );

    }
}
