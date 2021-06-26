package com.group.architecture.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableAsync
@Service
public class KafkaPublisherService {

    @Value(value = "${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;


    @Async
    public void publish(String continentId, String eTag) {
        Map<String, String> msgAsMap = new HashMap<>();
        try {
            msgAsMap.put(continentId, eTag);
           var  message = mapper.writeValueAsString(msgAsMap);
           publish(message);
        } catch (JsonProcessingException e) {
            log.error(String.format("Failed to convert map of id %s and eTag %s to String. Error: %s", continentId, eTag, e.getMessage()),e);
        }
    }

    public void publish(String message) {
        log.info("CHECKING");
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error(String.format("Unable to send message: %s", throwable.getMessage()), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(String.format("Message was sent: %s", result.getRecordMetadata().offset()));
            }
        });
    }
}
