package com.group.architecture.globe.controller;

import com.group.architecture.globe.exception.InvalidDataException;
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
    public CountryResponse createCountry(@RequestBody CountryRequest countryRequest) throws NotFoundException {
        if (countryRequest.getContinentId() == null) {
            throw new InvalidDataException("Unable to create country without continent.");
        }
        Country country = countryService.saveCountry(countryRequest);
        return new CountryResponse(country);
    }

    @GetMapping("{id}")
    public CountryResponse getCountryById(@PathVariable long id) throws NotFoundException {
        Country country =  countryService.getContinent(id);
        return new CountryResponse(country);
    }

    @PutMapping("{id}")
    public CountryResponse updateCountry(@PathVariable long id, @RequestBody CountryRequest request) throws NotFoundException {
        Country country = countryService.updateCountry(id, request);
        return new CountryResponse(country);
    }

    @DeleteMapping("{id}")
    public void deleteCountry(@PathVariable long id) throws NotFoundException {
        countryService.deleteCountry(id);
    }
}
