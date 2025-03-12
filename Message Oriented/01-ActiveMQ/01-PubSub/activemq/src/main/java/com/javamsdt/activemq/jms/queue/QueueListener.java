package com.javamsdt.activemq.jms.queue;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class QueueListener {
    public static final Logger LOGGER = Logger.getLogger(QueueListener.class.getName());


    @Value("${spring.activemq.queue.name}")
    private String queueName;

    @JmsListener(destination = "${spring.activemq.queue.name}", containerFactory = "queueListenerFactory")
    public void receiveMessageFromQueue(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String messageData = textMessage.getText();
        LOGGER.log(Level.INFO, () -> "Receiving the following Message:: " + messageData + ", to the following Queue: " + queueName);
    }
}
