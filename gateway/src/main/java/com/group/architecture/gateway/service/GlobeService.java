package com.group.architecture.gateway.service;

import com.group.architecture.gateway.feign.GlobeFeignClient;
import com.group.architecture.gateway.model.GlobeContinent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GlobeService {

    @Autowired
    private RestService restService;

    public List<GlobeContinent> getAllContinents() {
        var continents = restService.getAllContinents();
        return continents;
    }

    public GlobeContinent createContinent(GlobeContinent continent) {
        return restService.createContinent(continent);
    }

    public GlobeContinent getContinentById(long continentId) {
        return restService.getContinentById(continentId);
    }

    public GlobeContinent updateContinent(long continentId, GlobeContinent continent) {
        return restService.updateContinent(continentId, continent);
    }

    public void deleteContinent(long continentId) {
        restService.deleteContinent(continentId);
    }
}
