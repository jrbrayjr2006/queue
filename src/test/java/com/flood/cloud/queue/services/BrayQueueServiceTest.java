package com.flood.cloud.queue.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class BrayQueueServiceTest {

    @Mock
    RabbitTemplate mockRabbitTemplate;

    BrayQueueService brayQueueService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        brayQueueService = new BrayRabbitMQServiceImpl(mockRabbitTemplate);
    }

    @Test
    public void shouldCallRabbitTemplate() {
        //Given
        String exchange = "testExchange";
        String routingKey = "testRoutingKey";
        byte[] messageBody = "{'testKey': 'testValue'}".getBytes();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setConsumerQueue("testQueue");
        Message message = new Message(messageBody,messageProperties);
        //Message message = mock(Message.class);

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        //When - the queue service sends a message to the test exchange
        brayQueueService.sendToTestExchange(routingKey, exchange, message);

        //Then - the rabbitTemplate convertAndSend should be called
        verify(mockRabbitTemplate, times(1)).convertSendAndReceive(eq("testRoutingKey"),eq("testExchange"),messageArgumentCaptor.capture());
    }

}