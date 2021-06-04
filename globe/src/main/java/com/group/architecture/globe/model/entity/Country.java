package com.group.architecture.globe.model.entity;

import com.group.architecture.globe.model.common.BaseEntity;
import com.group.architecture.globe.model.request.CountryRequest;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Country extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, name = "code")
    private String countryCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "continent_id")
    private Continent continent;

    public Country(CountryRequest request) {
        this.name = request.getName();
        this.countryCode = request.getCountryCode();
    }

    @PreRemove
    private void removeCountryFromContinent() {
        this.continent.getCountries().remove(this);
    }
}
