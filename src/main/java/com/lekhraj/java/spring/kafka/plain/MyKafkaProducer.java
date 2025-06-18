package com.lekhraj.java.spring.kafka.plain;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MyKafkaProducer
{
    public static void main(String[] args) {
        // Step 1: Set up the producer properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("batch.size","400"); // null must be key
        //props.put("partitioner.class","");


        /*
        props.put("security.protocol", "SASL_PLAINTEXT"); // Or SASL_SSL for encrypted connections
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required  username=\" \" password=\" \" ");
        */

        // Step 2: Create a KafkaProducer instance
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            // Step 3: Send messages to a topic
            String topic = "first_topic"; // Replace with your topic name

            for (int i = 1; i <= 3; i++) {
                String key = "key-" + i;
                String value = "message-" + i;

                // Create a ProducerRecord
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                // Send the message (asynchronously)
                /*producer.send(record, (RecordMetadata metadata, Exception exception) -> {
                    if (exception == null) {
                        System.out.printf("Message sent successfully! Topic=%s, Partition=%d, Offset=%d%n",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                });
*/
                // Optional: Force synchronous send (for demonstration)
                // Uncomment the following lines to block on each send

                try {
                    RecordMetadata metadata = producer.send(record).get();
                    System.out.printf("Message sent synchronously! Topic=%s, Partition=%d, Offset=%d%n",
                            metadata.topic(), metadata.partition(), metadata.offset());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Finished producing messages.");
    }
}
