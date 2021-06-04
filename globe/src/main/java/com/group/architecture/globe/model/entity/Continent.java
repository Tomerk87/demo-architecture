package com.group.architecture.globe.model.entity;

import com.group.architecture.globe.model.common.BaseEntity;
import com.group.architecture.globe.model.request.ContinentRequest;
import com.group.architecture.globe.model.request.CountryRequest;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Continent extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "continent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Country> countries = new ArrayList<>();

    public Continent(ContinentRequest request) {
        this.name = request.getName();
        if (request.getCountries() == null) {
            return;
        }

        for (CountryRequest c : request.getCountries()) {
            var country = new Country(c);
            country.setContinent(this);
            countries.add(country);
        }

    }
}
