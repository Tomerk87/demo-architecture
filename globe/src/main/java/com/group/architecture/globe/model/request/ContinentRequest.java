package com.group.architecture.globe.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ContinentRequest {

    private String name;

    private Set<CountryRequest> countries;
}
