package com.lekhraj.java.spring.kafka.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// @Service
public class KafkaConsumerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // app.kafka.consumer.kafka-generic-consumer-group.enabled = true <<<

    @KafkaListener(topics = {"customer_student", "wikimedia"}, groupId = "kafka-generic-consumer-group")
    public void consume(String message) {
        try {
            if (message.contains("customerId")) {
                // Process Customer
                Customer customer = objectMapper.readValue(message, Customer.class);
                System.out.println("Received Customer: " + customer);
            }

            else if (message.contains("id")) {
                // Process Student
                Student student = objectMapper.readValue(message, Student.class);
                System.out.println("Received Student: " + student);
            }

            else {
                System.out.println("Unknown message: " + message);
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
