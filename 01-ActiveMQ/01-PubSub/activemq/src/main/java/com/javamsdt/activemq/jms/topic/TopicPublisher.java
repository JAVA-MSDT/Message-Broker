package com.javamsdt.activemq.jms.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TopicPublisher {
    public static final Logger LOGGER = Logger.getLogger(TopicPublisher.class.getName());

    @Autowired
    private JmsTemplate topicJmsTemplate;

    @Value("${spring.activemq.topic.name}")
    private String topicName;

    public void sendMessageToTopic(String message) {
        LOGGER.log(Level.INFO, () -> "Sending the following Message:: " + message + ", to Topic: " + topicName);
        topicJmsTemplate.convertAndSend(topicName, message);
    }
}
