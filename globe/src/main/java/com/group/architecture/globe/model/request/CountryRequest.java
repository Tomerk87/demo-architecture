package com.group.architecture.globe.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryRequest {

    private String name;

    private String countryCode;

    private Long continentId;
}
