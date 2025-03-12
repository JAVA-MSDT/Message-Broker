# ActiveMQ

## Prerequisites:

Download and install classic ActiveMQ.


## Practical Task:

I. Implement publish/subscribe interaction between two applications. Check durable vs non-durable subscription.

![image info](./Content/Attachments/dur_non_dur.png)

II. Implement request-reply interaction between two applications using a temporary queue in ActiveMQ.

![image info](./Content/Attachments/request-reply.png)

III. Implement subscriber scaling, i.e. create n subscribers to a topic with the same ClientID (see Virtual Topics in ActiveMQ)
Note. Implement the subscriber as a standalone application and show how it works when we add or remove subscribers.

![image info](./Content/Attachments/virtual-topic.png)

## References

1. [ActiveMQ Documentation](https://activemq.apache.org/components/classic/documentation)
2. [Spring: Messaging with JMS](https://www.linkedin.com/learning/spring-messaging-with-jms)



# RabbitMQ

## Prerequisites:

Download and install RabbitMQ.

## Practical Task:
I. Implement a Spring Boot application for sending notifications to customers about the receipt of goods based on the following architecture:

![image info](./Content/Attachments/Reliable-part1.png)

> 💡 **NOTE**: Failed Message Exchange is not configured as DLX for the source queues. Consumer is responsible to re-publish failed messages.

II. Update previous implementation and change retry mechanism from inprocess to retry exchange/queue. Retry queue should have ttl, after message expires it should be routed to the source queue.

![image info](./Content/Attachments/Reliable-part2.png)

> 💡 **NOTE**: Retry exchange is not configured as DLX for the source queues. Consumer is responsible to re-publish messages for retry. If retry limit reached message should be re-published to Failed Message Exchange instead.

III. Update previous implementation, configure message ttl, max size and dlx attributes on consumer queues. Expired messages or during queue overflow messages should be sent to DLQ by broker.

![image info](./Content/Attachments/Reliable-part3.png)

> 💡 **NOTE**: Dead Letter exchange should be specified as DLX attribute on source queues in addition to message TTL and max length attributes.

### Tips
1. Dead letter channel/Invalid message channel
![image](./Content/Attachments/DLQ_IMQ.png)

## References

1. [RabbitMQ Documentation](https://www.rabbitmq.com/documentation.html)
2. [Spring. Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
3. [Spring Cloud Stream, RabbitMQ Binder](https://docs.spring.io/spring-cloud-stream-binder-rabbit/docs/current/reference/html/spring-cloud-stream-binder-rabbit.html)
4. [Learning RabbitMQ](https://www.linkedin.com/learning/learning-rabbitmq)


# Apache Kafka

## Prerequisites:

Configure a Kafka cluster using Docker with the following parameters:
* Number of brokers - 3
* Number of partitions - 3
* Replication factor - 2
* observe the Kafka broker logs to see how leaders/replicas for every partition are assigned

### Tips
* If you're working on a machine with 8 Gb of RAM or less, you might need to fall back to just 2 brokers
* An example of a Docker Compose for a 2-node cluster based on the official Confluent Kafka image, can be found
  [here](https://www.baeldung.com/ops/kafka-docker-setup#kafka-cluster-setup)

## Practical Task:

I. Implement a pair of `"at least once"` producer and  `"at most once"` consumer.
1. no web application required
2. write an integration test using the [Kafka Containers library](https://www.testcontainers.org/modules/kafka/)

II. Implement a taxi Spring Boot application. The application should consist of:
1. REST API which
   1. accepts vehicle signals
   2. validates that every signal carries a valid vehicle id and 2d coordinates
   3. puts the signals into the "input" Kafka topic
2. Kafka broker
3. 3 consumers which
   1. poll the signals from the "input" topic
   2. calculate the distance traveled by every unique vehicle so far
   3. store the latest distance information per vehicle in another "output" topic
4. separate consumer that polls the "output" topic and logs the results in realtime

III. **Advanced task (optional)**: Improve the taxi application by wrapping the 
poll(input) > calculate distance > publish(output) loop into Kafka transactions 

![image](./Content/Attachments/kafka-taxi-app.jpg)

### Important
* Messages from every vehicle must be processed `sequentially`!

### Tips
* the first two subtasks may be done as integration tests (for example, using the
  [Embedded Kafka from Spring Boot](https://blog.knoldus.com/testing-spring-embedded-kafka-consumer-and-producer/))

## References

1. [Kafka Introduction](https://kafka.apache.org/intro)
2. [Kafka Quickstart Guide](https://kafka.apache.org/quickstart)
3. [Spring Kafka Introduction](https://docs.spring.io/spring-kafka/reference/html/#introduction)
4. [Learn Apache Kafka for Beginners](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners)








