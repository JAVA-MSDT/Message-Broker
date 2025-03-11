package com.javamsdt.activemq.modal;

public record BookOrder(
        String bookOrderId,
        String bookId,
        String customerId
) {
}
