package com.javamsdt.activemq.controller;

import com.javamsdt.activemq.jms.queue.QueuePublisher;
import com.javamsdt.activemq.jms.topic.TopicPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/active-mq")
public class JmsController {
    @Autowired
    private QueuePublisher queuePublisher;
    @Autowired
    private TopicPublisher topicPublisher;

    @PostMapping("/publish-to-queue")
    public void publishMessageToQueue(@RequestParam String message) {
        queuePublisher.sendMessageToQueue(message);
    }

    @PostMapping("/publish-to-topic")
    public void publishMessageToTopic(@RequestParam String message) {
        topicPublisher.sendMessageToTopic(message);
    }

    @GetMapping
    public String getActiveMQ() {
        return this.getClass().getName();
    }

}
