package com.group.architecture.globe.controller;

import com.group.architecture.globe.model.entity.Country;
import com.group.architecture.globe.model.request.CountryRequest;
import com.group.architecture.globe.model.response.CountryResponse;
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
    public CountryResponse createCountry(@RequestBody CountryRequest country) {
        return countryService.saveCountryContinent(country);
    }

    @GetMapping("{id}")
    public CountryResponse getCountryById(@PathVariable long id) throws NotFoundException {
        return countryService.getContinent(id);
    }

    @PutMapping("{id}")
    public CountryResponse updateCountry(@PathVariable long id, @RequestBody CountryRequest request) throws NotFoundException {
        return countryService.updateCountry(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteCountry(@PathVariable long id) {
        countryService.deleteCountry(id);
    }
}
