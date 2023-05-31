package org.javamsdt.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class.getSimpleName());

    public static void main(String[] args) {
        LOGGER.info("Consumer Started .....");

        String topic = "demo_java";
        // Consumer Properties
        Properties properties = getKafkaProperties();

        // Consumer
        KafkaConsumer<String, String> kafkaConsumer = getKafkaProducer(properties);

        // subscript to a topic
        kafkaConsumer.subscribe(List.of(topic));

        // poll data from kafka
        while (true) {
            LOGGER.info("Polling...");

            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1L));

            for (ConsumerRecord<String, String> record : records) {
                LOGGER.info("Key:: " + record.key() + ", Value:: " + record.value());
                LOGGER.info("Partition:: " + record.partition() + ", Offset:: " + record.offset());
            }
        }

    }

    private static Properties getKafkaProperties() {
        Properties properties = new Properties();
        try (InputStream input = Consumer.class.getClassLoader().getResourceAsStream("kafka.properties")) {

            if (input == null) {
                LOGGER.error("Sorry, unable to find config.properties");
            }
            properties.load(input);
            LOGGER.info("Bootstrap Server: " + properties.getProperty("bootstrap.servers"));

        } catch (IOException ex) {
            LOGGER.error("Error Loading Kafka Properties....");
            ex.printStackTrace();
        }
        return properties;
    }

    private static KafkaConsumer<String, String> getKafkaProducer(Properties kafkaProperties) {
        return new KafkaConsumer<>(kafkaProperties);
    }

}
