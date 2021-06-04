package com.group.architecture.gateway.service;

import com.group.architecture.gateway.model.GlobeContinent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GlobeService {

    public List<GlobeContinent> getAllContinents() {
        return new ArrayList<>();
    }
}
