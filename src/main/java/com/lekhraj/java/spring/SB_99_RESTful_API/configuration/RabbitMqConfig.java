package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

// @Configuration                       // <<<< here
public class RabbitMqConfig
{
    @Value("${rabbit.mq.queue}") String queueName;
    @Value("${rabbit.mq.exchange}") String exchangeName;
    @Value("${rabbit.mq.routingkey}") String key;

    @Bean
    Queue queue() { return  QueueBuilder.durable(queueName).quorum().build();}
/*    @Bean
    DirectExchange exchange() {return ExchangeBuilder.directExchange(exchangeName).build();}

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(key);
    }*/

    @RabbitListener(queues="spring.app.queue1")
    public void receiveMessage(String message) {
        System.out.println("Rabbit MQ test Received <" + message + ">");
    }

    //@Bean
    CommandLineRunner rabbitMQtest(RabbitTemplate rabbitTemplate){
        return (args)->{
            rabbitTemplate.convertAndSend(exchangeName, key, "{message}", m->m);
            System.out.println("Rabbit MQ test message sent at startup");
        };
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    //@Bean
    public ConnectionFactory rmqConnectionfactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setUri("amqps://localhost:5672");
        factory.setVirtualHost("/");
        return factory;
    }
}
