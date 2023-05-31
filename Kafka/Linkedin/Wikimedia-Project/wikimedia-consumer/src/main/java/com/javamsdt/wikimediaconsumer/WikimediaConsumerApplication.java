package com.javamsdt.wikimediaconsumer;

import com.javamsdt.wikimediaconsumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikimediaConsumerApplication.class, args);
		System.out.println("Started....");
	}

}
