package com.javamsdt.wikimediaconsumer.service;

import com.javamsdt.wikimediaconsumer.configuration.OpenSearchDBConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {
    private final OpenSearchDBConfiguration openSearchDBConfiguration;
    private final RestHighLevelClient restHighLevelClient;

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void kafkaListener(ConsumerRecord<String, String> message) throws IOException {
        openSearchDBConfiguration.createIndexRequest(restHighLevelClient);
        try {
            IndexRequest indexRequest = openSearchDBConfiguration.indexRequest().source(message.value(), XContentType.JSON);
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("Message Key:: " + message.key() + ", Message Value:: " + message.value());
            log.info("Response:: " + response.getId());
        } catch (Exception e) {
            
        }
    }


}
