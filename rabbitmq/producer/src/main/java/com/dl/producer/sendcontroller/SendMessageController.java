package com.dl.producer.sendcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName SendMessageController
 *@Description TODO
 *@Author DL
 *@Date 2019/9/6 17:39    
 *@Version 1.0
 */
@RestController
public class SendMessageController {
    private static final Logger log= LoggerFactory.getLogger(SendMessageController.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
        @GetMapping("/send1")
        public String send1(String message){
        amqpTemplate.convertAndSend("model1",message);
        return "已经发送消息："+message;
        }
   /* @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "model2",durable = "true"),
            exchange =@Exchange(value = "directExchange1",durable = "true"),key = "model2")})*/
        @GetMapping("/send2")
        public String send2(String message){
            //Jackson2JsonMessageConverter()是將數據轉換成2進制消息流
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange("directExchange1");
           rabbitTemplate.setRoutingKey("model2");
            for(int i=0 ;i<100;i++){
                log.info("发送第{}条消息:{}",i,message);
                amqpTemplate.convertAndSend("model2","消息"+i+":"+message);
            }

        return "已经发送消息："+message;
        }
}
