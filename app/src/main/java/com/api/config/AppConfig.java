package com.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.services.ExternalApiService;
import com.api.services.ExternalApiServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public ExternalApiService externalApiService() {
        return new ExternalApiServiceImpl();
    }
}