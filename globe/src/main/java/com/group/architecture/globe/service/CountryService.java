package com.group.architecture.globe.service;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.entity.Country;
import com.group.architecture.globe.repository.CountryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country saveCountryContinent(Country country) {
        Country dbCountry = countryRepository.save(country);
        return dbCountry;
    }

    public Country getContinent(long id) throws NotFoundException {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isEmpty()) {
            throw new NotFoundException(String.format("Country with Id %s is not found",id));
        }
        return optionalCountry.get();
    }
}
