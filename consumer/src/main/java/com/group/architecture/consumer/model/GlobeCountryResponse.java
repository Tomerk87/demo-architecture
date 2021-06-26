package com.group.architecture.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GlobeCountryResponse {

    private long id;

    private String name;

    private String code2;

    private String code3;

}
