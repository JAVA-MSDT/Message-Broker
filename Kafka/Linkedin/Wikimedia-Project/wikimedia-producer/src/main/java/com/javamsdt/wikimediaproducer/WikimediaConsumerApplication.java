package com.javamsdt.wikimediaproducer;

import com.javamsdt.wikimediaproducer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaConsumerApplication implements ApplicationRunner {

    @Autowired
    private ProducerService producerService;

    public static void main(String[] args) {
        SpringApplication.run(WikimediaConsumerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        producerService.sendMessage("Spring Boot ...");
    }
}
