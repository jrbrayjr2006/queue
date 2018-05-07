package com.flood.cloud.queue;

import com.flood.cloud.queue.services.BrayQueueService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    BrayQueueService brayQueueService;

    ApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);

    //AmqpTemplate template = context.getBean(AmqpTemplate.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "Flood-sample";

    @RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
    public Map<String,String> home() {
        Map<String,String> result = new HashMap<>();
        result.put("queue", "none");
        System.out.println("This works!");
        return result;
    }

    @RequestMapping( value = "/send", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
    public Map<String,String> sendToQueue() {
        Map<String,String> result = new HashMap<>();
        String message = "This is a test message";
        Queue queue = new Queue(QUEUE_NAME);
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        result.put("queue", "success");
        System.out.println("Sent message to queue!");
        return result;
    }


    @RequestMapping( value = "/retrieve", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
    public Map<String,String> getMessageFromQueue() {
        Map<String,String> result = new HashMap<>();
        String message = "This is a test message";
        Object objMessage = rabbitTemplate.receiveAndConvert(QUEUE_NAME);
        result.put("queue", "tmp");
        System.out.println("Message from queue:  " + objMessage.toString());
        return result;
    }

    @RequestMapping( value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public String sendToTestExchange() {
        brayQueueService.sendToTestExchange();
        return "{'result': 'success'}";
    }
}
