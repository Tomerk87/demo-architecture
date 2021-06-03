package com.group.architecture.globe.service;

import com.group.architecture.globe.model.entity.Country;
import com.group.architecture.globe.model.request.CountryRequest;
import com.group.architecture.globe.model.response.CountryResponse;
import com.group.architecture.globe.repository.CountryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public CountryResponse saveCountryContinent(CountryRequest request) {
        Country dbCountry = countryRepository.save(new Country(request));
        return new CountryResponse(dbCountry);
    }

    public CountryResponse getContinent(long id) throws NotFoundException {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isEmpty()) {
            throw new NotFoundException(String.format("Country with Id %s is not found",id));
        }
        return new CountryResponse(optionalCountry.get());
    }

    public CountryResponse updateCountry(long id, CountryRequest request) throws NotFoundException {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isEmpty()) {
            throw new NotFoundException(String.format("Country with Id %s is not found",id));
        }
        Country country = optionalCountry.get();
        country.setCountryCode(request.getCountryCode());
        country.setName(request.getName());
        return new CountryResponse(country);
    }

    public void deleteCountry(long id) {
        countryRepository.deleteById(id);
    }
}
