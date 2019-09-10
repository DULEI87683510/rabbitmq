package com.dl.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "1")
public class RabbitmqConfig6 {
    /**死信队列，延迟队列配置**/

    @Bean
    public Queue registerDelayQueue(){
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange","dealExchange");
        params.put("x-dead-letter-routing-key","all");
        return new Queue("dealQueue", true,false,false,params);
    }

    @Bean
    public DirectExchange registerDelayExchange(){
        return new DirectExchange("dealExchange");
    }

    @Bean
    public Binding registerDelayBinding(){
        return BindingBuilder.bind(registerDelayQueue()).to(registerDelayExchange()).with("");
    }

    /**指标消费队列配置**/

    @Bean
    public TopicExchange registerTopicExchange(){
        return new TopicExchange("testTopicExchange");
    }

    @Bean
    public Binding registerBinding(){
        return BindingBuilder.bind(registerQueue()).to(registerTopicExchange()).with("all");
    }

    @Bean
    public Queue registerQueue(){
        return new Queue("testQueue",true);
    }

}
