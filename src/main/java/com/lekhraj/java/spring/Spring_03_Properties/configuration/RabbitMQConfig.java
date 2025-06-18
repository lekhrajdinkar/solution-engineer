package com.lekhraj.java.spring.Spring_03_Properties.configuration;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

// rabbit-mq-dev1/2.property

// @Configuration  // un-comment/Comment  <<<< NOT WORKING...PATH is wrong

@DependsOn(value = "DatabaseConfig")
// load DB and then Rabbit.
// if not mentioned then AC loads in Alphabetic order. <<< IMP


@PropertySource("classpath:com/lekhraj/java/spring/SpringProperties/resources/rabbit-mq-{spring.profiles.active}.properties")
public class RabbitMQConfig {
}


