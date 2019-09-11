package com.dl.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息拒绝后回到死信队列得演示
 */
@Configuration
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "7")
public class RabbitmqConfig7 {
    /**死信队列，延迟队列配置**/

    @Bean
    public Queue registerDelayQueue(){
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange","TopicExchange7");
        params.put("x-dead-letter-routing-key","all");
       // params.put("x-message-ttl",5000);
        return new Queue("deal7Queue", true,false,false,params);
    }

    @Bean
    public DirectExchange registerDelayExchange(){
        return new DirectExchange("dealExchange7");
    }

    @Bean
    public Binding registerDelayBinding(){
        return BindingBuilder.bind(registerDelayQueue()).to(registerDelayExchange()).with("deal7");
    }

    /**指标消费队列配置**/

    @Bean
    public TopicExchange registerTopicExchange(){
        return new TopicExchange("TopicExchange7");
    }

    @Bean
    public Binding registerBinding(){
        return BindingBuilder.bind(registerQueue()).to(registerTopicExchange()).with("all");
    }

    @Bean
    public Queue registerQueue(){
        return new Queue("rejectQueue",true);
    }

}
