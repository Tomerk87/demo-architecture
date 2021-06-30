package com.group.architecture.gateway.service;

import com.group.architecture.gateway.feign.GlobeFeignClient;
import com.group.architecture.gateway.model.request.GlobeContinentRequest;
import com.group.architecture.gateway.model.response.GlobeContinentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RestService {

    private final Map<Long, String> eTagsMap = new HashMap<>();

    private static final String ETAG_HEADER = "eTag";

    @Autowired
    private GlobeFeignClient feignClient;

    @Autowired
    private KafkaPublisherService kafkaService;


    public List<GlobeContinentResponse> getAllContinents() {
        var response = feignClient.getAllContinents();
        return response.getBody();
    }

    public GlobeContinentResponse createContinent(GlobeContinentRequest continent) {
        var response = feignClient.createContinent(continent);
        var result = response.getBody();
        manageContinentResponse(result.getId(), response.getHeaders());
        return result;
    }

    public GlobeContinentResponse getContinentById(long continentId) {
        String etag = eTagsMap.getOrDefault(continentId, null);
        Map<String, String> headers = new HashMap<>();
        headers.put(ETAG_HEADER, etag);
        var response = feignClient.getContinentById(continentId, headers);
        manageContinentResponse(continentId, response.getHeaders());
        return response.getBody();
    }

    public GlobeContinentResponse updateContinent(long continentId, GlobeContinentRequest continent) {
        String etag = eTagsMap.getOrDefault(continentId, null);
        Map<String, String> headers = new HashMap<>();
        headers.put(ETAG_HEADER, etag);
        var response = feignClient.updateContinentById(continentId,continent, headers);
        var result = response.getBody();
        manageContinentResponse(result.getId(), response.getHeaders());
        return result;
    }

    public void deleteContinent(long continentId) {
        var response = feignClient.deleteContinentById(continentId);
        if (response.getStatusCode() == HttpStatus.OK) {
            eTagsMap.remove(continentId);
        }
    }



    private void manageContinentResponse(long continentId, HttpHeaders headers) {
        if (!headers.containsKey(ETAG_HEADER) || headers.get(ETAG_HEADER) == null || headers.get(ETAG_HEADER).isEmpty()) {
            return;
        }
        String etag = headers.get(ETAG_HEADER).get(0);
        eTagsMap.put(continentId, etag);

        kafkaService.publish(String.valueOf(continentId),etag);
    }
}
