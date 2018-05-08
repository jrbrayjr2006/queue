package com.flood.cloud.queue;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfiguration {

    public static final String TEST_QUEUE_NAME = "testQueue";

    public static final String TEST_EXCHANGE_NAME = "testExchange.topic";

    private static final String TEST_BINDING_NAME ="testBinding";

    public static final String TEST_ROUTE_KEY = "testRoutingKey";

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

    @Bean
    public Exchange testTopicExchange() {
        TopicExchange topicExchange = new TopicExchange(TEST_EXCHANGE_NAME);
        return topicExchange;
    }

    /**
     * <p>
     *     Binds an exchange to a queue for message routing
     * </p>
     * @return
     */
    @Bean
    public Binding testBinding() {
        Binding testBinding = BindingBuilder.bind(testQueue()).to(testTopicExchange()).with(TEST_ROUTE_KEY).noargs();
        return testBinding;
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
