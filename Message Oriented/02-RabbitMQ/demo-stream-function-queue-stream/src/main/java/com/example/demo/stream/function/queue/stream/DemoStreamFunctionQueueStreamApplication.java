package com.example.demo.stream.function.queue.stream;

import com.rabbitmq.stream.Address;
import com.rabbitmq.stream.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.rabbit.RabbitStreamMessageHandler;
import org.springframework.cloud.stream.config.ProducerMessageHandlerCustomizer;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@SpringBootApplication
public class DemoStreamFunctionQueueStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoStreamFunctionQueueStreamApplication.class, args);
	}

	@Bean
	public Environment streamEnv(@Value("${spring.rabbitmq.virtual-host:/}") String virtualHost) {
		return Environment.builder()
				.virtualHost(virtualHost)
				// when running in docker either advertised host need to be tuned or custom address resolver should be provided
				// https://blog.rabbitmq.com/posts/2021/07/connecting-to-streams/
				.addressResolver(address -> {
					return new Address("localhost", address.port());
				})
				.build();
	}

	@Bean
	public ProducerMessageHandlerCustomizer<MessageHandler> handlerCustomizer() {
		return (hand, dest) -> {
			RabbitStreamMessageHandler handler = (RabbitStreamMessageHandler) hand;
			handler.setConfirmTimeout(5000);
			/*((RabbitStreamTemplate) handler.getStreamOperations()).setProducerCustomizer(
					(name, builder) -> {
						...
					});*/
		};
	}

	@Bean
	public ApplicationRunner runner(StreamBridge streamBridge) {
		return args -> {
			IntStream.range(0, 10).forEach(i -> {
				streamBridge.send("source-out-0",
						MessageBuilder
								.withPayload("Hello queue " + i)
								.build());
			});
		};
	}

	// consumer should be based on byte[]
	@Bean
	public Consumer<byte[]> streamSink1() {
		return payload -> System.out.println("Consumer1: " + new String(payload, StandardCharsets.UTF_8));
	}

	// consumer should be based on byte[]
	@Bean
	public Consumer<byte[]> streamSink2() {
		return payload -> System.out.println("Consumer2: " + new String(payload, StandardCharsets.UTF_8));
	}

}
