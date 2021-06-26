package com.group.architecture.consumer.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.architecture.consumer.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class KafkaConsumerService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ConsumerService consumerService;

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consume(String message) {
        log.info(String.format("Arrive Message: %s",message));
        try {
            KafkaMessage kafkaMessage = mapper.readValue(message, KafkaMessage.class);
            consumerService.checkCachedFromGlobe(kafkaMessage);
        }
        catch (Exception e) {
            log.error(String.format("Failed to convert message to object. Error: %s", e.getMessage()), e);
        }
    }

}
