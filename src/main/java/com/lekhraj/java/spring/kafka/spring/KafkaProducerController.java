package com.lekhraj.java.spring.kafka.spring;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {
    private final KafkaProducerService producerService;
    String topic = "customer_student";
    String topic_wikimedia = "wikimedia";

    public KafkaProducerController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/student")
    public String sendStudent( @RequestBody Student student) {
        producerService.sendStudent(topic, student);
        return "Student message sent to topic: " + topic;
    }

    @PostMapping("/customer")
    public String sendCustomer( @RequestBody Customer customer) {
        producerService.sendCustomer(topic, customer);
        return "Customer message sent to topic: " + topic;
    }

    @PostMapping("/wikimdeia")
    public String sendCustomer() {
        //producerService.sendCustomer(topic_wikimedia);
        return "wikimdeia stream sent to " + topic_wikimedia;
    }
}

/*

{"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}
{"id": "1", "name": "Alice", "age": 22}

 */

