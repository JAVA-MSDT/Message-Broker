package com.javamsdt.activemq.jms.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class QueuePublisher {
    public static final Logger LOGGER = Logger.getLogger(QueuePublisher.class.getName());

    @Autowired
    private JmsTemplate queueJmsTemplate;

    @Value("${spring.activemq.queue.name}")
    private String queueName;

    public void sendMessageToQueue(String message) {
        LOGGER.log(Level.INFO, () -> "Sending the following Message:: " + message + ", to the following Queue: " + queueName);
        queueJmsTemplate.convertAndSend(queueName, message);
    }
}
