package com.epam.activemqdemo.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Slf4j
@Component
public class TopicListener2 {

    @Value("${activemq.topic}")
    private String topic;

    @JmsListener(destination = "${activemq.topic}", containerFactory = "topicListenerFactory")
    public void receiveMessageFromTopic(Message jsonMessage) throws JMSException {

        TextMessage textMessage = (TextMessage) jsonMessage;
        String messageData = textMessage.getText();
        log.info("Received message: " + messageData + " from topic - " + topic + " by subscriber 2.");
    }
}
