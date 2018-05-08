package com.flood.cloud.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrayRabbitMQExchangeConfiguration {

    protected static final String FITNESS_EXCHANGE_NAME = "fitnessExchange.topic";

    protected static final String BRAY_DIRECT_EXCHANGE = "brayExchange.direct";

    @Bean
    public Exchange fitnessExchange() {
        return new TopicExchange(FITNESS_EXCHANGE_NAME);
    }

    @Bean
    public Exchange omnibusExchange() {
        Exchange exchange = ExchangeBuilder.topicExchange("brayExchange.topic").durable(true).build();
        return exchange;
    }

    @Bean
    public Exchange brayDirectExchange() {
        Exchange brayExchange1 = ExchangeBuilder.directExchange(BRAY_DIRECT_EXCHANGE).autoDelete().internal().build();
        return brayExchange1;
    }

    @Bean
    public Exchange brayFanoutExchange() {
        // can also be created using the ExchangeBuilder convenience class
        return new FanoutExchange("brayExchange.fanout");
    }

    @Bean
    public Exchange brayHeadersExchange() {
        // can also be created using the ExchangeBuilder convenience class
        return new HeadersExchange("brayExchange.headers");
    }

}
