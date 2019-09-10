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
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "1")
public class RabbitmqConfig1 {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig1.class);

    /**
     * 声明队列参数
     * name（队列名称）
     * Durable（消息代理重启后，队列依旧存在）
     * Exclusive（只被一个连接（connection）使用，而且当连接关闭后队列即被删除）
     * Auto-delete（当最后一个消费者退订后即被删除）
     * Arguments（一些消息代理用他来完成类似与TTL的某些额外功能）
     *
     * @return
     */
    @Bean
    public Queue model1() {
        //声明队列
        log.info("声明队列model1");
        return new Queue("model1");
    }

}
