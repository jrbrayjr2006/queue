# Queue Project Using RabbitMQ

## Purpose
This is a simple Spring Boot project that creates and sends messages to RabbitMQ exchanges and retrieves messages from queues.  It is for demonstration and learning purposes only.

## Configuration
A `RabbitConfiguration` class is used to do the initial configuration of the exchanges and queues by annotating them as beans in the project.

A `BrayRabbitMQExchangeConfiguraion` class is used to configure exchanges programmatically.

## References

- [Spring AMPQ](https://projects.spring.io/spring-amqp/)