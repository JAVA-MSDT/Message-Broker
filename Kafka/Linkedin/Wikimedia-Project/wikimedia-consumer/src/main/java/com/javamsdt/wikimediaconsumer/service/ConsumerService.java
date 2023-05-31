package com.javamsdt.wikimediaconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {
    @KafkaListener(topics = "${spring.kafka.consumer-string.topic-name}",
            groupId = "${spring.kafka.consumer-string.group-id}")
    public void kafkaListener(ConsumerRecord<String, String> message) {
        System.out.println("Message Key:: " + message.key() + ", Message Value:: " + message.value());
    }
}
