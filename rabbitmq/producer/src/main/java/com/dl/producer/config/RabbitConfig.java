package com.dl.producer.config;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName RabbitConfig
 * @Description TODO
 * @Author DL
 * @Date 2019/8/2 14:41
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(name="model",prefix = "demo.rabbitmq.config" ,matchIfMissing = false,havingValue = "0")
public class RabbitConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 并发消费者的线程初始化数
     */
    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private int concurrency;
    /**
     * 并发消费者的线程最大数
     */
    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private int maxConcurrency;
    /**
     * 每个消费者每次监听时可拉取处理的消息数量
     */
    @Value("${spring.rabbitmq.listener.simple.prefetch}")
    private int prefetch;

    @Bean
    public Queue helloWorldQueue() {
        //声明队列
        return new Queue("hello");
    }

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
    @Bean("logUser")
    public Queue logUserQueue() {
        //durable当消息代理重启后，队列依旧存在
        return new Queue("logUserQueue", true);
    }

    /**
     * 交换机类型
     * FanoutExchange（扇形交换机）: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange（头交换机） ：通过添加属性key-value匹配
     * DirectExchange（直连交换机）:按照routingkey分发到指定队列
     * TopicExchange（主题交换机）:多关键字匹配
     * 参数
     * Name （交换机名称）
     * Durability （消息代理重启后，交换机是否还存在）
     * Auto-delete （当所有与之绑定的消息队列都完成了对此交换机的使用后，删掉它）
     * Arguments（依赖代理本身）
     *
     * @return
     */
    @Bean
    public DirectExchange logUserExchange() {
        return new DirectExchange("logUserExchange", true, false);
    }

    @Bean
    public Binding logUserBinding() {
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with("logUser");
    }

    //流量削峰,模拟抢购

    @Bean("orderQueue")
    public Queue orderQueue() {
        return new Queue("orderQueue", true);
    }

    @Bean
    public TopicExchange orderExChange() {
        return new TopicExchange("orderExchange", true, false);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExChange()).with("order");

    }



    @Bean("simpleMessageListenerContainer")
    public SimpleMessageListenerContainer messageListenerContainer(@Qualifier("orderQueue") Queue queue) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setMessageConverter(new Jackson2JsonMessageConverter());
        //并发配置
        //初始化消费者
        simpleMessageListenerContainer.setConcurrentConsumers(concurrency);
        //最大消费者数量
        simpleMessageListenerContainer.setMaxConcurrentConsumers(maxConcurrency);
        //每次监听时可以拉取的最大消息数量

        simpleMessageListenerContainer.setPrefetchCount(prefetch);
        //消息确认机制
        simpleMessageListenerContainer.setQueues(queue);
        //手动确认
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置监听
     //   simpleMessageListenerContainer.setMessageListener(receiver);

        return simpleMessageListenerContainer;

    }


    /**
     * 单一消费者
     * SimpleRabbitListenerContainerFactory用于管理RabbitMQ监听器listener 的容器工厂
     *
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.createListenerContainer();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //初始化消费者1
        factory.setConcurrentConsumers(1);
        //最大消费者数量1
        factory.setMaxConcurrentConsumers(1);
        //每次监听时可以拉取的最大消息数量1
        //保证公平分发
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 确认处理机制有三种：Auto - 自动、Manual - 手动、None - 无需确认，而确认机制需
     * 多个消费者
     *
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(concurrency);
        factory.setMaxConcurrentConsumers(maxConcurrency);
        factory.setPrefetchCount(prefetch);
        return factory;
    }

    /**
     * #开启 confirm 确认机制
     * spring.rabbitmq.publisher-confirms=true
     * #开启 return 确认机制
     * spring.rabbitmq.publisher-returns=true
     *
     *
     #设置消费端手动 ack
     spring.rabbitmq.listener.simple.acknowledge-mode=manual
     #消费者最小数量
     spring.rabbitmq.listener.simple.concurrency=1
     #消费之最大数量
     spring.rabbitmq.listener.simple.max-concurrency=10

     #在单个请求中处理的消息个数，他应该大于等于事务数量(unack的最大数量)
     spring.rabbitmq.listener.simple.prefetch=2
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }


}
