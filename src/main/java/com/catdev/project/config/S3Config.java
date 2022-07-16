package com.catdev.project.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.catdev.project.constant.CommonConstant;
import com.catdev.project.exception.ErrorResponse;
import com.catdev.project.exception.ProductException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class S3Config {

    private Environment env;

    @Bean
    private AmazonS3 initS3Client(){

        String accessKey = env.getProperty(CommonConstant.AWS_S3_ACCESS_KEY_ID);
        String secretKey = env.getProperty(CommonConstant.AWS_S3_SECRET_ACCESS_KEY);

        if(StringUtils.isAnyBlank(accessKey,secretKey))
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode("NOT_NULL");
            errorResponse.setErrorType("NOT_NULL_AWS_CREDENTIAL");
            errorResponse.setMessage("AccessKey and SecretKey must not be null");
            throw new ProductException(errorResponse);
        }

        AWSCredentials credentials = new BasicAWSCredentials(
            accessKey,secretKey
        );

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(env.getProperty(CommonConstant.AWS_S3_REGION))
                .build();
    }
}
