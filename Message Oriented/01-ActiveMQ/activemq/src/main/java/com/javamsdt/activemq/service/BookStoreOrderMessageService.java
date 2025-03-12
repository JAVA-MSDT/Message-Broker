package com.javamsdt.activemq.service;

import com.javamsdt.activemq.modal.BookOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookStoreOrderMessageService {

    @Value("${spring.activemq.order-queue}")
    private String orderQueueDestination;

    private final JmsTemplate jmsTemplate;

    public void sendBookOrderMessage(BookOrder bookOrder) {
        log.info("MessageSending Message,,,,,");
        jmsTemplate.convertAndSend(orderQueueDestination, bookOrder);
    }

    @JmsListener(destination = "${spring.activemq.order-queue}", containerFactory = "containerFactory")
    public void receiveBookOrderMessage(BookOrder bookOrder) {
        log.info("Received a message with a book Order Id:: " + bookOrder.bookOrderId());
    }
}
