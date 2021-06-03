package com.group.architecture.globe.service;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.request.ContinentRequest;
import com.group.architecture.globe.model.response.ContinentResponse;
import com.group.architecture.globe.repository.ContinentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContinentService {

    @Autowired
    private ContinentRepository continentRepository;

    public ContinentResponse saveContinent(ContinentRequest request) {

        var dbContinent = continentRepository.save(new Continent(request));

        return new ContinentResponse(dbContinent);
    }

    public List<ContinentResponse> getAllContinents() {
        List<ContinentResponse> responses = new ArrayList<>();
        List<Continent> allContinents = continentRepository.findAll();
        for (Continent c : allContinents) {
            responses.add(new ContinentResponse(c));
        }
        return responses;
    }

    public ContinentResponse getContinent(long id) throws NotFoundException {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isEmpty()) {
            throw new NotFoundException(String.format("Continent with Id %s is not found",id));
        }
        var continent = optionalContinent.get();
        return new ContinentResponse(continent);
    }

    public ContinentResponse updateContinent(long id, ContinentRequest request) throws NotFoundException {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isEmpty()) {
            throw new NotFoundException(String.format("Continent with Id %s is not found",id));
        }
        var continent = optionalContinent.get();
        continent.setName(request.getName());
        continentRepository.save(continent);
        return new ContinentResponse(continent);
    }

    public void deleteContinent(long id) {
        continentRepository.deleteById(id);
    }
}