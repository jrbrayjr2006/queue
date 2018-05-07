package com.flood.cloud.queue;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfiguration {

    private static final String TEST_QUEUE_NAME = "TestQueue";

    private static final String TEST_EXCHANGE_NAME = "TestExchange";

    private static final String TEST_TOPIC_EXCHANGE ="TestTopicExchange";

    private static final String TEST_ROUTE_KEY = "TestRoutingKey";

    @Value("${rabbitmq.hostname}")
    String hostname;

//    @Value("${rabbitmq.port}")
//    Integer port;


    @Bean
    @Primary
    public ConnectionFactory routedConnectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Bean
    public Queue testQueue() {
        return new Queue(TEST_QUEUE_NAME);
    }

//    @Bean
//    public Exchange testExchange() {
//        return new DirectExchange(TEST_EXCHANGE_NAME);
//    }

    @Bean
    public Exchange testTopicExchange() {
        TopicExchange topicExchange = new TopicExchange(TEST_EXCHANGE_NAME);
        return topicExchange;
    }

    @Bean
    public ConnectionFactory testRoutingConnectionFactory() {
        return new SimpleRoutingConnectionFactory();
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(routedConnectionFactory());
        return rabbitAdmin;
    }


}
