package com.group.architecture.consumer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerBeanConfig {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
