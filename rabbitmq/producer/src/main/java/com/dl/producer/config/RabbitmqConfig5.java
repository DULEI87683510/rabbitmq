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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "5")
public class RabbitmqConfig5 {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig5.class);

    @Bean
    public Queue mnodel5_1() {
        //声明队列
        log.info("声明队列model5_1");
        return new Queue("model5_1");
    }
    @Bean
    public Queue mnodel5_2() {
        //声明队列
        log.info("声明队列model5_2");
        return new Queue("model5_2");
    }


    @Bean
    public TopicExchange topicExchange() {
        log.info("声明交换机topicExchange");
        return   new TopicExchange("topicExchange",true,false);
    }

    /**
     * 队列1绑定到交换机上
     * @return
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(mnodel5_1()).to(topicExchange()).with("rout.*");
    }

    /**
     * 队列2绑定到交换机上
     * @return
     */
    @Bean
    public Binding directBinding2() {

        return BindingBuilder.bind(mnodel5_2()).to(topicExchange()).with("rout.*");
    }
    @Bean
    public Binding directBinding3() {

        return BindingBuilder.bind(mnodel5_1()).to(topicExchange()).with("test.1");
    }
    @Bean
    public Binding directBinding4() {

        return BindingBuilder.bind(mnodel5_2()).to(topicExchange()).with("test.2");
    }


}
