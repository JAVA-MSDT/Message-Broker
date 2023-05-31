package org.javamsdt.consumer.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class.getSimpleName());

    public static void main(String[] args) {
        LOGGER.info("Producer Started .....");

        // Producer Properties
        Properties properties = getKafkaProperties();

        // Producer
        KafkaProducer<String, String> producer = getKafkaProducer(properties);

        // Producer Record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "Producer");

        // Send data
        producer.send(producerRecord);

        // flush & close the producer
        producer.flush();
        producer.close();
    }

    private static Properties getKafkaProperties() {
        Properties properties = new Properties();
        try (InputStream input = Producer.class.getClassLoader().getResourceAsStream("kafka.properties")) {

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

    private static KafkaProducer<String, String> getKafkaProducer(Properties kafkaProperties) {
        return new KafkaProducer<>(kafkaProperties);
    }
}
