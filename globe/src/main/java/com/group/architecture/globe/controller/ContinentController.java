package com.group.architecture.globe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.request.ContinentRequest;
import com.group.architecture.globe.model.response.ContinentResponse;
import com.group.architecture.globe.service.ContinentService;
import com.group.architecture.globe.service.cache.CacheService;
import com.group.architecture.globe.utils.MD5Utils;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("continent/")
public class ContinentController {

    @Autowired
    private ContinentService continentService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ObjectMapper mapper;

    private static final String ETAG_HEADER = "eTag";


    @GetMapping("all")
    public List<ContinentResponse> getAllContinents() {
        List<Continent> allContinents = continentService.getAllContinents();
        List<ContinentResponse> responses = new ArrayList<>();
        for (Continent c : allContinents) {
            responses.add(new ContinentResponse(c));
        }
        return responses;
    }

    @PostMapping("")
    public ContinentResponse saveContinent(@RequestBody ContinentRequest continentRequest, HttpServletResponse response) {
        Continent continent = continentService.saveContinent(continentRequest);
        ContinentResponse continentResponse = new ContinentResponse(continent);
        addResponseEtagHeader(response, continentResponse);
        return continentResponse;
    }

    @GetMapping("{id}")
    public ContinentResponse getContinentById(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws NotFoundException {
        if (request.getHeader(ETAG_HEADER) != null) {
            try {
                return cacheService.getContinentByEtag(id, request.getHeader(ETAG_HEADER));
            } catch (Exception e) {
                log.error(String.format("Failed to collect Continent with Id %s from cache. Going to db. Error: %s", id, e.getMessage()),e);
            }
        }

        Continent continent = continentService.getContinent(id);
        ContinentResponse continentResponse = new ContinentResponse(continent);

        addResponseEtagHeader(response, continentResponse);
        return continentResponse;
    }


    @PutMapping("{id}")
    public ContinentResponse updateContinent(@PathVariable long id, @RequestBody ContinentRequest request, HttpServletResponse response) throws NotFoundException {
        Continent continent = continentService.updateContinent(id, request);
        ContinentResponse continentResponse = new ContinentResponse(continent);
        addResponseEtagHeader(response, continentResponse);
        return continentResponse;
    }

    @DeleteMapping("{id}")
    public void deleteContinent(@PathVariable long id) {
        continentService.deleteContinent(id);
        cacheService.deleteTag(id);
    }



    private void addResponseEtagHeader(HttpServletResponse response, ContinentResponse continent) {
        try {
            String json = mapper.writeValueAsString(continent);
            String etag = MD5Utils.digest(json);
            response.addHeader("eTag",etag);
        } catch (Exception e) {
            log.error(String.format("Failed to convert continent to MD5 and add as Header. Error: %s",e.getMessage()),e);
        }
    }
}