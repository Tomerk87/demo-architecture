package com.group.architecture.globe.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class utils {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}