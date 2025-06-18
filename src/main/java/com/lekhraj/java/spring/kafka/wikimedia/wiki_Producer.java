package com.lekhraj.java.spring.kafka.wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
public class wiki_Producer {
    static String topic = "wikimedia";
    static String url = "https://stream.wikimedia.org/v2/stream/recentchange";
    public static void main(String[] args) throws InterruptedException
    {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        EventHandler eventHandler = new Wikimedia_event_handler(producer, topic);
        EventSource eventSource = new EventSource
                .Builder(eventHandler, URI.create(url))
                .build();


        // start the producer in another thread
        eventSource.start();

        // we produce for 10 minutes
        Thread.sleep(1000 * 60 * 10);
    }
}

class Wikimedia_event_handler implements EventHandler {

    KafkaProducer<String, String> kafkaProducer;
    String topic;

    public Wikimedia_event_handler(KafkaProducer<String, String> kafkaProducer, String topic){
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }


    @Override
    public void onOpen() { }

    @Override
    public void onClosed() {
        kafkaProducer.close();
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        System.out.println("Error in Stream Reading"+messageEvent.getData());
        // asynchronous
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, messageEvent.getData());
        kafkaProducer.send(producerRecord );
    }

    @Override
    public void onComment(String comment) { }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error in Stream Reading"+t.getMessage());
    }
}
