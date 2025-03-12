package com.epam.activemqdemo.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    @Autowired
    @Qualifier("queueJmsTemplate")
    private JmsTemplate queueJmsTemplate;

    @Value("${activemq.queue}")
    private String queue;

    public void sendMessageToQueue(String messageText) {
        log.info("Sending message " + messageText + " to  queue - " + queue);
        queueJmsTemplate.convertAndSend(queue, messageText);
    }
}
