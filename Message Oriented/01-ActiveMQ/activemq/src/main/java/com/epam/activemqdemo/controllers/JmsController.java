package com.epam.activemqdemo.controllers;

import com.epam.activemqdemo.jms.Producer;
import com.epam.activemqdemo.jms.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JmsController {

    private final Producer producer;

    private final Publisher publisher;

    @PostMapping("/topic")
    public void publishMessageToTopic(@RequestParam String message) {
        publisher.sendMessageToTopic(message);
    }

    @PostMapping("/queue")
    public void publishMessageToQueue(@RequestParam String message) {
        producer.sendMessageToQueue(message);
    }
}
