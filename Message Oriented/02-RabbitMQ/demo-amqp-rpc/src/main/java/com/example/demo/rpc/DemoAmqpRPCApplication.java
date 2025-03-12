package com.example.demo.rpc;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

@SpringBootApplication
public class DemoAmqpRPCApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAmqpRPCApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(AmqpTemplate template) {
		return args -> {
			var response = (Integer) template.convertSendAndReceive("demo-rpc-exchange", "routing-rpc-queue", "Hello world!!!");
			System.out.println("Got response: " + response);
		};
	}

	@RabbitListener(
			bindings = @QueueBinding(
					value = @Queue(name = "demo-rpc-queue", durable = "true"),
					key = "routing-rpc-queue",
					exchange = @Exchange(name = "demo-rpc-exchange", type = ExchangeTypes.TOPIC))
	)
	public Integer rpc(Message<String> in) {
		var payload = in.getPayload();
		System.out.println("Got request: " + payload);
		return payload.length(); // returns length of string
	}

}
