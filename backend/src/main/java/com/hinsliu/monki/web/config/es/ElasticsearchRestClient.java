package com.hinsliu.monki.web.config.es;

import io.swagger.models.auth.In;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchRestClient {

    @Value("${elasticsearch.server}")
    private String server;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient() {
        HttpHost httpHost = new HttpHost(server, port, "http");
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }

}
