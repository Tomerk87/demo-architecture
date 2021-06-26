package com.group.architecture.gateway.controller;

import com.group.architecture.gateway.model.request.GlobeContinentRequest;
import com.group.architecture.gateway.model.response.GlobeContinentResponse;
import com.group.architecture.gateway.service.GlobeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("globe/")
public class GlobeController {

    @Autowired
    private GlobeService globeService;

    //TODO: Delete
    @GetMapping("send")
    public String send() {
        return globeService.send();
    }

    @GetMapping("continent/all")
    public List<GlobeContinentResponse> getAllContinents() {
        return globeService.getAllContinents();
    }

    @PostMapping("continent/")
    public GlobeContinentResponse createContinent(@RequestBody GlobeContinentRequest continent) {
        return globeService.createContinent(continent);
    }

    @GetMapping("continent/{id}")
    public GlobeContinentResponse getContinentById(@PathVariable long id) {
        return globeService.getContinentById(id);
    }

    @PutMapping("continent/{id}")
    public GlobeContinentResponse updateContinent(@PathVariable long id, @RequestBody GlobeContinentResponse continent) {
        return globeService.getContinentById(id);
    }

    @DeleteMapping("continent/{id}")
    public void updateContinent(@PathVariable long id) {
        globeService.deleteContinent(id);
    }
}
