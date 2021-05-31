package com.group.architecture.globe.controller;

import com.group.architecture.globe.model.entity.Country;
import com.group.architecture.globe.service.CountryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("country/")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("")
    public Country createCountry(@RequestBody Country country) {
        return countryService.saveCountryContinent(country);
    }

    @GetMapping("{id}")
    public Country getCountryById(@PathVariable long id) throws NotFoundException {
        return countryService.getContinent(id);
    }
}
