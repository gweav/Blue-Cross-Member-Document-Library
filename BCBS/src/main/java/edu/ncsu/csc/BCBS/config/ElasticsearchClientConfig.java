package edu.ncsu.csc.BCBS.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.net.ssl.SSLContext;

/**
 * A class specifying ElasticSearch configuration details
 *
 * @author Gabe Weaver
 */
@Configuration
@EnableAutoConfiguration
public class ElasticsearchClientConfig {

    /**
     * An environment access object for retrieving environment variable values
     */
    @Autowired
    Environment env;

    /**
     * ElasticSearch client dependency injection setup
     *
     * @return an ElasticSearch client
     */
    @Bean
    public ElasticsearchClient getElasticsearchClient() {

        String fingerprint = env.getProperty("elastic.fingerprint");

        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", env.getProperty("elastic.password")));

        RestClient restClient = RestClient
                .builder(new HttpHost("localhost", 9200, "https"))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credsProv)
                )
                .build();

        JacksonJsonpMapper mapper = new JacksonJsonpMapper();
        mapper.objectMapper().registerModule(new JavaTimeModule());

        ElasticsearchTransport transport = new RestClientTransport(restClient, mapper);
        return new ElasticsearchClient(transport);
    }
}
