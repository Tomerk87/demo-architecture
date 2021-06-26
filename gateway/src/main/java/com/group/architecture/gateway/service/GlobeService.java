package com.group.architecture.gateway.service;

import com.group.architecture.gateway.model.request.GlobeContinentRequest;
import com.group.architecture.gateway.model.response.GlobeContinentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobeService {

    @Autowired
    private RestService restService;


    public List<GlobeContinentResponse> getAllContinents() {
        var continents = restService.getAllContinents();
        return continents;
    }

    public GlobeContinentResponse createContinent(GlobeContinentRequest continent) {
        return restService.createContinent(continent);
    }

    public GlobeContinentResponse getContinentById(long continentId) {
        return restService.getContinentById(continentId);
    }

    public GlobeContinentResponse updateContinent(long continentId, GlobeContinentRequest continent) {
        return restService.updateContinent(continentId, continent);
    }

    public void deleteContinent(long continentId) {
        restService.deleteContinent(continentId);
    }
}
