package com.dl.producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "2")
public class RabbitmqConfig2 {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig2.class);

    @Bean
    public Queue mnodel2() {
        //声明队列
        log.info("声明队列model2");
        return new Queue("model2");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange1", true, false);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(mnodel2()).to(directExchange()).with("model2");
    }

}
