package com.javamsdt.wikimediaproducer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerService {

    @Value("${spring.kafka.producer-string.topic-name}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(String message) {
        System.out.println("Message:: " + message);
        template.send(topic, message);
        template.destroy();
    }

}
