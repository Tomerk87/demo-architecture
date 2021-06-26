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

    private String code2;

    private String code3;

    public CountryResponse(Country country) {
        this.id = country.getId();
        this.name = country.getName();
        this.code2 = country.getCountryCode2();
        this.code3 = country.getCountryCode3();
    }
}
