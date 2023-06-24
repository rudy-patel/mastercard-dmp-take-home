package com.configuration;

import com.api.services.ExternalApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("TransactionController-test")
@Configuration
public class TestConfiguration {
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // @Bean
    // public ExternalApiService testExternalApiService() {
    //     return Mockito.mock(ExternalApiService.class);
    // }
}
