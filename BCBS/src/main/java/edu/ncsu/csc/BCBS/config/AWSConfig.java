package edu.ncsu.csc.BCBS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * A class specifying AWS configuration details
 *
 * @author Gabe Weaver
 */
@Configuration
public class AWSConfig {

    /**
     * S3 client dependency injection setup
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();
    }
}
