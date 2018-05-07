package com.flood.cloud.queue.services;

import org.springframework.amqp.core.Message;

public interface BrayQueueService {

    public String sendToTestExchange();

    public String sendToTestExchange(String routingKey, String exchange, Message msg);
}
