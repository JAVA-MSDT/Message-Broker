package com.javamsdt.activemq.controller;

import jakarta.jms.Destination;
import jakarta.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RequestReplyController {

    private final JmsTemplate jmsTemplate;

    public RequestReplyController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/sendTemporaryRequest")
    public void sendRequest(@RequestParam String message) {
        jmsTemplate.send("requestQueue", session -> {
            // Create a text message with the request
            TextMessage textMessage = session.createTextMessage(message);
            // Create a temporary queue for the reply
            Destination replyQueue = session.createTemporaryQueue();
            // Set the reply queue as the JMSReplyTo header
            textMessage.setJMSReplyTo(replyQueue);
            System.out.println("textMessage.getText():: " + textMessage.getText());
            System.out.println("Destination:: " + textMessage.getJMSReplyTo());
            session.commit();
            return textMessage;
        });
    }

}