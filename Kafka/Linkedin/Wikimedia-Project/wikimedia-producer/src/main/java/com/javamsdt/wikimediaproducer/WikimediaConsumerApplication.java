package com.javamsdt.wikimediaproducer;

import com.javamsdt.wikimediaproducer.service.ProducerService;
import com.launchdarkly.eventsource.EventSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
public class WikimediaConsumerApplication implements ApplicationRunner {

    private final ProducerService producerService;
    private final EventSource eventSource;

    public static void main(String[] args) {
        SpringApplication.run(WikimediaConsumerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("eventSource.readMessage():: " + eventSource.readMessage().getData());
        System.out.println(" ========================== ");
        eventSource.messages().forEach(messageEvent -> producerService.sendMessage(messageEvent.getData()));
        eventSource.start();
        TimeUnit.MINUTES.sleep(1);


    }
}
