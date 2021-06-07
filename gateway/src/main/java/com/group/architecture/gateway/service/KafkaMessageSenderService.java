package com.group.architecture.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
public class KafkaMessageSenderService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Value(value = "${kafka.topic.name}")
    private String topicName;


    @Async
    public void sendMessage(String continentId, String eTag) {
        Map<String, String> msgAsMap = new HashMap<>();
        try {
           var  message = mapper.writeValueAsString(msgAsMap);
           send(message);
        } catch (JsonProcessingException e) {
            log.error(String.format("Failed to convert map of id %s and eTag %s to String. Error: %s", continentId, eTag, e.getMessage()),e);
        }
    }

    private void send(String message) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(String.format("Sent message=[%s] with offset=[%s]", message, result.getRecordMetadata().offset()));
            }
            @Override
            public void onFailure(@NotNull Throwable ex) {
                log.error(String.format("Unable to send message=[%s] due to : %s", message, ex.getMessage()),ex);
            }
        });
    }
}
