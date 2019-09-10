package com.dl.producer.config;

import com.dl.producer.entity.DemoEntity;
import com.dl.producer.entity.DemoEntity2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName CommonConfig
 *@Description TODO
 *@Author DL
 *@Date 2019/9/6 17:48    
 *@Version 1.0
 */
@Configuration
public class CommonConfig  {
    private static final Logger log = LoggerFactory.getLogger(CommonConfig.class);
   // @Autowired
    //private CachingConnectionFactory connectionFactory;

    @Bean
    public  CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory  connectionFactory=new CachingConnectionFactory("192.168.117.132");
     //   connectionFactory.setUri("192.168.117.132");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    /**
     * 自定义Jackson2JsonMessageConverter消息转换器
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(DemoEntity.class);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("entity1", DemoEntity.class);
        idClassMapping.put("entity2", DemoEntity2.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
    /**
     * 如果消息没有到exchange,则confirm回调,ack=false

     如果消息到达exchange,则confirm回调,ack=true

     exchange到queue成功,则不回调return

     exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)

     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        //AmqpTemplate 是amqp协议的抽象模板
        //RabbitTemplate是AmqpTemplate的具体实现，spring定义的一套amqp协议标准，而RabbitTemplate是它的具体实现

 /*       connectionFactory=new CachingConnectionFactory("localhost");
        connectionFactory.setUri("192.168.117.132");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);*/
      //  Connection connection= connectionFactory.createConnection();


        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }

}
