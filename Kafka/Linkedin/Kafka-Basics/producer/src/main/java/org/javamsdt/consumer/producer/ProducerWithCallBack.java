package org.javamsdt.consumer.producer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerWithCallBack {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerWithCallBack.class.getSimpleName());

    public static void main(String[] args) {
        LOGGER.info("Producer Call Back Started .....");

        // Producer Properties
        Properties properties = getKafkaProperties();
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        // Producer
        KafkaProducer<String, String> producer = getKafkaProducer(properties);

        // Producer Record

        // Send data
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java",
                    "Producer Call Back #" + i);
            producer.send(producerRecord, (recordMetadata, e) -> {
                // executed every time a record sent.
                if (e == null) {
                    LOGGER.info("Received new metadata:: \n" +
                            "Topic: " + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + Instant.ofEpochMilli(recordMetadata.timestamp())
                            .atZone(ZoneId.systemDefault()).toLocalDateTime());
                } else {
                    LOGGER.error("Error while producing:: ", e);
                }
            });
        }

        // flush & close the producer
        producer.flush();
        producer.close();
    }

    private static Properties getKafkaProperties() {
        Properties properties = new Properties();
        try (InputStream input = ProducerWithCallBack.class.getClassLoader().getResourceAsStream("kafka.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
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
