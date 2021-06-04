package com.group.architecture.gateway.service;

import com.group.architecture.gateway.feign.GlobeFeignClient;
import com.group.architecture.gateway.model.GlobeContinent;
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
    private Map<Long, String> eTagsMap = new HashMap<>();

    private static String ETAG_HEADER = "eTag";

    @Autowired
    private GlobeFeignClient globeClient;

    public List<GlobeContinent> getAllContinents() {
        var response = globeClient.getAllContinents();
        return response.getBody();
    }

    public GlobeContinent createContinent(GlobeContinent continent) {
        var response = globeClient.createContinent(continent);
        var result = response.getBody();
        manageHeaders(result.getId(), response.getHeaders());
        return result;
    }

    public GlobeContinent getContinentById(long continentId) {
        String etag = eTagsMap.getOrDefault(continentId, null);
        Map<String, String> headers = new HashMap<>();
        headers.put(ETAG_HEADER, etag);
        var response = globeClient.getContinentById(continentId, headers);
        manageHeaders(continentId, response.getHeaders());
        return response.getBody();
    }

    public GlobeContinent updateContinent(long continentId, GlobeContinent continent) {
        String etag = eTagsMap.getOrDefault(continentId, null);
        Map<String, String> headers = new HashMap<>();
        headers.put(ETAG_HEADER, etag);
        var response = globeClient.updateContinentById(continentId,continent, headers);
        var result = response.getBody();
        manageHeaders(result.getId(), response.getHeaders());
        return result;
    }

    public void deleteContinent(long continentId) {
        var response = globeClient.deleteContinentById(continentId);
        if (response.getStatusCode() == HttpStatus.OK) {
            eTagsMap.remove(continentId);
        }
    }



    private void manageHeaders(long continentId, HttpHeaders headers) {
        if (!headers.containsKey(ETAG_HEADER) || headers.get(ETAG_HEADER) == null || headers.get(ETAG_HEADER).isEmpty()) {
            return;
        }
        String etag = headers.get(ETAG_HEADER).get(0);
        eTagsMap.put(continentId, etag);
    }
}
