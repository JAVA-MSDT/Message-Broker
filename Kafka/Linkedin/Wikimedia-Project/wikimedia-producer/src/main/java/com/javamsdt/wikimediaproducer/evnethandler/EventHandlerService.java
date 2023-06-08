package com.javamsdt.wikimediaproducer.evnethandler;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@Slf4j
public class EventHandlerService {
    @Value("${event.handler.url}")
    private String eventUrl;

//    @Bean
//    public BackgroundEventHandler eventHandler(KafkaProducer<String, String> kafkaProducer, String topic) {
//        return new WikimediaChangeHandler(kafkaProducer, topic);
//    }
    @Bean
    public EventSource.Builder builder() {
        return new EventSource.Builder(URI.create(eventUrl));
    }

    @Bean
    public EventSource eventSource() {
        return builder().build();
    }
}
