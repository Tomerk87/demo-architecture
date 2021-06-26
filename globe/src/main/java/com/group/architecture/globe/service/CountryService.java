package com.group.architecture.globe.service;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.entity.Country;
import com.group.architecture.globe.model.request.CountryRequest;
import com.group.architecture.globe.repository.CountryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private ContinentService continentService;

    @Autowired
    private CountryRepository countryRepository;

    public Country saveCountry(CountryRequest request) throws NotFoundException {
        Continent continent = continentService.getContinent(request.getContinentId());
        Country country = new Country(request);
        country.setContinent(continent);
        countryRepository.save(country);
        return country;
    }

    public Country getContinent(long id) throws NotFoundException {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isEmpty()) {
            throw new NotFoundException(String.format("Country with Id %s is not found",id));
        }
        return optionalCountry.get();
    }

    public Country updateCountry(long id, CountryRequest request) throws NotFoundException {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isEmpty()) {
            throw new NotFoundException(String.format("Country with Id %s is not found",id));
        }
        Country country = optionalCountry.get();
        country.setCountryCode2(request.getCode2());
        country.setName(request.getName());
        return country;
    }

    public void deleteCountry(long id) throws NotFoundException {
        var country = getContinent(id);
        countryRepository.delete(country);
    }
}
