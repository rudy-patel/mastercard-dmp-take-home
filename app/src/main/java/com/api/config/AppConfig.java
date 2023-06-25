package com.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.api.services.ExternalApiService;
import com.api.services.ExternalApiServiceImpl;

/**
 * Configuration class for application beans.
 */
@Configuration
public class AppConfig {

    /**
     * Creates an instance of the external API service.
     *
     * @return The external API service instance.
     */
    @Bean
    @Primary
    public ExternalApiService externalApiService() {
        return new ExternalApiServiceImpl();
    }
}
