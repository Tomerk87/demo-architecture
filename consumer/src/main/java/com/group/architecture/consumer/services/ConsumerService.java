package com.group.architecture.consumer.services;

import com.group.architecture.consumer.model.GlobeContinentResponse;
import com.group.architecture.consumer.model.GlobeCountryResponse;
import com.group.architecture.consumer.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class ConsumerService {

    private static final String ETAG_HEADER = "eTag";

    @Value("${globe.base.url}")
    private String globeUrl;

    @Value("${globe.continent.url}")
    private String continentUrl;

    @Autowired
    private RestTemplate template;

    public void checkCachedFromGlobe(KafkaMessage message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ETAG_HEADER, message.getETag());

        String url = String.format("%s%s%s",globeUrl, continentUrl, message.getContinentId());
        ResponseEntity<GlobeContinentResponse> result = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), GlobeContinentResponse.class);

        if (result.getStatusCode() != HttpStatus.OK) {
            log.error(String.format("Failed to get continent by id %s. Status Code arrived: %s, Error: %s ", message.getContinentId(), result.getStatusCode(), result.getBody()));
            return;
        }

        log.info(String.format("Arrived Continent object with ID: %s", message.getContinentId()));
        HttpHeaders resultHeaders = result.getHeaders();
        if (!resultHeaders.containsKey(ETAG_HEADER)) {
            log.info("Results arrived without eTag header");
        } else {
            List<String> strings = resultHeaders.get(ETAG_HEADER);
            log.info(String.format("Results arrived with eTag header: %s", strings.get(0)));
            log.info(String.format("Is Same Header: %s", strings.get(0).equals(message.getETag())));
        }
        GlobeContinentResponse body = result.getBody();
    }
}
