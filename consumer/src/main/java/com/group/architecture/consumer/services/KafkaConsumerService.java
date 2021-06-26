package com.group.architecture.consumer.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consume(String message) {
        log.info(String.format("------- DEMO ARCHITECTURE KAFKA Consumer Service: MESSAGE RECEIVED. Content: %s -------",message));
    }

}
