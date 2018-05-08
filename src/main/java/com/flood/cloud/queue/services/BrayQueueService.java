package com.flood.cloud.queue.services;

import org.springframework.amqp.core.Message;

import java.util.Map;

public interface BrayQueueService {

    public String sendToTestExchange();

    public String sendToTestExchange(String routingKey, String exchange, Message msg);

    public String sendToTestExchange(String routingKeyName, String exchangeName, String queueName, Message msg, Map<String, Object> args);
}
