package com.summer.tools.mq.service;

import com.rabbitmq.client.Channel;
import com.summer.tools.mq.config.MqConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitListenerService {

    @RabbitListener(queues = MqConstants.DEFAULT_DIRECT_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void defaultDirect(Map<String, Object> params, Channel channel, Message message) {
        System.out.println("DefaultDirectReceiver消费者收到消息  : " + params.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = MqConstants.DIRECT_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void direct(Map<String, Object> params, Channel channel, Message message) {
        System.out.println("DirectReceiver消费者收到消息  : " + params.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动提交
     */
    @RabbitListener(queues = MqConstants.MAN, containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void topicMan(Map<String, Object> params, Channel channel, Message message) {
        System.out.println("topicMan消费者收到消息  : " + params.toString());try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 手动提交
     */
    @RabbitListener(queues = MqConstants.WOMAN, containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void topicWoman(Map<String, Object> params, Channel channel, Message message) {
        System.out.println("topicWoman消费者收到消息  : " + params.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
