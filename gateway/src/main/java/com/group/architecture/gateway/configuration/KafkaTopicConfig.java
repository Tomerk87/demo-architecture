package com.group.architecture.gateway.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.topic.name}")
    private String topicName;

    @Bean
    public NewTopic adviceTopic() {
        return new NewTopic(topicName,1, (short) 1);
    }
}
