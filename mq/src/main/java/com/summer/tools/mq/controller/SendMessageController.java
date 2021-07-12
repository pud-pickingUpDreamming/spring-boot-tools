package com.summer.tools.mq.controller;

import com.summer.tools.mq.config.MqConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/mq")
@Api(tags = "消息队列自测接口")
public class SendMessageController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("直连交换机-自定义交换机")
    @GetMapping("/directMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test direct message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(MqConstants.DIRECT_EXCHANGE, MqConstants.DIRECT_ROUTING, map);
        return "ok";
    }

    @ApiOperation("直连交换机-默认交换机")
    @GetMapping("/defaultDirectMessage")
    public String sendDefaultDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test default direct message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(MqConstants.DEFAULT_DIRECT_QUEUE, map);
        return "ok";
    }

    @ApiOperation("主题交换机")
    @GetMapping("/topic")
    public String sendTopicMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test topic message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(MqConstants.TOPIC_EXCHANGE, MqConstants.TOPIC_ROUTING, map);
        return "ok";
    }
}