package com.dl.producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@ClassName RabbitmqConfig1
 *@Description TODO
 *@Author DL
 *@Date 2019/9/6 17:28    
 *@Version 1.0
 */
@Configuration
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "1")
public class RabbitmqConfig1 {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig1.class);
    @Bean
    public Queue helloWorldQueue() {
        //声明队列
        return new Queue("hello");
    }

}
