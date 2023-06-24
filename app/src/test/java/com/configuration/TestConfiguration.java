package com.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("TransactionController-IntegTest")
@Configuration
public class TestConfiguration {
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
