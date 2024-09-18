package com.unotalks.s3demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class DemoConfiguration {

    @Value("${s3demo.config.aws.endpoint}")
    String s3Endpoint = "http://localhost:4566";
    @Value("${s3demo.config.aws.region}")
    String region = "us-east-1";
    @Value("${s3demo.config.aws.accesskey}")
    String accessKey;
    @Value("${s3demo.config.aws.secretkey}")
    String secretKey;

    @Bean
    public AmazonS3 configS3Client() {

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Endpoint, region))
                .withCredentials(getCredentials())
                .withPathStyleAccessEnabled(Boolean.TRUE)
                .build();
    }

    private AWSStaticCredentialsProvider getCredentials(){
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                accessKey,
                secretKey
        ));
    }
}
