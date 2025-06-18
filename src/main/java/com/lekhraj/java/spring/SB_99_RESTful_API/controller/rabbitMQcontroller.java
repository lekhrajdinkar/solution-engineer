package com.lekhraj.java.spring.SB_99_RESTful_API.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rabbitMQcontroller {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.mq.queue}") String queueName;
    @Value("${rabbit.mq.exchange}") String exchangeName;
    @Value("${rabbit.mq.routingkey}") String key;

    @GetMapping("/rabbitmq/send")
    public String send() {
        rabbitTemplate.convertAndSend(exchangeName, key, "hello lekhu");
        return "Message sent: ";
    }
}
