package com.javamsdt.wikimediaconsumer;

import com.javamsdt.wikimediaconsumer.configuration.OpenSearchDBConfiguration;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WikimediaConsumerApplication implements ApplicationRunner {

    private final OpenSearchDBConfiguration openSearchDBConfiguration;
    private final RestHighLevelClient restHighLevelClient;

    public static void main(String[] args) {
        SpringApplication.run(WikimediaConsumerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        openSearchDBConfiguration.createIndexRequest(restHighLevelClient);
    }

}
