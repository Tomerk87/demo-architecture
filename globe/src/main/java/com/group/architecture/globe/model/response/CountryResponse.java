package com.group.architecture.globe.model.response;

import com.group.architecture.globe.model.entity.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CountryResponse {

    private long id;

    private String name;

    private String countryCode;

    public CountryResponse(Country country) {
        this.id = country.getId();
        this.name = country.getName();
        this.countryCode = country.getCountryCode();
    }
}
