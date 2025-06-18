package com.lekhraj.java.spring.kafka.spring;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class KafkaConfig {

        @Bean
        @ConditionalOnProperty(name = "app.kafka.consumer.kafka-generic-consumer-group.enabled", havingValue = "true", matchIfMissing = false)
        public KafkaConsumerService kafkaConsumerService() {
            return new KafkaConsumerService();
        }
    }

