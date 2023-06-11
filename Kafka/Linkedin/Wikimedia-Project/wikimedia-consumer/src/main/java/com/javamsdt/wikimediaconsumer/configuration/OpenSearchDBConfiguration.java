package com.javamsdt.wikimediaconsumer.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;

@Configuration
@Slf4j
public class OpenSearchDBConfiguration {
    @Value("${opensearch.db.url}")
    private String dbUrl;

    @Value("${opensearch.db.index.name}")
    private String indexName;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient;
        URI uri = URI.create(dbUrl);
        String userInfo = uri.getUserInfo();
        log.info(String.format("UserInfo: %s,  Db-URL: %s", userInfo, dbUrl));
        if (userInfo == null) {
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(
                    new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme())
            ));
        } else {
            String[] auth = userInfo.split(":");
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth[0], auth[1]));
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                                    new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()))
                            .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(provider)
                                    .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));
        }
        return restHighLevelClient;
    }

    public void createIndexRequest(RestHighLevelClient restHighLevelClient) {
        try {
            boolean isIndexExist = isIndexExists(restHighLevelClient, indexName);
            if (!isIndexExist) {
                restHighLevelClient.indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);
                log.info(String.format("Done creating Index: %s successfully.", indexName));
            } else {
                log.info(String.format("Index: %s is already exists.", indexName));
            }
        } catch (IOException e) {
            log.error("Error creating index:: ", e);
            throw new RuntimeException(e);
        }
    }

    public boolean isIndexExists(RestHighLevelClient restHighLevelClient, String indexName) {
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(String.format("Error checking the existence of Index:: %s", indexName), e);
            throw new RuntimeException(e);
        }
    }

    public IndexRequest indexRequest() {
        return new IndexRequest(indexName);
    }
}
