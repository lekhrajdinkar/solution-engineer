package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;

@Configuration
public class AWS_S3Config {

    @Value("${aws.primary.region}") String region;
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                //.credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
