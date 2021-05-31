package com.group.architecture.globe.controller;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.service.ContinentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("continent/")
public class ContinentController {

    @Autowired
    private ContinentService continentService;


    @PostMapping("")
    public Continent saveContinent(@RequestBody Continent continent) {
        return continentService.saveContinent(continent);
    }

    @GetMapping("{id}")
    public Continent getContinentById(@PathVariable long id) throws NotFoundException {
        return continentService.getContinent(id);
    }
}