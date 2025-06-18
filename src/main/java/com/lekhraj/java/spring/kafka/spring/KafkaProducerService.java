package com.lekhraj.java.spring.kafka.spring;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendStudent(String topic, Object student) {
        try {
            String message = objectMapper.writeValueAsString(student);
            kafkaTemplate.send(topic, message);
            System.out.println("Student message sent: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Student object: " + e.getMessage());
        }
    }

    public void sendCustomer(String topic, Object customer) {
        try {
            String message = objectMapper.writeValueAsString(customer);
            kafkaTemplate.send(topic, message);
            System.out.println("Customer message sent: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Customer object: " + e.getMessage());
        }
    }
}
