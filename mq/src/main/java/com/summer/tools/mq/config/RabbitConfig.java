package com.summer.tools.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Resource
    private ConnectionFactory connectionFactory;

    // 直连交换机,rabbitMq 默认是直连交换机,有默认的交换机配置和绑定路由配置(路由名称和队列名称一样),
    // 如果不是刻意要将不同的消息发送到不同的交换机,可以不用配置交换机和绑定关系
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MqConstants.DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Queue directQueue() {
        return new Queue(MqConstants.DIRECT_QUEUE,true);
    }

    /**
     * 不显示的通过路由绑定到指定的交换机,采用默认交换机和路由
     */
    @Bean
    public Queue defaultDirectQueue() {
        return new Queue(MqConstants.DEFAULT_DIRECT_QUEUE,true);
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(MqConstants.DIRECT_ROUTING);
    }

    // 主题交换机
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MqConstants.TOPIC_EXCHANGE);
    }

    @Bean
    public Queue manQueue() {
        return new Queue(MqConstants.MAN);
    }

    @Bean
    public Queue womanQueue() {
        return new Queue(MqConstants.WOMAN);
    }

    @Bean
    Binding bindingTopicMan() {
        return BindingBuilder.bind(manQueue()).to(exchange()).with(MqConstants.TOPIC_ROUTING);
    }

    @Bean
    Binding bindingTopicWoman() {
        return BindingBuilder.bind(womanQueue()).to(exchange()).with(MqConstants.TOPIC_ROUTING);
    }

    // 配置发送端
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 配置confirm机制, 需要在配置文件里面配置
        // spring.rabbitmq.publisher-confirm-type=correlated
        // spring.rabbitmq.publisher-returns=true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
            System.out.println("ConfirmCallback:     "+"确认情况："+ack);
            System.out.println("ConfirmCallback:     "+"原因："+cause);
        });

        rabbitTemplate.setReturnsCallback(returned -> System.out.println("ReturnCallback: "+"消息："+returned));

        return rabbitTemplate;
    }

    //配置接收端
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(connectionFactory);

        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        // 设置手动提交
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }
}