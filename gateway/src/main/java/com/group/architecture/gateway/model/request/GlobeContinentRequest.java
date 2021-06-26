package com.group.architecture.gateway.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GlobeContinentRequest {

    private String name;

    private List<GlobeCountryRequest> countries = new ArrayList<>();
}
