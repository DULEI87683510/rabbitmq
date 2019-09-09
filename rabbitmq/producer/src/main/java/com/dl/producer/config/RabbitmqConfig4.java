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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "4")
public class RabbitmqConfig4 {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig4.class);

    @Bean
    public Queue mnodel4_1() {
        //声明队列
        log.info("声明队列model4_1");
        return new Queue("model4_1");
    }
    @Bean
    public Queue mnodel4_2() {
        //声明队列
        log.info("声明队列model4_2");
        return new Queue("model4_2");
    }


    @Bean
    public DirectExchange directExchange() {
        log.info("声明交换机directExchange");
        return new DirectExchange("directExchange", true, false);
    }

    /**
     * 队列1绑定到交换机上
     * @return
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(mnodel4_1()).to(directExchange()).with("rout1");
    }

    /**
     * 队列2绑定到交换机上
     * @return
     */
    @Bean
    public Binding directBinding2() {

        return BindingBuilder.bind(mnodel4_2()).to(directExchange()).with("rout2");
    }
    @Bean
    public Binding directBinding3() {

        return BindingBuilder.bind(mnodel4_2()).to(directExchange()).with("rout3");
    }
    @Bean
    public Binding directBinding4() {

        return BindingBuilder.bind(mnodel4_2()).to(directExchange()).with("rout4");
    }


}
