package com.group.architecture.gateway.controller;

import com.group.architecture.gateway.model.GlobeContinent;
import com.group.architecture.gateway.service.GlobeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("globe/")
public class GlobeController {

    @Autowired
    private GlobeService globeService;

    @GetMapping("")
    public List<GlobeContinent> getAllContinents() {
        return globeService.getAllContinents();
    }
}
