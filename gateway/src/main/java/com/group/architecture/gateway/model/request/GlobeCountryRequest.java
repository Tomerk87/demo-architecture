package com.group.architecture.gateway.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GlobeCountryRequest {

    private String name;

    private String code2;

    private String code3;

    private Long continentId;
}
