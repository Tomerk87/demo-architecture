package com.group.architecture.gateway.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GlobeCountryResponse {

    private Long id;

    private String name;

    private String countryCode;

}
