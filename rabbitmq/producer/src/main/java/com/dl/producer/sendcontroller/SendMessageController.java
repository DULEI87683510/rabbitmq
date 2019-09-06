package com.dl.producer.sendcontroller;

import org.springframework.amqp.core.AmqpTemplate;
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
    @Autowired
    private AmqpTemplate amqpTemplate;
        @GetMapping("/send1")
        public String send1(String message){
        amqpTemplate.convertAndSend("hello",message);
        return "已经发送消息："+message;
        }
}
