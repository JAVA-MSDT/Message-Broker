package org.javamsdt.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerCooperative {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerCooperative.class.getSimpleName());

    public static void main(String[] args) {
        LOGGER.info("Consumer with Shutdown Started .....");

        String groupId = "java-app";
        String topic = "demo_java";
        // Consumer Properties
        Properties properties = getKafkaProperties();
        // Deserializing depends on the data consumed
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("group.id", groupId);
        // none: if there is no consumer group will fail (We must set the consumer group before starting the app).
        // earliest: read from the beginning of my topic.
        // latest: read the new messages from now.
        properties.put("auto.offset.reset", "earliest");
        properties.put("partition.assignment.strategy", CooperativeStickyAssignor.class.getName());

        // Consumer
        KafkaConsumer<String, String> kafkaConsumer = getKafkaProducer(properties);

        // get a reference to the main thread
        final Thread mainThread = Thread.currentThread();

        // Adding shutdown hook
        Runtime.getRuntime().addShutdownHook((new Thread(() -> {
            LOGGER.info("Detected a shutdown, exit by calling kafkaConsumer.wakeup()...");
            kafkaConsumer.wakeup();

            // Join the main thread to allow the execution of the code in the main thread
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                LOGGER.error("Error Joining the main thread: ", e);
                throw new RuntimeException(e);
            }
        })));
        try {
            // subscript to a topic
            kafkaConsumer.subscribe(List.of(topic));

            // poll data from kafka
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1L));

                for (ConsumerRecord<String, String> record : records) {
                    LOGGER.info("Key:: " + record.key() + ", Value:: " + record.value());
                    LOGGER.info("Partition:: " + record.partition() + ", Offset:: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            LOGGER.error("Consumer is starting to shutdown ...");
        } catch (Exception e) {
            LOGGER.error("Unexpected exception in the consumer: ", e);
        } finally {
            kafkaConsumer.close();
            LOGGER.info("Consumer is gracefully shutdown ...");
        }

    }

    private static Properties getKafkaProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConsumerCooperative.class.getClassLoader().getResourceAsStream("kafka.properties")) {

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
