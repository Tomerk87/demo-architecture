package com.group.architecture.gateway.controller;

import com.group.architecture.gateway.model.GlobeContinent;
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

    @GetMapping("continent/all")
    public List<GlobeContinent> getAllContinents() {
        return globeService.getAllContinents();
    }

    @PostMapping("continent/")
    public GlobeContinent createContinent(@RequestBody GlobeContinent continent) {
        return globeService.createContinent(continent);
    }

    @GetMapping("continent/{id}")
    public GlobeContinent getContinentById(@PathVariable long id) {
        return globeService.getContinentById(id);
    }

    @PutMapping("continent/{id}")
    public GlobeContinent updateContinent(@PathVariable long id, @RequestBody GlobeContinent continent) {
        return globeService.getContinentById(id);
    }

    @DeleteMapping("continent/{id}")
    public void updateContinent(@PathVariable long id) {
        globeService.deleteContinent(id);
    }
}
