package com.epam.activemqdemo.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Publisher {

    @Autowired
    @Qualifier("topicJmsTemplate")
    private JmsTemplate topicJmsTemplate;

    @Value("${activemq.topic}")
    private String topic;

    public void sendMessageToTopic(String messageText) {
        log.info("Sending message " + messageText + " to  topic - " + topic);
        topicJmsTemplate.convertAndSend(topic, messageText);
    }
}
