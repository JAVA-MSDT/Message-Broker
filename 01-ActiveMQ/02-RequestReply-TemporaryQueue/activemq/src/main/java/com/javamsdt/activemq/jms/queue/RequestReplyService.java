package com.javamsdt.activemq.jms.queue;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class RequestReplyService {

    private final JmsTemplate jmsTemplate;

    public RequestReplyService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "requestQueue")
    public void receiveRequest(TextMessage message) throws JMSException {
        // Get the request from the message
        String request = message.getText();
        // Do something with the request
        System.out.println("Received request: " + request);
        System.out.println("message.getJMSReplyTo(): " + message.getJMSReplyTo());
        // Get the temporary queue from the JMSReplyTo header
        Destination replyQueue = message.getJMSReplyTo();
        // Send a reply to the temporary queue
        jmsTemplate.convertAndSend(replyQueue, "Hello, this is a reply");
    }

//    @JmsListener(destination = "simple-queue")
//    public void onRequestMessageReceived(TextMessage requestMessage) throws JMSException {
//        try {
//            System.out.println("Received request: " + requestMessage.getText());
//
//            // Process the request (you can implement your business logic here)
//
//            // Send a reply
//            Destination replyDestination = requestMessage.getJMSReplyTo();
//            if (replyDestination != null) {
//                String replyMessage = "Reply to: " + requestMessage.getText();
//                System.out.println("Sending reply: " + replyMessage);
//                jmsTemplate.convertAndSend(replyDestination, replyMessage);
//                System.out.println("Sent reply: " + replyMessage);
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
}
