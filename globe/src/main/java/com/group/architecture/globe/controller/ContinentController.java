package com.group.architecture.globe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.request.ContinentRequest;
import com.group.architecture.globe.model.response.ContinentResponse;
import com.group.architecture.globe.service.ContinentService;
import com.group.architecture.globe.service.cache.CacheService;
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
        var continent = continentService.saveContinent(continentRequest);
        var continentResponse = new ContinentResponse(continent);
        try {
            String eTag = saveResponseToCache(continentResponse);
            addETagHeader(response,eTag);
        } catch (Exception e) {
            log.error(String.format("Failed to save response to cache. Error: %s",e.getMessage()));
        }
        return continentResponse;
    }

    @GetMapping("{id}")
    public ContinentResponse getContinentById(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws NotFoundException {
        String eTag = request.getHeader(ETAG_HEADER);
        if (!Strings.isNullOrEmpty(eTag)) {
            try {
                var continent = cacheService.getContinentByEtag(id, request.getHeader(ETAG_HEADER));
                addETagHeader(response, eTag);
                return continent;
            } catch (Exception e) {
                log.error(String.format("Failed to collect Continent with Id %s from cache. Going to db. Error: %s", id, e.getMessage()),e);
            }
        }

        var continent = continentService.getContinent(id);
        var continentResponse = new ContinentResponse(continent);
        try {
            eTag = saveResponseToCache(continentResponse);
            addETagHeader(response,eTag);
        } catch (Exception e) {
            log.error(String.format("Failed to save response to cache. Error: %s",e.getMessage()),e);
        }

        return continentResponse;
    }


    @PutMapping("{id}")
    public ContinentResponse updateContinent(@PathVariable long id, @RequestBody ContinentRequest request, HttpServletResponse response) throws NotFoundException {
        var continent = continentService.updateContinent(id, request);
        var continentResponse = new ContinentResponse(continent);
        try {
            String eTag = saveResponseToCache(continentResponse);
            addETagHeader(response,eTag);
        } catch (Exception e) {
            log.error(String.format("Failed to save response to cache. Error: %s",e.getMessage()));
        }
        return continentResponse;
    }

    @DeleteMapping("{id}")
    public void deleteContinent(@PathVariable long id) throws NotFoundException {
        continentService.deleteContinent(id);
        cacheService.deleteTag(id);
    }



    private String saveResponseToCache(ContinentResponse continentResponse) throws Exception {
        return cacheService.saveContinentInCache(continentResponse);
    }

    private void addETagHeader(HttpServletResponse response, String etag) {
        response.addHeader(ETAG_HEADER, etag);
    }
}