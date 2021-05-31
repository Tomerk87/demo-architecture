package com.group.architecture.globe.service;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.repository.ContinentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContinentService {

    @Autowired
    private ContinentRepository continentRepository;

    public Continent saveContinent(Continent continent) {
        Continent dbContinent = continentRepository.save(continent);
        return dbContinent;
    }

    public Continent getContinent(long id) throws NotFoundException {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isEmpty()) {
            throw new NotFoundException(String.format("Continent with Id %s is not found",id));
        }
        return optionalContinent.get();
    }
}