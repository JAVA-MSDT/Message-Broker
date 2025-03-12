package com.javamsdt.activemq.jms.topic;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TopicListenerTwo {
    public static final Logger LOGGER = Logger.getLogger(TopicListenerTwo.class.getName());


    @Value("${spring.activemq.topic.name}")
    private String topicName;

    @JmsListener(destination = "${spring.activemq.topic.name}", containerFactory = "topicListenerFactory")
    public void receiveMessageFromTopic(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String messageData = textMessage.getText();
        LOGGER.log(Level.INFO, () -> "TopicListenerTwo:: is Receiving the following Message:: " + messageData + ", from Topic: " + topicName);
    }
}
