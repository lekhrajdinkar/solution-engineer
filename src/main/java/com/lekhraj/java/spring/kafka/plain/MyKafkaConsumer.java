package com.lekhraj.java.spring.kafka.plain;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {
    public static void main(String[] args) {
        // Step 1: Set up the properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        /*
        props.put("security.protocol", "SASL_PLAINTEXT"); // Or SASL_SSL for encrypted connections
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required  username=\" \" password=\" \" ");
        */

        // Step 2: Create a KafkaConsumer instance
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props))
        {
            // Step 3: Subscribe to a topic
            consumer.subscribe(Collections.singletonList("customers")); // Replace with your topic name

            System.out.println("Consuming messages from topic...");

            // Step 4: Poll for messages
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000)); // Poll every 1 second

                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Consumed message: Key=%s, Value=%s, Topic=%s, Partition=%d, Offset=%d%n",
                            record.key(), record.value(), record.topic(), record.partition(), record.offset());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}