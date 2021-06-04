package com.group.architecture.globe.service;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.request.ContinentRequest;
import com.group.architecture.globe.repository.ContinentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContinentService {

    @Autowired
    private ContinentRepository continentRepository;

    public Continent saveContinent(ContinentRequest request) {
        var continent = new Continent(request);
        return continentRepository.save(continent);
    }

    public List<Continent> getAllContinents() {
        return continentRepository.findAll();
    }

    public Continent getContinent(long id) throws NotFoundException {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isEmpty()) {
            throw new NotFoundException(String.format("Continent with Id %s is not found",id));
        }
        return optionalContinent.get();
    }

    public Continent updateContinent(long id, ContinentRequest request) throws NotFoundException {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isEmpty()) {
            throw new NotFoundException(String.format("Continent with Id %s is not found",id));
        }
        var continent = optionalContinent.get();
        continent.setName(request.getName());
        continentRepository.save(continent);
        return continent;
    }

    public void deleteContinent(long id) {
        continentRepository.deleteById(id);
    }
}