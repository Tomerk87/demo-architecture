package com.group.architecture.globe.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobeBeanConfig {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
