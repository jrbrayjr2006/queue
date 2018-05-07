package com.flood.cloud.queue.services;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BrayRabbitMQServiceImpl implements BrayQueueService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Exchange testTopicExchange;

    @Autowired
    Queue testQueue;

    @Autowired
    RabbitAdmin rabbitAdmin;

    public BrayRabbitMQServiceImpl() {
    }

    public BrayRabbitMQServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public String sendToTestExchange() {
        Map<String, Object> args = new HashMap<>();
        Binding binding = new Binding("TestQueue", Binding.DestinationType.QUEUE, "TestExchange", "TestRoutingKey", args);
        rabbitAdmin.declareBinding(binding);
        String routingKey = "TestRoutingKey";
        String exchange = "TestExchange";
        byte[] messageBody = "{'testKey': 'testValue'}".getBytes();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setConsumerQueue("TestQueue");
        Message message = new Message(messageBody,messageProperties);
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
        return "sent";
    }

    public String sendToTestExchange(String routingKey, String exchange, Message msg) {
        rabbitTemplate.convertAndSend(routingKey,exchange,msg);
        return "sent";
    }
}
