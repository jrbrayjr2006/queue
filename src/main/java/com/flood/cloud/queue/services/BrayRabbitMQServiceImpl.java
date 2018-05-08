package com.flood.cloud.queue.services;

import com.flood.cloud.queue.RabbitConfiguration;
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
        Binding binding = new Binding(RabbitConfiguration.TEST_QUEUE_NAME, Binding.DestinationType.QUEUE, RabbitConfiguration.TEST_EXCHANGE_NAME, RabbitConfiguration.TEST_ROUTE_KEY, args);
        rabbitAdmin.declareBinding(binding);
        String routingKey = "testRoutingKey";
        String exchange = "testExchange";
        byte[] messageBody = "{'testKey': 'testValue'}".getBytes();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setConsumerQueue("testQueue");
        Message message = new Message(messageBody,messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfiguration.TEST_EXCHANGE_NAME,RabbitConfiguration.TEST_ROUTE_KEY,message);
        return "sent";
    }

    @Override
    public String sendToTestExchange(String routingKeyName, String exchangeName, Message msg) {
        Map<String, Object> args = new HashMap<>();
        Binding binding = new Binding(RabbitConfiguration.TEST_QUEUE_NAME, Binding.DestinationType.QUEUE, exchangeName, routingKeyName, args);
        rabbitAdmin.declareBinding(binding);
        rabbitTemplate.convertAndSend(routingKeyName, exchangeName ,msg);
        return "sent";
    }


    public String sendToTestExchange(String routingKeyName, String exchangeName, String queueName, Message msg, Map<String, Object> args) {
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKeyName, args);
        rabbitAdmin.declareBinding(binding);
        rabbitTemplate.convertAndSend(routingKeyName, exchangeName ,msg);
        return "sent";
    }
}
