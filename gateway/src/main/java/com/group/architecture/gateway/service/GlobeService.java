package com.group.architecture.gateway.service;

import com.group.architecture.gateway.feign.GlobeFeignClient;
import com.group.architecture.gateway.model.GlobeContinent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GlobeService {

    private Map<Long, String> eTagsMap = new HashMap<>();

    private static String ETAG_HEADER = "eTag";

    @Autowired
    private GlobeFeignClient globeClient;

    public List<GlobeContinent> getAllContinents() {
        var continents = globeClient.getAllContinents();
        return continents;
    }

    public GlobeContinent getContinentById(long continentId) {
        String etag = eTagsMap.getOrDefault(continentId, null);
        Map<String, String> headers = new HashMap<>();
        headers.put(ETAG_HEADER, etag);
        var response = globeClient.getContinentById(continentId, headers);
        manageHeaders(continentId, response.getHeaders());
        return response.getBody();
    }

    private void manageHeaders(long continentId, HttpHeaders headers) {
        if (!headers.containsKey(ETAG_HEADER) || headers.get(ETAG_HEADER) == null || headers.get(ETAG_HEADER).isEmpty()) {
            return;
        }
        String etag = headers.get(ETAG_HEADER).get(0);
        eTagsMap.put(continentId, etag);
    }
}
