package com.dl.producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "3")
public class RabbitmqConfig3 {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig3.class);

    @Bean
    public Queue mnodel3_1() {
        //声明队列
        log.info("声明队列model3_1");
        return new Queue("model3_1");
    }
    @Bean
    public Queue mnodel3_2() {
        //声明队列
        log.info("声明队列model3_2");
        return new Queue("model3_2");
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        log.info("声明交换机fanoutExchange");
        return new FanoutExchange("fanoutExchange", true, false);
    }

    /**
     * 队列1绑定到交换机上
     * @return
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(mnodel3_1()).to(fanoutExchange());
    }

    /**
     * 队列2绑定到交换机上
     * @return
     */
    @Bean
    public Binding directBinding2() {
        return BindingBuilder.bind(mnodel3_2()).to(fanoutExchange());
    }

}
