package com.javamsdt.activemq.controller;

import com.javamsdt.activemq.modal.BookOrder;
import com.javamsdt.activemq.service.BookStoreOrderMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api}/book-order")
@RequiredArgsConstructor
@Slf4j
public class BookOrderController {
    private final BookStoreOrderMessageService storeOrderService;

    @PostMapping()
    public String sendBookOrderMessage(@RequestBody BookOrder bookOrder) {
        log.info("bookOrder:: " + bookOrder);
        storeOrderService.sendBookOrderMessage(bookOrder);
        return "Done sendBookOrderMessage";
    }
}
